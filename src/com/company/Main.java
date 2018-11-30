package com.company;

import com.company.Numerical.ODE.Embedded.RKDP;
import com.company.Numerical.ODE.Embedded.RKF45;
import com.company.Numerical.ODE.Explicit.RK438;
import com.company.Numerical.ODE.ODESolver;
import com.company.Numerical.ODE.ODESystem;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Formatter;

import static java.lang.Math.*;

public class Main {
    public static final String PATH = "src/com/company/data.txt";
    public static final double E = expm1(1) + 1;
    public static final double G = 9.8;

    public static void main(String[] args) {
        ODESolver rkClassic = new RK438();
        ODESolver rkf45 = new RKF45();
        ODESolver rkdp = new RKDP();
        double[][] initialConditions = {
                {1, 1}, // first equation
                {1, 2}  // second equation
        };
        ODESystem[] system = {
                (x, y) -> sqrt(y[1][0]),        // first equation
                (x, y) -> 4 * pow(y[0][0], 2)   // second equation
        };
        double[][] rkClassicResult = rkClassic.solveHighOrder(0, initialConditions, 0.1, system);
        System.out.println(Arrays.deepToString(rkClassicResult));
//        System.out.println(E + " " + pow(E, 2) + " " + 2 * pow(E, 2));
    }

    private static void getPoints(double x0, double[][] y0, double upTo, ODESystem[] ode, ODESolver ODESolver) {
        Formatter file;
        double iter = 0.0;
        double[][] before = new double[y0.length][y0[0].length];
        System.arraycopy(y0, 0, before, 0, before.length);
        try {
            file = new Formatter(PATH);
            file.flush();
            while (iter < upTo) {
                double v1 = ODESolver.solveHighOrder(x0, y0, x0 + iter, ode)[0][0];
                file.format("%s", v1 + "\n");
                iter += 0.01;
                for (int j = 0; j < y0.length; j++) {
                    System.arraycopy(before[j], 0, y0[j], 0, y0[0].length); // reset
                }
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}