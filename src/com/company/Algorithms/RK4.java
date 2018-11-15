package com.company.Algorithms;

public class RK4 {

    private int ITERATION_COUNT;

    public RK4(int STEP_SIZE) {
        this.ITERATION_COUNT = STEP_SIZE;
    }

    public RK4() {
        this.ITERATION_COUNT = 1000;
    }

    public static void main(String[] args) {
        RK4 rk4 = new RK4(1000);
//        rk4.solveSystem(0, new double[]{1}, 1, (x, y) -> y[0]);
//        System.out.println(rk4.solve(0, 1, 1, (x, y) -> y[0]));
        System.out.println(rk4.solveSystem(0, new double[]{1, 0}, 1, (x, y) -> -6 * y[0] + 5 * y[1]));
        System.out.println(rk4.solveSecondOrder(0, 1, 0, 1, (x, y) -> -6 * y[0] + 5 * y[1]));
        System.out.println(Math.E * Math.E * (3 - 2 * Math.E));
    }

    public double solveSystem(double x0, double[] y0, double x, ODE ode) {
        int order = y0.length;
        double h = (x - x0) / ITERATION_COUNT;
        // construct the system
        ODE[] system = new ODE[order];
        for (int i = 0; i < order; i++) {
            int finalI = i;
            if (i == order - 1) {
                system[i] = ode;
            } else {
                system[i] = (x1, y) -> y[finalI + 1];
            }
        }
        double[] y = y0;
        double[] before = new double[order];
        System.arraycopy(y, 0, before, 0, order);
        double[] k1 = new double[order], k2 = new double[order], k3 = new double[order], k4 = new double[order];

        for (int i = 0; i < ITERATION_COUNT; i++) {
            // k1
            for (int j = 0; j < order; j++) {
                k1[j] = h * system[j].derivative(x0, y);
            }

            // k2
            for (int j = 0; j < order; j++) {
                y[j] += k1[j] / 2;
            }
            for (int j = 0; j < order; j++) {
                k2[j] = h * system[j].derivative(x0 + h / 2, y);
            }
            System.arraycopy(before, 0, y, 0, order);

            // k3
            for (int j = 0; j < order; j++) {
                y[j] += k2[j] / 2;
            }
            for (int j = 0; j < order; j++) {
                k3[j] = h * system[j].derivative(x0 + h / 2, y);
            }
            System.arraycopy(before, 0, y, 0, order);

            // k4
            for (int j = 0; j < order; j++) {
                y[j] += k3[j];
            }
            for (int j = 0; j < order; j++) {
                k4[j] = h * system[j].derivative(x0 + h, y);
            }
            System.arraycopy(before, 0, y, 0, order);

            for (int j = 0; j < order; j++) {
                y[j] += 1.0 / 6.0 * (k1[j] + 2 * k2[j] + 2 * k3[j] + k4[j]);
            }
            System.arraycopy(y, 0, before, 0, order);
            x0 += h;
        }
        return y[0];
    }

    /**
     * @param x0  initial position - x0
     * @param y0  initial value - f(x0)
     * @param x   desired point
     * @param ode dx/dy = f(x, y) ode = f(x, y)
     * @return value of the function at point x
     */
    public double solve(double x0, double y0, double x, ODE ode) {
        double k1, k2, k3, k4;
        double h = (x - x0) / ITERATION_COUNT;
        double y = y0;
        for (int i = 0; i < ITERATION_COUNT; i++) {
            // init weights
            // h*f(x0, y)
            k1 = h * ode.derivative(x0, y);
            // h*f(x0+h/2, y+k1/2)
            k2 = h * ode.derivative(x0 + h * 0.5, y + k1 * 0.5);
            // h*f(x0+h/2, y+k2/2)
            k3 = h * ode.derivative(x0 + h * 0.5, y + k2 * 0.5);
            // h*f(x0+h, y+k3)
            k4 = h * ode.derivative(x0 + h, y + k3);
            // update
            y += (1.0 / 6.0) * (k1 + 2 * k2 + 2 * k3 + k4);
            x0 += h;
        }
        return y;
    }

    public double solveSecondOrder(double x0, double y0, double yPrime0, double x, ODE ode) {
        return solveSystem(x0, new double[]{y0, yPrime0}, x, ode);
    }
}