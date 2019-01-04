package com.company.Numerical.Fourier.Transform;

import static java.lang.Math.PI;

public class FFT {

    ComplexNumbers[] FastFourierTransform(double[] data) {
        int N = data.length;
        ComplexNumbers[] fin = new ComplexNumbers[N];
        // TODO: 12/12/2018 implement Fourier
        return fin;
    }


    ComplexNumbers exp(double k, double N) {
        return ComplexNumbers.eToIX(-2 * PI * k / N);
    }
}
