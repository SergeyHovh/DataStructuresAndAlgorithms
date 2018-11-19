package com.company.Numerical.ODE;

public class MidPoint extends ODESolver {
    @Override
    protected double[][] coefficients() {
        return new double[][]{
                {0, 0, 0},
                {0.5, 0.5, 0},
                {0, 0, 1}
        };
    }
}
