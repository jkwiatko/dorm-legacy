package com.dorm.webapp.data.shared.mappper;

import com.dorm.webapp.data.entity.Picture;
import com.dorm.webapp.profile.ProfilePictureDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.io.File;
import java.util.Base64;

public class ProfilePictureConverter implements Converter<ProfilePictureDTO, Picture> {

    @Override
    public Picture convert(MappingContext<ProfilePictureDTO, Picture> context) {
        Picture picture = new Picture();
        picture.setUrl(produceHashPictureDirectoryFilename(context.getSource().getName()));
        picture.setPictureName(context.getSource().getName());
        picture.setPicture(Base64.getMimeDecoder().decode(context.getSource().getBase64String().split(";base64,")[1]));
        return picture;
    }

    private String produceHashPictureDirectoryFilename(String fileName) {
        int hash = fileName.hashCode();
        int mask = 255;
        int firstDir = hash & mask;
        int secondDir = (hash >> 8) & mask;
        return File.separator +
                String.format("%03d", firstDir) +
                File.separator +
                String.format("%03d", secondDir) +
                File.separator;
    }
}
