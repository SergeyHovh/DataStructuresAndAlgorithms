package com.company.Numerical.Spline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

import static java.lang.Math.sqrt;

public class DrawPanel extends JPanel implements MouseListener {

    private final double numberOfPoints = 5E3;
    private double sin, cos;
    private LinkedList<Point> points = new LinkedList<>();
    private Point[] splinePoints = new Point[3];
    private NaturalCubicSpline spline;
    private double h = 2;

    public DrawPanel() {
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for (Ellipse2D point : points) {
            graphics2D.fill(point);
        }
        if (points.size() > 2) {
            int size = points.size() - splinePoints.length + 1;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < splinePoints.length; j++) {
                    splinePoints[j] = points.get(i + j);
                }
                adjustPoints(splinePoints);
                spline = new NaturalCubicSpline(splinePoints);
                double X0 = splinePoints[0].getxCoordinate();
//                double Y0 = splinePoints[0].getyCoordinate();
                double X1 = splinePoints[1].getxCoordinate();
//                double Y1 = splinePoints[1].getyCoordinate();
                double X2 = splinePoints[2].getxCoordinate();
//                double Y2 = splinePoints[2].getyCoordinate();
//                prevA = spline.getA()[1];
//                prevB = spline.getB()[1];
//                prevC = spline.getC()[1];
//                prevD = spline.getD()[1];
                double stepSize1 = 2 * (X1 - X0) / numberOfPoints;
                double stepSize2 = 2 * (X2 - X1) / numberOfPoints;
//                System.out.println("-----------------------------------");
//                System.out.println(X0 + " " + (spline.Si(X0, 0) - Y0));
//                System.out.println(X1 + " " + (spline.Si(X1, 0) - Y1));
//                System.out.println(X1 + " " + (spline.Si(X1, 1) - Y1));
//                System.out.println(X2 + " " + (spline.Si(X2, 1) - Y2));
//                System.out.println(X0 + " " + X1 + " " + X2);
                drawLine(graphics2D, X1, stepSize1, stepSize2, X0);
            }
        }
    }

    private void drawLine(Graphics2D graphics2D, double x1, double stepSize1, double stepSize2, double step) {
        for (int k = 0; k < numberOfPoints; k++) {
            Point p;
            double x, y, X, Y;
            if (step <= x1) {
                x = step;
                y = spline.Si(-x, 0);
                step += stepSize1;
            } else {
                x = step;
                y = spline.Si(x, 1);
                step += stepSize2;
            }
            Point first = splinePoints[0];
            X = x * sin - y * cos + first.getX();
            Y = first.getY() - (x * cos + y * sin);
            p = new Point(X - h / 2, Y - h / 2, h, h);
            graphics2D.fill(p);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point point = new Point(e.getX() - h / 2, e.getY() - h / 2, h, h);
        points.addLast(point);
    }

    private void adjustPoints(Point... splinePoints) {
        splinePoints[0].setXYCoordinate(0, 0);
        //    private double prevA, prevB, prevC, prevD, prevSiPrime;
        //    private Line2D finalLine;
        double centerX = splinePoints[0].getX();
        double centerY = splinePoints[0].getY();
        double X1 = splinePoints[1].getX() - centerX;
        double X2 = splinePoints[2].getX() - centerX;
        double Y1 = centerY - splinePoints[1].getY();
        double Y2 = centerY - splinePoints[2].getY();
        double distance = sqrt(X1 * X1 + Y1 * Y1);
        sin = X1 / distance;
        cos = Y1 / distance;
        splinePoints[1].setXYCoordinate(distance, 0);
        double xPrime = X2 * sin + Y2 * cos;
        double yPrime = -X2 * cos + Y2 * sin;
        splinePoints[2].setXYCoordinate(xPrime, yPrime);
    }

/*    private void rotate(Point p) {
        double X = p.getX() - centerX;
        double Y = centerY - p.getY();
        double xPrime = X * sin + Y * cos;
        double yPrime = -X * cos + Y * sin;
        p.setXYCoordinate(xPrime, yPrime);
    }*/

    @Override
    public void mouseReleased(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

