package com.dorm.webapp.shared.mappper.picture;

import com.dorm.webapp.shared.data.entities.Picture;
import com.dorm.webapp.profile.dto.ProfilePictureDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Base64;

public class ProfilePictureDTOConverter implements Converter<ProfilePictureDTO, Picture> {

    @Override
    public Picture convert(MappingContext<ProfilePictureDTO, Picture> context) {
        Picture picture = new Picture();
        picture.setPictureName(context.getSource().getName());
        picture.setUrl(Picture.produceHashPictureDirectoryFilename(context.getSource().getName()));
        picture.setPicture(Base64.getMimeDecoder().decode(context.getSource().getBase64String().split(";base64,")[1]));
        return picture;
    }
}
