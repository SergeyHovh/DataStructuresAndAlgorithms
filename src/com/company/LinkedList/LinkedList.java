package com.company.LinkedList;

public class LinkedList {
    private Link firstLink;

    LinkedList() {
        this.firstLink = null;
    }

    boolean isEmpty() {
        return firstLink == null;
    }

    public void insertFirstLink(String name, int age) {
        Link newLink = new Link(name, age);
        newLink.next = firstLink;
        firstLink = newLink;
    }

    public Link removeFirst() {
        Link linkRef = firstLink;
        if (!isEmpty()) {
            firstLink = firstLink.next;
        } else {
            System.out.println("empty linked list");
        }
        return linkRef;
    }

    public void display() {
        Link theLink = firstLink;
        while (theLink != null) {
            theLink.display();
            theLink = theLink.next;
        }
    }
}

