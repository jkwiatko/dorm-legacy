package com.dorm.webapp.data.shared.mappper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public Date convert(MappingContext<String, Date> mappingContext) {
        Date date = null;
        try {
             date = (new SimpleDateFormat()).parse(mappingContext.getSource());
        } catch (ParseException e) {
            logger.error(String.format("Could not parse date '%s' while converting from dto to entity", mappingContext.getSource()), e);
        }

        return date;
    }
}
