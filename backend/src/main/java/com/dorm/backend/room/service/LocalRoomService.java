package com.dorm.backend.room.service;

import com.dorm.backend.room.dto.RoomDTO;
import com.dorm.backend.room.dto.RoomSearchCriteria;
import com.dorm.backend.shared.data.dto.PictureDTO;
import com.dorm.backend.shared.data.dto.ProfilePreviewDTO;
import com.dorm.backend.shared.data.dto.RoomPreviewDTO;
import com.dorm.backend.shared.data.entity.Room;
import com.dorm.backend.shared.data.entity.User;
import com.dorm.backend.shared.data.entity.address.City;
import com.dorm.backend.shared.data.entity.base.BaseEntity;
import com.dorm.backend.shared.data.entity.picture.LocalPicture;
import com.dorm.backend.shared.data.entity.picture.LocalPictureEntity;
import com.dorm.backend.shared.data.enums.Amenity;
import com.dorm.backend.shared.data.repo.CityRepository;
import com.dorm.backend.shared.data.repo.RoomRepository;
import com.dorm.backend.shared.data.repo.search.RoomSearchRepository;
import com.dorm.backend.shared.error.exc.CannotBookOwnRoomException;
import com.dorm.backend.shared.error.exc.DuplicatedPictureException;
import com.dorm.backend.shared.error.exc.NoSuchCityException;
import com.dorm.backend.shared.service.UserService;
import com.dorm.backend.shared.service.storage.LocalPictureService;
import com.dorm.backend.shared.service.storage.PictureLocalStorage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocalRoomService implements RoomService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final LocalPictureService pictureService;
    private final RoomSearchRepository roomSearchRepository;
    private final RoomRepository roomRepository;
    private final CityRepository cityRepository;

    public LocalRoomService(
            ModelMapper modelMapper,
            UserService userService,
            LocalPictureService pictureService,
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


    @Override
    public RoomDTO getRoom(Long id) {
        return roomRepository.findById(id)
                .map(room -> modelMapper.map(room, RoomDTO.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<String> getPossibleCities() {
        return cityRepository.findAll()
                .stream()
                .map(City::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomPreviewDTO> searchRoom(RoomSearchCriteria roomSearchCriteria) {
        return roomSearchRepository.findRoomUsingCriteria(roomSearchCriteria).stream()
                .map(room -> modelMapper.map(room, RoomPreviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfilePreviewDTO> getPossibleRoommates(Long id) {
        return roomRepository.findById(id)
                .map(Room::getPossibleRoommates)
                .orElseThrow(EntityNotFoundException::new)
                .stream()
                .map(user -> modelMapper.map(user, ProfilePreviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
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
                .map(Amenity::getEnum)
                .collect(Collectors.toList())
        );
        room.setOwner(user);

        List<LocalPicture> roomPictures = pictureService.mapToLocalPictures(roomDTO.getPictures());
        room.setPictures(new ArrayList<>(roomPictures));
        setPictureDetails(room);
        roomRepository.save(room);
        roomPictures.forEach(PictureLocalStorage::savePicture);
    }

    @Override
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
                .map(Amenity::getEnum)
                .collect(Collectors.toList())
        );

        List<LocalPictureEntity> oldPictures = new ArrayList<>(currentRoom.getPictures());
        List<LocalPicture> newPictures = pictureService.mapToLocalPictures(roomDTO.getPictures());
        currentRoom.getPictures().clear();
        currentRoom.getPictures().addAll(newPictures);
        setPictureDetails(currentRoom);

        roomRepository.save(currentRoom);
        newPictures.forEach(PictureLocalStorage::savePicture);
        oldPictures.forEach(PictureLocalStorage::deletePicture);
    }

    @Override
    public void bookRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        User thisUser = Optional.of(userService.getCurrentAuthenticatedUser())
                .filter(user -> !user.getId().equals(room.getOwner().getId()))
                .orElseThrow(CannotBookOwnRoomException::new);

        thisUser.getPossibleRooms().add(room);
        room.getPossibleRoommates().add(thisUser);
        roomRepository.save(room);
    }

    @Override
    public void unBookRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        User thisUser = Optional.of(userService.getCurrentAuthenticatedUser())
                .filter(user -> !user.getId().equals(room.getOwner().getId()))
                .orElseThrow(CannotBookOwnRoomException::new);

        thisUser.getPossibleRooms().remove(room);
        room.getPossibleRoommates().remove(thisUser);
        roomRepository.save(room);
    }

    @Override
    public boolean isBooked(Long id) {
        return roomRepository.getOne(id).getPossibleRoommates()
                .stream()
                .map(BaseEntity::getId)
                .anyMatch(userService.getCurrentAuthenticatedUser().getId()::equals);
    }

    private void setPictureDetails(Room room) {
        if (room.getPictures() != null) {
            for (LocalPictureEntity picture : room.getPictures()) {
                picture.setOwner(room.getOwner());
                picture.setOfRoom(room);
            }
        }
    }
}
