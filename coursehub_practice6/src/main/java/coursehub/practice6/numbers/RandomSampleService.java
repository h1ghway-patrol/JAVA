package coursehub.practice6.numbers;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public final class RandomSampleService {
    private final RandomGenerator random;

    public RandomSampleService(RandomGenerator random) { this.random = java.util.Objects.requireNonNull(random); }

    public List<Integer> sample(int count, int originInclusive, int boundExclusive) {
        if (count < 0 || originInclusive >= boundExclusive) throw new IllegalArgumentException("Invalid sample parameters");
        List<Integer> values = new ArrayList<>(count);
        for (int i = 0; i < count; i++) values.add(random.nextInt(originInclusive, boundExclusive));
        return List.copyOf(values);
    }
}
