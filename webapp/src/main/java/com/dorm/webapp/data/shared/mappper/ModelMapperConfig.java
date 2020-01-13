package com.dorm.webapp.data.shared.mappper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    // TODO repair bug with bias on birthDate
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addConverter(new ProfilePictureConverter());
        return mapper;
    }
}
