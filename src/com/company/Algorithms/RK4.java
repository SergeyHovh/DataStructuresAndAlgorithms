package com.company.Algorithms;

public class RK4 {

    private int ITERATION_COUNT;

    public RK4(int STEP_SIZE) {
        this.ITERATION_COUNT = STEP_SIZE;
    }

    public RK4() {
        this.ITERATION_COUNT = 1000;
    }

    /**
     * @param x0  initial position - x0
     * @param y0  initial value - f(x0)
     * @param x   desired point
     * @param ode dx/dy = f(x, y) ode = f(x, y)
     * @return value of the function at point x
     */
    double solve(double x0, double y0, double x, ODE ode) {
        double k1, k2, k3, k4, k5;
        double h = (x - x0) / ITERATION_COUNT;
        double y = y0;
        for (int i = 0; i < ITERATION_COUNT; i++) {
            // init weights
            k1 = h * ode.derivative(x0, y);
            k2 = h * ode.derivative(x0 + h * 0.5, y + k1 * 0.5);
            k3 = h * ode.derivative(x0 + h * 0.5, y + k2 * 0.5);
            k4 = h * ode.derivative(x0 + h, y + k3);
            // update
            y += (1.0 / 6.0) * (k1 + 2 * k2 + 2 * k3 + k4);
            x0 += h;
        }
        return y;
    }
}
