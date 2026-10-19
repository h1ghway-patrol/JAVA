package coursehub.practice6.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.Currency;
import static org.junit.jupiter.api.Assertions.*;

class DomainTest {
    private final Currency usd = Currency.getInstance("USD");
    @Test void titleKeepsDisplayAndNormalizesSearchKey() {
        CourseTitle title = new CourseTitle("  Java   Basics ");
        assertEquals("Java Basics", title.displayValue());
        assertEquals("java basics", title.searchKey());
        assertEquals(title.searchKey(), new CourseTitle("JAVA BASICS").searchKey());
    }
    @Test void moneyAddsOnlySameCurrencyAndUsesValueEquality() {
        Money a = new Money(new BigDecimal("10"), usd);
        Money b = new Money(new BigDecimal("2.00"), usd);
        assertEquals(new Money(new BigDecimal("12.00"), usd), a.add(b));
        assertThrows(IllegalArgumentException.class, () -> a.add(new Money(new BigDecimal("1"), Currency.getInstance("EUR"))));
    }
    @Test void earlyBirdDeadlineIsExclusive() {
        EarlyBirdPolicy policy = new EarlyBirdPolicy(Instant.parse("2026-11-01T00:00:00Z"), new BigDecimal("10"));
        PricingService pricing = new PricingService(RoundingMode.HALF_UP);
        Money base = new Money(new BigDecimal("100.00"), usd);
        assertEquals(new Money(new BigDecimal("90.00"), usd), pricing.price(base, policy, Instant.parse("2026-10-31T23:59:59Z")));
        assertEquals(base, pricing.price(base, policy, Instant.parse("2026-11-01T00:00:00Z")));
    }
    @Test void refundIsProportionalToRemainingSessions() {
        CourseOffering offering = new CourseOffering("WEB-1", Instant.parse("2026-12-01T12:00:00Z"), ZoneId.of("Europe/Moscow"), 4);
        Purchase purchase = new Purchase("P-1", offering, new Money(new BigDecimal("100.00"), usd), Instant.now());
        assertEquals(new Money(new BigDecimal("50.00"), usd), new RefundService(RoundingMode.HALF_UP).refundForRemainingSessions(purchase, 2));
    }
    @Test void seriesUsesCalendarIntervalInOrganizerZone() {
        WebinarSeriesPlan plan = new WebinarSeriesPlan(Instant.parse("2024-02-28T15:00:00Z"), ZoneId.of("Europe/Berlin"), Period.ofDays(1), Duration.ofHours(1), 3);
        assertEquals(LocalDate.of(2024, 2, 29), plan.starts().get(1).toLocalDate());
        assertEquals(3, plan.starts().size());
    }
}
