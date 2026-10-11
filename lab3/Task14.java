import java.util.*;

public class Task14 {
    static class LruCache<K, V> {
        private final int capacity;
        private final LinkedHashMap<K, V> cache;

        LruCache(int capacity) {
            if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity must be positive");
            }

            this.capacity = capacity;

            cache = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                    return size() > LruCache.this.capacity;
                }
            };
        }

        public V get(K key) {
            return cache.get(Objects.requireNonNull(key));
        }

        public void put(K key, V value) {
            cache.put(
                    Objects.requireNonNull(key),
                    Objects.requireNonNull(value)
            );
        }

        public int size() {
            return cache.size();
        }

        public List<K> keys() {
            return new ArrayList<>(cache.keySet());
        }
    }

    public static void main(String[] args) {
        LruCache<Integer, String> cache = new LruCache<>(3);

        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");

        cache.get(1);
        cache.put(4, "D");

        System.out.println(cache.keys());
        System.out.println(cache.get(2));
        System.out.println(cache.keys());
    }
}
