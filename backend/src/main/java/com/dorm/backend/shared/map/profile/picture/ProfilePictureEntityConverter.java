package com.dorm.backend.shared.map.profile.picture;

import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.profile.dto.PictureDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Base64;

public class ProfilePictureEntityConverter implements Converter<Picture, PictureDTO> {

    @Override
    public PictureDTO convert(MappingContext<Picture, PictureDTO> context) {
        PictureDTO pictureDTO = new PictureDTO();
        pictureDTO.setName(context.getSource().getPictureName());
        pictureDTO.setPictureOrder(context.getSource().getPictureOrder());
        pictureDTO.setBase64String(new String(Base64.getMimeEncoder().encode(context.getSource().getPicture())));
        return pictureDTO;
    }
}
