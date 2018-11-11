package com.company.Physics.Bouncing.tablesAndBalls;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

public class Ball extends Ellipse2D.Double implements BallBehavior {
    //    double momentumX = momentumModule * Math.sin(theta), momentumY = momentumModule * Math.cos(theta);
    double momentumX = 0, momentumY = 1;
    private Point2D[] intersectionPoints = new Point2D[2];
    private Vector<Rectangle2D.Double> pathBefore = new Vector<>(), pathAfter = new Vector<>();
    private int bounceCount = 0;
    private double theta = 2 * Math.PI * new Random().nextDouble(), ballSlope = 0, intersectionSlope = 0;

    public Ball(double radius) {
        this(new Point2D.Double(0, 0), radius);
    }

    public Ball(Point2D position, double radius) {
        super(position.getX(), position.getY(), radius, radius);
        this.setMomentum(momentumX, momentumY);
    }

    private Vector<Point2D> getIntersectionPoints(Shape shape1, Shape shape2) {
        Vector<Point2D> res = new Vector<>();
        int maxWidth = shape1.getBounds().width + (int) shape1.getBounds().getX() + 1;
        int maxHeight = shape1.getBounds().height + (int) shape1.getBounds().getY() + 1;
        BufferedImage newImage = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();

//
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, maxWidth, maxHeight);

// Nkarum enq shape1
        g.setColor(Color.RED);
        g.draw(shape1);

        Color[][] checkColors1 = new Color[maxWidth][maxHeight];
        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                checkColors1[x][y] = new Color(newImage.getRGB(x, y));
            }
        }

// Nkarum enq shape2
        g.setColor(Color.BLUE);
        g.draw(shape2);

        Color[][] checkColors2 = new Color[maxWidth][maxHeight];
        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                checkColors2[x][y] = new Color(newImage.getRGB(x, y));
            }
        }

        g.dispose();

/////
        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                if (checkColors1[x][y].getRGB() == Color.RED.getRGB() && checkColors2[x][y].getRGB() != Color.RED.getRGB()) {
                    res.add(new Point2D.Double(x, y));
                }
            }
        }
        boolean saveImage = false;
        if (saveImage) {
            try {
                String saveImagePath = "test_shape.png";
                File outputfile = new File(saveImagePath);
                ImageIO.write(newImage, "png", outputfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public void move() {
        move(momentumX, momentumY);
    }

    @Override
    public void move(double x, double y) {
        setFrame(getX() + x, getY() + y, getHeight(), getWidth());
    }

    @Override
    public void stop() {
        setMomentum(0, 0);
    }

    @Override
    public void setMomentum(double momentumX, double momentumY) {
        this.momentumX = momentumX;
        this.momentumY = momentumY;
    }

    // TODO: 11/11/2018 implement
    @Override
    public void bounce(Shape table, Graphics2D graphics2D) {
        double boxLength = 3 * getHeight() / 4;
        move();
        if (!table.contains(getBounds())) {
            Rectangle2D outerBox = new Rectangle2D.Double(getCenterX() - boxLength, getCenterY() - boxLength,
                    2 * boxLength, 2 * boxLength);
            Vector<Point2D> points = getIntersectionPoints(table, outerBox);
            double intersectionX1 = points.firstElement().getX();
            double intersectionY1 = points.firstElement().getY();
            double intersectionX2 = points.lastElement().getX();
            double intersectionY2 = points.lastElement().getY();
            graphics2D.setColor(Color.RED);
            graphics2D.draw(outerBox);
            graphics2D.setColor(Color.BLACK);
            // maths
            intersectionSlope = (intersectionY1 - intersectionY2) / (intersectionX1 - intersectionX2);
            System.out.println(intersectionSlope);
            setMomentum(momentumX, -momentumY);
        }
    }
}
