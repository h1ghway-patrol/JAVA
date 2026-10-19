package coursehub.practice6.numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public final class DecimalDemo {
    private DecimalDemo() { }

    public static List<BigDecimal> representations() {
        return List.of(new BigDecimal(0.1), BigDecimal.valueOf(0.1), new BigDecimal("0.1"));
    }

    public static BigDecimal totalCourseCost(List<BigDecimal> prices) {
        return prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
    }
}
