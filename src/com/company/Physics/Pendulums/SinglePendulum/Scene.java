package com.company.Physics.Pendulums.SinglePendulum;

import com.company.Numerical.ODE.Embedded.RKDP;
import com.company.Numerical.ODE.ODESolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Vector;

public class Scene extends JPanel implements ActionListener {
    private Vector<Double> values;
    private ODESolver solveSecondOrder = new RKDP();
    private int unitLength = 30;
    private int delay = 1;
    private Timer timer = new Timer(delay, this);
    private double offsetX, offsetY;
    private double r = 3;
    private double M = 10, L = r * unitLength, theta = 1, gravity = 9.81;
    private double x = 0, y = 0;
    private double x0 = 0;
    private double v0 = 0;
    private double step = 0;
    private double stepSize = 0.1;
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
        compute();

        ball.setFrame(ball.getX(), ball.getY(), M, M);
        repaint();
    }

    private void setPosition(Ellipse2D ball, double x, double y) {
        ball.setFrame(x - ball.getWidth() / 2, y - ball.getWidth() / 2, ball.getWidth(), ball.getHeight());
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
        values = new Vector<>();
        timer.start();
    }

    void stop() {
        writeToFile(values, "fOff.txt");
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

        M = SinglePendulum.getMap().get("Mass").getValue();
        L = SinglePendulum.getMap().get("Length").getValue();
        gravity = SinglePendulum.getMap().get("Gravity").getValue();

        double[][] thetaArr = solveSecondOrder.solveSecondOrder(x0, theta, v0, step, this::motionEquation);
        // update initial values
        x0 = step;
        theta = thetaArr[0][0];
        v0 = thetaArr[0][1];
        step += stepSize;
        values.add(theta);
    }

    private double motionEquation(double x, double[][] y) {
        return -gravity / L * y[0][0] - damping(x) * y[0][1];
    }

    private double damping(double x) {
        return 0;
//        return (pow(E, x) % 10) / 100;
//        return pow(-1, (int) (x / 20)) * (x % 10) / 100;
    }

    private void writeToFile(Vector<Double> value, String fileName) {
        Formatter file;
        try {
            file = new Formatter(fileName);
            file.flush();
            for (Double v : value) {
                file.format("%s", v + "\n");
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
