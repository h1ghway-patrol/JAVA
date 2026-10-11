import java.util.*;

public class Task8 {
    static class StudentScore implements Comparable<StudentScore> {
        private final String name;
        private final int score;

        StudentScore(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public int compareTo(StudentScore other) {
            return Integer.compare(score, other.score);
        }

        @Override
        public String toString() {
            return name + ": " + score;
        }
    }

    public static <T extends Comparable<? super T>> T max(List<T> list) {
        Objects.requireNonNull(list);

        if (list.isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        T maximum = Objects.requireNonNull(list.get(0));

        for (T item : list) {
            Objects.requireNonNull(item);

            if (item.compareTo(maximum) > 0) {
                maximum = item;
            }
        }

        return maximum;
    }

    public static void main(String[] args) {
        System.out.println(max(Arrays.asList(3, 9, 2, 7)));
        System.out.println(max(Arrays.asList("Java", "SQL", "Python")));

        List<StudentScore> scores = Arrays.asList(
                new StudentScore("Ivan", 75),
                new StudentScore("Anna", 95),
                new StudentScore("Petr", 80)
        );

        System.out.println(max(scores));
    }
}
