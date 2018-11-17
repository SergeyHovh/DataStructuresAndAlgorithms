package com.company.Numerical;

public class Euler extends RK {
    @Override
    protected double[][] coefficients() {
        return new double[][]{
                {0, 0},
                {0, 1}
        };
    }
}
