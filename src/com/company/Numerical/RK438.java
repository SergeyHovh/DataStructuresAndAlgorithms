package com.company.Numerical;

public class RK438 extends RK {
    public RK438() {
        super();
    }

    public RK438(int N) {
        super(N);
    }

    @Override
    protected double[][] coefficients() {
        return new double[][]{
                {0, 0, 0, 0, 0},
                {1.0 / 3, 1.0 / 3, 0, 0, 0},
                {2.0 / 3, -1.0 / 3, 1, 0, 0},
                {1, 1, -1, 1, 0},
                {0, 1.0 / 8, 3.0 / 8, 3.0 / 8, 1.0 / 8}
        };
    }
}
