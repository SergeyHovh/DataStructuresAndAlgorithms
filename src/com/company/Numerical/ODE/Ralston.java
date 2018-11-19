package com.company.Numerical.ODE;

public class Ralston extends ODESolver {
    @Override
    protected double[][] coefficients() {
        return new double[][]{
                {0, 0, 0},
                {2 / 3, 2 / 3, 0},
                {0, 1 / 4, 3 / 4}
        };
    }
}
