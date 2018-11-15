package com.company;

import com.company.Algorithms.RK4;

public class Main {
    public static final String PATH = "src/com/company/data.txt";
    public static final double PI = Math.PI;
    public static final double E = Math.expm1(1) + 1;

    public static void main(String[] args) {
        RK4 rk4 = new RK4((int) Math.pow(10, 5));
        System.out.println(rk4.solve(0, 1, 1, Main::derivative));
    }

    private static double derivative(double x, double... y) {
        return Math.cos(x);
    }
}
