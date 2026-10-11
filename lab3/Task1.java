import java.util.*;

public class Task1 {
    public static void main(String[] args) {
        List<String> registrations = new ArrayList<>(
                Arrays.asList("Ivan", "Anna", "Petr", "Anna", "Oleg")
        );

        registrations.add(2, "Maria");
        registrations.remove("Petr");

        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new LinkedHashSet<>();

        for (String name : registrations) {
            if (!seen.add(name)) {
                duplicates.add(name);
            }
        }

        System.out.println(registrations);
        System.out.println(duplicates);

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        for (int i = 0; i < 100000; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        long start = System.nanoTime();
        long sum1 = 0;

        for (int i = 0; i < arrayList.size(); i++) {
            sum1 += arrayList.get(i);
        }

        long arrayTime = System.nanoTime() - start;

        start = System.nanoTime();
        long sum2 = 0;

        for (int i = 0; i < linkedList.size(); i++) {
            sum2 += linkedList.get(i);
        }

        long linkedTime = System.nanoTime() - start;

        System.out.println("ArrayList: " + arrayTime);
        System.out.println("LinkedList: " + linkedTime);

        start = System.nanoTime();
        sum1 = 0;
        for (int value : arrayList) {
            sum1 += value;
        }
        long arrayIterationTime = System.nanoTime() - start;

        start = System.nanoTime();
        sum2 = 0;
        for (int value : linkedList) {
            sum2 += value;
        }
        long linkedIterationTime = System.nanoTime() - start;

        System.out.println("ArrayList iteration: " + arrayIterationTime);
        System.out.println("LinkedList iteration: " + linkedIterationTime);
    }
}
