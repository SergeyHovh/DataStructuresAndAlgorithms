package com.company.Numerical;

public class RK438 extends RK4 {
    public RK438() {
        super();
    }

    public RK438(int N) {
        super(N);
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
            y0[i] += K[0][i] / 3;
        }
        for (int i = 0; i < order; i++) {
            K[1][i] = h * system[i].derivative(x0 + h / 3, y0);
        }
        System.arraycopy(before, 0, y0, 0, order); // reset

        // k3
        for (int i = 0; i < order; i++) {
            y0[i] += -K[0][i] / 3 + K[1][i];
        }
        for (int i = 0; i < order; i++) {
            K[2][i] = h * system[i].derivative(x0 + 2 * h / 3, y0);
        }
        System.arraycopy(before, 0, y0, 0, order); // reset

        // k4
        for (int i = 0; i < order; i++) {
            y0[i] += K[0][i] - K[1][i] + K[2][i];
        }
        for (int i = 0; i < order; i++) {
            K[3][i] = h * system[i].derivative(x0 + h, y0);
        }
        System.arraycopy(before, 0, y0, 0, order); // reset

        for (int i = 0; i < K.length; i++) {
            double[] aK = K[i];
            for (int j = 0; j < aK.length; j++) {
                if (i == 0 || i == 3) {
                    K[i][j] /= 8.0;
                } else {
                    K[i][j] /= 8.0;
                    K[i][j] *= 3.0;
                }
            }
        }

        /*// k1
            for (int j = 0; j < order; j++) {
                k1[j] = h * system[j].derivative(x0, y0);
            }

            // k2
            for (int j = 0; j < order; j++) {
                y0[j] += k1[j] / 3;
            }
            for (int j = 0; j < order; j++) {
                k2[j] = h * system[j].derivative(x0 + h / 3, y0);
            }
            System.arraycopy(before, 0, y0, 0, order); // reset

            // k3
            for (int j = 0; j < order; j++) {
                y0[j] += (-k1[j] / 3 + k2[j]);
            }
            for (int j = 0; j < order; j++) {
                k3[j] = h * system[j].derivative(x0 + 2 * h / 3, y0);
            }
            System.arraycopy(before, 0, y0, 0, order); // reset

            // k4
            for (int j = 0; j < order; j++) {
                y0[j] += k1[j] - k2[j] + k3[j];
            }
            for (int j = 0; j < order; j++) {
                k4[j] = h * system[j].derivative(x0 + h, y0);
            }
            System.arraycopy(before, 0, y0, 0, order); // reset

            for (int j = 0; j < order; j++) {
                y0[j] += 1.0 / 8.0 * (k1[j] + 3 * k2[j] + 3 * k3[j] + k4[j]);
            }*/

        return K;
    }
}
