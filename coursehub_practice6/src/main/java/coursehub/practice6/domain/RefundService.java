package coursehub.practice6.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class RefundService {
    private final RoundingMode roundingMode;
    public RefundService(RoundingMode roundingMode) { this.roundingMode = Objects.requireNonNull(roundingMode); }

    public Money refundForRemainingSessions(Purchase purchase, int completedSessions) {
        Objects.requireNonNull(purchase);
        int total = purchase.offering().totalSessions();
        if (completedSessions < 0 || completedSessions > total) throw new IllegalArgumentException("Invalid completed session count");
        BigDecimal ratio = BigDecimal.valueOf(total - completedSessions).divide(BigDecimal.valueOf(total), 12, roundingMode);
        return new Money(purchase.paid().amount().multiply(ratio).setScale(purchase.paid().amount().scale(), roundingMode), purchase.paid().currency());
    }
}
