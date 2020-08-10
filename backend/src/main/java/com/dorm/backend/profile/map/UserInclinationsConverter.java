package com.dorm.backend.profile.map;

import com.dorm.backend.shared.data.enums.Inclination;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class UserInclinationsConverter implements Converter<String, Inclination> {
    @Override
    public Inclination convert(MappingContext<String, Inclination> mappingContext) {
        return Inclination.getEnum(mappingContext.getSource());
    }
}
