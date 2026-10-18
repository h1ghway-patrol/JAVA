package coursehub;

public final class Task01StackTrace {
    private Task01StackTrace() { }

    public static int importHours(String raw) {
        return controller(raw);
    }
    private static int controller(String raw) {
        return service(raw);
    }
    private static int service(String raw) {
        return parser(raw);
    }
    private static int parser(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Количество часов не задано");
        }
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException cause) {
            throw new IllegalArgumentException("Количество часов должно быть целым числом", cause);
        }
    }
}
