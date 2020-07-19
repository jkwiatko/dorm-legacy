package com.dorm.backend.shared.map;

import com.dorm.backend.profile.map.EUserCharacteristicConverter;
import com.dorm.backend.profile.map.UserEntitySkipper;
import com.dorm.backend.room.map.EAmenityConverter;
import com.dorm.backend.room.map.RoomEntitySkipper;
import com.dorm.backend.shared.map.picture.PictureDTOConverter;
import com.dorm.backend.shared.map.picture.PictureEntityConverter;
import com.dorm.backend.shared.map.preview.RoomPreviewConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addConverter(new PictureDTOConverter());
        mapper.addConverter(new PictureEntityConverter());
        mapper.addConverter(new RoomPreviewConverter());
        mapper.addConverter(new EUserCharacteristicConverter());
        mapper.addConverter(new EAmenityConverter());
        mapper.addMappings(new RoomEntitySkipper());
        mapper.addMappings(new UserEntitySkipper());
        return mapper;
    }
}
