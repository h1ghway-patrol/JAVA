package coursehub.practice6.time;

import org.junit.jupiter.api.Test;
import java.time.*;
import static org.junit.jupiter.api.Assertions.*;

class TimeTest {
    @Test void calendarOperationsReturnNewDatesAndHandleLeapYear() {
        LocalDate start = LocalDate.of(2024, 2, 28);
        assertEquals(LocalDate.of(2024, 2, 29), StudyCalendar.moduleStarts(start, 2, 1).get(1));
        assertTrue(StudyCalendar.isLeapYear(start));
        assertEquals(LocalDate.of(2024, 3, 1), start.plusDays(2));
    }
    @Test void strictParserRejectsImpossibleDateAndTrailingText() {
        assertEquals(LocalDate.of(2024, 2, 29), DateParser.parse("startDate", "29.02.2024"));
        assertThrows(InvalidDateFieldException.class, () -> DateParser.parse("startDate", "29.02.2023"));
        assertThrows(InvalidDateFieldException.class, () -> DateParser.parse("startDate", "29.02.2024 extra"));
    }
    @Test void instantDisplaysDifferentlyByZoneButRepresentsSameMoment() {
        Instant instant = Instant.parse("2026-10-18T12:00:00Z");
        var result = WebinarSchedule.displayInZones(instant, ZoneId.of("UTC"), ZoneId.of("Asia/Tokyo"), ZoneId.of("America/New_York"));
        assertEquals(instant, result.get(ZoneId.of("Asia/Tokyo")).toInstant());
        assertNotEquals(result.get(ZoneId.of("UTC")).getHour(), result.get(ZoneId.of("Asia/Tokyo")).getHour());
    }
    @Test void calendarDayAndTwentyFourHoursCanDifferAcrossDst() {
        ZoneId zone = ZoneId.of("America/New_York");
        ZonedDateTime beforeJump = ZonedDateTime.of(2024, 3, 9, 12, 0, 0, 0, zone);
        assertEquals(Duration.ofHours(23), Duration.between(beforeJump, beforeJump.plus(Period.ofDays(1))));
        assertEquals(Duration.ofHours(24), Duration.between(beforeJump, beforeJump.plus(Duration.ofHours(24))));
    }
    @Test void dstGapIsRejectedAndCanBeShiftedByPolicy() {
        ZoneId zone = ZoneId.of("America/New_York");
        LocalDateTime missing = LocalDateTime.of(2024, 3, 10, 2, 30);
        assertThrows(InvalidLocalTimeException.class, () -> LocalDateTimeResolver.requireValid(missing, zone));
        assertEquals(LocalDateTime.of(2024, 3, 10, 3, 30), LocalDateTimeResolver.shiftPastGap(missing, zone));
    }
}
