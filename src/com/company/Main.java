package com.company;

interface Functions {
    void hello();

    void bye();

    void arg(String arg);
}

public class Main {
    public static void main(String[] args) {
        MapGraph<String, Integer> stringIntegerMapGraph = new MapGraph<>();
        stringIntegerMapGraph.addEdgeUD("Help", "Me", 3);
        stringIntegerMapGraph.addEdgeD("Help", "No", 10);
        System.out.println(stringIntegerMapGraph);

    }

    private static int preIndex = 0;

    private static int search(int start, int end, int arr[], int x) {
        for (int i = start; i <= end; i++)
            if (arr[i] == x)
                return i;
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
