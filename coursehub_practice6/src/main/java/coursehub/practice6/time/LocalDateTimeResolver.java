package coursehub.practice6.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneOffsetTransition;
import java.util.List;

public final class LocalDateTimeResolver {
    private LocalDateTimeResolver() { }

    public static ZonedDateTime requireValid(LocalDateTime local, ZoneId zone) {
        if (local == null || zone == null) throw new IllegalArgumentException("Local date-time and zone are required");
        ZoneRules rules = zone.getRules();
        List<java.time.ZoneOffset> offsets = rules.getValidOffsets(local);
        if (offsets.isEmpty()) throw new InvalidLocalTimeException(local, zone);
        return ZonedDateTime.ofLocal(local, zone, offsets.get(0));
    }

    public static LocalDateTime shiftPastGap(LocalDateTime local, ZoneId zone) {
        ZoneOffsetTransition transition = zone.getRules().getTransition(local);
        if (transition == null || !transition.isGap()) return local;
        return local.plusSeconds(transition.getDuration().getSeconds());
    }
}
