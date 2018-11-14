package com.company.Algorithms;

public interface ODE {
    /**
     * @param x
     * @param y
     * @return dy/dx = f(x, y)
     */
    double derivative(double x, double y);
}
