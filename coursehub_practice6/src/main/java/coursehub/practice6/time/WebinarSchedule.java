package coursehub.practice6.time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.LinkedHashMap;

public final class WebinarSchedule {
    private WebinarSchedule() { }

    public static Map<ZoneId, ZonedDateTime> displayInZones(Instant start, ZoneId... zones) {
        if (start == null || zones == null) throw new IllegalArgumentException("Start and zones are required");
        Map<ZoneId, ZonedDateTime> result = new LinkedHashMap<>();
        for (ZoneId zone : zones) result.put(zone, start.atZone(zone));
        return Map.copyOf(result);
    }
}
