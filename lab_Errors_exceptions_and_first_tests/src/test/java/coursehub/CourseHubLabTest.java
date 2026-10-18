package coursehub;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CourseHubLabTest {
    private Course sampleCourse() { return new Course("c-1", "java-101", 10, 40, 20); }

    @Test void task1_validTextBecomesInteger() { assertEquals(12, Task01StackTrace.importHours("12")); }
    @Test void task1_invalidTextKeepsOriginalCause() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> Task01StackTrace.importHours("abc"));
        assertInstanceOf(NumberFormatException.class, error.getCause());
    }

    @Test void task2ClassificationContainsTenCases() { assertEquals(10, Task02ExceptionPolicy.examples().size()); }
    @Test void task2OutOfMemoryIsNotOrdinaryCheckedException() { assertEquals(Task02ExceptionPolicy.Choice.FATAL_OR_NOT_RECOVERABLE, Task02ExceptionPolicy.examples().stream().filter(c -> c.situation().equals("Недостаток памяти")).findFirst().orElseThrow().choice()); }

    @Test void task3MessageDoesNotRevealIds() throws Exception {
        var error = new Task03EnrollmentRejectedException("student-secret", "course-secret", "COURSE_FULL", null);
        assertFalse(error.getMessage().contains("student-secret")); assertFalse(error.toString().contains("course-secret"));
        assertEquals("COURSE_FULL", error.reasonCode());
    }
    @Test void task3CauseIsPreserved() throws Exception {
        IOException cause = new IOException("storage down");
        var wrapped = new Task03EnrollmentRejectedException("s1", "c1", "IMPORT_FAILED", cause);
        assertSame(cause, wrapped.getCause());
    }

    @Test void task4FinallyRunsBeforeReturnCompletes() {
        StringBuilder log = new StringBuilder(); assertEquals("значение из try", Task04FinallyOrder.returnsFromTry(log));
        assertEquals("try; finally; ", log.toString());
    }
    @Test void task4FinallyRunsAfterCatchReturn() {
        StringBuilder log = new StringBuilder(); assertEquals("значение из catch", Task04FinallyOrder.catchesThenFinally(log));
        assertEquals("try; catch; finally; ", log.toString());
    }
    @Test void task4FinallyRunsWhenExceptionEscapes() {
        StringBuilder log = new StringBuilder(); assertThrows(IllegalArgumentException.class, () -> Task04FinallyOrder.throwsAndFinally(log));
        assertEquals("try; finally; ", log.toString());
    }

    @Test void task5ResourceClosesAfterSuccess() throws Exception {
        StringBuilder log = new StringBuilder(); Task05Resources.run(log, false, false);
        assertEquals("open; use; close; ", log.toString());
    }
    @Test void task5CloseFailureIsSuppressedWhenUseAlsoFails() {
        StringBuilder log = new StringBuilder(); IOException primary = assertThrows(IOException.class, () -> Task05Resources.run(log, true, true));
        assertEquals("ошибка use", primary.getMessage()); assertEquals(1, primary.getSuppressed().length);
        assertEquals("ошибка close", primary.getSuppressed()[0].getMessage());
    }

    @Test void task6ParserWrapsFormatFailureWithRecordNumber() {
        Task06CourseImportException error = assertThrows(Task06CourseImportException.class, () -> Task06Importer.parseHours("ten", 7));
        assertEquals(7, error.recordNumber()); assertInstanceOf(NumberFormatException.class, error.getCause());
    }
    @Test void task6ValidHoursAreParsed() throws Exception { assertEquals(25, Task06Importer.parseHours("25", 3)); }

    @Test void task7FindMissingCourseReturnsEmpty() { assertTrue(Task07DecisionExamples.findCourse("UNKNOWN").isEmpty()); }
    @Test void task7EnrollmentReturnsTypedFailure() {
        var result = Task07DecisionExamples.enroll(false, false);
        assertFalse(result.isSuccess()); assertEquals(Task07Outcome.Error.COURSE_FULL, result.error().orElseThrow());
    }
    @Test void task7PriceRejectsNegativeHours() { assertThrows(IllegalArgumentException.class, () -> Task07DecisionExamples.calculatePrice(-1, 10)); }

    @Test void task8CourseCanBeCreatedWithValidHours() { assertEquals(20, sampleCourse().hours()); }
    @Test void task8CompleteHoursChangesValueOnSuccess() {
        Course course = sampleCourse();
        course.completeHours(30);
        assertEquals(30, course.hours());
    }

    @ParameterizedTest(name = "hours={0}: valid={1}")
    @CsvSource({"9,false", "10,true", "40,true", "41,false"})
    void task9HoursBoundaryContract(int hours, boolean valid) {
        Course course = sampleCourse();
        if (valid) { course.completeHours(hours); assertEquals(hours, course.hours()); }
        else { assertThrows(IllegalArgumentException.class, () -> course.completeHours(hours)); assertEquals(20, course.hours()); }
    }

    @ParameterizedTest(name = "'{0}' нормализуется в '{1}'")
    @CsvSource({"java-101,JAVA-101", "' JAVA-101 ',JAVA-101", "PY-222,PY-222"})
    void task10CourseCodeNormalizesInput(String raw, String expected) { assertEquals(expected, new Task10CourseCode(raw).value()); }
    @ParameterizedTest(name = "код '{0}' отклоняется")
    @CsvSource({"JAVA101", "A-12", "'   '"})
    void task10CourseCodeRejectsInvalidInput(String raw) { assertThrows(IllegalArgumentException.class, () -> new Task10CourseCode(raw)); }

    @Test void task11RejectedEnrollmentDoesNotConsumeSeat() {
        Task11EnrollmentState state = new Task11EnrollmentState(); state.close();
        assertThrows(IllegalStateException.class, () -> state.enroll("student-1"));
        assertFalse(state.contains("student-1")); assertEquals(1, state.seatsLeft());
    }
    @Test void task11SuccessfulEnrollmentConsumesExactlyOneSeat() {
        Task11EnrollmentState state = new Task11EnrollmentState(); state.enroll("student-1");
        assertTrue(state.contains("student-1")); assertEquals(0, state.seatsLeft());
    }

    @Test void task12InvalidCodeMapsToStableErrorCode() { assertEquals(Task12StableText.ErrorCode.INVALID_COURSE_CODE, Task12StableText.parseCode("bad").orElseThrow()); }
    @Test void task12ValidCodeHasNoError() { assertTrue(Task12StableText.parseCode("JAVA-101").isEmpty()); }

    @ParameterizedTest(name = "status={0}, seat={1}, prereq={2}, duplicate={3} -> {4}")
    @CsvSource({"OPEN,true,true,false,ACCEPTED", "CLOSED,true,true,false,COURSE_CLOSED", "OPEN,false,true,false,COURSE_FULL", "OPEN,true,false,false,PREREQUISITE_MISSING", "OPEN,true,true,true,ALREADY_APPLIED"})
    void task13DecisionTable(CourseHubStatus status, boolean seat, boolean prereq, boolean duplicate, Task13EnrollmentRules.Decision expected) {
        assertEquals(expected, Task13EnrollmentRules.decide(status.toStatus(), seat, prereq, duplicate));
    }
    enum CourseHubStatus { OPEN, CLOSED; Task13EnrollmentRules.CourseStatus toStatus() { return this == OPEN ? Task13EnrollmentRules.CourseStatus.OPEN : Task13EnrollmentRules.CourseStatus.CLOSED; } }

    @Test void task14MalformedRecordKeepsCloseFailureSuppressed() {
        Reader reader = new Reader() {
            private final StringReader delegate = new StringReader("10\nnot-a-number\n");
            @Override public int read(char[] cbuf, int off, int len) throws IOException { return delegate.read(cbuf, off, len); }
            @Override public void close() throws IOException { delegate.close(); throw new IOException("close failed"); }
        };
        Task14AtomicImporter.ImportFailure failure = assertThrows(Task14AtomicImporter.ImportFailure.class, () -> Task14AtomicImporter.importHours(reader));
        assertEquals(2, failure.processedRecords()); assertInstanceOf(NumberFormatException.class, failure.getCause());
        assertEquals(1, failure.getSuppressed().length); assertEquals("close failed", failure.getSuppressed()[0].getMessage());
    }
    @Test void task14SuccessfulImportPublishesCompleteImmutableResult() throws Exception {
        var result = Task14AtomicImporter.importHours(new StringReader("10\n20\n"));
        assertEquals(List.of(10, 20), result.hours()); assertEquals(2, result.processedRecords());
        assertThrows(UnsupportedOperationException.class, () -> result.hours().add(30));
    }

    @Test void task15MissingCourseMapsToStablePublicCode() {
        CourseHub hub = new CourseHub(); CourseHub.ApiError api = hub.toPublicError(assertThrows(CourseHub.CourseMissingException.class, () -> hub.requireCourse("missing")));
        assertEquals(CourseHub.ErrorCode.COURSE_NOT_FOUND, api.code()); assertEquals("Курс не найден", api.message());
    }
    @Test void task15DuplicateCourseIdIsNotSilentlyOverwritten() {
        CourseHub hub = new CourseHub(); hub.addCourse(sampleCourse());
        assertThrows(IllegalArgumentException.class, () -> hub.addCourse(new Course("c-1", "PY-222", 5, 20, 10)));
    }
    @Test void task15InfrastructureWrapperKeepsCause() {
        IOException cause = new IOException("database unavailable");
        CourseHub.InfrastructureFailure wrapped = CourseHub.infrastructureFailure(cause);
        assertSame(cause, wrapped.getCause());
    }
    @Test void task15EnrollmentRejectsFullCourseWithSafePublicError() {
        CourseHub hub = new CourseHub(); hub.addCourse(sampleCourse());
        var internal = assertThrows(Task03EnrollmentRejectedException.class, () -> hub.enroll("student-secret", "c-1", true, false, true, false));
        var api = hub.toPublicError(internal);
        assertEquals(CourseHub.ErrorCode.COURSE_FULL, api.code()); assertFalse(api.message().contains("student-secret"));
    }
    @Test void task15ProgrammingErrorIsNotSilentlyConvertedInsideUseCase() {
        CourseHub hub = new CourseHub();
        assertThrows(IllegalArgumentException.class, () -> hub.requireCourse(" "));
    }
}
