package com.dorm.webapp.data.shared.mappper;

import com.dorm.webapp.data.enums.EGender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class GenderConverter implements Converter<String, EGender> {
    protected final Log logger = LogFactory.getLog(this.getClass());


    @Override
    public EGender convert(MappingContext<String, EGender> mappingContext) {
        logger.error("dupa");
        if(mappingContext.getSource().matches("^[01]")) {
            return Integer.parseInt(mappingContext.getSource()) > 1 ? EGender.FEMALE : EGender.MALE;
        }
        return null;
    }
}
