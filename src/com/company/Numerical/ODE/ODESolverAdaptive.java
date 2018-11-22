package com.company.Numerical.ODE;

import java.util.Arrays;
import java.util.Hashtable;

public abstract class ODESolverAdaptive extends ODESolver {
    private final double min = 0.01;
    private final double max = 10 * min;
    private final double minErr = 1.0E-13;
    private final double maxErr = 2 * minErr;
    private double h = 0;
    private Hashtable<IVP, Hashtable<Double, Value>> odeValues = new Hashtable<>();
    private Value value = new Value();

    @Override
    public double solveHighOrder(double x0, double[] y0, double x, ODE[] system) {
        int order = y0.length;
        double[] before = new double[order];
        double[] high = new double[order];
        double[] low = new double[order];
        double[] current = new double[order];
        System.arraycopy(y0, 0, current, 0, order);
        System.arraycopy(y0, 0, before, 0, order);
        IVP ivp = new IVP(system, x0, current);
        odeValues.putIfAbsent(ivp, new Hashtable<>());
        int multiplier = 2;
        while (x0 < x) {
            adjustH();
            if (odeValues.get(ivp).containsKey(x0)) {
                System.arraycopy(odeValues.get(ivp).get(x0).value, 0, y0, 0, order);
                System.arraycopy(y0, 0, before, 0, order); // update
                x0 += odeValues.get(ivp).get(x0).h;
            } else { // computation
                double[][] keys = generateUnweighted(system, x0, y0, before, h, coefficients());
                System.arraycopy(rungeKutta(y0, keys, true), 0, high, 0, order);
                System.arraycopy(before, 0, y0, 0, order); // reset y0
                System.arraycopy(rungeKutta(y0, keys, false), 0, low, 0, order);
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
    protected double[][] generateKeys(double[][] unweighted, double[][] coefficients, boolean high) {
        // adjust weights
        for (int i = 0; i < unweighted.length; i++) {
            double[] aK = unweighted[i];
            for (int j = 0; j < aK.length; j++) {
                double multiplier;
                if (high) {
                    multiplier = coefficients[coefficients.length - 2][i + 1];
                } else {
                    multiplier = coefficients[coefficients.length - 1][i + 1];
                }
                unweighted[i][j] *= multiplier;
            }
        }
        return unweighted;
    }

    @Override
    protected double[][] generateUnweighted(ODE[] system, double x0, double[] y0, double[] before, double h, double[][] coefficients) {
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
        return K;
    }

    /**
     * @param y0   - initial value
     * @param keys unweighted keys
     * @param high true - returns high order RK, false - returns low order RK
     */
    private double[] rungeKutta(double[] y0, double[][] keys, boolean high) {
        double[][] generateKeys = generateKeys(keys, coefficients(), high);
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
        ODE[] ode;
        double x0;
        double[] y0;

        IVP(ODE[] ode, double x0, double[] y0) {
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
                return
                        this.x0 == ((IVP) obj).x0
                                && Arrays.equals(this.y0, ((IVP) obj).y0)
                                && Arrays.equals(this.ode, ((IVP) obj).ode);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            double Ys = 0, vals = 0;
            for (double v : y0) {
                Ys += v;
            }
            for (ODE ode1 : ode) {
                vals += ode1.derivative(x0, y0);
            }
            return 2 * (int) vals + 3 * (int) x0 + 7 * (int) Ys;
        }
    }
}
