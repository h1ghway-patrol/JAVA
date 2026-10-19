package coursehub.practice6.time;

public class InvalidDateFieldException extends IllegalArgumentException {
    private final String field;
    private final String originalValue;

    public InvalidDateFieldException(String field, String originalValue, Throwable cause) {
        super("Invalid " + field + ": '" + originalValue + "'", cause);
        this.field = field;
        this.originalValue = originalValue;
    }
    public String field() { return field; }
    public String originalValue() { return originalValue; }
}
