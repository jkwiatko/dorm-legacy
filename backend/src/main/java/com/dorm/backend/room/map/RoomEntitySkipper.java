package com.dorm.backend.room.map;

import com.dorm.backend.room.dto.RoomDTO;
import com.dorm.backend.shared.data.entity.Room;
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
