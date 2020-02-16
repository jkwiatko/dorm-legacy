package com.dorm.backend.shared.mappper.profile.picture.room;

import com.dorm.backend.profile.dto.PictureDTO;
import com.dorm.backend.profile.dto.PreviewRoomDTO;
import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.shared.data.entities.Room;
import com.dorm.backend.shared.services.PictureLocalStorage;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Base64;
import java.util.Comparator;
import java.util.Optional;

public class RoomPreviewConverter implements Converter<Room, PreviewRoomDTO> {

    @Override
    public PreviewRoomDTO convert(MappingContext<Room, PreviewRoomDTO> mappingContext) {
        PreviewRoomDTO previewRoomDTO = new PreviewRoomDTO();
        Room room = mappingContext.getSource();
        previewRoomDTO.setId(room.getId());
        previewRoomDTO.setName(room.getName());
        previewRoomDTO.setMinDuration(room.getMinDuration());
        previewRoomDTO.setRoomsNumber(room.getRoomsNumber());
        Optional.ofNullable(room.getAvailableFrom())
                .map(Object::toString)
                .ifPresent(previewRoomDTO::setAvailableFrom);
        room.getPictures().stream()
                .min(Comparator.comparingInt(Picture::getPictureOrder))
                .ifPresent(img -> convert2Picture(previewRoomDTO, img));
        return previewRoomDTO;
    }

    private void convert2Picture(PreviewRoomDTO previewRoomDTO, Picture picture) {
        PictureDTO pictureDTO = new PictureDTO();
        PictureLocalStorage.loadPictureFromFileSystem(picture);
        pictureDTO.setBase64String(new String(Base64.getMimeEncoder().encode(picture.getPicture())));
        pictureDTO.setName(picture.getPictureName());
        pictureDTO.setPictureOrder(picture.getPictureOrder());
        previewRoomDTO.setPicture(pictureDTO);
    }
}
