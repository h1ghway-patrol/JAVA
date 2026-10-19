package coursehub.practice6.domain;

import java.time.Instant;
import java.util.Objects;

public record Purchase(String id, CourseOffering offering, Money paid, Instant purchasedAt) {
    public Purchase {
        Objects.requireNonNull(id); Objects.requireNonNull(offering); Objects.requireNonNull(paid); Objects.requireNonNull(purchasedAt);
        if (id.isBlank()) throw new IllegalArgumentException("Purchase id must not be blank");
    }
}
