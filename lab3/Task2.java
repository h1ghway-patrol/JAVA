import java.util.*;

public class Task2 {
    static class CourseCode {
        private final String code;

        public CourseCode(String code) {
            this.code = Objects.requireNonNull(code);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof CourseCode)) return false;
            CourseCode other = (CourseCode) obj;
            return code.equals(other.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code);
        }

        @Override
        public String toString() {
            return code;
        }
    }

    public static void main(String[] args) {
        List<CourseCode> codes = Arrays.asList(
                new CourseCode("JAVA"),
                new CourseCode("SQL"),
                new CourseCode("JAVA"),
                new CourseCode("HTML"),
                new CourseCode("SQL")
        );

        Set<CourseCode> uniqueCodes = new LinkedHashSet<>(codes);

        System.out.println(uniqueCodes);
    }
}
