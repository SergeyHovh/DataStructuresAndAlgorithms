package com.company.Numerical;

public class MidPoint extends RK {
    @Override
    protected double[][] coefficients() {
        return new double[][]{
                {0, 0, 0},
                {0.5, 0.5, 0},
                {0, 0, 1}
        };
    }
}
