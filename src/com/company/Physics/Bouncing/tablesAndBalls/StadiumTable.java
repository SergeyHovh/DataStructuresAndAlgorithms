package com.company.Physics.Bouncing.tablesAndBalls;

import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;
import java.util.Vector;

public class StadiumTable extends RoundRectangle2D.Double implements Table {
    private Vector<Ball> balls = new Vector<>();
    private double length, diameter;
    private Point2D centerLeft, centerRight;

    public StadiumTable(double x, double y, double length, double diameter) {
        this(new Point2D.Double(x, y), length, diameter);
    }

    public StadiumTable(Point2D position, double length, double diameter) {
        super(position.getX(), position.getY(), (diameter + length) * unitSize, diameter * unitSize,
                diameter * unitSize, diameter * unitSize);
        this.length = length;
        this.diameter = diameter;
        this.centerLeft = new Point2D.Double(this.getCenterX() - length / 2, this.getCenterY());
        this.centerRight = new Point2D.Double(this.getCenterX() + length / 2, this.getCenterY());
    }

    @Override
    public void add(Ball ball, boolean nextToEachOther) {
        double[] xy = place(nextToEachOther);
        ball.setFrame(xy[0], xy[1], ball.getWidth(), ball.getHeight());
        if (nextToEachOther) {
            if (!balls.isEmpty()) {
                ball.setMomentum(balls.lastElement().momentumX, balls.lastElement().momentumY);
            }
        }
        balls.add(ball);
    }

    @Override
    public void remove(Ball ball) {
        balls.removeElement(ball);
    }

    @Override
    public Vector<Ball> getBalls() {
        return balls;
    }

    @Override
    public double[] place(boolean nextToEachOther) {
        double res[] = new double[2];
        double x = java.lang.Double.MAX_VALUE;
        double y = x;
        if (nextToEachOther) {
            if (balls.isEmpty()) {
                while (!getBounds().contains(x, y)) {
                    x = getWidth() * new Random().nextDouble();
                    y = getHeight() * new Random().nextDouble();
                }
                res[0] = x;
                res[1] = y;
            } else {
                res[0] = balls.lastElement().getX() + Math.pow(10, -5);
                res[1] = balls.lastElement().getY();
            }
        } else {
            if (length / diameter > 0.3) {
                while (!(y > getY())
                        || !(y < getY() + getHeight())
                        || !(x > centerLeft.getX())
                        || !(x < centerRight.getX())) {
                    x = getWidth() * new Random().nextDouble();
                    y = getHeight() * new Random().nextDouble();
                }
            } else {
                while (distance(centerLeft, x, y) > diameter / 3 || distance(centerRight, x, y) > diameter / 3) {
                    x = getWidth() * new Random().nextDouble();
                    y = getHeight() * new Random().nextDouble();
                }
            }
            res[0] = x;
            res[1] = y;
        }
        return res;
    }

    private double distance(Point2D shape, double x, double y) {
        return Math.sqrt(
                Math.pow(shape.getX() - x, 2)
                        + Math.pow(shape.getY() - y, 2)
        );
    }
}
