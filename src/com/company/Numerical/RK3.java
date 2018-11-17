package com.company.Numerical;

public class RK3 extends RK {
    @Override
    protected double[][] coefficients() {
        return new double[][]{
                {0, 0, 0, 0},
                {0.5, 0.5, 0, 0},
                {1, -1, 2, 0},
                {0, 1.0 / 6, 2.0 / 3, 2.0 / 6}
        };
    }
}
