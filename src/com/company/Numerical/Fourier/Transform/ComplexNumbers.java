package com.company.Numerical.Fourier.Transform;

import java.util.Objects;

import static java.lang.Math.*;

public class ComplexNumbers {
    private double real, imaginary;

    public ComplexNumbers(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public static ComplexNumbers add(ComplexNumbers z1, ComplexNumbers z2) {
        double real = z1.real + z2.real;
        double imaginary = z1.imaginary + z2.imaginary;
        return new ComplexNumbers(real, imaginary);
    }

    public static ComplexNumbers subtract(ComplexNumbers z1, ComplexNumbers z2) {
        double real = z1.real - z2.real;
        double imaginary = z1.imaginary - z2.imaginary;
        return new ComplexNumbers(real, imaginary);
    }

    public static ComplexNumbers multiply(ComplexNumbers z1, ComplexNumbers z2) {
        double real = z1.real * z2.real - z1.imaginary * z2.imaginary;
        double imaginary = z1.real * z2.imaginary + z1.imaginary * z2.real;
        return new ComplexNumbers(real, imaginary);
    }

    public static ComplexNumbers multiply(ComplexNumbers z1, double scalar) {
        double real = z1.real * scalar;
        double imaginary = z1.imaginary * scalar;
        return new ComplexNumbers(real, imaginary);
    }

    /**
     * e^(ix)
     */
    public static ComplexNumbers eToIX(double x) {
        return new ComplexNumbers(cos(x), sin(x));
    }

    public ComplexNumbers add(ComplexNumbers complexNumber) {
        double real = this.real + complexNumber.real;
        double imaginary = this.imaginary + complexNumber.imaginary;
        return new ComplexNumbers(real, imaginary);
    }

    public ComplexNumbers subtract(ComplexNumbers complexNumber) {
        double real = this.real - complexNumber.real;
        double imaginary = this.imaginary - complexNumber.imaginary;
        return new ComplexNumbers(real, imaginary);
    }

    public ComplexNumbers multiply(ComplexNumbers complexNumber) {
        double real = this.real * complexNumber.real - this.imaginary * complexNumber.imaginary;
        double imaginary = this.real * complexNumber.imaginary + this.imaginary * complexNumber.real;
        return new ComplexNumbers(real, imaginary);
    }

    public ComplexNumbers multiply(double scalar) {
        double real = this.real * scalar;
        double imaginary = this.imaginary * scalar;
        return new ComplexNumbers(real, imaginary);
    }

    public double length() {
        return sqrt(pow(this.real, 2) + pow(this.imaginary, 2));
    }

    public double phiDeg() {
        return toDegrees(phi());
    }

    public double phi() {
        if (this.real == 0 && this.imaginary == 0) {
            return Double.MIN_VALUE;
        } else if (this.real == 0) {
            if (this.imaginary > 0) {
                return PI / 2;
            } else {
                return 1.5 * PI;
            }
        } else if (this.imaginary == 0) {
            if (this.real > 0) return 0;
            else return PI;
        } else {
            if (real < 0 && imaginary > 0)
                return PI + atan(imaginary / real);
            else {
                if (real < 0) {
                    return atan(imaginary / real) - PI;
                } else {
                    return atan(imaginary / real);
                }
            }
        }
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    @Override
    public String toString() {
        return real + " + " + imaginary + "i" + " phi = " + phi() + " / " + phiDeg();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumbers that = (ComplexNumbers) o;
        return Double.compare(that.real, real) == 0 &&
                Double.compare(that.imaginary, imaginary) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }
}
