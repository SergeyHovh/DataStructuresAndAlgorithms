package com.company.Physics.Pendulums.SinglePendulum;

import com.company.Algorithms.RK4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Scene extends JPanel implements ActionListener {
    private RK4 rk4 = new RK4();
    private int unitLength = 30;
    private int delay = 5;
    private Timer timer = new Timer(delay, this);
    private double offsetX, offsetY;
    private double r = 5;
    private double M = 10, L = r * unitLength, theta = 1, gravity = 10;
    private double theta0 = Math.PI / 2;
    private double x = 0, y = 0;
    private double x0 = x, y0 = y;
    private double v0 = 0;
    private double step = 0;
    private double stepSize = delay * 0.01;
    private double friction = 0;

    private Line2D line = new Line2D.Double(0, 0, 0, 0);
    private Ellipse2D ball = new Ellipse2D.Double(0, 0, M, M);

    Scene() {
        setup();
        setPosition(ball, x, y);
        line.setLine(offsetX, offsetY, ball.getCenterX(), ball.getCenterY());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.fill(ball);
        graphics2D.draw(line);
        setPosition(ball, x, y);
        line.setLine(offsetX, offsetY, ball.getCenterX(), ball.getCenterY());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        M = SinglePendulum.getMap().get("Mass").getValue();
        L = SinglePendulum.getMap().get("Length").getValue();
        gravity = SinglePendulum.getMap().get("Gravity").getValue();
        compute();

        ball.setFrame(ball.getX(), ball.getY(), M, M);
        repaint();
    }

    private void setPosition(Ellipse2D ball, double x, double y) {
        ball.setFrame(x - ball.getWidth() / 2, y - ball.getWidth() / 2, ball.getWidth(), ball.getHeight());
    }

    private double motion(double x, double... y) {
        return -gravity / L * Math.sin(y[0]) - friction * y[1];
    }

    private void setup() {
        // max
        SinglePendulum.getMap().get("Mass").setMaximum(20);
        SinglePendulum.getMap().get("Length").setMaximum(10 * unitLength);
        SinglePendulum.getMap().get("Gravity").setMaximum(20);
        // min
        SinglePendulum.getMap().get("Mass").setMinimum(1);
        SinglePendulum.getMap().get("Length").setMinimum(unitLength);
        SinglePendulum.getMap().get("Gravity").setMinimum(1);
        // initial values
        reset();
    }


    void start() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    void reset() {
        offsetY = getSize().height * 0.5;
        offsetX = getSize().width * 0.5;
        SinglePendulum.getMap().get("Mass").setValue(10);
        SinglePendulum.getMap().get("Length").setValue(5 * unitLength);
        SinglePendulum.getMap().get("Gravity").setValue(10);
        repaint();
    }


    private void compute() {
        offsetY = getSize().height * 0.5;
        offsetX = getSize().width * 0.5;
        x = L * Math.sin(theta) + offsetX;
        y = L * Math.cos(theta) + offsetY;
        theta = rk4.solveSecondOrder(x0, theta0, v0, step, this::motion);
        step += stepSize;
    }
}
