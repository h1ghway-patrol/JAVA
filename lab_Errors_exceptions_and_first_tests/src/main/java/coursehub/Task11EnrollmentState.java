package coursehub;

import java.util.HashSet;
import java.util.Set;

public final class Task11EnrollmentState {
    private final Set<String> enrolled = new HashSet<>();
    private boolean open = true;
    private int seats = 1;
    public void close() { open = false; }
    public void enroll(String studentId) {
        if (studentId == null || studentId.isBlank()) throw new IllegalArgumentException("studentId обязателен");
        if (!open) throw new IllegalStateException("COURSE_CLOSED");
        if (enrolled.contains(studentId)) throw new IllegalStateException("ALREADY_ENROLLED");
        if (seats <= 0) throw new IllegalStateException("COURSE_FULL");
        enrolled.add(studentId);
        seats--;
    }
    public boolean contains(String studentId) { return enrolled.contains(studentId); }
    public int seatsLeft() { return seats; }
}
