package com.company.Graph;

import java.util.*;

/**
 * @param <K> key
 */
public class MapGraph<K> {
    private Map<K, List<Edge<K>>> graph;
    private Set<K> vertices;

    /**
     * @param type requires parameter, to determine if data type is comparable or not.
     */
    public MapGraph(K type) {
        if (isComparable(type.getClass())) {
            graph = new TreeMap<>();
        } else {
            graph = new LinkedHashMap<>();
        }
        vertices = new HashSet<>();
    }

    public void DFS(K source) {
        LinkedList<K> stack = new LinkedList<>();
        Map<K, Boolean> visited = new HashMap<>();
        for (K vertex : vertices) {
            visited.put(vertex, vertex == source);
        }
        stack.add(source);
        while (!stack.isEmpty()) {
            K explore = stack.removeLast();
            System.out.print(explore + " ");
            if (!graph.containsKey(explore)) continue;
            for (Edge<K> kvEdge : graph.get(explore)) {
                if (!visited.get(kvEdge.value)) {
                    visited.put(kvEdge.value, true);
                    stack.addLast(kvEdge.value);
                }
            }
        }
        System.out.println();
    }

    // TODO: 11/8/2018 implement dijkstra
    MapGraph<K> dijkstra(K source) {
        double min = 0;
        MapGraph<K> path = new MapGraph<>(source);
        Map<K, Boolean> visited = new LinkedHashMap<>();
        for (K vertex : vertices) {
            visited.put(vertex, vertex == source);
        }

        Map<K, Double> dijkstra = new LinkedHashMap<>();

        for (K k : graph.keySet()) {
            dijkstra.put(k, Double.MAX_VALUE); // setting all values to infinity
        }

        System.out.println(dijkstra);

        for (Edge<K> kEdge : graph.get(source)) {
            dijkstra.put(kEdge.value, kEdge.weight);
        }

        min = Collections.min(dijkstra.values());
        for (K k : dijkstra.keySet()) {
            if (min == dijkstra.get(k)) {
                path.addEdgeUD(source, k, min);
            }
        }
        return path;
    }

    public void BFS(K source) {
        LinkedList<K> queue = new LinkedList<>();
        Map<K, Boolean> visited = new HashMap<>();
        for (K vertex : vertices) {
            visited.put(vertex, vertex == source);
        }
        queue.add(source);
        while (!queue.isEmpty()) {
            K explore = queue.removeFirst();
            System.out.print(explore + " ");
            if (!graph.containsKey(explore)) continue;
            for (Edge<K> kvEdge : graph.get(explore)) {
                if (!visited.get(kvEdge.value)) {
                    visited.put(kvEdge.value, true);
                    queue.add(kvEdge.value);
                }
            }
        }
        System.out.println();
    }

    public void addEdgeD(K source, K destination, Double weight) {
        if (!graph.containsKey(source)) {
            graph.put(source, new LinkedList<>());
        }
        if (weight != 0) {
            graph.get(source).add(new Edge<>(destination, weight));
            vertices.add(source);
            vertices.add(destination);
        }
    }

    public void addEdgeUD(K source, K destination, Double weight) {
        if (!graph.containsKey(source)) {
            graph.put(source, new LinkedList<>());
        }
        if (!graph.containsKey(destination)) {
            graph.put(destination, new LinkedList<>());
        }
        if (weight != 0) {
            graph.get(source).add(new Edge<>(destination, weight));
            graph.get(destination).add(new Edge<>(source, weight));
            vertices.add(source);
            vertices.add(destination);
        }
    }

    public boolean isConnected(K s, K d) {
        if (graph.containsKey(s) && graph.containsKey(d)) {
            for (Edge<K> kvEdge : graph.get(s)) {
                if (kvEdge.value.equals(d)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isComparable(Class<?> x) {
        for (Class<?> aClass : x.getInterfaces()) {
            if (aClass.toString().equals("interface java.lang.Comparable")) {
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return vertices.size();
    }

    public Set<K> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (K i : graph.keySet()) {
            result.append(i).append(" -> ").append(graph.get(i)).append('\n');
        }
        return result.toString();
    }

    private class Edge<T> {
        private T value;
        private Double weight;

        Edge(T value, Double weight) {
            this.value = value;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + value.toString() + " - " + weight.toString() + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Edge) {
                return value.equals(((Edge) obj).value);
            }
            return false;
        }
    }
}