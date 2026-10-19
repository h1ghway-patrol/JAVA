package coursehub.practice6.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public record WebinarSeriesPlan(Instant firstStart, ZoneId organizerZone, Period calendarInterval, Duration sessionLength, int sessionCount) {
    public WebinarSeriesPlan {
        Objects.requireNonNull(firstStart); Objects.requireNonNull(organizerZone);
        Objects.requireNonNull(calendarInterval); Objects.requireNonNull(sessionLength);
        if (sessionCount < 1 || sessionLength.isNegative() || sessionLength.isZero()) throw new IllegalArgumentException("Invalid series parameters");
    }
    public List<ZonedDateTime> starts() {
        ZonedDateTime first = firstStart.atZone(organizerZone);
        java.util.ArrayList<ZonedDateTime> result = new java.util.ArrayList<>();
        for (int i = 0; i < sessionCount; i++) result.add(first.plus(calendarInterval.multipliedBy(i)));
        return List.copyOf(result);
    }
    public LocalDate firstLocalDate() { return firstStart.atZone(organizerZone).toLocalDate(); }
}
