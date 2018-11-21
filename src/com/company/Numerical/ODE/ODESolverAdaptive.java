package com.company.Numerical.ODE;

import java.util.Arrays;
import java.util.Hashtable;

public abstract class ODESolverAdaptive extends ODESolver {
    private final double min = 0.01;
    private final double max = 5 * min;
    IVP ivp;
    private Hashtable<IVP, Hashtable<Double, Value>> odeValues = new Hashtable<>();
    private double h = 0;

    @Override
    public double solveHighOrder(double x0, double[] y0, double x, ODE[] system) {
        double minErr = 1.0E-13;
        double maxErr = 2 * minErr;
        int order = y0.length;
        ODE ode = system[order - 1];
        double[] before = new double[order];
        double[] high = new double[order];
        double[] low = new double[order];
        double[] current = new double[order];
        Value value = new Value();
        System.arraycopy(y0, 0, current, 0, order);
        // set the unique initial value problem
        ivp = new IVP(ode, x0, current);
        odeValues.put(ivp, new Hashtable<>());
        for (IVP ivp1 : odeValues.keySet()) {
            if (ivp1.equals(ivp)) {
                ivp = ivp1;
            }
        }
        System.arraycopy(y0, 0, before, 0, order);
        int multiplier = 2;
        while (x0 < x) {
            System.arraycopy(before, 0, y0, 0, order); // reset y0
            adjustH();
            if (odeValues.get(ivp).containsKey(x0)) {
                System.arraycopy(odeValues.get(ivp).get(x0).value, 0, y0, 0, order);
                System.arraycopy(y0, 0, before, 0, order); // update
                x0 += odeValues.get(ivp).get(x0).h;
            } else { // computation
                System.arraycopy(rungeKutta(x0, y0, system, h, true), 0, high, 0, order);
                System.arraycopy(before, 0, y0, 0, order); // reset y0
                System.arraycopy(rungeKutta(x0, y0, system, h, false), 0, low, 0, order);
                double error = Math.abs(high[0] - low[0]);
                if (error > maxErr && h > min) {
                    h /= multiplier;
                } else {
                    System.arraycopy(high, 0, y0, 0, order);
                    System.arraycopy(y0, 0, before, 0, order); // update
                    value.setValue(high);
                    value.setH(h);
                    odeValues.get(ivp).put(x0, value);
                    x0 += h;
                    if (error < minErr && h <= max) h *= multiplier;
                }
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

    /**
     * @param x0     - initial position
     * @param y0     - initial value
     * @param system - system of first order ODEs
     * @param h      - step size
     * @param high   true - returns high order RK, false - returns low order RK
     */
    private double[] rungeKutta(double x0, double[] y0, ODE[] system, double h, boolean high) {
        int order = y0.length;
        double[] before = new double[order];
        System.arraycopy(y0, 0, before, 0, order);
        double[][] generateKeys = generateKeys(system, x0, y0, before, h, coefficients(), high);
        for (double[] generateKey : generateKeys) {
            for (int i = 0; i < generateKey.length; i++) {
                y0[i] += generateKey[i];
            }
        }
        return y0;
    }

    private void adjustH() {
        if (h < min) h = min;
        else if (h > max) h = max;
    }

    class Value {
        double[] value;
        double h;

        void setValue(double[] value) {
            this.value = value;
        }

        void setH(double h) {
            this.h = h;
        }
    }

    class IVP {
        ODE ode;
        double x0;
        double[] y0;

        IVP(ODE ode, double x0, double[] y0) {
            this.ode = ode;
            this.x0 = x0;
            this.y0 = y0;
        }

        @Override
        public String toString() {
            return x0 + " " + Arrays.toString(y0);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof IVP) {
                return this.x0 == ((IVP) obj).x0 && Arrays.equals(this.y0, ((IVP) obj).y0);
            } else {
                return false;
            }
        }
    }
}
