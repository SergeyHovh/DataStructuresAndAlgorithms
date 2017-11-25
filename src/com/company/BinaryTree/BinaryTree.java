package com.company.BinaryTree;

import com.company.Traversal;

class BinaryTree<T> implements Traversal {
    private BinaryTree<T> left;
    private BinaryTree<T> right;
    private T value;
    private int objectHashCode;

    // constructor
    public BinaryTree(T val) {
        this.value = val;
        this.right = null;
        this.left = null;
        objectHashCode = val.toString().toLowerCase().hashCode();
    }

    @Override
    public void preOrder() {
        System.out.println("PreOrder Traversal");
        preOrder(this);
        System.out.println();
    }

    @Override
    public void inOrder() {
        System.out.println("InOrder Traversal");
        inOrder(this);
        System.out.println();
    }

    @Override
    public void postOrder() {
        System.out.println("PostOrder Traversal");
        postOrder(this);
        System.out.println();
    }

    public void print() {
        print(this);
    }

    public void mirror() {
        mirror(this);
    }

    public void isAncestor(T q1, T q2) {
        System.out.println(isAncestor(this, q1, q2));
    }
    // QUIZ 12
    // PROBLEM 1
    private boolean isAncestor(BinaryTree<T> start, T q1, T q2) {
        if (start == null) return false;
        if (start.getValue().equals(q1))
            return start.getValue().equals(q2) || isAncestor(start.left, q2, q2) || isAncestor(start.right, q2, q2);
        return isAncestor(start.left, q1, q2) || isAncestor(start.right, q1, q2);
    }

    private void preOrder(BinaryTree<T> start) {
        if (start == null) return;
        print(start);
        preOrder(start.left);
        preOrder(start.right);
    }

    private void inOrder(BinaryTree<T> start) {
        if (start == null) return;
        inOrder(start.left);
        print(start);
        inOrder(start.right);
    }

    private void postOrder(BinaryTree<T> start) {
        if (start == null) return;
        postOrder(start.left);
        postOrder(start.right);
        print(start);
    }

    private void mirror(BinaryTree<T> root) {
        BinaryTree<T> temp = root.left;
        root.left = root.right;
        root.right = temp;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    private void print(BinaryTree<T> root) {
        System.out.print(root.getValue() + " ");
    }

    public int getObjectHashCode() {
        return objectHashCode;
    }

    public BinaryTree<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryTree<T> left) {
        this.left = left;
    }

    public BinaryTree<T> getRight() {
        return right;
    }

    void setRight(BinaryTree<T> right) {
        this.right = right;
    }
}
