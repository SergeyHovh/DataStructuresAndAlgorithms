package com.company.Numerical.SLE;

import static java.lang.Math.pow;

public class SLESolver {
    private static double determinant(double[][] A) {
        double det = 0;
        if (A.length != A[0].length) return Double.MIN_VALUE;
        if (A.length == 2) {
            return A[0][0] * A[1][1] - A[1][0] * A[0][1]; // 2X2 matrix determinant
        } else {
            for (int i = 0; i < A.length; i++) {
                if (A[i][0] == 0) continue;
                det += pow(-1, i) * A[i][0] * determinant(reduce(A, i, 0));
            }
            return det;
        }
    }

    private static double[][] reduce(double[][] A, int i, int j) {
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

    /**
     * replace i-th column of A by b
     *
     * @param A initial matrix
     * @param b desired column
     * @param i column number
     * @return modified matrix
     */
    private static double[][] put(double[][] A, double[] b, int i) {
        for (int j = 0; j < A[0].length; j++) {
            A[j][i] = b[j];
        }
        return A;
    }

    /**
     * Solves Ax=b by Cramer's rule (works fast for up to 8 equations)
     */
    public static double[] solveCramer(double[][] A, double[] b) {
        int length = b.length;
        double[][] before = new double[length][length];
        double[] X = new double[length];
        double determinant = determinant(A); // computing the determinant of the original matrix
        for (int i = 0; i < before.length; i++) {
            System.arraycopy(A[i], 0, before[i], 0, length); // update
        }
        for (int i = 0; i < length; i++) {
            double[][] put = put(A, b, i);
            X[i] = determinant(put) / determinant;
            for (int j = 0; j < before.length; j++) {
                System.arraycopy(before[j], 0, A[j], 0, length); // update
            }
        }
        return X;
    }

    static double[] matrixVectorMultiply(double[][] A, double[] x) {
        double[] X = new double[x.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                X[i] += A[i][j] * x[j];
            }
        }
        return X;
    }
}
