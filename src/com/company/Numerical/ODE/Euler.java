package com.company.Numerical.ODE;

public class Euler extends ODESolver {
    @Override
    protected double[][] coefficients() {
        return new double[][]{
                {0, 0},
                {0, 1}
        };
    }
}
