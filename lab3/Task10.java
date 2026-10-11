import java.util.*;

public class Task10 {
    static class Course {
        private final int id;
        private String name;

        Course(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Course)) return false;
            Course other = (Course) obj;
            return id == other.id;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(id);
        }

        @Override
        public String toString() {
            return id + ": " + name;
        }
    }

    static class BadCourse {
        private final int id;
        private String name;

        BadCourse(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof BadCourse)) return false;
            BadCourse other = (BadCourse) obj;
            return id == other.id && Objects.equals(name, other.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

    public static void main(String[] args) {
        Course course = new Course(1, "Java");

        Set<Course> set = new HashSet<>();
        set.add(course);
        course.name = "Advanced Java";

        System.out.println(set.contains(course));

        Map<Course, String> map = new HashMap<>();
        map.put(course, "Active");
        System.out.println(map.get(course));

        BadCourse bad = new BadCourse(1, "Java");
        Set<BadCourse> badSet = new HashSet<>();
        badSet.add(bad);

        bad.name = "Advanced Java";

        System.out.println(badSet.contains(bad));
    }
}
