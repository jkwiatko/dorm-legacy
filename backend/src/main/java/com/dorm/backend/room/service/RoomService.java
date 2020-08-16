package com.dorm.backend.room.service;

import com.dorm.backend.room.dto.RoomDTO;
import com.dorm.backend.room.dto.RoomSearchCriteria;
import com.dorm.backend.shared.data.dto.RoomPreviewDTO;

import java.util.List;

public interface RoomService {

    RoomDTO getRoom(Long id);

    List<String> getPossibleCities();

    List<RoomPreviewDTO> searchRoom(RoomSearchCriteria roomSearchCriteria);

    void createRoom(RoomDTO dto);

    void editRoom(RoomDTO dto);

    void bookRoom(Long id);

    void unBookRoom(Long id);

    boolean isBooked(Long id);
}
