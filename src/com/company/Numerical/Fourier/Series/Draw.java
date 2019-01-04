package com.company.Numerical.Fourier.Series;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.LinkedList;

import static java.lang.Math.*;

public class Draw extends JPanel implements ActionListener {
    private Timer timer = new Timer(1, this);
    private double t = 0;
    private LinkedList<java.lang.Double> points = new LinkedList<>();
    private int numberOfCircles;
    private double offX = 250, offY = 250;
    private double pointX, pointY;
    private double R = 50;
    private double offset = 0;

    Draw(int n) {
        this.numberOfCircles = n;
        timer.start();
        for (int i = 0; i < numberOfCircles; i++) {
            int k = 2 * i + 1;
            offset += R * 4 / (k * PI);
        }
    }

    Draw() {
        this(5);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        double prevX = offX, prevY = offY;
        for (int i = 0; i < numberOfCircles; i++) {
            // square wave
//            int n = 2 * i + 1;
//            double radius = R * 4 / (n * PI);
            int n = i + 1;
            double radius = R * 2 / (pow(-1, n + 1) * n * PI); // saw wave
//            double radius = R * 2 / (pow(-1, n) * n * PI); // saw wave reverse
            double angle = n * t;


            Ellipse2D circle = new Ellipse2D.Double(prevX - radius, prevY - radius, 2 * radius, 2 * radius);
            pointX = prevX + radius * cos(angle);
            pointY = prevY + radius * sin(angle);

            Line2D line = new Line2D.Double(prevX, prevY, pointX, pointY);

            prevX = pointX;
            prevY = pointY;

            graphics2D.setColor(new Color(0, 0, 0, 100));
            graphics2D.draw(circle);
            graphics2D.setColor(new Color(0, 0, 0, 255));
            graphics2D.draw(line);
        }
        Path2D path2D = new Path2D.Double();
        path2D.moveTo(pointX, pointY);
        points.addFirst(pointY);
        for (int j = 0; j < points.size(); j++) {
            double p = points.get(j);
            path2D.lineTo(offX + offset + 20 + R / 125 * j, p);
            graphics2D.draw(path2D);
        }
        if (points.size() >= 430) {
            points.removeLast();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        t += 0.02;
        repaint();
    }
}
