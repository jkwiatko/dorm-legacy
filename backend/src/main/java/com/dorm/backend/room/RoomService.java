package com.dorm.backend.room;

import com.dorm.backend.profile.UserService;
import com.dorm.backend.room.dtos.RoomDTO;
import com.dorm.backend.room.dtos.RoomSearchCriteria;
import com.dorm.backend.shared.data.dtos.PictureDTO;
import com.dorm.backend.shared.data.dtos.ProfilePreviewDTO;
import com.dorm.backend.shared.data.dtos.RoomPreviewDTO;
import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.shared.data.entities.Room;
import com.dorm.backend.shared.data.entities.User;
import com.dorm.backend.shared.data.entities.address.Address;
import com.dorm.backend.shared.data.entities.address.City;
import com.dorm.backend.shared.data.repos.AddressRepository;
import com.dorm.backend.shared.data.repos.CityRepository;
import com.dorm.backend.shared.data.repos.RoomRepository;
import com.dorm.backend.shared.data.repos.search.RoomSearchRepository;
import com.dorm.backend.shared.error.exc.DuplicatedPictureException;
import com.dorm.backend.shared.error.exc.NoSuchCityException;
import com.dorm.backend.shared.storage.PictureLocalStorage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomService {

    private final ModelMapper modelMapper;
    private final PictureLocalStorage pictureStorage;
    private final UserService userService;
    private final RoomSearchRepository roomSearchRepository;
    private final RoomRepository roomRepository;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;

    public RoomService(
            ModelMapper modelMapper,
            PictureLocalStorage pictureStorage,
            UserService userService,
            RoomSearchRepository roomSearchRepository,
            RoomRepository roomRepository,
            AddressRepository addressRepository,
            CityRepository cityRepository
    ) {
        this.modelMapper = modelMapper;
        this.pictureStorage = pictureStorage;
        this.userService = userService;
        this.roomSearchRepository = roomSearchRepository;
        this.roomRepository = roomRepository;
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
    }

    public void createRoom(RoomDTO roomDTO) {
        if (!roomDTO.getPictures().stream()
                .map(PictureDTO::getName)
                .allMatch(new HashSet<>()::add)) {
            throw new DuplicatedPictureException();
        }

        User user = userService.getCurrentAuthenticatedUser();
        Room room = modelMapper.map(roomDTO, Room.class);
        room.getAddress().setCity(
                cityRepository.findByName(StringUtils.capitalize(roomDTO.getAddress().getCity().toLowerCase()))
                        .orElseThrow(() -> new NoSuchCityException(roomDTO.getAddress().getCity()))
        );
        room.setPictures(
                roomDTO.getPictures().stream()
                        .map(dto -> modelMapper.map(dto, Picture.class))
                        .collect(Collectors.toList())
        );
        room.setOwner(user);
        setPictureDetails(room);

        room.getPictures().forEach(pictureStorage::savePicture);
        roomRepository.save(room);
    }

    public void editRoom(RoomDTO roomDTO) {
        if (!roomDTO.getPictures().stream()
                .map(PictureDTO::getName)
                .allMatch(new HashSet<>()::add)) {
            throw new DuplicatedPictureException();
        }
        City city = cityRepository.findByName(roomDTO.getAddress().getCity())
                .orElseThrow(() -> new NoSuchCityException(roomDTO.getAddress().getCity()));
        Room currentRoom = roomRepository.findById(roomDTO.getId()).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(roomDTO, currentRoom);
        currentRoom.getAddress().setCity(city);

        List<Picture> newPictures = roomDTO.getPictures()
                .stream()
                .map(dto -> modelMapper.map(dto, Picture.class))
                .collect(Collectors.toList());
        removeOldPictures(currentRoom.getPictures(), newPictures);
        removeNewNotUpdatedPictures(currentRoom.getPictures(), newPictures);
        updateRepositionedOldPictures(currentRoom.getPictures(), newPictures);
        removeOldPicturesToReplace(currentRoom.getPictures(), newPictures);
        currentRoom.getPictures().addAll(newPictures);
        setPictureDetails(currentRoom);
        newPictures.forEach(pictureStorage::savePicture);

        roomRepository.save(currentRoom);
    }

    public List<RoomPreviewDTO> searchRoom(RoomSearchCriteria roomSearchCriteria) {
        return roomSearchRepository.findRoomUsingCriteria(roomSearchCriteria).stream()
                .map(room -> {
                    room.getPictures().forEach(PictureLocalStorage::loadPictureFromFileSystem);
                    room.getOwner().getProfilePictures().forEach(PictureLocalStorage::loadPictureFromFileSystem);
                    return modelMapper.map(room, RoomPreviewDTO.class);
                })
                .collect(Collectors.toList());
    }

    private void removeOldPictures(List<Picture> oldPictures, List<Picture> newPictures) {
        oldPictures
                .removeIf(currentPicture ->
                        newPictures.stream()
                                .noneMatch(newPic -> currentPicture.getPictureName().equals(newPic.getPictureName()))
                );
    }

    private void removeNewNotUpdatedPictures(List<Picture> oldPictures, List<Picture> newPictures) {
        for (Picture picture : oldPictures) {
            newPictures.removeIf(
                    newPic ->
                            picture.getPictureName().equals(newPic.getPictureName()) &&
                                    picture.getPictureOrder().equals(newPic.getPictureOrder())
            );
        }
    }

    private void updateRepositionedOldPictures(List<Picture> oldPictures, List<Picture> newPictures) {
        for (Picture picture : oldPictures) {
            newPictures.stream()
                    .filter(newPic -> picture.getPictureName().equals(newPic.getPictureName()))
                    .findFirst()
                    .ifPresent(newPic -> {
                        picture.setPictureOrder(newPic.getPictureOrder());
                        newPictures.remove(newPic);
                    });
        }
    }

    private void removeOldPicturesToReplace(List<Picture> oldPictures, List<Picture> newPictures) {
        oldPictures
                .removeIf(currentPicture ->
                        newPictures.stream()
                                .anyMatch(newPic -> currentPicture.getPictureOrder().equals(newPic.getPictureOrder())
                                )
                );
    }

    public RoomDTO getRoom(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        room.getPictures().forEach(PictureLocalStorage::loadPictureFromFileSystem);
        room.getOwner().getProfilePictures().forEach(PictureLocalStorage::loadPictureFromFileSystem);
        return modelMapper.map(room, RoomDTO.class);
    }

    private void setPictureDetails(Room room) {
        if (room.getPictures() != null) {
            for (Picture picture : room.getPictures()) {
                picture.setOwner(room.getOwner());
                picture.setOfRoom(room);
            }

        }
    }

    public List<String> getCities() {
        return addressRepository.findAll()
                .stream()
                .map(Address::getCity)
                .filter(Objects::nonNull)
                .map(City::getName)
                .collect(Collectors.toList());
    }

    public void bookRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        User thisUser = userService.getCurrentAuthenticatedUser();

        thisUser.getPossibleRooms().add(room);
        room.getPossibleRoommates().add(thisUser);
        roomRepository.save(room);
    }

    public List<ProfilePreviewDTO> getPossibleRoommates(Long id) {
        return roomRepository.findById(id)
                .map(Room::getPossibleRoommates)
                .orElseThrow(EntityNotFoundException::new)
                .stream()
                .map(user -> modelMapper.map(user, ProfilePreviewDTO.class))
                .collect(Collectors.toList());
    }
}
