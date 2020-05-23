package com.dorm.backend.shared.mappper.profile;

import com.dorm.backend.shared.enums.EUserCharacteristic;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class EUserCharacteristicConverter implements Converter<String, EUserCharacteristic> {
    @Override
    public EUserCharacteristic convert(MappingContext<String, EUserCharacteristic> mappingContext) {
        return EUserCharacteristic.getEnum(mappingContext.getSource());
    }
}
