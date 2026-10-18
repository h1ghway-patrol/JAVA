package coursehub;

public record Task10CourseCode(String value) {
    public Task10CourseCode {
        value = Course.normalizeCode(value);
    }
}
