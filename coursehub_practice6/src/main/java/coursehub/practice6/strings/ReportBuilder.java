package coursehub.practice6.strings;

import java.util.List;
import java.util.Objects;

public final class ReportBuilder {
    private ReportBuilder() { }

    public static String buildWithPlus(List<String> lines) {
        Objects.requireNonNull(lines, "lines");
        String result = "";
        for (String line : lines) result = result + line + System.lineSeparator();
        return result;
    }

    public static String buildWithBuilder(List<String> lines) {
        Objects.requireNonNull(lines, "lines");
        StringBuilder result = new StringBuilder();
        for (String line : lines) result.append(line).append(System.lineSeparator());
        return result.toString();
    }

    public static Timing compareOnce(List<String> lines) {
        long plusStart = System.nanoTime();
        String plus = buildWithPlus(lines);
        long plusNanos = System.nanoTime() - plusStart;
        long builderStart = System.nanoTime();
        String builder = buildWithBuilder(lines);
        long builderNanos = System.nanoTime() - builderStart;
        if (!plus.equals(builder)) throw new IllegalStateException("Implementations returned different reports");
        return new Timing(plusNanos, builderNanos, plus.length());
    }

    public record Timing(long plusNanos, long builderNanos, int resultLength) { }
}
