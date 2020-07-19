package com.dorm.backend.room;

import com.dorm.backend.profile.UserService;
import com.dorm.backend.room.dtos.RoomDTO;
import com.dorm.backend.room.dtos.RoomSearchCriteria;
import com.dorm.backend.shared.data.dtos.PictureDTO;
import com.dorm.backend.shared.data.dtos.ProfilePreviewDTO;
import com.dorm.backend.shared.data.dtos.RoomPreviewDTO;
import com.dorm.backend.shared.data.entities.LocalPicture;
import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.shared.data.entities.Room;
import com.dorm.backend.shared.data.entities.User;
import com.dorm.backend.shared.data.entities.address.City;
import com.dorm.backend.shared.data.enums.EAmenity;
import com.dorm.backend.shared.data.repos.CityRepository;
import com.dorm.backend.shared.data.repos.RoomRepository;
import com.dorm.backend.shared.data.repos.search.RoomSearchRepository;
import com.dorm.backend.shared.error.exc.DuplicatedPictureException;
import com.dorm.backend.shared.error.exc.NoSuchCityException;
import com.dorm.backend.shared.storage.PictureLocalStorage;
import com.dorm.backend.shared.storage.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PictureService pictureService;
    private final RoomSearchRepository roomSearchRepository;
    private final RoomRepository roomRepository;
    private final CityRepository cityRepository;

    public RoomService(
            ModelMapper modelMapper,
            UserService userService,
            PictureService pictureService,
            RoomSearchRepository roomSearchRepository,
            RoomRepository roomRepository,
            CityRepository cityRepository
    ) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.pictureService = pictureService;
        this.roomSearchRepository = roomSearchRepository;
        this.roomRepository = roomRepository;
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
        room.setAmenities(roomDTO.getAmenities()
                .stream()
                .distinct()
                .map(EAmenity::getEnum)
                .collect(Collectors.toList())
        );
        room.setOwner(user);

        List<LocalPicture> roomPictures = pictureService.mapToLocalPictures(roomDTO.getPictures());
        room.setPictures(new ArrayList<>(roomPictures));
        setPictureDetails(room);
        roomRepository.save(room);
        roomPictures.forEach(PictureLocalStorage::savePicture);
    }

    public void editRoom(RoomDTO roomDTO) {
        City city = cityRepository.findByName(roomDTO.getAddress().getCity())
                .orElseThrow(() -> new NoSuchCityException(roomDTO.getAddress().getCity()));
        Room currentRoom = roomRepository.findById(roomDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        if (!roomDTO.getPictures().stream().map(PictureDTO::getName).allMatch(new HashSet<>()::add)) {
            throw new DuplicatedPictureException();
        }

        modelMapper.map(roomDTO, currentRoom);
        currentRoom.getAddress().setCity(city);
        currentRoom.getAmenities().clear();
        currentRoom.getAmenities().addAll(roomDTO.getAmenities()
                .stream()
                .distinct()
                .map(EAmenity::getEnum)
                .collect(Collectors.toList())
        );

        List<Picture> oldPictures = new ArrayList<>(currentRoom.getPictures());
        List<LocalPicture> newPictures = pictureService.mapToLocalPictures(roomDTO.getPictures());
        currentRoom.getPictures().clear();
        currentRoom.getPictures().addAll(newPictures);
        setPictureDetails(currentRoom);

        roomRepository.save(currentRoom);
        newPictures.forEach(PictureLocalStorage::savePicture);
        oldPictures.forEach(PictureLocalStorage::deletePicture);
    }

    public List<RoomPreviewDTO> searchRoom(RoomSearchCriteria roomSearchCriteria) {
        return roomSearchRepository.findRoomUsingCriteria(roomSearchCriteria).stream()
                .map(room -> modelMapper.map(room, RoomPreviewDTO.class))
                .collect(Collectors.toList());
    }

    public RoomDTO getRoom(Long id) {
       return roomRepository.findById(id)
               .map(room -> modelMapper.map(room, RoomDTO.class))
               .orElseThrow(EntityNotFoundException::new);
    }

    public List<String> getCities() {
        return cityRepository.findAll()
                .stream()
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

    private void setPictureDetails(Room room) {
        if (room.getPictures() != null) {
            for (Picture picture : room.getPictures()) {
                picture.setOwner(room.getOwner());
                picture.setOfRoom(room);
            }
        }
    }
}
