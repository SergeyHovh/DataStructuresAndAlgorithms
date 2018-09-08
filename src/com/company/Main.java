package com.company;

import com.company.BinaryTree.BinarySearchTree;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
//        System.out.println(generateTree(10));
//        System.out.println(generateGraph(10));
        MapGraph<Integer, Integer> mapGraph = generateGraph(10);
        System.out.println(mapGraph);
        mapGraph.BFS(0);
    }

    private static BinarySearchTree<Integer> generateTree(int size) {
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

    private static MapGraph<Integer, Integer> generateGraph(int size) {
        MapGraph<Integer, Integer> graph = new MapGraph<>(0);
        for (int i = 0; i < size; ++i) {
            for (int k = 0; k < new Random().nextInt(3) + 1; k++) {
                int d = new Random().nextInt(size);
                if (!graph.isConnected(i, d) && i != d) {
                    graph.addEdgeUD(i, d, new Random().nextInt(size / 2)
                            + new Random().nextInt(size / 2));
                }
            }
        }
        return graph;
    }

    private static Set<Integer> generateSet(int size) {
        Set<Integer> integerSet = new LinkedHashSet<>(1);
        while (integerSet.size() != size) {
            integerSet.add(new Random().nextInt(size * size));
        }
        System.out.println(integerSet);
        return integerSet;
    }

    public static boolean isComparable(Class<?> x) {
        for (Class<?> aClass : x.getInterfaces()) {
            if (aClass.toString().equals("interface java.lang.Comparable")) {
                return true;
            }
        }
        return false;
    }

    private static int preIndex = 0;

    private static int search(int start, int end, int arr[], int x) {
        for (int i = start; i <= end; i++)
            if (arr[i] == x) return i;
        return -1;
    }

    private static void postOrder(int[] in, int[] pre) {
        System.out.println();
        printPostOrder(0, in.length - 1, in, pre);
        System.out.println();
    }

    private static void printPostOrder(int inStart, int inEnd, int in[], int pre[]) {
        if (inStart > inEnd) return;

        if (inStart == inEnd) {
            System.out.print(pre[preIndex++] + " ");
            return;
        }

        int index = search(inStart, inEnd, in, pre[preIndex++]);
        printPostOrder(inStart, index - 1, in, pre);
        printPostOrder(index + 1, inEnd, in, pre);
        System.out.print(in[index] + " ");
    }
}
