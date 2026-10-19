package coursehub.practice6.numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DiscountAllocator {
    private DiscountAllocator() { }

    public static List<BigDecimal> allocate(List<BigDecimal> prices, BigDecimal discount, int scale, RoundingMode mode) {
        Objects.requireNonNull(prices, "prices");
        Objects.requireNonNull(discount, "discount");
        Objects.requireNonNull(mode, "mode");
        if (prices.isEmpty()) {
            if (discount.signum() != 0) throw new IllegalArgumentException("Cannot allocate discount to empty list");
            return List.of();
        }
        BigDecimal total = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        if (discount.signum() < 0 || discount.compareTo(total) > 0) throw new IllegalArgumentException("Invalid discount");
        List<BigDecimal> result = new ArrayList<>();
        BigDecimal assigned = BigDecimal.ZERO;
        for (int i = 0; i < prices.size(); i++) {
            BigDecimal share;
            if (i == prices.size() - 1) {
                share = discount.subtract(assigned).setScale(scale, mode);
            } else if (total.signum() == 0) {
                share = BigDecimal.ZERO.setScale(scale, mode);
            } else {
                share = discount.multiply(prices.get(i)).divide(total, scale, mode);
                assigned = assigned.add(share);
            }
            result.add(prices.get(i).subtract(share).setScale(scale, mode));
        }
        return List.copyOf(result);
    }
}
