package com.dorm.backend.room.map;

import com.dorm.backend.shared.data.enums.Amenity;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class AmenityConverter implements Converter<String, Amenity> {
    @Override
    public Amenity convert(MappingContext<String, Amenity> mappingContext) {
        return Amenity.getEnum(mappingContext.getSource());
    }
}
