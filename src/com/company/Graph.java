package com.company;

import java.util.*;

public class Graph<E> {
    private Map<E, Set<E>> edges = new TreeMap<>();

    public void addNode(E value) {
        if (!edges.containsKey(value)) {
            edges.put(value, new TreeSet<E>());
        }
    }

    public void removeNode(E value) {
        if (!edges.containsKey(value)) return;
        for (E v : edges.get(value)) {
            edges.get(v).remove(value);
        }
        edges.remove(value);
    }

    public void addEdge(E v1, E v2) {
        addNode(v1);
        addNode(v2);
        edges.get(v1).add(v2);
        edges.get(v2).add(v1);
    }

    public void removeEdge(E v1, E v2) {
        edges.get(v1).remove(v2);
        edges.get(v2).remove(v1);
    }

    public Map<E, Set<E>> getEdges() {
        return edges;
    }
}
