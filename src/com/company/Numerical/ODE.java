package com.company.Numerical;

public interface ODE {
    static ODE add(ODE f1, ODE f2) {
        return (x, y) -> f1.derivative(x, y) + f2.derivative(x, y);
    }

    /**
     * @param x initial position
     * @param y initial values of y, y', y'' ...
     * @return
     */
    double derivative(double x, double... y);

    default ODE add(ODE ode) {
        return (x, y) -> this.derivative(x, y) + ode.derivative(x, y);
    }

    default ODE subtract(ODE ode) {
        return (x, y) -> this.derivative(x, y) - ode.derivative(x, y);
    }
}
