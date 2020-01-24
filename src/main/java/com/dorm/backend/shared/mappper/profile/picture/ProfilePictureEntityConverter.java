package com.dorm.backend.shared.mappper.profile.picture;

import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.profile.dto.ProfilePictureDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Base64;

public class ProfilePictureEntityConverter implements Converter<Picture, ProfilePictureDTO> {

    @Override
    public ProfilePictureDTO convert(MappingContext<Picture, ProfilePictureDTO> context) {
        ProfilePictureDTO pictureDTO = new ProfilePictureDTO();
        pictureDTO.setName(context.getSource().getPictureName());
        pictureDTO.setBase64String(new String(Base64.getMimeEncoder().encode(context.getSource().getPicture())));
        return pictureDTO;
    }
}
