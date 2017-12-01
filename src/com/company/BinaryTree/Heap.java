package com.company.BinaryTree;

public class Heap<T> extends BinaryTree<T> {
    private int size = 1;

    public Heap(T val) {
        super(val);
    }

    public int getSize() {
        return size;
    }

    public void insert(T val) {
        insert(this, val);
        size++;
    }

    private Heap<T> insert(Heap<T> start, T val) {
        if (start == null) start = new Heap<>(val);
        int valHash = val.hashCode();
        int objHash = start.getObjectHashCode();
        if (valHash < objHash) {
            if (start.isLeaf()) {
                start.setLeft(insert((Heap<T>) start.getLeft(), val));
            } else if (start.getLeft() != null) {
                start.setRight(insert((Heap<T>) start.getRight(), val));
            } else if (start.getRight() != null) {
                start.setLeft(insert((Heap<T>) start.getRight(), val));
            }
        }
        return start;
    }
}
