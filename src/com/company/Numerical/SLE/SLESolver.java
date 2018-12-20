package com.company.Numerical.SLE;

public class SLESolver {

    /**
     * Solves Ax=b by Cramer's rule (works fast for up to 20 equations)
     */
    public static double[] solveCramer(SLE system) {
        system.eliminate();
        double[] b = system.getB();
        int length = b.length;
        double[] X = new double[length];
        double determinant = system.determinant(); // computing the determinant of the original matrix
        for (int i = 0; i < length; i++) {
            SLE p = system.put(b, i);
            X[i] = p.determinant() / determinant;
        }
        return X;
    }

    /**
     * solves Ax=b by Gauss-Jordan elimination rule (very fast)
     */
    public static double[] solveGaussJordan(SLE system) {
        system.eliminate();
        double[][] A = system.getCoefficients();
        int length = A.length;

        for (int i = length - 2; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                system.addRows(i + 1, j, -A[j][i + 1]);
            }
        }
        return system.getB();
    }
}
