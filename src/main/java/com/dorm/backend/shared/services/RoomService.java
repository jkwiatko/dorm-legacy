package com.dorm.backend.shared.services;

import com.dorm.backend.profile.dto.PictureDTO;
import com.dorm.backend.room.dto.RoomDTO;
import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.shared.data.entities.Room;
import com.dorm.backend.shared.data.entities.User;
import com.dorm.backend.shared.data.repos.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final ModelMapper modelMapper;
    private final PictureLocalStorage pictureStorage;
    private final UserService userService;
    private final RoomRepository roomRepository;

    public RoomService(
            ModelMapper modelMapper,
            PictureLocalStorage pictureStorage,
            UserService userService,
            RoomRepository roomRepository
    ) {
        this.modelMapper = modelMapper;
        this.pictureStorage = pictureStorage;
        this.userService = userService;
        this.roomRepository = roomRepository;
    }

    public void createRoom(RoomDTO roomDTO) {
        User user = userService.getCurrentAuthenticatedUser();
        Room room = modelMapper.map(roomDTO, Room.class);
        room.setOwner(user);
        setPictureDetails(room);

        room.getPictures().forEach(pictureStorage::savePicture);
        roomRepository.save(room);
    }

    public void editRoom(RoomDTO roomDTO) {
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
        for (Picture picture : room.getPictures()) {
            picture.setPicture(pictureStorage.loadPictureFromFileSystem(picture));
        }
        RoomDTO dto = modelMapper.map(room, RoomDTO.class);
        room.getOwner().getProfilePictures()
                .stream()
                .findFirst()
                .ifPresent(picture -> {
                    picture.setPicture(pictureStorage.loadPictureFromFileSystem(picture));
                    dto.getOwner().setProfilePicture(modelMapper.map(picture, PictureDTO.class));
                });
        return dto;
    }

    private void setPictureDetails(Room room) {
        for (Picture picture : room.getPictures()) {
            picture.setOwner(room.getOwner());
            picture.setOfRoom(room);
        }
    }
}