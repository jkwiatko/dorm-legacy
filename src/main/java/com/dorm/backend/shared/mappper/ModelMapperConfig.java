package com.dorm.backend.shared.mappper;

import com.dorm.backend.shared.mappper.profile.picture.ProfilePictureDTOConverter;
import com.dorm.backend.shared.mappper.profile.picture.ProfilePictureEntityConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    // TODO repair bug with bias on birthDate
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addConverter(new ProfilePictureDTOConverter());
        mapper.addConverter(new ProfilePictureEntityConverter());
        return mapper;
    }
}
