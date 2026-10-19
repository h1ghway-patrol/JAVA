package coursehub.practice6.numbers;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.random.RandomGeneratorFactory;
import static org.junit.jupiter.api.Assertions.*;

class NumbersTest {
    @Test void nullableIntegerContractIsExplicit() {
        assertThrows(NullPointerException.class, () -> IntegerPolicy.requireValue(null, "count"));
        assertTrue(IntegerPolicy.sameValue(Integer.valueOf(1000), Integer.valueOf(1000)));
        assertFalse(IntegerPolicy.sameValue(null, 1));
    }
    @Test void decimalRepresentationsDifferAndStringIsExact() {
        List<BigDecimal> values = DecimalDemo.representations();
        assertNotEquals(values.get(0), values.get(1));
        assertEquals(new BigDecimal("0.1"), values.get(2));
        assertEquals(new BigDecimal("30.00"), DecimalDemo.totalCourseCost(List.of(new BigDecimal("10.00"), new BigDecimal("10.00"), new BigDecimal("10.00"))));
    }
    @Test void allocationPreservesDiscountTotalToCents() {
        List<BigDecimal> result = DiscountAllocator.allocate(List.of(new BigDecimal("10.00"), new BigDecimal("20.00"), new BigDecimal("30.00")), new BigDecimal("10.00"), 2, RoundingMode.HALF_UP);
        assertEquals(new BigDecimal("50.00"), result.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
    }
    @Test void seededRandomIsRepeatable() {
        var first = new RandomSampleService(RandomGeneratorFactory.of("L64X128MixRandom").create(42L));
        var second = new RandomSampleService(RandomGeneratorFactory.of("L64X128MixRandom").create(42L));
        assertEquals(first.sample(20, 1, 100), second.sample(20, 1, 100));
    }
    @Test void sampleStatsCalculatesBoundsAndMean() {
        assertEquals( new SampleStats(3, 1, 5, new BigDecimal("3.00")), SampleStats.from(List.of(1, 3, 5)));
    }
}
