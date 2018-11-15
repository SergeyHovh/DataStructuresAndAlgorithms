package com.company.Algorithms;

public class RK4 {

    private int ITERATION_COUNT;

    public RK4(int STEP_SIZE) {
        this.ITERATION_COUNT = STEP_SIZE;
    }

    public RK4() {
        this(1000);
    }

    /**
     * @param x0  initial position - x0
     * @param y0  initial value - f(x0)
     * @param x   desired point
     * @param ode y' = f(x, y) ode = f(x, y)
     * @return value of the function at point x
     */
    public double solveFirstOrder(double x0, double y0, double x, ODE ode) {
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
    public double solveSecondOrder(double x0, double y0, double yPrime0, double x, ODE ode) {
        return solveHighOrder(x0, new double[]{y0, yPrime0}, x, ode);
    }

    public double[] solveHighSystem(double x0[], double y0[][], double x[], ODE[] odes) {
        int numberOfEquations = x0.length;
        double[] result = new double[numberOfEquations];
        for (int i = 0; i < numberOfEquations; i++) {
            result[i] = solveHighOrder(x0[i], y0[i], x[i], odes[i]);
        }
        return result;
    }

    public double solveHighOrder(double x0, double[] y0, double x, ODE ode) {
        return solveHighOrder(x0, y0, x, higherOrderToSystem(ode, y0.length));
    }

    /**
     * @param x0     initial position - x0
     * @param y0     initial values of y(x0), y'(x0), y''(x0), ...
     * @param x      desired position
     * @param system system of first order ODEs
     * @return value of the function at point x
     */
    public double solveHighOrder(double x0, double[] y0, double x, ODE[] system) {
        int order = y0.length;
        double h = (x - x0) / ITERATION_COUNT;
        double[] before = new double[order];
        System.arraycopy(y0, 0, before, 0, order);
        double[] k1 = new double[order], k2 = new double[order], k3 = new double[order], k4 = new double[order];

        for (int i = 0; i < ITERATION_COUNT; i++) {
            // k1
            for (int j = 0; j < order; j++) {
                k1[j] = h * system[j].derivative(x0, y0);
            }

            // k2
            for (int j = 0; j < order; j++) {
                y0[j] += k1[j] / 2;
            }
            for (int j = 0; j < order; j++) {
                k2[j] = h * system[j].derivative(x0 + h / 2, y0);
            }
            System.arraycopy(before, 0, y0, 0, order); // reset

            // k3
            for (int j = 0; j < order; j++) {
                y0[j] += k2[j] / 2;
            }
            for (int j = 0; j < order; j++) {
                k3[j] = h * system[j].derivative(x0 + h / 2, y0);
            }
            System.arraycopy(before, 0, y0, 0, order); // reset

            // k4
            for (int j = 0; j < order; j++) {
                y0[j] += k3[j];
            }
            for (int j = 0; j < order; j++) {
                k4[j] = h * system[j].derivative(x0 + h, y0);
            }
            System.arraycopy(before, 0, y0, 0, order); // reset

            for (int j = 0; j < order; j++) {
                y0[j] += 1.0 / 6.0 * (k1[j] + 2 * k2[j] + 2 * k3[j] + k4[j]);
            }
            System.arraycopy(y0, 0, before, 0, order); // update
            x0 += h; // increment
        }
        return y0[0];
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