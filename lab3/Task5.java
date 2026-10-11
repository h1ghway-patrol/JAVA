import java.util.*;

public class Task5 {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(
                Arrays.asList(1, 2, 3, 4, 5, 6)
        );

        try {
            for (Integer number : numbers) {
                if (number % 2 == 0) {
                    numbers.remove(number);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException");
        }

        List<Integer> first = new ArrayList<>(
                Arrays.asList(1, 2, 3, 4, 5, 6)
        );

        Iterator<Integer> iterator = first.iterator();

        while (iterator.hasNext()) {
            if (iterator.next() % 2 == 0) {
                iterator.remove();
            }
        }

        List<Integer> second = new ArrayList<>(
                Arrays.asList(1, 2, 3, 4, 5, 6)
        );

        second.removeIf(number -> number % 2 == 0);

        System.out.println(first);
        System.out.println(second);
    }
}
