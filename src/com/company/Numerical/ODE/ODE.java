package com.company.Numerical.ODE;

public interface ODE {
    static ODE add(ODE f1, ODE f2) {
        return (x, y) -> f1.derivative(x, y) + f2.derivative(x, y);
    }

    /**
     * @param x initial position
     * @param y initial values of y, y', y'' ...
     */
    double derivative(double x, double... y);

    default ODE add(ODE ode) {
        return (x, y) -> this.derivative(x, y) + ode.derivative(x, y);
    }

    default ODE subtract(ODE ode) {
        return (x, y) -> this.derivative(x, y) - ode.derivative(x, y);
    }

    default boolean equal(ODE ode) {
        for (double i = 0; i < 100; i++) {
            if (!(this.derivative(i / 100, 1, 0) == ode.derivative(i / 100, 1, 0))) {
                return false;
            }
        }
        return true;
    }
}
