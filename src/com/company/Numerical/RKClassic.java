package com.company.Numerical;

public class RKClassic extends RK {
    public RKClassic(int N) {
        super(N);
    }

    public RKClassic() {
        super();
    }

    @Override
    double[][] keys(ODE[] system, double x0, double[] y0, double[] before, double h) {
        double[][] coefficients = new double[][]{
                {0, 0, 0, 0, 0},
                {0.5, 0.5, 0, 0, 0},
                {0.5, 0, 0.5, 0, 0},
                {1, 0, 0, 1, 0},
                {0, 1.0 / 6.0, 1.0 / 3.0, 1.0 / 3.0, 1.0 / 6.0}
        };
        return generateKeys(system, x0, y0, before, h, coefficients);
    }
}
