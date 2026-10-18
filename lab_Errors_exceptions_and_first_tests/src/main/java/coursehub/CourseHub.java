package coursehub;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class CourseHub {
    private final Map<String, Course> courses = new HashMap<>();
    public enum ErrorCode { INVALID_COURSE, COURSE_NOT_FOUND, COURSE_CLOSED, COURSE_FULL, ALREADY_ENROLLED, PREREQUISITE_MISSING, IMPORT_FAILED }
    public record ApiError(ErrorCode code, String message) { }
    public static final class InfrastructureFailure extends Exception {
        public InfrastructureFailure(String message, Throwable cause) { super(message, cause); }
    }
    public static final class CourseMissingException extends Exception {
        public CourseMissingException(String id) { super("Курс не найден: " + id); }
    }
    public void addCourse(Course course) {
        Objects.requireNonNull(course, "course");
        if (courses.putIfAbsent(course.id(), course) != null) throw new IllegalArgumentException("Курс с таким ID уже существует");
    }
    public Course requireCourse(String id) throws CourseMissingException {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID курса обязателен");
        Course course = courses.get(id);
        if (course == null) throw new CourseMissingException(id);
        return course;
    }
    public ApiError toPublicError(Exception error) {
        if (error instanceof CourseMissingException) return new ApiError(ErrorCode.COURSE_NOT_FOUND, "Курс не найден");
        if (error instanceof Task06CourseImportException) return new ApiError(ErrorCode.IMPORT_FAILED, "Не удалось импортировать курс");
        if (error instanceof Task03EnrollmentRejectedException rejected) {
            ErrorCode code = switch (rejected.reasonCode()) {
                case "COURSE_FULL" -> ErrorCode.COURSE_FULL;
                case "ALREADY_ENROLLED" -> ErrorCode.ALREADY_ENROLLED;
                case "COURSE_CLOSED" -> ErrorCode.COURSE_CLOSED;
                case "PREREQUISITE_MISSING" -> ErrorCode.PREREQUISITE_MISSING;
                default -> ErrorCode.INVALID_COURSE;
            };
            return new ApiError(code, "Зачисление отклонено");
        }
        return new ApiError(ErrorCode.INVALID_COURSE, "Операция не выполнена");
    }
    public int parseImportedHours(String raw, int record) throws Task06CourseImportException {
        return Task06Importer.parseHours(raw, record);
    }
    public void enroll(String studentId, String courseId, boolean open, boolean hasSeat, boolean prerequisite, boolean alreadyEnrolled)
            throws Task03EnrollmentRejectedException, CourseMissingException {
        requireCourse(courseId);
        if (studentId == null || studentId.isBlank()) throw new IllegalArgumentException("studentId обязателен");
        if (!open) throw new Task03EnrollmentRejectedException(studentId, courseId, "COURSE_CLOSED", null);
        if (alreadyEnrolled) throw new Task03EnrollmentRejectedException(studentId, courseId, "ALREADY_ENROLLED", null);
        if (!hasSeat) throw new Task03EnrollmentRejectedException(studentId, courseId, "COURSE_FULL", null);
        if (!prerequisite) throw new Task03EnrollmentRejectedException(studentId, courseId, "PREREQUISITE_MISSING", null);
    }
    public static InfrastructureFailure infrastructureFailure(IOException cause) {
        return new InfrastructureFailure("Ошибка инфраструктуры CourseHub", cause);
    }
}
