package com.company;

import com.company.BinaryTree.BinarySearchTree;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(10);
        tree.insert(25, "asjdnk");
        tree.insert(102, "aksdladml");
        tree.insert(8, "maksdnasdn");
        tree.insert(9, "ahdb");
        tree.insert(6, "aksjdba");
        tree.insert(30);
        tree.preOrder();
        tree.inOrder();
        tree.postOrder();
        int[] pre = {10, 8, 6, 9, 25, 102, 30};
        int[] in = {6, 8, 9, 10, 25, 30, 102};
        System.out.println();
        postOrder(in, pre);
    }

    private static int search(int start, int end, int arr[], int x) {
        for (int i = start; i <= end; i++)
            if (arr[i] == x)
                return i;
        return -1;
    }

    private static int preIndex = 0;

    private static void postOrder(int[] in, int[] pre) {
        printPostOrder(0, in.length - 1, in, pre);
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
