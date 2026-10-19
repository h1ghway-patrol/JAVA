package coursehub.practice6.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public record EarlyBirdPolicy(Instant deadline, BigDecimal discountPercent) {
    public EarlyBirdPolicy {
        Objects.requireNonNull(deadline); Objects.requireNonNull(discountPercent);
        if (discountPercent.signum() < 0 || discountPercent.compareTo(new BigDecimal("100")) > 0) throw new IllegalArgumentException("Discount must be from 0 to 100 percent");
    }
    public boolean appliesAt(Instant purchaseTime) { return Objects.requireNonNull(purchaseTime).isBefore(deadline); }
}
