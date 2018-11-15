package com.company;

import com.company.Algorithms.RK4;

import java.io.FileNotFoundException;
import java.util.Formatter;

public class Main {
    public static final String PATH = "src/com/company/data.txt";
    public static final double PI = Math.PI;
    public static final double E = Math.expm1(1) + 1;
    public static final double G = 9.8;

    public static void main(String[] args) {
        RK4 rk4 = new RK4();
        Formatter file;
        double iter = 0.0, R = 1;
        try {
            file = new Formatter(PATH);
            file.flush();
            while (iter < 10) {
                double v = rk4.solveSecondOrder(0, 1, 0, iter, (x, y) -> -G / R * Math.sin(y[0]));
                file.format("%s", v + "\n");
                iter += 0.01;
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
