package coursehub.practice6.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Locale;

public final class DateParser {
    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd.MM.uuuu").toFormatter(Locale.ROOT).withResolverStyle(ResolverStyle.STRICT);
    private DateParser() { }

    public static LocalDate parse(String field, String value) {
        try {
            if (value == null) throw new IllegalArgumentException("value is null");
            return LocalDate.parse(value, FORMATTER);
        } catch (RuntimeException exception) {
            if (exception instanceof InvalidDateFieldException invalid) throw invalid;
            throw new InvalidDateFieldException(field, value, exception);
        }
    }
}
