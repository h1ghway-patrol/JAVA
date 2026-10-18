package coursehub;

public final class Task03EnrollmentRejectedException extends Exception {
    private final String studentId;
    private final String courseId;
    private final String reasonCode;

    public Task03EnrollmentRejectedException(String studentId, String courseId, String reasonCode, Throwable cause) {
        super("Зачисление отклонено: " + reasonCode, cause);
        this.studentId = requireNonBlank(studentId, "studentId");
        this.courseId = requireNonBlank(courseId, "courseId");
        this.reasonCode = requireNonBlank(reasonCode, "reasonCode");
    }
    private static String requireNonBlank(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " обязателен");
        return value;
    }
    public String studentId() { return studentId; }
    public String courseId() { return courseId; }
    public String reasonCode() { return reasonCode; }
    @Override public String toString() { return "EnrollmentRejectedException{reasonCode='" + reasonCode + "'}"; }
}
