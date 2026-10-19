package coursehub.practice6.domain;

import java.util.Locale;
import java.util.Objects;

public final class CourseTitle {
    private final String displayValue;
    private final String searchKey;

    public CourseTitle(String value) {
        Objects.requireNonNull(value, "value");
        String stripped = value.strip().replaceAll("\s+", " ");
        if (stripped.isEmpty()) throw new IllegalArgumentException("Course title must not be blank");
        this.displayValue = stripped;
        this.searchKey = stripped.toLowerCase(Locale.ROOT);
    }
    public String displayValue() { return displayValue; }
    public String searchKey() { return searchKey; }
}
