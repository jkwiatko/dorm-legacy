package com.dorm.backend.room.dtos;

import java.util.Date;
import java.util.Optional;

public class RoomSearchCriteria {

    private String roomName;
    private String cityName;
    private Date startingDate;
    private Integer duration;
    private Integer maxPrice;

    public Optional<String> getRoomName() {
        return Optional.ofNullable(roomName);
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Optional<Date> getStartingDate() {
        return Optional.ofNullable(startingDate);
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Optional<Integer> getDuration() {
        return Optional.ofNullable(duration);
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Optional<Integer> getMaxPrice() {
        return Optional.ofNullable(maxPrice);
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }
}
