package coursehub;

public final class Task13EnrollmentRules {
    private Task13EnrollmentRules() { }
    public enum CourseStatus { OPEN, CLOSED }
    public enum Decision { ACCEPTED, COURSE_CLOSED, COURSE_FULL, PREREQUISITE_MISSING, ALREADY_APPLIED }
    public static Decision decide(CourseStatus status, boolean hasSeat, boolean prerequisite, boolean alreadyApplied) {
        if (status == CourseStatus.CLOSED) return Decision.COURSE_CLOSED;
        if (alreadyApplied) return Decision.ALREADY_APPLIED;
        if (!hasSeat) return Decision.COURSE_FULL;
        if (!prerequisite) return Decision.PREREQUISITE_MISSING;
        return Decision.ACCEPTED;
    }
}
