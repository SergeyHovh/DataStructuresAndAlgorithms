package com.company;

import com.company.Numerical.ODE.Embedded.CashKarp;
import com.company.Numerical.ODE.Embedded.RKDP;
import com.company.Numerical.ODE.Embedded.RKF45;
import com.company.Numerical.ODE.Explicit.RKClassic;
import com.company.Numerical.ODE.ODE;
import com.company.Numerical.ODE.ODESolver;

import java.io.FileNotFoundException;
import java.util.Formatter;

import static java.lang.Math.expm1;

public class Main {
    public static final String PATH = "src/com/company/data.txt";
    public static final double E = expm1(1) + 1;
    public static final double G = 9.8;

    public static void main(String[] args) {
        ODESolver rkf45 = new RKF45();
        ODESolver rkClassic = new RKClassic();
        ODESolver cashkarp = new CashKarp();
        ODESolver rkdp = new RKDP();
        ODE ode = (x, y) -> y[0];
        double x = 1;
        double rkf = rkf45.solveSecondOrder(0, 1, 0, x, ode);
        double cashKarp = cashkarp.solveSecondOrder(0, 1, 0, x, ode);
        double RKDP = rkdp.solveSecondOrder(0, 1, 0, x, ode);
        double classic = rkClassic.solveSecondOrder(0, 1, 0, x, ode);
        System.out.println("rkf = " + rkf);
        System.out.println("cashKarp = " + cashKarp);
        System.out.println("RKDP = " + RKDP);
        System.out.println("classic = " + classic);
        System.out.println(classic - rkf);
        System.out.println(classic - cashKarp);
        System.out.println(classic - RKDP);
        System.out.println(rkf - cashKarp);
    }

    private static void getPoints(double x0, double[] y0, double upTo, ODE ode, ODESolver ODESolver) {
        Formatter file;
        double iter = 0.0;
        double[] before = new double[y0.length];
        System.arraycopy(y0, 0, before, 0, before.length);
        try {
            file = new Formatter(PATH);
            file.flush();
            while (iter < upTo) {
                double v1 = ODESolver.solveHighOrder(x0, y0, iter, ode);
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