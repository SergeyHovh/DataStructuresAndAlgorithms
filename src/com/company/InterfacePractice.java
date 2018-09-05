package com.company;

public class InterfacePractice {
    public interface MyInterface {
        boolean compare(int a, int b);
    }

    boolean isBigger(int a, int b, MyInterface action) {
        return action.compare(a, b);
    }
}
