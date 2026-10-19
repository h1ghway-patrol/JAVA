package coursehub.practice6.strings;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record CourseCode(String prefix, int number) {
    private static final Pattern FORMAT = Pattern.compile("([A-Z]{2,8})-([0-9]{3})");

    public CourseCode {
        Objects.requireNonNull(prefix, "prefix");
        if (!prefix.matches("[A-Z]{2,8}") || number < 0 || number > 999)
            throw new IllegalArgumentException("Invalid course code components");
    }

    public static CourseCode parse(String value) {
        Objects.requireNonNull(value, "value");
        Matcher matcher = FORMAT.matcher(value);
        if (!matcher.matches()) throw new IllegalArgumentException("Invalid course code: " + value);
        return new CourseCode(matcher.group(1), Integer.parseInt(matcher.group(2)));
    }

    @Override public String toString() { return prefix + "-" + String.format("%03d", number); }
}
