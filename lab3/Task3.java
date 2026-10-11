import java.util.*;

public class Task3 {
    static class Course {
        private final int id;
        private final String name;

        Course(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return id + ": " + name;
        }
    }

    public static void main(String[] args) {
        Map<Integer, Course> courses = new HashMap<>();

        Course first = new Course(1, "Java");
        Course second = new Course(2, "SQL");
        Course duplicate = new Course(1, "Python");

        addCourse(courses, first);
        addCourse(courses, second);
        addCourse(courses, duplicate);

        System.out.println(courses.get(1));
        System.out.println(courses.containsKey(2));
        courses.putIfAbsent(3, new Course(3, "HTML"));
        courses.computeIfAbsent(4, id -> new Course(id, "CSS"));

        System.out.println(courses);
    }

    static void addCourse(Map<Integer, Course> courses, Course course) {
        if (courses.containsKey(course.id)) {
            System.out.println("ID already exists: " + course.id);
            return;
        }
        courses.put(course.id, course);
    }
}
