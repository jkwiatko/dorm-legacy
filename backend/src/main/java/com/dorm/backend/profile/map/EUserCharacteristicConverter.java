package com.dorm.backend.profile.map;

import com.dorm.backend.shared.data.enums.EUserCharacteristic;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class EUserCharacteristicConverter implements Converter<String, EUserCharacteristic> {
    @Override
    public EUserCharacteristic convert(MappingContext<String, EUserCharacteristic> mappingContext) {
        return EUserCharacteristic.getEnum(mappingContext.getSource());
    }
}
