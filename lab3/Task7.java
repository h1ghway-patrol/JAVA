import java.util.*;
import java.util.function.Function;

public class Task7 {
    public static <T> T firstOrThrow(List<T> list) {
        Objects.requireNonNull(list);
        if (list.isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return list.get(0);
    }

    public static <T> void swap(List<T> list, int i, int j) {
        Objects.requireNonNull(list);
        Collections.swap(list, i, j);
    }

    public static <T, K> Map<K, T> indexBy(
            List<T> list,
            Function<T, K> keyFunction
    ) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(keyFunction);

        Map<K, T> result = new LinkedHashMap<>();

        for (T item : list) {
            K key = Objects.requireNonNull(keyFunction.apply(item));

            if (result.containsKey(key)) {
                throw new IllegalArgumentException("Duplicate key: " + key);
            }

            result.put(key, item);
        }

        return result;
    }

    public static void main(String[] args) {
        List<String> names = new ArrayList<>(
                Arrays.asList("Ivan", "Anna", "Petr")
        );

        System.out.println(firstOrThrow(names));
        swap(names, 0, 2);
        System.out.println(names);

        Map<Integer, String> indexed = indexBy(
                names,
                String::length
        );

        System.out.println(indexed);
    }
}
