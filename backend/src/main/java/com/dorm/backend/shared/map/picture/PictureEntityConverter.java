package com.dorm.backend.shared.map.picture;

import com.dorm.backend.shared.data.dto.PictureDTO;
import com.dorm.backend.shared.data.entity.picture.LocalPictureEntity;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import static com.dorm.backend.shared.service.storage.PictureLocalStorage.loadPictureFromFileSystem;
import static java.util.Base64.getMimeEncoder;

public class PictureEntityConverter implements Converter<LocalPictureEntity, PictureDTO> {

    @Override
    public PictureDTO convert(MappingContext<LocalPictureEntity, PictureDTO> context) {
        LocalPictureEntity picture = context.getSource();

        return PictureDTO.builder()
                .name(picture.getPictureName())
                .pictureOrder(picture.getPictureOrder())
                .base64String(new String(getMimeEncoder().encode(loadPictureFromFileSystem(picture))))
                .build();
    }
}
