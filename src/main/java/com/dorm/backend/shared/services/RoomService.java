package com.dorm.backend.shared.services;

import com.dorm.backend.profile.dto.PictureDTO;
import com.dorm.backend.room.dto.RoomDTO;
import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.shared.data.entities.Room;
import com.dorm.backend.shared.data.entities.User;
import com.dorm.backend.shared.data.repos.RoomRepository;
import com.dorm.backend.shared.error.exc.FileNameAlreadyTaken;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final ModelMapper modelMapper;
    private final PictureService pictureService;
    private final UserService userService;
    private final RoomRepository roomRepository;

    public RoomService(
            ModelMapper modelMapper,
            PictureService pictureService,
            UserService userService,
            RoomRepository roomRepository
    ) {
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
        this.userService = userService;
        this.roomRepository = roomRepository;
    }

    public void createRoom(RoomDTO roomDTO) {
        User user = userService.getCurrentAuthenticatedUser();
        Room room = modelMapper.map(roomDTO, Room.class);
        room.setOwner(user);

        for (Picture picture : room.getPictures()) {
            picture.setOfRoom(room);
            pictureService.savePicture(user, picture);
        }
        roomRepository.save(room);
    }

    public void editRoom(RoomDTO roomDTO) {
        User user = userService.getCurrentAuthenticatedUser();
        Room room = roomRepository.findById(roomDTO.getId()).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(roomDTO, room);
        room.getPictures().forEach(picture -> {
            picture.setOfRoom(room);
            pictureService.savePicture(user, picture);
        });
//        roomRepository.save(room);
    }

    public RoomDTO getRoom(Long id) {
       Room room = roomRepository.findById(id).orElseThrow(EntityNotFoundException::new);
       for(Picture picture : room.getPictures()) {
           picture.setPicture(pictureService.loadPictureFromFileSystem(picture));
       }
       RoomDTO dto = modelMapper.map(room, RoomDTO.class);
       room.getOwner().getProfilePictures()
               .stream()
               .findFirst()
               .ifPresent(picture -> {
                   picture.setPicture(pictureService.loadPictureFromFileSystem(picture));
                   dto.getOwner().setProfilePicture(modelMapper.map(picture, PictureDTO.class));
               });
       return dto;
    }
}
