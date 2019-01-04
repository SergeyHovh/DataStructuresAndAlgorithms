package com.company.Numerical.SLE;

import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class SLE {
    private double[][] coefficients;
    private double[] B;

    public SLE(double[][] coefficients, double[] B) {
        this.coefficients = coefficients;
        this.B = B;
    }

    public void eliminate() {
        eliminate(coefficients);
    }

    private void rowEchelonForm() {
        rowEchelonForm(coefficients);
    }

    private void rowEchelonForm(double[][] A) {
        int length = A.length;
        for (int i = 0; i < length; i++) {
            int j = i + 1;
            while (A[i][i] == 0) {
                changeRows(i, j++);
            }
        }
    }

    private void eliminate(double[][] A) {
        rowEchelonForm();
        int length = A.length;
        for (int i = 1; i < length; i++) {
            for (int j = i; j < length; j++) {
                if (abs(A[j][i - 1]) <= 1.0E-10) continue;
                addRows(i - 1, j, -A[j][i - 1] / A[i - 1][i - 1]);
            }
        }
        for (int i = 0; i < length; i++) {
            multiplyRow(i, 1 / A[i][i]);
        }
    }

    public void changeRows(int source, int dest) {
        addRows(source, dest, 1);
        addRows(dest, source, -1);
        addRows(source, dest, 1);

        multiplyRow(source, -1);
    }

    public void addRows(int source, int dest, double coefficient) {
        int length = coefficients.length;
        for (int i = 0; i < length; i++) {
            coefficients[dest][i] += coefficients[source][i] * coefficient;
        }
        B[dest] += B[source] * coefficient;
    }

    public void multiplyRow(int i, double coefficient) {
        int length = coefficients.length;
        for (int j = 0; j < length; j++) {
            coefficients[i][j] *= coefficient;
        }
        B[i] *= coefficient;
    }

    public double determinant() {
        return determinant(this.coefficients);
    }

    public SLE put(double[] x, int i) {
        return put(this, x, i);
    }

    private SLE put(SLE A, double[] x, int i) {
        int length = A.coefficients.length;
        double[][] temp = new double[length][length];
        for (int j = 0; j < length; j++) {
            System.arraycopy(A.coefficients[j], 0, temp[j], 0, length); // update
        }
        for (int j = 0; j < temp.length; j++) {
            temp[j][i] = x[j];
        }
        return new SLE(temp, A.B);
    }

    private double determinant(double[][] A) {
        double det = 0;
        if (A.length != A[0].length) return Double.MIN_VALUE;
        if (A.length == 2)
            return A[0][0] * A[1][1] - A[0][1] * A[1][0];
        else {
            for (int i = 0; i < A.length; i++) {
                if (abs(A[i][0]) <= 1.0E-10) continue;
                det += pow(-1, i) * A[i][0] * determinant(reduce(A, i, 0));
            }
            return det;
        }
    }

    private double[][] reduce(double[][] A, int i, int j) {
        double[][] aPrime = new double[A.length - 1][A.length - 1];
        int length = aPrime.length;
        int length1 = aPrime[0].length;
        for (int m = 0; m < length; m++) {
            for (int n = 0; n < length1; n++) {
                if (m <= i && n <= j) aPrime[m][n] = A[m][n];
                if (m >= i && n <= j) aPrime[m][n] = A[m + 1][n];
                if (m <= i && n >= j) aPrime[m][n] = A[m][n + 1];
                if (m >= i && n >= j) aPrime[m][n] = A[m + 1][n + 1];
            }
        }
        return aPrime;
    }

    public double[][] getCoefficients() {
        return coefficients;
    }

    public double[] getB() {
        return B;
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder();
        for (int i = 0, coefficientsLength = coefficients.length; i < coefficientsLength; i++) {
            double[] coefficient = coefficients[i];
            print.append(Arrays.toString(coefficient)).append(" | ").append(B[i]).append("\n");
        }
        return print.toString();
    }
}
