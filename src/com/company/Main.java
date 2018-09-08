package com.company;

import com.company.BinaryTree.BinarySearchTree;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println(generateTree(5));
        System.out.println(generateGraph(5));
    }

    private static BinarySearchTree<Integer> generateTree(int size) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(new Random().nextInt(2 * size));
        for (Integer integer : generateSet(size)) {
            tree.insert(integer);
        }
        return tree;
    }

    private static MapGraph<Integer, Integer> generateGraph(int size) {
        MapGraph<Integer, Integer> graph = new MapGraph<>(0);
        Set<Integer> set = generateSet(size);
        Integer[] arr = set.toArray(new Integer[size]);
        for (Integer integer : arr) {
            for (int i = 0; i < new Random().nextInt(2) + 1; i++) {
                int s = integer, d = arr[new Random().nextInt(size)];
                if (!graph.isConnected(s, d) && s != d) {
                    graph.addEdgeUD(integer, d, new Random().nextInt(size));
                }
            }
        }
        return graph;
    }

    static Set<Integer> generateSet(int size) {
        Set<Integer> integerSet = new HashSet<>(1);
        while (integerSet.size() != size) {
            integerSet.add(new Random().nextInt(2 * size));
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
