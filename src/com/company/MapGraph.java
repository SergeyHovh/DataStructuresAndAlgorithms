package com.company;

import java.util.*;

/**
 * @param <K> key
 * @param <V> value
 */
public class MapGraph<K, V> {
    private class Edge<T, U> {
        private T value;
        private U weight;

        Edge(T value, U weight) {
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

    private Map<K, List<Edge<K, V>>> graph;
    private Set<K> vertices;


    /**
     * @param type requires parameter, to determine if data type is comparable or not.
     */
    MapGraph(K type) {
        if (Main.isComparable(type.getClass())) {
            graph = new TreeMap<>();
        } else {
            graph = new LinkedHashMap<>();
        }
        vertices = new HashSet<>();
    }

    void DFS(K source) {
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
            for (Edge<K, V> kvEdge : graph.get(explore)) {
                if (!visited.get(kvEdge.value)) {
                    visited.put(kvEdge.value, true);
                    stack.addLast(kvEdge.value);
                }
            }
        }
        System.out.println();
    }

    void BFS(K source) {
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
            for (Edge<K, V> kvEdge : graph.get(explore)) {
                if (!visited.get(kvEdge.value)) {
                    visited.put(kvEdge.value, true);
                    queue.add(kvEdge.value);
                }
            }
        }
        System.out.println();
    }

    public void addEdgeD(K source, K destination, V weight) {
        if (!graph.containsKey(source)) {
            graph.put(source, new LinkedList<>());
        }
        if (!weight.equals(null)) {
            graph.get(source).add(new Edge<>(destination, weight));
            vertices.add(source);
            vertices.add(destination);
        }
    }

    void addEdgeUD(K source, K destination, V weight) {
        if (!graph.containsKey(source)) {
            graph.put(source, new LinkedList<>());
        }
        if (!graph.containsKey(destination)) {
            graph.put(destination, new LinkedList<>());
        }
        if (!weight.equals(null)) {
            graph.get(source).add(new Edge<>(destination, weight));
            graph.get(destination).add(new Edge<>(source, weight));
            vertices.add(source);
            vertices.add(destination);
        }
    }

    boolean isConnected(K s, K d) {
        if (graph.containsKey(s) && graph.containsKey(d)) {
            for (Edge<K, V> kvEdge : graph.get(s)) {
                if (kvEdge.value.equals(d)) {
                    return true;
                }
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
}