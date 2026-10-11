import java.util.*;

public class Task13 {
    private final Map<String, Set<Integer>> index = new HashMap<>();
    private final Map<Integer, Set<String>> courseTags = new HashMap<>();

    public void addCourse(int id, Set<String> tags) {
        Objects.requireNonNull(tags);
        removeCourse(id);

        Set<String> copy = new HashSet<>(tags);
        courseTags.put(id, copy);

        for (String tag : copy) {
            Objects.requireNonNull(tag);
            index.computeIfAbsent(tag, key -> new HashSet<>()).add(id);
        }
    }

    public void removeCourse(int id) {
        Set<String> tags = courseTags.remove(id);

        if (tags == null) {
            return;
        }

        for (String tag : tags) {
            Set<Integer> ids = index.get(tag);
            ids.remove(id);

            if (ids.isEmpty()) {
                index.remove(tag);
            }
        }
    }

    public Set<Integer> findByTags(Set<String> tags) {
        Objects.requireNonNull(tags);
        Set<Integer> result = null;

        for (String tag : tags) {
            Set<Integer> ids = index.get(tag);

            if (ids == null) {
                return new HashSet<>();
            }

            if (result == null) {
                result = new HashSet<>(ids);
            } else {
                result.retainAll(ids);
            }
        }

        if (result == null) {
            return new HashSet<>();
        }

        return result;
    }

    public static void main(String[] args) {
        Task13 catalog = new Task13();

        catalog.addCourse(1, new HashSet<>(Arrays.asList("java", "backend")));
        catalog.addCourse(2, new HashSet<>(Arrays.asList("java", "database")));
        catalog.addCourse(3, new HashSet<>(Arrays.asList("backend", "database")));

        System.out.println(catalog.findByTags(
                new HashSet<>(Arrays.asList("java", "backend"))
        ));

        catalog.removeCourse(1);

        System.out.println(catalog.findByTags(
                new HashSet<>(Arrays.asList("java"))
        ));
    }
}
