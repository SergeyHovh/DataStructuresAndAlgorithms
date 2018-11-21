package com.company;

import com.company.Numerical.ODE.Embedded.RKDP;
import com.company.Numerical.ODE.Embedded.RKF45;
import com.company.Numerical.ODE.Explicit.RK438;
import com.company.Numerical.ODE.ODE;
import com.company.Numerical.ODE.ODESolver;

import java.io.FileNotFoundException;
import java.util.Formatter;

import static java.lang.Math.expm1;
import static java.lang.Math.sin;

public class Main {
    public static final String PATH = "src/com/company/data.txt";
    public static final double E = expm1(1) + 1;
    public static final double G = 9.8;

    public static void main(String[] args) {
        ODESolver rkf45 = new RKF45();
        ODESolver rkClassic = new RK438();
        ODESolver rkdp = new RKDP();
        ODE ode = (x, y) -> -5 * sin(y[0]);
        double x0 = 0;
        double y0 = 1;
        double yPrime0 = 0;
        getPoints(x0, new double[]{y0, yPrime0}, 10, ode, rkf45);
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
                double v1 = ODESolver.solveHighOrder(x0, y0, x0 + iter, ode);
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