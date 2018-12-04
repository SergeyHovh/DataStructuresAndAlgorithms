package com.company.Numerical.Spline;

import static java.lang.Math.pow;

public class NaturalCubicSpline {

    private double[] A = new double[2], B = new double[2], C = new double[2], D = new double[2];

    NaturalCubicSpline(Point[] points) {
        double Y = points[2].getyCoordinate();
        double X0 = points[1].getxCoordinate();
        double X1 = points[2].getxCoordinate();
        A[0] = -Y / (X1 * (X1 - X0) * (X1 + X0));
        B[0] = 0;
        C[0] = -A[0] * pow(X0, 2);
        D[0] = 0;
        A[1] = -A[0];
        B[1] = 0;
        C[1] = -C[0];
        D[1] = 0;
    }

    double Si(double x, int i) {
        return A[i] * pow(x, 3) + B[i] * pow(x, 2) + C[i] * x + D[i];
    }

    double SiPrime(double x, int i) {
        return 3 * A[i] * pow(x, 2) + 2 * B[i] * x + C[i];
    }

    double SiDoublePrime(double x, int i) {
        return 6 * A[i] * x + 2 * B[i];
    }

    public double[] getA() {
        return A;
    }

    public void setA(double[] a) {
        A = a;
    }

    public double[] getB() {
        return B;
    }

    public void setB(double[] b) {
        B = b;
    }

    public double[] getC() {
        return C;
    }

    public void setC(double[] c) {
        C = c;
    }

    public double[] getD() {
        return D;
    }

    public void setD(double[] d) {
        D = d;
    }
}
