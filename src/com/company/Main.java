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
    }
}
