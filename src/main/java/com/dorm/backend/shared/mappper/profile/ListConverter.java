package com.dorm.backend.shared.mappper.profile;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.List;

public class ListConverter implements Converter<List<String>, List<String>> {

    @Override
    //TODO fix
    public List<String> convert(MappingContext<List<String>, List<String>> mappingContext) {
        return mappingContext.getSource();
    }
}
