package com.company.Numerical;

public class RK4Classic extends RK4 {
    public RK4Classic(int N) {
        super(N);
    }

    public RK4Classic() {
        super();
    }

    @Override
    double[][] keys(ODE[] system, double x0, double[] y0, double[] before, double h) {
        int order = y0.length;
        double[][] K = new double[4][order];
        System.arraycopy(y0, 0, before, 0, order);
        // k1
        for (int i = 0; i < order; i++) {
            K[0][i] = h * system[i].derivative(x0, y0);
        }

        // k2
        for (int i = 0; i < order; i++) {
            y0[i] += K[0][i] / 2;
        }
        for (int i = 0; i < order; i++) {
            K[1][i] = h * system[i].derivative(x0 + h / 2, y0);
        }
        System.arraycopy(before, 0, y0, 0, order); // reset

        // k3
        for (int i = 0; i < order; i++) {
            y0[i] += K[1][i] / 2;
        }
        for (int i = 0; i < order; i++) {
            K[2][i] = h * system[i].derivative(x0 + h / 2, y0);
        }
        System.arraycopy(before, 0, y0, 0, order); // reset

        // k4
        for (int i = 0; i < order; i++) {
            y0[i] += K[2][i];
        }
        for (int i = 0; i < order; i++) {
            K[3][i] = h * system[i].derivative(x0 + h, y0);
        }
        System.arraycopy(before, 0, y0, 0, order); // reset

        // adjust weights
        for (int i = 0; i < K.length; i++) {
            double[] keys = K[i];
            for (int j = 0; j < keys.length; j++) {
                if (i == 0 || i == 3) {
                    K[i][j] /= 6.0;
                } else {
                    K[i][j] /= 3.0;
                }
            }
        }
        return K;
    }
}
