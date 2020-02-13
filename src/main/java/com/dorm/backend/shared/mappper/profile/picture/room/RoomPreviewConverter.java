package com.dorm.backend.shared.mappper.profile.picture.room;

import com.dorm.backend.profile.dto.PictureDTO;
import com.dorm.backend.profile.dto.PreviewRoomDTO;
import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.shared.data.entities.Room;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Comparator;

public class RoomPreviewConverter implements Converter<Room, PreviewRoomDTO> {

    @Override
    public PreviewRoomDTO convert(MappingContext<Room, PreviewRoomDTO> mappingContext) {
        PreviewRoomDTO previewRoomDTO = new PreviewRoomDTO();
        Room room = mappingContext.getSource();

        previewRoomDTO.setId(room.getId());
        previewRoomDTO.setName(room.getName());
        previewRoomDTO.setAvailableFrom(room.getAvailableFrom().toString());
        previewRoomDTO.setMinDuration(room.getMinDuration());
        previewRoomDTO.setRoomsNumber(room.getRoomsNumber());

        PictureDTO pictureDTO = new PictureDTO();
        pictureDTO.setBase64String();
        pictureDTO.setName();
        pictureDTO.setPictureOrder();
    }
}
