package coursehub;

import java.util.Optional;

public final class Task12StableText {
    private Task12StableText() { }
    public enum ErrorCode { COURSE_NOT_FOUND, COURSE_FULL, INVALID_COURSE_CODE }
    public static Optional<ErrorCode> parseCode(String raw) {
        try {
            Course.normalizeCode(raw);
            return Optional.empty();
        } catch (IllegalArgumentException expectedInputError) {
            return Optional.of(ErrorCode.INVALID_COURSE_CODE);
        }
    }
}
