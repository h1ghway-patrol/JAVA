package coursehub.practice6.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class StudyCalendar {
    private StudyCalendar() { }

    public static List<LocalDate> moduleStarts(LocalDate start, int modules, int daysBetween) {
        if (start == null || modules < 0 || daysBetween < 0) throw new IllegalArgumentException("Invalid calendar input");
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < modules; i++) dates.add(start.plusDays((long) i * daysBetween));
        return List.copyOf(dates);
    }

    public static LocalDate lastWeekday(LocalDate start, int calendarDays) {
        if (start == null || calendarDays <= 0) throw new IllegalArgumentException("calendarDays must be positive");
        LocalDate date = start.plusDays(calendarDays - 1L);
        while (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) date = date.minusDays(1);
        return date;
    }

    public static boolean isLeapYear(LocalDate date) { return date != null && date.isLeapYear(); }
}
