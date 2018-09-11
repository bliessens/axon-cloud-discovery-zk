package be.cheops.cloud.axonclouddiscovery.infrastructure;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class XStreamLocalDateConverter extends AbstractSingleValueConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE;

    @Override
    public boolean canConvert(Class type) {
        return type.equals(LocalDate.class);
    }

    @Override
    public LocalDate fromString(String str) {
        return LocalDate.parse(str, FORMATTER);
    }

    @Override
    public String toString(Object obj) {
        final LocalDate localDate = (LocalDate) obj;

        return localDate.format(FORMATTER);
    }
}
