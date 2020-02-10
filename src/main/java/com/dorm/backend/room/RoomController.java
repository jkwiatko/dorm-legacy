package com.dorm.backend.room;

import com.dorm.backend.room.dto.RoomDTO;
import com.dorm.backend.shared.services.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createRoom(@RequestBody RoomDTO roomDTO) {
        roomService.createRoom(roomDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit")
        public ResponseEntity<Void> editRoom(@RequestBody RoomDTO roomDTO) {
            roomService.editRoom(roomDTO);
            return ResponseEntity.ok().build();
        }


    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok().body(roomService.getRoom(id));
    }
}
