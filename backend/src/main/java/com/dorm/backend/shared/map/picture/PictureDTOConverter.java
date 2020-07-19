package com.dorm.backend.shared.map.picture;

import com.dorm.backend.shared.data.dtos.PictureDTO;
import com.dorm.backend.shared.data.entities.LocalPicture;
import com.dorm.backend.shared.storage.PictureLocalStorage;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Base64;
import java.util.Optional;

public class PictureDTOConverter implements Converter<PictureDTO, LocalPicture> {

    @Override
    public LocalPicture convert(MappingContext<PictureDTO, LocalPicture> context) {
        LocalPicture picture = Optional.ofNullable(context.getDestination()).orElseGet(LocalPicture::new);
        PictureDTO dto = context.getSource();

        picture.setPictureOrder(dto.getPictureOrder());
        picture.setUrl(PictureLocalStorage.produceHashPictureDirectoryFilename(dto.getBase64String().substring(0,255)));
        picture.setPicture(Base64.getMimeDecoder().decode(dto.getBase64String().split(";base64,")[1]));
        return picture;
    }
}
