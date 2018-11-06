package com.company;

public class Person implements Comparable<Person> {
    private String name, surname;

    Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    Person() {
        this.name = this.surname = "";
    }

    @Override
    public int compareTo(Person person) {
        return surname.compareTo(person.surname);
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            return this.name.equals(((Person) obj).name) && this.surname.equals(((Person) obj).surname);
        } else {
            return false;
        }
    }
}
