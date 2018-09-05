package com.company;

import java.util.*;

public class MapGraph<V, W> {
    private class Edge<V, W> {
        private V value;
        private W weight;

        Edge(V value, W weight) {
            this.value = value;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + value.toString() + " - " + weight.toString() + ")";
        }
    }

    private Map<V, List<Edge<V, W>>> graph;
    private Set<V> vertices;

    MapGraph() {
        graph = new TreeMap<>();
        vertices = new TreeSet<>();
    }

    public void addEdgeD(V source, V destination, W weight) {
        if (!graph.containsKey(source)) {
            graph.put(source, new LinkedList<>());
        }
        graph.get(source).add(new Edge<>(destination, weight));
        vertices.add(source);
        vertices.add(destination);
    }

    public void addEdgeUD(V source, V destination, W weight) {
        if (!graph.containsKey(source)) {
            graph.put(source, new LinkedList<>());
        }
        if (!graph.containsKey(destination)) {
            graph.put(destination, new LinkedList<>());
        }
        graph.get(source).add(new Edge<>(destination, weight));
        graph.get(destination).add(new Edge<>(source, weight));
        vertices.add(source);
        vertices.add(destination);
    }

    public int getSize() {
        return vertices.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (V i : graph.keySet()) {
            result.append(i).append(" -> ").append(graph.get(i)).append('\n');
        }
        return result.toString();
    }
}