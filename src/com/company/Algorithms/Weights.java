package com.company.Algorithms;

import java.util.Arrays;

public class Weights {
    private int m1, m2, m3, m4;
    private int[] weigths;

    public Weights() {
        this(1, 2, 3, 4);
    }

    public Weights(int k1, int k2, int k3, int k4) {
        m1 = k1;
        m2 = k2;
        m3 = k3;
        m4 = k4;
    }

    Weights(int... weigths) {
        this.weigths = weigths;
    }

    public static void count(int M, int N, int... weights) {
        int iterator = 1, times = weights.length;
        int[] Xs = new int[times];
        Arrays.sort(weights);

    }

    /**
     * prints all possible combinations of 4 different rocks to get M mass with exactly N rocks
     *
     * @param k1 weight of the first rock
     * @param k2 weight of the second rock
     * @param k3 weight of the third rock
     * @param k4 weight of the forth rock
     * @param M  total mass
     * @param N  exact number of rocks needed
     */
    public static void count(int k1, int k2, int k3, int k4, int M, int N) {
        System.out.println(M + " KGs with " + N + " weights" + '\n');
        int x1, x2, x3, x4, iterator = 1;
        int[] vals = {k1, k2, k3, k4};
        Arrays.sort(vals);
        for (int i = 0; i < M / vals[2] + 1; ++i) {
            for (int j = 0; j < M / vals[3] + 1; ++j) {
                x3 = i;
                x4 = j;
                x1 = (k2 * N - M - x3 * (k2 - k3) - x4 * (k2 - k4)) / (k2 - k1); // GAUSS-JORDAN ELIMINATION
                x2 = (M - k1 * N - x3 * (k3 - k1) - x4 * (k4 - k1)) / (k2 - k1); // GAUSS-JORDAN ELIMINATION
                if (x1 + x2 + x3 + x4 == N && x1 * k1 + x2 * k2 + x3 * k3 + x4 * k4 == M && x1 >= 0 && x2 >= 0) {
                    System.out.println(iterator++ + ") " + '\n' +
                            k1 + " kg weights: " + x1 + '\n' +
                            k2 + " kg weights: " + x2 + '\n' +
                            k3 + " kg weights: " + x3 + '\n' +
                            k4 + " kg weights: " + x4 + '\n');
                }
            }
        }
    }

    public void count(int M, int N) {
        count(m1, m2, m3, m4, M, N);
    }
}
