package com.company.Numerical.ODE;

public interface ODESystem {
    /**
     * @param x0 initial position
     * @param y0 initial values - y[0][0] = y(x0), y[0][1] = y'(x0), y[1][0] = z(x0), y[1][1] = z'(x0), ...
     */
    double derivative(double x0, double[][] y0);
}
