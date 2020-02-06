package com.dorm.backend.shared.mappper.room;

import com.dorm.backend.shared.enums.EAmenities;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.ArrayList;
import java.util.List;

public class AmenitiesConverter implements Converter<List<String>, List<EAmenities>> {
    @Override
    public List<EAmenities> convert(MappingContext<List<String>, List<EAmenities>> mappingContext) {
        List<EAmenities> amenities = new ArrayList<>();
        mappingContext.getSource().forEach(amenity -> amenities.add(EAmenities.valueOf(amenity)));
        return amenities;
    }
}
