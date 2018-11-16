package com.company;

import com.company.Numerical.ODE;
import com.company.Numerical.RK4;
import com.company.Numerical.RK438;
import com.company.Numerical.RK4Classic;

import java.io.FileNotFoundException;
import java.util.Formatter;

public class Main {
    public static final String PATH = "src/com/company/data.txt";
    public static final double PI = Math.PI;
    public static final double E = Math.expm1(1) + 1;
    public static final double G = 9.8;

    public static void main(String[] args) {
        ODE ode = (x, y) -> -G / 4 * Math.sin(y[0]);
        RK4Classic rk4Classic = new RK4Classic();
        RK438 rk438 = new RK438();
        getPoints(0, new double[]{1, 0}, 10, ode, rk4Classic);
    }

    private static void getPoints(double x0, double[] y0, double upTo, ODE ode, RK4 rk) {
        Formatter file;
        double iter = 0.0;
        double[] before = new double[y0.length];
        System.arraycopy(y0, 0, before, 0, before.length);
        try {
            file = new Formatter(PATH);
            file.flush();
            while (iter < upTo) {
                double v1 = rk.solveHighOrder(x0, y0, iter, ode);
                file.format("%s", v1 + "\n");
                iter += 0.01;
                System.arraycopy(before, 0, y0, 0, before.length);
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
/*
 *
 * classic    3/8	3_8
 *
 * ab

 *
 * */
