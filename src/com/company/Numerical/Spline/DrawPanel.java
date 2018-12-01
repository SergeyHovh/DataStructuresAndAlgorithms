package com.company.Numerical.Spline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

import static java.lang.Math.sqrt;

public class DrawPanel extends JPanel implements MouseListener {

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
        if (points.size() == 3) {
            spline = new NaturalCubicSpline(splinePoints);
            double X1 = splinePoints[1].getxCoordinate();
            double X2 = splinePoints[2].getxCoordinate();
            int numberOfPoints = 1000;
            double stepSize1 = 2 * X1 / numberOfPoints;
            double stepSize2 = 2 * (X2 - X1) / numberOfPoints;
            double step = 0;
            for (int i = 0; i < numberOfPoints; i++) {
                Point p;
                double x, y, X, Y;
                if (step < X1) {
                    x = step;
                    y = spline.Si(-x, 0);
                    step += stepSize1;
                } else {
                    x = step;
                    y = spline.Si(x, 1);
                    step += stepSize2;
                }
                X = x * sin - y * cos + points.getFirst().getX();
                Y = points.getFirst().getY() - (x * cos + y * sin);
                p = new Point(X - h / 2, Y - h / 2, h, h);
                graphics2D.fill(p);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point point = new Point(e.getX() - h / 2, e.getY() - h / 2, h, h);
        if (points.size() != 0) {
            double X, Y;
            X = point.getX() - points.getFirst().getX();
            Y = -point.getY() + points.getFirst().getY();
            double distance = sqrt(X * X + Y * Y);
            if (points.size() == 1) {
                sin = X / distance;
                cos = Y / distance;
                point.setxCoordinate(distance);
                point.setyCoordinate(0);
            } else {
                double xPrime = X * sin + Y * cos;
                double yPrime = -X * cos + Y * sin;
                point.setxCoordinate(xPrime);
                point.setyCoordinate(yPrime);
            }
        } else {
            point.setxCoordinate(0);
            point.setyCoordinate(0);
        }
        switch (points.size()) {
            case 0:
                splinePoints[0] = point;
            case 1:
                splinePoints[1] = point;
            case 2:
                splinePoints[2] = point;
        }
        points.addLast(point);
    }

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

