package com.dorm.backend.room;

import com.dorm.backend.room.dtos.RoomDTO;
import com.dorm.backend.room.dtos.RoomSearchCriteria;
import com.dorm.backend.shared.data.enums.EAmenity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private RoomService roomService;

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

    @PostMapping("/search")
    public ResponseEntity<List<RoomDTO>> getRoomsFromCityEmptySearch(@RequestBody RoomSearchCriteria roomSearchCriteria) {
        return ResponseEntity.ok().body(roomService.searchRoom(roomSearchCriteria));
    }

    @GetMapping("/amenities")
    public ResponseEntity<List<String>> getAmenities() {
        return ResponseEntity.ok()
                .body(Arrays.stream(EAmenity.values()).map(EAmenity::toString).collect(Collectors.toList()));
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getCities() {
        return ResponseEntity.ok().body(roomService.getCities());
    }
}
