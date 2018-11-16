package com.company;

import com.company.Algorithms.ODE;
import com.company.Algorithms.RK4;

import java.io.FileNotFoundException;
import java.util.Formatter;

public class Main {
    public static final String PATH = "src/com/company/data.txt";
    public static final double PI = Math.PI;
    public static final double E = Math.expm1(1) + 1;
    public static final double G = 9.8;

    public static void main(String[] args) {
        getPoints(0, new double[]{1, 0}, (x, y) -> -5 * Math.sin(y[0]));

    }

    private static void getPoints(double x0, double[] y0, ODE ode) {
        RK4 rk4 = new RK4();
        Formatter file;
        double iter = 0.0;
        double[] before = new double[y0.length];
        System.arraycopy(y0, 0, before, 0, before.length);
        try {
            file = new Formatter(PATH);
            file.flush();
            while (iter < 10) {
                double v = rk4.solveHighOrder(x0, y0, iter, ode);
                file.format("%s", v + "\n");
                iter += 0.01;
                System.arraycopy(before, 0, y0, 0, before.length);
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
