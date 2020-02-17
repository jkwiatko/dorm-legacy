package com.dorm.backend.shared.services;

import com.dorm.backend.profile.dto.PictureDTO;
import com.dorm.backend.profile.dto.PreviewRoomDTO;
import com.dorm.backend.room.dto.CityRoomDTO;
import com.dorm.backend.room.dto.RoomDTO;
import com.dorm.backend.shared.data.entities.Address;
import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.shared.data.entities.Room;
import com.dorm.backend.shared.data.entities.User;
import com.dorm.backend.shared.data.repos.AddressRepository;
import com.dorm.backend.shared.data.repos.RoomRepository;
import com.dorm.backend.shared.error.exc.DuplicatedPictureException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final ModelMapper modelMapper;
    private final PictureLocalStorage pictureStorage;
    private final UserService userService;
    private final RoomRepository roomRepository;
    private final AddressRepository addressRepository;

    public RoomService(
            ModelMapper modelMapper,
            PictureLocalStorage pictureStorage,
            UserService userService,
            RoomRepository roomRepository,
            AddressRepository addressRepository
    ) {
        this.modelMapper = modelMapper;
        this.pictureStorage = pictureStorage;
        this.userService = userService;
        this.roomRepository = roomRepository;
        this.addressRepository = addressRepository;
    }

    public void createRoom(RoomDTO roomDTO) {
        if(!roomDTO.getPictures().stream()
                .map(PictureDTO::getName)
                .allMatch(new HashSet<>()::add)) {
            throw new DuplicatedPictureException();
        }
        User user = userService.getCurrentAuthenticatedUser();
        Room room = modelMapper.map(roomDTO, Room.class);
        room.setPictures(roomDTO.getPictures().stream()
                .map(dto -> modelMapper.map(dto, Picture.class))
                .collect(Collectors.toList()));
        room.setOwner(user);
        setPictureDetails(room);

        room.getPictures().forEach(pictureStorage::savePicture);
        roomRepository.save(room);
    }

    public void editRoom(RoomDTO roomDTO) {
        if(!roomDTO.getPictures().stream()
                .map(PictureDTO::getName)
                .allMatch(new HashSet<>()::add)) {
            throw new DuplicatedPictureException();
        }
        Room currentRoom = roomRepository.findById(roomDTO.getId()).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(roomDTO, currentRoom);

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

    private void removeOldPictures(List<Picture> oldPictures, List<Picture> newPictures){
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
        if(room.getPictures() != null) {
            for (Picture picture : room.getPictures()) {
                picture.setOwner(room.getOwner());
                picture.setOfRoom(room);
            }

        }
    }

    public CityRoomDTO getRoomsFromCity(String city) {
        List<Room> rooms = roomRepository.findAllByCity(city);
        CityRoomDTO cityRoomDTO = new CityRoomDTO();
        cityRoomDTO.setRooms(
                rooms.stream()
                        .map(room -> modelMapper.map(room, PreviewRoomDTO.class))
                        .collect(Collectors.toList())
        );
        cityRoomDTO.setCityName(city);
        return cityRoomDTO;
    }

    public List<String> getCities() {
        return addressRepository.findAll().stream().map(Address::getCity).collect(Collectors.toList());
    }

    public CityRoomDTO getRoomsFromCityWithFilter(String city, String value) {
        List<Room> rooms = roomRepository.findAllByCity(city);
        CityRoomDTO cityRoomDTO = new CityRoomDTO();
        cityRoomDTO.setRooms(
                rooms.stream()
                .filter(room -> room.getName().toLowerCase().startsWith(value.toLowerCase()))
                .map(room -> modelMapper.map(room, PreviewRoomDTO.class))
                .collect(Collectors.toList())
        );
        cityRoomDTO.setCityName(city);
        return cityRoomDTO;
    }
}