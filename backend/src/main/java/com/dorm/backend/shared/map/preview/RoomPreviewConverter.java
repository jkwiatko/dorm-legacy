package com.dorm.backend.shared.map.preview;

import com.dorm.backend.shared.data.dto.PictureDTO;
import com.dorm.backend.shared.data.dto.RoomPreviewDTO;
import com.dorm.backend.shared.data.entity.picture.LocalPictureEntity;
import com.dorm.backend.shared.data.entity.Room;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Comparator;
import java.util.Optional;

import static com.dorm.backend.shared.service.storage.PictureLocalStorage.loadPictureFromFileSystem;
import static java.util.Base64.getMimeEncoder;

public class RoomPreviewConverter implements Converter<Room, RoomPreviewDTO> {

    @Override
    public RoomPreviewDTO convert(MappingContext<Room, RoomPreviewDTO> mappingContext) {
        RoomPreviewDTO roomPreviewDTO = new RoomPreviewDTO();
        Room room = mappingContext.getSource();
        roomPreviewDTO.setId(room.getId());
        roomPreviewDTO.setName(room.getName());
        roomPreviewDTO.setMinDuration(room.getMinDuration());
        roomPreviewDTO.setRoomsNumber(room.getRoomsNumber());
        roomPreviewDTO.setMonthlyPrice(room.getMonthlyPrice());
        Optional.ofNullable(room.getAvailableFrom())
                .map(Object::toString)
                .ifPresent(roomPreviewDTO::setAvailableFrom);
        room.getPictures().stream()
                .min(Comparator.comparingInt(LocalPictureEntity::getPictureOrder))
                .ifPresent(img -> roomPreviewDTO.setPicture(convert2Picture(img)));
        return roomPreviewDTO;
    }

    private PictureDTO convert2Picture(LocalPictureEntity picture) {
        return PictureDTO.builder()
                .pictureOrder(picture.getPictureOrder())
                .base64String(new String(getMimeEncoder().encode(loadPictureFromFileSystem(picture))))
                .build();
    }
}
