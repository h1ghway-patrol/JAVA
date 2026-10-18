package coursehub;

import java.util.Objects;
import java.util.Optional;

public final class Task07Outcome<T> {
    public enum Error { COURSE_FULL, ALREADY_ENROLLED, PREREQUISITE_MISSING }
    private final T value;
    private final Error error;
    private Task07Outcome(T value, Error error) { this.value = value; this.error = error; }
    public static <T> Task07Outcome<T> success(T value) {
        return new Task07Outcome<>(Objects.requireNonNull(value), null);
    }
    public static <T> Task07Outcome<T> failure(Error error) {
        return new Task07Outcome<>(null, Objects.requireNonNull(error));
    }
    public boolean isSuccess() { return error == null; }
    public Optional<T> value() { return Optional.ofNullable(value); }
    public Optional<Error> error() { return Optional.ofNullable(error); }
}
