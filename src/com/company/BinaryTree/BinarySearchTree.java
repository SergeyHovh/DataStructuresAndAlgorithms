package com.company.BinaryTree;

public class BinarySearchTree<T> extends BinaryTree<T> {

    private String phoneNumber;

    public BinarySearchTree(T val) {
        super(val);
    }


    public BinarySearchTree<T> getMin(BinarySearchTree<T> start) {
        if (start.getLeft() == null) {
            return start;
        }
        return getMin((BinarySearchTree<T>) start.getLeft());
    }

    public BinarySearchTree<T> getMax(BinarySearchTree<T> start) {
        if (start.getRight() == null) {
            return start;
        }
        return getMax((BinarySearchTree<T>) start.getRight());
    }

    public BinarySearchTree<T> find(T value) {
        return find(this, value);
    }

    private BinarySearchTree<T> find(BinarySearchTree<T> start, T value) {
        if (start == null || start.getValue() == value) return start;
        int first = value.toString().toLowerCase().hashCode();
        int second = start.getObjectHashCode();
        if (first < second) {
            return find((BinarySearchTree<T>) start.getLeft(), value);
        } else {
            return find((BinarySearchTree<T>) start.getRight(), value);
        }
    }

    public void insert(T value) {
        insert(this, value);
        find(value).setPhoneNumber("");
    }

    public void insert(T value, String phoneNumber) {
        insert(value);
        find(value).setPhoneNumber(phoneNumber);
    }

    public void remove(T value) {
        remove(this, value);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void removePhoneNum(char first) {
        removePhoneNum(this, first);
    }

    // QUIZ 12
    // PROBLEM 2
    private void removePhoneNum(BinarySearchTree<T> start, char first) {
        if (start == null) return;
        removePhoneNum((BinarySearchTree<T>) start.getLeft(), first);
        removePhoneNum((BinarySearchTree<T>) start.getRight(), first);
        char[] chars = start.getPhoneNumber().toCharArray();
        if (first == chars[0]) remove(start, start.getValue());
    }

    private BinarySearchTree<T> remove(BinarySearchTree<T> start, T value) {
        if (start == null) return null;
        int first = value.toString().toLowerCase().hashCode();
        int second = start.getObjectHashCode();
        if (first < second) {
            start.setLeft(remove((BinarySearchTree<T>) start.getLeft(), value));
        } else if (first > second) {
            start.setRight(remove((BinarySearchTree<T>) start.getRight(), value));
        } else {
            if (start.getLeft() == null && start.getRight() == null) return null;
            if (start.getRight() == null) return (BinarySearchTree<T>) start.getLeft();
            if (start.getLeft() == null) return (BinarySearchTree<T>) start.getRight();
            BinaryTree<T> temp = getMax((BinarySearchTree<T>) start.getLeft());
            remove((BinarySearchTree<T>) start.getLeft(), temp.getValue());
            start.setValue(temp.getValue());
        }
        return start;
    }

    private BinarySearchTree<T> insert(BinarySearchTree<T> start, T value) {
        if (start == null) {
            start = new BinarySearchTree<>(value);
        } else {
            int first = value.toString().toLowerCase().hashCode();
            int second = start.getObjectHashCode();
            if (first < second) {
                start.setLeft(insert((BinarySearchTree<T>) start.getLeft(), value));
            } else if (first > second) {
                start.setRight(insert((BinarySearchTree<T>) start.getRight(), value));
            }
        }
        return start;
    }
}
