package com.dorm.backend.profile.map;

import com.dorm.backend.shared.data.enums.UserCharacteristic;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class UserCharacteristicConverter implements Converter<String, UserCharacteristic> {
    @Override
    public UserCharacteristic convert(MappingContext<String, UserCharacteristic> mappingContext) {
        return UserCharacteristic.getEnum(mappingContext.getSource());
    }
}
