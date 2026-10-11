import java.util.*;

public class Task12 {
    interface Repository<ID, T> {
        void save(ID id, T value);
        Optional<T> findById(ID id);
        List<T> findAll();
        boolean deleteById(ID id);
    }

    static class InMemoryRepository<ID, T>
            implements Repository<ID, T> {

        private final Map<ID, T> storage = new LinkedHashMap<>();

        @Override
        public void save(ID id, T value) {
            storage.put(
                    Objects.requireNonNull(id),
                    Objects.requireNonNull(value)
            );
        }

        @Override
        public Optional<T> findById(ID id) {
            return Optional.ofNullable(
                    storage.get(Objects.requireNonNull(id))
            );
        }

        @Override
        public List<T> findAll() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public boolean deleteById(ID id) {
            return storage.remove(Objects.requireNonNull(id)) != null;
        }
    }

    public static void main(String[] args) {
        Repository<Integer, String> repository =
                new InMemoryRepository<>();

        repository.save(1, "Java");
        repository.save(2, "SQL");
        repository.save(1, "Advanced Java");

        System.out.println(repository.findById(1));
        System.out.println(repository.findAll());

        repository.deleteById(2);

        System.out.println(repository.findAll());
    }
}
