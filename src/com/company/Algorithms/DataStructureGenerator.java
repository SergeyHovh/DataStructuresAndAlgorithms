package com.company.Algorithms;

import com.company.BinaryTree.BinarySearchTree;
import com.company.Graph.MapGraph;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class DataStructureGenerator {
    public static BinarySearchTree<Integer> generateTree(int size) {
        Set<Integer> set = generateSet(size);
        int iter = 0;
        BinarySearchTree<Integer> tree = null;
        for (Integer integer : set) {
            if (iter == 0) {
                tree = new BinarySearchTree<>(integer);
                iter++;
            } else {
                tree.insert(integer);
            }
        }
        return tree;
    }

    public static MapGraph<Integer> generateGraph(int size) {
        MapGraph<Integer> graph = new MapGraph<>(0);
        for (int i = 0; i < size; ++i) {
            for (int k = 0; k < new Random().nextInt(2 * size) + 1; k++) {
                int d = new Random().nextInt(size);
                if (!graph.isConnected(i, d) && i != d) {
                    graph.addEdgeUD(i, d, new Random().nextDouble() * size);
                }
            }
        }
        return graph;
    }

    public static Set<Integer> generateSet(int size) {
        Set<Integer> integerSet = new LinkedHashSet<>(1);
        while (integerSet.size() != size) {
            integerSet.add(new Random().nextInt(size * size));
        }
        return integerSet;
    }
}
