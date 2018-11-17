package com.company.Numerical;

public class RKClassic extends RK {
    public RKClassic(int N) {
        super(N);
    }

    public RKClassic() {
        super();
    }

    @Override
    protected double[][] coefficients() {
        return new double[][]{
                {0, 0, 0, 0, 0},
                {0.5, 0.5, 0, 0, 0},
                {0.5, 0, 0.5, 0, 0},
                {1, 0, 0, 1, 0},
                {0, 1.0 / 6.0, 1.0 / 3.0, 1.0 / 3.0, 1.0 / 6.0}
        };
    }
}