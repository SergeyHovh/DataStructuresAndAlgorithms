package com.company.Numerical.ODE.Explicit;

import com.company.Numerical.ODE.ODESolver;

public class Heun extends ODESolver {
    @Override
    protected double[][] coefficients() {
        return new double[][]{
                {0, 0, 0},
                {1, 1, 0},
                {0, 0.5, 0.5}
        };
    }
}
