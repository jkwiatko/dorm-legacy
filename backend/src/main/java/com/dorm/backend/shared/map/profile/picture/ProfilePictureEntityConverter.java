package com.dorm.backend.shared.map.profile.picture;

import com.dorm.backend.shared.data.dtos.PictureDTO;
import com.dorm.backend.shared.data.entities.Picture;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import static com.dorm.backend.shared.storage.PictureLocalStorage.loadPictureFromFileSystem;
import static java.util.Base64.getMimeEncoder;

public class ProfilePictureEntityConverter implements Converter<Picture, PictureDTO> {

    @Override
    public PictureDTO convert(MappingContext<Picture, PictureDTO> context) {
        Picture picture = context.getSource();

        return PictureDTO.builder()
                .name(picture.getPictureName())
                .pictureOrder(picture.getPictureOrder())
                .base64String(new String(getMimeEncoder().encode(loadPictureFromFileSystem(picture))))
                .build();
    }
}
