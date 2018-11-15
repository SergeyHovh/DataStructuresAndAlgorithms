package com.company.Algorithms;

public interface ODE {
    /**
     * @param x initial position
     * @param y initial values of y, y', y'' ...
     * @return
     */
    double derivative(double x, double... y);
}
