package coursehub.practice6.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;

public final class PricingService {
    private final RoundingMode roundingMode;
    public PricingService(RoundingMode roundingMode) { this.roundingMode = Objects.requireNonNull(roundingMode); }

    public Money price(Money basePrice, EarlyBirdPolicy policy, Instant purchaseTime) {
        Objects.requireNonNull(basePrice); Objects.requireNonNull(policy); Objects.requireNonNull(purchaseTime);
        if (!policy.appliesAt(purchaseTime)) return basePrice;
        BigDecimal factor = BigDecimal.ONE.subtract(policy.discountPercent().divide(new BigDecimal("100")));
        return new Money(basePrice.amount().multiply(factor).setScale(basePrice.amount().scale(), roundingMode), basePrice.currency());
    }
}
