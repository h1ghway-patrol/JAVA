package coursehub;

public final class Course {
    private final String id;
    private final String code;
    private final int minHours;
    private final int maxHours;
    private int hours;
    public Course(String id, String code, int minHours, int maxHours, int hours) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id обязателен");
        if (minHours < 1 || maxHours < minHours) throw new IllegalArgumentException("Некорректные границы часов");
        this.id = id; this.code = normalizeCode(code); this.minHours = minHours; this.maxHours = maxHours;
        completeHours(hours);
    }
    public static String normalizeCode(String code) {
        if (code == null) throw new IllegalArgumentException("Код курса обязателен");
        String normalized = code.trim().toUpperCase(java.util.Locale.ROOT);
        if (!normalized.matches("[A-Z]{2,6}-[0-9]{3}") ) throw new IllegalArgumentException("Недопустимый код курса");
        return normalized;
    }
    public void completeHours(int newHours) {
        if (newHours < minHours || newHours > maxHours) throw new IllegalArgumentException("Часы должны быть от " + minHours + " до " + maxHours);
        this.hours = newHours;
    }
    public String id() { return id; }
    public String code() { return code; }
    public int hours() { return hours; }
}
