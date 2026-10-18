package coursehub;

import java.util.Optional;

public final class Task07DecisionExamples {
    private Task07DecisionExamples() { }
    public static Optional<String> findCourse(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id обязателен");
        return id.equals("JAVA-101") ? Optional.of("Java Basics") : Optional.empty();
    }
    public static int parseCourseHours(String raw) throws Task06CourseImportException {
        return Task06Importer.parseHours(raw, 1);
    }
    public static Task07Outcome<String> enroll(boolean hasSeat, boolean alreadyEnrolled) {
        if (alreadyEnrolled) return Task07Outcome.failure(Task07Outcome.Error.ALREADY_ENROLLED);
        if (!hasSeat) return Task07Outcome.failure(Task07Outcome.Error.COURSE_FULL);
        return Task07Outcome.success("Зачисление выполнено");
    }
    public static double calculatePrice(int hours, double hourlyRate) {
        if (hours < 0 || hourlyRate < 0) throw new IllegalArgumentException("Часы и ставка не могут быть отрицательными");
        return hours * hourlyRate;
    }
}
