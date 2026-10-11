import java.util.*;

public class Task9 {
    static class Course {
        private final String name;

        Course(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class JavaCourse extends Course {
        JavaCourse() {
            super("Java");
        }
    }

    public static <T> void copy(
            List<? extends T> source,
            List<? super T> destination
    ) {
        for (T item : source) {
            destination.add(item);
        }
    }

    public static void main(String[] args) {
        List<JavaCourse> source = Arrays.asList(
                new JavaCourse(),
                new JavaCourse()
        );

        List<Course> destination = new ArrayList<>();

        copy(source, destination);

        List<Object> objects = new ArrayList<>();
        copy(destination, objects);

        System.out.println(destination);
        System.out.println(objects);
    }
}
