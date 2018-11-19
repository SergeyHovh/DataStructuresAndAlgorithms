package com.company.Numerical.ODE;

public abstract class ODESolverAdaptive extends ODESolver {
    private final double minH = 1.0E-07;
    private final double maxH = 1.0E-03;

    @Override
    public double solveHighOrder(double x0, double[] y0, double x, ODE[] system) {
        int order = y0.length;
        double h = (x - x0) / ITERATION_COUNT, error;
        double[] before = new double[order];
        double[] highOrder = new double[order];
        double[] lowOrder = new double[order];
        System.arraycopy(y0, 0, before, 0, order);
        while (x0 < x) {
            System.arraycopy(highOrder(x0, y0, system, h), 0, highOrder, 0, order);
            System.arraycopy(before, 0, y0, 0, order);
            System.arraycopy(lowOrder(x0, y0, system, h), 0, lowOrder, 0, order);
            error = Math.abs(highOrder[0] - lowOrder[0]);
            double minErr = 1.0E-11;
            double maxErr = 1.5 * minErr;
            if (error > maxErr && h > minH) {
                h /= 2;
            } else {
                x0 += h;
                System.arraycopy(highOrder, 0, y0, 0, highOrder.length);
                System.arraycopy(y0, 0, before, 0, order); // update
                if (error < minErr && h < maxH) h *= 2;
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
//                    System.out.println("high " + coefficients[coefficients.length - 2][i + 1]);
                    multiplier = coefficients[coefficients.length - 2][i + 1];
                } else {
//                    System.out.println("low " + coefficients[coefficients.length - 1][i + 1]);
                    multiplier = coefficients[coefficients.length - 1][i + 1];
                }
                K[i][j] *= multiplier;
            }
        }
        return K;
    }

    private double[] highOrder(double x0, double[] y0, ODE[] system, double h) {
        int order = y0.length;
        if (h < minH) h = minH;
        else if (h > maxH) h = maxH;
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
        if (h < minH) h = minH;
        else if (h > maxH) h = maxH;
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
