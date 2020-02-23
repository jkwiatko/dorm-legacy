package com.dorm.backend.shared.mappper.room;

import com.dorm.backend.shared.enums.EAmenity;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class EAmenityConverter implements Converter<String, EAmenity> {
    @Override
    public EAmenity convert(MappingContext<String, EAmenity> mappingContext) {
        return EAmenity.getEnum(mappingContext.getSource());
    }
}
