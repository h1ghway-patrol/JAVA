package coursehub;

public final class Task04FinallyOrder {
    private Task04FinallyOrder() { }
    public static String returnsFromTry(StringBuilder log) {
        try {
            log.append("try; ");
            return "значение из try";
        } finally {
            log.append("finally; ");
        }
    }
    public static String catchesThenFinally(StringBuilder log) {
        try {
            log.append("try; ");
            throw new IllegalStateException("ошибка");
        } catch (IllegalStateException ex) {
            log.append("catch; ");
            return "значение из catch";
        } finally {
            log.append("finally; ");
        }
    }
    public static void throwsAndFinally(StringBuilder log) {
        try {
            log.append("try; ");
            throw new IllegalArgumentException("основная ошибка");
        } finally {
            log.append("finally; ");
        }
    }
}
