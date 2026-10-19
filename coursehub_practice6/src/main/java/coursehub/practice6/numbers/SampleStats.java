package coursehub.practice6.numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record SampleStats(int count, int min, int max, BigDecimal mean) {
    public static SampleStats from(List<Integer> values) {
        if (values == null || values.isEmpty()) throw new IllegalArgumentException("Sample must not be empty");
        int min = values.stream().mapToInt(Integer::intValue).min().orElseThrow();
        int max = values.stream().mapToInt(Integer::intValue).max().orElseThrow();
        BigDecimal mean = BigDecimal.valueOf(values.stream().mapToLong(Integer::longValue).sum())
                .divide(BigDecimal.valueOf(values.size()), 2, RoundingMode.HALF_UP);
        return new SampleStats(values.size(), min, max, mean);
    }
}
