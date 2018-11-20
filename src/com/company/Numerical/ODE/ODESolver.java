package com.company.Numerical.ODE;

public abstract class ODESolver {

    int ITERATION_COUNT;

    ODESolver(int STEP_SIZE) {
        this.ITERATION_COUNT = STEP_SIZE;
    }

    public ODESolver() {
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

        for (int i = 0; i < ITERATION_COUNT; i++) {
            double[][] keys = generateKeys(system, x0, y0, before, h, coefficients(), false);
            for (double[] key : keys) {
                for (int j = 0; j < key.length; j++) {
                    y0[j] += key[j];
                }
            }
            System.arraycopy(y0, 0, before, 0, order); // update
            x0 += h; // increment
        }
        return y0[0];
    }

    protected abstract double[][] coefficients();

    protected double[][] generateKeys(ODE[] system, double x0, double[] y0, double[] before, double h, double[][] coefficients, boolean f) {
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