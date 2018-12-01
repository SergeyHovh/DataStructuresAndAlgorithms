package com.company.StacksAndQueues;

public class Stack<E> {
    private Node<E> top;

    public boolean isEmpty() {
        return top == null;
    }

    public E peek() {
        if (!isEmpty()) {
            System.out.println(top.val);
            return top.val;
        } else return null;
    }

    public void push(E val) {
        if (isEmpty()) top = new Node<>(val);
        Node<E> node = new Node<>(val);
        node.next = top;
        top = node;
    }

    public E pop() {
        if (isEmpty()) return null;
        E val = top.val;
        top = top.next;
        return val;
    }

    public void display() {
        if (isEmpty()) return;
        while (top.next != null) {
            top.display();
            top = top.next;
        }
        System.out.println();
    }

    static class Node<E> {
        Node<E> next;
        E val;

        Node(E val) {
            this.val = val;
            this.next = null;
        }

        void display() {
            System.out.println(val.toString());
        }
    }
}
