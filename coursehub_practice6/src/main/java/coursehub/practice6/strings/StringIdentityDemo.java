package coursehub.practice6.strings;

public final class StringIdentityDemo {
    private StringIdentityDemo() { }

    public static boolean sameReference(String left, String right) {
        return left == right;
    }

    public static boolean sameText(String left, String right) {
        return left != null && left.equals(right);
    }

    public static String canonical(String value) {
        return value == null ? null : value.intern();
    }
}
