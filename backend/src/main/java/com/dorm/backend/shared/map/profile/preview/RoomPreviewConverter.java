package com.dorm.backend.shared.map.profile.preview;

import com.dorm.backend.shared.data.dtos.PictureDTO;
import com.dorm.backend.shared.data.dtos.RoomPreviewDTO;
import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.shared.data.entities.Room;
import com.dorm.backend.shared.storage.PictureLocalStorage;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Base64;
import java.util.Comparator;
import java.util.Optional;

public class RoomPreviewConverter implements Converter<Room, RoomPreviewDTO> {

    @Override
    public RoomPreviewDTO convert(MappingContext<Room, RoomPreviewDTO> mappingContext) {
        RoomPreviewDTO roomPreviewDTO = new RoomPreviewDTO();
        Room room = mappingContext.getSource();
        roomPreviewDTO.setId(room.getId());
        roomPreviewDTO.setName(room.getName());
        roomPreviewDTO.setMinDuration(room.getMinDuration());
        roomPreviewDTO.setRoomsNumber(room.getRoomsNumber());
        Optional.ofNullable(room.getAvailableFrom())
                .map(Object::toString)
                .ifPresent(roomPreviewDTO::setAvailableFrom);
        room.getPictures().stream()
                .min(Comparator.comparingInt(Picture::getPictureOrder))
                .ifPresent(img -> convert2Picture(roomPreviewDTO, img));
        return roomPreviewDTO;
    }

    private void convert2Picture(RoomPreviewDTO previewRoomDTO, Picture picture) {
        PictureDTO pictureDTO = new PictureDTO();
        PictureLocalStorage.loadPictureFromFileSystem(picture);
        pictureDTO.setBase64String(new String(Base64.getMimeEncoder().encode(picture.getPicture())));
        pictureDTO.setName(picture.getPictureName());
        pictureDTO.setPictureOrder(picture.getPictureOrder());
        previewRoomDTO.setPicture(pictureDTO);
    }
}
