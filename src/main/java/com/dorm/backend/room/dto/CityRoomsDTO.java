package com.dorm.backend.room.dto;

import com.dorm.backend.profile.dto.PreviewRoomDTO;

import java.util.List;

public class CityRoomsDTO {

    private String cityName;
    private List<PreviewRoomDTO> rooms;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<PreviewRoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<PreviewRoomDTO> rooms) {
        this.rooms = rooms;
    }
}
