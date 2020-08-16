package com.dorm.backend.room;

import com.dorm.backend.room.dto.RoomDTO;
import com.dorm.backend.room.dto.RoomSearchCriteria;
import com.dorm.backend.room.service.LocalRoomService;
import com.dorm.backend.room.service.RoomService;
import com.dorm.backend.shared.data.dto.RoomPreviewDTO;
import com.dorm.backend.shared.data.enums.Amenity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(LocalRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/amenities")
    public ResponseEntity<List<String>> getAmenities() {
        return ResponseEntity.ok()
                .body(Arrays.stream(Amenity.values()).map(Amenity::toString).collect(Collectors.toList()));
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getCities() {
        return ResponseEntity.ok().body(roomService.getPossibleCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok().body(roomService.getRoom(id));
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

    @PatchMapping("/book")
    public ResponseEntity<Void> bookRoom(@RequestBody Long id) {
        roomService.bookRoom(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/unBook")
    ResponseEntity<Void> unBookRoom(@RequestBody Long id) {
        roomService.unBookRoom(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/booked/{id}")
    public ResponseEntity<Boolean> isBooked(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.isBooked(id));
    }

    @PostMapping("/search")
    public ResponseEntity<List<RoomPreviewDTO>> getRoomsFromCityEmptySearch(@RequestBody RoomSearchCriteria roomSearchCriteria) {
        return ResponseEntity.ok().body(roomService.searchRoom(roomSearchCriteria));
    }
}
