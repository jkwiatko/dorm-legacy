package com.dorm.backend.shared.map.room;

import com.dorm.backend.shared.data.enums.EAmenity;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class EAmenityConverter implements Converter<String, EAmenity> {
    @Override
    public EAmenity convert(MappingContext<String, EAmenity> mappingContext) {
        return EAmenity.getEnum(mappingContext.getSource());
    }
}
