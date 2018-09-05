package com.company.List;

public class LinkedList<E> {
    class Node<E> {
        Node<E> next;
        E val;

        Node(E val) {
            this.val = val;
            this.next = null;
        }

        void print() {
            System.out.print(val.toString());
        }
    }

    private boolean isEmpty() {
        return head == null;
    }

    private Node<E> head;

    public LinkedList() {
        head = null;
    }

    public void insert(E val) {
        Node<E> newNode = new Node<>(val);
        newNode.next = head;
        head = newNode;
    }

    public Node<E> pop() {
        Node<E> temp = head;
        if (!isEmpty()) {
            head = head.next;
        }
        return temp;
    }

    public void display() {
        Node<E> node = head;
        while (node != null) {
            node.print();
            if (node.next != null)
                System.out.print(" -> ");
            node = node.next;
        }
        System.out.println();
    }

    public Node<E> find(E val) {
        Node<E> node = head;
        if (!isEmpty()) {
            while (!node.val.equals(val)) {
                if (node.next == null) {
                    return null;
                } else {
                    node = node.next;
                }
            }
        } else {
            System.out.println("Empty List");
        }
        return node;
    }

    public Node<E> removeNode(E val) {
        Node<E> current = head;
        Node<E> prev = head;
        while (!current.val.equals(val)) {
            if (current.next == null) return null;
            else {
                prev = current;
                current = current.next;
            }
        }
        if (current == head) head = head.next;
        else {
            prev.next = current.next;
        }
        return current;
    }
}

