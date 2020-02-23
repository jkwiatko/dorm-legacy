package com.dorm.backend.room;

import com.dorm.backend.room.dto.CityRoomDTO;
import com.dorm.backend.room.dto.RoomDTO;
import com.dorm.backend.shared.enums.EAmenity;
import com.dorm.backend.shared.services.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/find/{city}")
    public ResponseEntity<CityRoomDTO> getRoomsFromCity(@PathVariable String city) {
        return ResponseEntity.ok().body(roomService.getRoomsFromCity(city));
    }

    @GetMapping("/find/{city}/search")
    public ResponseEntity<CityRoomDTO> getRoomsFromCityEmptySearch(@PathVariable String city) {
        return ResponseEntity.ok().body(roomService.getRoomsFromCity(city));
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

    @GetMapping("/find/{city}/search/{value}")
    public ResponseEntity<CityRoomDTO> getFilteredCities(@PathVariable String city, @PathVariable String value) {
        return ResponseEntity.ok().body(roomService.getRoomsFromCityWithFilter(city, value));
    }
}
