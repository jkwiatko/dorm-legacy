package com.dorm.backend.shared.mappper.picture;

import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.profile.dto.ProfilePictureDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Base64;
import java.util.List;

public class ProfilePictureEntityConverter implements Converter<List<Picture>, ProfilePictureDTO> {

    @Override
    public ProfilePictureDTO convert(MappingContext<List<Picture>, ProfilePictureDTO> context) {
        ProfilePictureDTO pictureDTO = new ProfilePictureDTO();
        pictureDTO.setName(context.getSource().get(0).getPictureName());
        pictureDTO.setBase64String(new String(Base64.getMimeEncoder().encode(context.getSource().get(0).getPicture())));
        return pictureDTO;
    }
}
