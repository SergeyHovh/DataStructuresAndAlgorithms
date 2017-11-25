package com.company.LinkedList;

class Link {
    private String name;
    private int age;
    Link next;

    Link(String name, int age) {
        this.age = age;
        this.name = name;
    }

    void display() {
        System.out.println(name + ": " + age);
    }

    public String toString() {
        return name;
    }
}
