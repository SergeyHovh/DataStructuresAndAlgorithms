package com.company.Numerical.ODE;

import com.company.Numerical.ODE.Explicit.RKClassic;

import java.util.Arrays;

import static java.lang.Math.sqrt;

public abstract class ODESolver {

    private static int ITERATION_COUNT;

    private ODESolver(int STEP_SIZE) {
        ITERATION_COUNT = STEP_SIZE;
    }

    public ODESolver() {
        this(1000);
    }

    public static void main(String[] args) {
        ODESystem[] odeSystem = new ODESystem[]{
                (x, y) -> sqrt(y[1][0]),
                (x, y) -> 4 * y[0][0] * y[0][0]
        };
        ODESolver solve = new RKClassic();
        double[][] y0 = {
                {1, 1},
                {1, 2}
        };
        double[][] highOrderSystem = solve.solveHighOrderSystem(0, y0, 1, odeSystem);
        System.out.println(Arrays.deepToString(highOrderSystem));
    }

    public double[][] solveHighOrderSystem(double x0, double[][] y0, double x, ODESystem[] odeSystem) {
        return solveHighOrderSystem(x0, y0, x,
                highOrderSystemToFirstOrderSystem(odeSystem, y0[0].length, y0.length));
    }

    private double[][] solveHighOrderSystem(double x0, double[][] y0, double x, ODESystem[][] odeSystem) {
        int numberOfEquations = y0.length;
        int order = y0[0].length;
        double[][] before = new double[numberOfEquations][order];
        double h = (x - x0) / ITERATION_COUNT;
        for (int i = 0; i < numberOfEquations; i++) {
            System.arraycopy(y0[i], 0, before[i], 0, order);
        }

        double[][]
                K1 = new double[numberOfEquations][order],
                K2 = new double[numberOfEquations][order],
                K3 = new double[numberOfEquations][order],
                K4 = new double[numberOfEquations][order];

        for (int i = 0; i < ITERATION_COUNT; i++) {
            // K1
            for (int j = 0; j < numberOfEquations; j++) {
                for (int k = 0; k < order; k++) {
                    K1[j][k] = h * odeSystem[j][k].derivative(x0, y0);
                }
            }

            // K2
            for (int j = 0; j < numberOfEquations; j++) {
                for (int k = 0; k < order; k++) {
                    y0[j][k] += K1[j][k] / 2;
                }
            }
            for (int j = 0; j < numberOfEquations; j++) {
                for (int k = 0; k < order; k++) {
                    K2[j][k] = h * odeSystem[j][k].derivative(x0 + h / 2, y0);
                }
            }
            for (int j = 0; j < numberOfEquations; j++) {
                System.arraycopy(before[j], 0, y0[j], 0, order); // reset
            }
            // K3
            for (int j = 0; j < numberOfEquations; j++) {
                for (int k = 0; k < order; k++) {
                    y0[j][k] += K2[j][k] / 2;
                }
            }
            for (int j = 0; j < numberOfEquations; j++) {
                for (int k = 0; k < order; k++) {
                    K3[j][k] = h * odeSystem[j][k].derivative(x0 + h / 2, y0);
                }
            }
            for (int j = 0; j < numberOfEquations; j++) {
                System.arraycopy(before[j], 0, y0[j], 0, order); // reset
            }
            // K4
            for (int j = 0; j < numberOfEquations; j++) {
                for (int k = 0; k < order; k++) {
                    y0[j][k] += K3[j][k];
                }
            }
            for (int j = 0; j < numberOfEquations; j++) {
                for (int k = 0; k < order; k++) {
                    K4[j][k] = h * odeSystem[j][k].derivative(x0 + h, y0);
                }
            }
            for (int j = 0; j < numberOfEquations; j++) {
                System.arraycopy(y0[j], 0, before[j], 0, order); // update
            }
            x0 += h;
        }
        return y0;
    }

