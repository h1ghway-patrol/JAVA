package coursehub.practice6.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(currency, "currency");
        amount = amount.setScale(Math.max(2, currency.getDefaultFractionDigits()), RoundingMode.HALF_UP);
    }

    public Money add(Money other) { checkCurrency(other); return new Money(amount.add(other.amount), currency); }
    public Money subtract(Money other) { checkCurrency(other); return new Money(amount.subtract(other.amount), currency); }
    public Money percentage(BigDecimal percent, RoundingMode mode) {
        Objects.requireNonNull(percent); Objects.requireNonNull(mode);
        BigDecimal result = amount.multiply(percent).divide(new BigDecimal("100"), Math.max(2, currency.getDefaultFractionDigits()), mode);
        return new Money(result, currency);
    }
    public Money rounded(int scale, RoundingMode mode) { return new Money(amount.setScale(scale, mode), currency); }
    private void checkCurrency(Money other) {
        Objects.requireNonNull(other, "other");
        if (!currency.equals(other.currency)) throw new IllegalArgumentException("Currency mismatch: " + currency + " / " + other.currency);
    }
}
