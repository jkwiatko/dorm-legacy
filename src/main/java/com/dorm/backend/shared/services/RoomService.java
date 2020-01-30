package com.dorm.backend.shared.services;

import com.dorm.backend.room.dto.RoomDTO;
import com.dorm.backend.shared.data.entities.Room;
import com.dorm.backend.shared.data.repos.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final ModelMapper modelMapper;
    private final PictureService pictureService;
    private final RoomRepository roomRepository;

    public RoomService(ModelMapper modelMapper, PictureService pictureService, RoomRepository roomRepository) {
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
        this.roomRepository = roomRepository;
    }


    public void addRoom(RoomDTO roomDTO) {
        Room room = modelMapper.map(roomDTO, Room.class);
        room.getPictures().forEach(pictureService::addRoomPicture);
        roomRepository.save(room);
    }
}
