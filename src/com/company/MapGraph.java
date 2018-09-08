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
            graph = new HashMap<>();
        }
        vertices = new HashSet<>();
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

    public void addEdgeUD(K source, K destination, V weight) {
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

    public boolean isConnected(K s, K d) {
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (K i : graph.keySet()) {
            result.append(i).append(" -> ").append(graph.get(i)).append('\n');
        }
        return result.toString();
    }
}