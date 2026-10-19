package coursehub.practice6.domain;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

public record CourseOffering(String id, Instant webinarStart, ZoneId organizerZone, int totalSessions) {
    public CourseOffering {
        Objects.requireNonNull(id); Objects.requireNonNull(webinarStart); Objects.requireNonNull(organizerZone);
        if (id.isBlank() || totalSessions < 1) throw new IllegalArgumentException("Invalid offering");
    }
}
