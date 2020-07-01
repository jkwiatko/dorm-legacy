package com.dorm.backend.shared.map.profile.picture;

import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.profile.dto.PictureDTO;
import com.dorm.backend.shared.storage.PictureLocalStorage;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Base64;
import java.util.Optional;

public class ProfilePictureDTOConverter implements Converter<PictureDTO, Picture> {

    @Override
    public Picture convert(MappingContext<PictureDTO, Picture> context) {
        Picture picture = Optional.ofNullable(context.getDestination()).orElseGet(Picture::new);
        picture.setPictureName(context.getSource().getName());
        picture.setPictureOrder(context.getSource().getPictureOrder());
        picture.setUrl(PictureLocalStorage.produceHashPictureDirectoryFilename(context.getSource().getName()));
        picture.setPicture(Base64.getMimeDecoder().decode(context.getSource().getBase64String().split(";base64,")[1]));
        return picture;
    }
}
