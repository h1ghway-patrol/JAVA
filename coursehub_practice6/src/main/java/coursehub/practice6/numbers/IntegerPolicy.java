package coursehub.practice6.numbers;

import java.util.Objects;

public final class IntegerPolicy {
    private IntegerPolicy() { }

    public static boolean sameValue(Integer left, Integer right) {
        return Objects.equals(left, right);
    }

    public static int requireValue(Integer value, String fieldName) {
        return Objects.requireNonNull(value, fieldName + " must not be null");
    }
}
