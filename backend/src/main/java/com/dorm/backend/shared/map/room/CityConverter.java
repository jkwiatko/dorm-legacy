package com.dorm.backend.shared.map.room;

import com.dorm.backend.shared.data.entities.address.City;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.util.StringUtils;

public class CityConverter implements Converter<String, City> {



    // ?? should it even work ??? or should it given an error with there is no city like this in db!!!!!
    @Override
    public City convert(MappingContext<String, City> mappingContext) {
        City city = new City();
        city.setName(StringUtils.capitalize(mappingContext.getSource().toLowerCase()));
        return city;
    }
}