    private ODESystem[][] highOrderSystemToFirstOrderSystem(ODESystem[] system, int order, int numberOfEquations) {
        ODESystem[][] result = new ODESystem[numberOfEquations][order];
        for (int i = 0; i < numberOfEquations; i++) {
            for (int j = 0; j < order; j++) {
                if (j == order - 1) result[i][j] = system[i];
                else {
                    int finalI = i;
                    int finalJ = j;
                    result[i][j] = (x0, y0) -> y0[finalI][finalJ + 1];
                }
            }
        }
        return result;
    }

    /**
     * @param x0  initial position - x0
     * @param y0  initial value - f(x0)
     * @param x   desired point
     * @param ode y' = f(x, y) ode = f(x, y)
     * @return value of the function at point x
     */
    public double[] solveFirstOrder(double x0, double y0, double x, ODE ode) {
        return solveHighOrder(x0, new double[]{y0}, x, ode);
    }

    /**
     * @param x0      initial position - x0
     * @param y0      initial value - y(x0)
     * @param yPrime0 initial value - y'(x0)
     * @param x       desired point
     * @param ode     y'' = f(x, y, y') ode = f(x, y, y')
     * @return value of the function at point x
     */
    public double[] solveSecondOrder(double x0, double y0, double yPrime0, double x, ODE ode) {
        return solveHighOrder(x0, new double[]{y0, yPrime0}, x, ode);
    }

    public double[] solveHighOrder(double x0, double[] y0, double x, ODE ode) {
        return solveHighOrder(x0, y0, x, higherOrderToSystem(ode, y0.length));
    }

    /**
     * @param x0     initial position - x0
     * @param y0     initial values of y(x0), y'(x0), y''(x0), ...
     * @param x      desired position
     * @param system system of first order ODEs
     * @return value of the all derivatives at point x - y(x), y'(x), y''(x), ...
     */
    public double[] solveHighOrder(double x0, double[] y0, double x, ODE[] system) {
        int order = y0.length;
        double h = (x - x0) / ITERATION_COUNT;
        double[] before = new double[order];
        System.arraycopy(y0, 0, before, 0, order);

        for (int i = 0; i < ITERATION_COUNT; i++) {
            double[][] keys = generateKeys(generateUnweighted(system, x0, y0, before, h, coefficients()), coefficients(), false);
            for (double[] key : keys) {
                for (int j = 0; j < key.length; j++) {
                    y0[j] += key[j];
                }
            }
            System.arraycopy(y0, 0, before, 0, order); // update
            x0 += h; // increment
        }
        return y0;
    }

    protected abstract double[][] coefficients();

    protected double[][] generateKeys(double[][] K, double[][] coefficients, boolean f) {
        // adjust weights
        for (int i = 0; i < K.length; i++) {
            double[] aK = K[i];
            for (int j = 0; j < aK.length; j++) {
                double multiplier = coefficients[coefficients.length - 1][i + 1];
                K[i][j] *= multiplier;
            }
        }
        return K;
    }

    protected double[][] generateUnweighted(ODE[] system, double x0, double[] y0, double[] before, double h, double[][] coefficients) {
        int order = y0.length;
        double[][] K = new double[coefficients.length - 1][order];
        System.arraycopy(y0, 0, before, 0, order);
        // init Ks
        for (int i = 0; i < K.length; i++) {
            for (int j = 0; j < order; j++) {
                for (int k = 0; k < K.length; k++) {
                    y0[j] += coefficients[i][k + 1] * K[k][j];
                }
            }
            for (int j = 0; j < order; j++) {
                K[i][j] = h * system[j].derivative(x0 + coefficients[i][0] * h, y0);
            }
            System.arraycopy(before, 0, y0, 0, order); // reset
        }
        return K;
    }

    private ODE[] higherOrderToSystem(ODE ode, int order) {
        ODE[] system = new ODE[order];
        for (int i = 0; i < order; i++) {
            int finalI = i;
            if (i == order - 1) {
                system[i] = ode;
            } else {
                system[i] = (x1, y) -> y[finalI + 1];
            }
        }
        return system;
    }
}