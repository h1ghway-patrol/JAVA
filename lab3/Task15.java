import java.util.*;

public class Task15 {
    static class Graph<T extends Comparable<? super T>> {
        private final Map<T, Set<T>> graph = new TreeMap<>();

        public void addVertex(T vertex) {
            graph.computeIfAbsent(
                    Objects.requireNonNull(vertex),
                    key -> new TreeSet<>()
            );
        }

        public void addEdge(T from, T to) {
            addVertex(from);
            addVertex(to);
            graph.get(from).add(to);
        }

        public List<T> topologicalSort() {
            Map<T, Integer> degree = new TreeMap<>();

            for (T vertex : graph.keySet()) {
                degree.put(vertex, 0);
            }

            for (Set<T> neighbours : graph.values()) {
                for (T neighbour : neighbours) {
                    degree.put(neighbour, degree.get(neighbour) + 1);
                }
            }

            Queue<T> queue = new PriorityQueue<>();

            for (T vertex : degree.keySet()) {
                if (degree.get(vertex) == 0) {
                    queue.offer(vertex);
                }
            }

            List<T> result = new ArrayList<>();

            while (!queue.isEmpty()) {
                T vertex = queue.poll();
                result.add(vertex);

                for (T neighbour : graph.get(vertex)) {
                    degree.put(neighbour, degree.get(neighbour) - 1);

                    if (degree.get(neighbour) == 0) {
                        queue.offer(neighbour);
                    }
                }
            }

            if (result.size() != graph.size()) {
                throw new IllegalStateException(
                        "Cycle found: " + findCycle()
                );
            }

            return result;
        }

        private List<T> findCycle() {
            Set<T> visited = new HashSet<>();
            Set<T> active = new HashSet<>();
            List<T> path = new ArrayList<>();

            for (T vertex : graph.keySet()) {
                List<T> cycle = dfs(vertex, visited, active, path);

                if (cycle != null) {
                    return cycle;
                }
            }

            return Collections.emptyList();
        }

        private List<T> dfs(
                T vertex,
                Set<T> visited,
                Set<T> active,
                List<T> path
        ) {
            if (active.contains(vertex)) {
                int start = path.indexOf(vertex);
                List<T> cycle = new ArrayList<>(path.subList(start, path.size()));
                cycle.add(vertex);
                return cycle;
            }

            if (visited.contains(vertex)) {
                return null;
            }

            visited.add(vertex);
            active.add(vertex);
            path.add(vertex);

            for (T neighbour : graph.get(vertex)) {
                List<T> cycle = dfs(neighbour, visited, active, path);

                if (cycle != null) {
                    return cycle;
                }
            }

            path.remove(path.size() - 1);
            active.remove(vertex);

            return null;
        }
    }

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();

        graph.addEdge("Java", "Collections");
        graph.addEdge("Basics", "Java");
        graph.addEdge("Collections", "Generics");
        graph.addVertex("SQL");

        try {
            System.out.println(graph.topologicalSort());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        Graph<String> cyclic = new Graph<>();

        cyclic.addEdge("A", "B");
        cyclic.addEdge("B", "C");
        cyclic.addEdge("C", "A");

        try {
            System.out.println(cyclic.topologicalSort());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
