package com.dorm.backend.room;

import com.dorm.backend.room.dto.RoomDTO;
import com.dorm.backend.shared.services.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

    RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<Void> addRoom(@RequestBody RoomDTO roomDTO) {
        roomService.addRoom(roomDTO);
        return ResponseEntity.ok().build();
    }
}
