package be.cheops.cloud.axonclouddiscovery.infrastructure;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class XStreamLocalDateTimeConverter extends AbstractSingleValueConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public boolean canConvert(Class type) {
        return type.equals(LocalDateTime.class);
    }

    @Override
    public LocalDateTime fromString(String str) {
        return LocalDateTime.parse(str, FORMATTER);
    }

    @Override
    public String toString(Object obj) {
        final LocalDateTime localDateTime = (LocalDateTime) obj;

        return localDateTime.format(FORMATTER);
    }
}
