package com.company.Numerical.Spline;

import java.awt.geom.Ellipse2D;

class Point extends Ellipse2D.Double {
    private double xCoordinate, yCoordinate;

    public Point(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    @Override
    public String toString() {
        return "Point{" +
                "xCoordinate = " + xCoordinate +
                "\nyCoordinate = " + yCoordinate +
                "\nx = " + x +
                "\ny = " + y +
                '}';
    }

    public void setXYCoordinate(double xCoordinate, double yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}