package com.dorm.backend.room.map;

import com.dorm.backend.room.dtos.RoomDTO;
import com.dorm.backend.shared.data.entities.Room;
import org.modelmapper.PropertyMap;

public class RoomEntitySkipper extends PropertyMap<RoomDTO, Room> {
    @Override
    protected void configure() {
        skip(destination.getPictures());
        skip(destination.getOwner());
        skip(destination.getId());
        skip(destination.getAmenities());
    }
}
