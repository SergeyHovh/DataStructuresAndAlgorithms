package com.company.Numerical.ODE;

public abstract class ODESolverAdaptive extends ODESolver {
    private final double min = 0.01;
    private final double max = 2 * min;
    private double h = 0, X = 0;


    @Override
    public double solveHighOrder(double x0, double[] y0, double x, ODE[] system) {
        double minErr = 1.0E-13;
        double maxErr = 2 * minErr;
        int order = y0.length;
        double[] before = new double[order];
        double[] highOrder = new double[order];
        double[] lowOrder = new double[order];
        System.arraycopy(y0, 0, before, 0, order);
        X = x0;
        while (X < x) {
            if (h <= min) h = min;
            else if (h >= max) h = max;

            System.arraycopy(before, 0, y0, 0, order); // reset y0
            System.arraycopy(highOrder(X, y0, system, h), 0, highOrder, 0, order);
            System.arraycopy(before, 0, y0, 0, order); // reset y0
            System.arraycopy(lowOrder(X, y0, system, h), 0, lowOrder, 0, order);
            double error = Math.abs(highOrder[0] - lowOrder[0]);
            int multiplier = 2;
            if (error > maxErr && h > min) {
                h /= multiplier;
            } else {
                System.arraycopy(highOrder, 0, y0, 0, order);
                System.arraycopy(y0, 0, before, 0, order); // update
                X += h;
                if (error < minErr && h <= max) h *= multiplier;
            }
        }
        return y0[0];
    }

    @Override
    protected double[][] generateKeys(ODE[] system, double x0, double[] y0, double[] before, double h, double[][] coefficients, boolean high) {
        int order = y0.length;
        double[][] K = new double[coefficients.length - 2][order];
        System.arraycopy(y0, 0, before, 0, order);
        // init Ks
        for (int i = 0; i < K.length; i++) {
            for (int j = 0; j < order; j++) {
                for (int k = 0; k < K.length; k++) {
                    y0[j] += coefficients[i][k + 1] * K[k][j];
                }
            }
            for (int j = 0; j < order; j++) {
                K[i][j] = h * system[j].derivative(x0 + coefficients[i][0] * h, y0);
            }
            System.arraycopy(before, 0, y0, 0, order); // reset
        }
        // adjust weights
        for (int i = 0; i < K.length; i++) {
            double[] aK = K[i];
            for (int j = 0; j < aK.length; j++) {
                double multiplier;
                if (high) {
                    multiplier = coefficients[coefficients.length - 2][i + 1];
                } else {
                    multiplier = coefficients[coefficients.length - 1][i + 1];
                }
                K[i][j] *= multiplier;
            }
        }
        return K;
    }

    private double[] highOrder(double x0, double[] y0, ODE[] system, double h) {
        int order = y0.length;
        double[] before = new double[order];
        System.arraycopy(y0, 0, before, 0, order);
        double[][] generateKeys = generateKeys(system, x0, y0, before, h, coefficients(), true);
        for (double[] generateKey : generateKeys) {
            for (int i = 0; i < generateKey.length; i++) {
                y0[i] += generateKey[i];
            }
        }
        return y0;
    }

    private double[] lowOrder(double x0, double[] y0, ODE[] system, double h) {
        int order = y0.length;
        double[] before = new double[order];
        System.arraycopy(y0, 0, before, 0, order);
        double[][] generateKeys = generateKeys(system, x0, y0, before, h, coefficients(), false);
        for (double[] generateKey : generateKeys) {
            for (int i = 0; i < generateKey.length; i++) {
                y0[i] += generateKey[i];
            }
        }
        return y0;
    }
}
