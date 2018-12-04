package com.company.Physics.Pendulums.DoublePendulum;

import com.company.Numerical.ODE.Embedded.RKF45;
import com.company.Numerical.ODE.ODESolver;
import com.company.Numerical.ODE.ODESystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import static java.lang.Math.*;

public class Scene extends JPanel implements ActionListener {
    private ODESolver solver = new RKF45();
    private int unitLength = 30;
    private Timer timer = new Timer(1, this);
    // values
    private double offsetX, offsetY;
    private double m1 = 8, m2 = 8; // masses
    private double L_1 = 3, L_2 = 3; // rode lengths
    private double theta1 = PI, theta2 = 1.5 * PI, theta_v1 = 0, theta_v2 = 0; // angles
    private double x0 = 0, x1 = 0, y1 = 0, x2 = 0, y2 = 0; // initial positions
    private double stepSize = 0.1, step = 0;
    private double gravity = 10;
    private double r1 = L_1 * unitLength, r2 = L_2 * unitLength;
    private ODESystem[] equations = new ODESystem[]{
            this::upperBallEquation,
            this::lowerBallEquation,
    };
    // drawable elements
    private Line2D line1, line2;
    private Ellipse2D ball1 = new Ellipse2D.Double(0, 0, m1, m1);
    private Ellipse2D ball2 = new Ellipse2D.Double(0, 0, m2, m2);

    Scene() {
        setup();
        setPosition(ball1, x1, y1);
        setPosition(ball2, x2, y2);
        line1 = new Line2D.Double(offsetX, offsetY, ball1.getCenterX(), ball1.getCenterY());
        line2 = new Line2D.Double(ball1.getCenterX(), ball1.getCenterY(), ball2.getCenterX(), ball2.getCenterY());
        repaint();
    }

    private double upperBallEquation(double x, double[][] y) {
        double theta1 = y[0][0];
        double theta2 = y[1][0];
        double theta_v1 = y[0][1];
        double theta_v2 = y[1][1];


        double num1 = -gravity * (2 * m1 + m2) * sin(theta1);
        double num2 = -gravity * m2 * sin(theta1 - 2 * theta2);
        double num3 = -2 * m2 * sin(theta1 - theta2);
        double num4 = theta_v2 * theta_v2 * r2 + theta_v1 * theta_v1 * r1 * cos(theta1 - theta2);
        double den1 = r1 * (2 * m1 + m2 - m2 * cos(2 * (theta1 - theta2)));

        return (num1 + num2 + num3 * num4) / den1;
    }

    private double lowerBallEquation(double x, double[][] y) {
        double theta1 = y[0][0];
        double theta2 = y[1][0];
        double theta_v1 = y[0][1];
        double theta_v2 = y[1][1];

        double num1 = 2 * Math.sin(theta1 - theta2);
        double num2 = theta_v1 * theta_v1 * r1 * (m1 + m2);
        double num3 = gravity * (m1 + m2) * Math.cos(theta1);
        double num4 = theta_v2 * theta_v2 * r2 * m2 * Math.cos(theta1 - theta2);
        double den2 = r2 * (2 * m1 + m2 - m2 * cos(2 * (theta1 - theta2)));

        return (num1 * (num2 + num3 + num4)) / den2;
    }

    void reset() {
        offsetY = getSize().height * 0.5;
        offsetX = getSize().width * 0.5;
        DoublePendulum.getMap().get("Length 1").setValue(3 * unitLength);
        DoublePendulum.getMap().get("Length 2").setValue(3 * unitLength);
        DoublePendulum.getMap().get("Mass 1").setValue(8);
        DoublePendulum.getMap().get("Mass 2").setValue(8);
        DoublePendulum.getMap().get("Gravity").setValue(10);
        repaint();
    }

    void start() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    private void setup() {
        // setting max
        DoublePendulum.getMap().get("Length 1").setMaximum(5 * unitLength);
        DoublePendulum.getMap().get("Length 2").setMaximum(5 * unitLength);
        DoublePendulum.getMap().get("Mass 1").setMaximum(15);
        DoublePendulum.getMap().get("Mass 2").setMaximum(15);
        DoublePendulum.getMap().get("Gravity").setMaximum(20);
        // min
        DoublePendulum.getMap().get("Length 1").setMinimum(unitLength);
        DoublePendulum.getMap().get("Length 2").setMinimum(unitLength);
        DoublePendulum.getMap().get("Mass 1").setMinimum(3);
        DoublePendulum.getMap().get("Mass 2").setMinimum(1);
        DoublePendulum.getMap().get("Gravity").setMinimum(1);
        // initial values
        reset();
    }

    private void compute() {
        offsetY = getSize().height * 0.5;
        offsetX = getSize().width * 0.5;
        // math & physics
        gravity /= 10;
        x1 = r1 * Math.sin(theta1) + offsetX;
        y1 = r1 * Math.cos(theta1) + offsetY;
        x2 = x1 + r2 * Math.sin(theta2);
        y2 = y1 + r2 * Math.cos(theta2);

        double[][] initialValues = {
                {theta1, theta_v1},
                {theta2, theta_v2}
        };
        double[][] solveHighOrder = solver.solveHighOrder(x0, initialValues, step, equations);
        x0 = step;
        theta1 = solveHighOrder[0][0];
        theta_v1 = solveHighOrder[0][1];
        theta2 = solveHighOrder[1][0];
        theta_v2 = solveHighOrder[1][1];
        step += stepSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // drawing
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.fill(ball1);
        graphics2D.fill(ball2);
        graphics2D.draw(line1);
        graphics2D.draw(line2);
        // updating
        setPosition(ball1, x1, y1);
        setPosition(ball2, x2, y2);
        line1.setLine(offsetX, offsetY, ball1.getCenterX(), ball1.getCenterY());
        line2.setLine(ball1.getCenterX(), ball1.getCenterY(), ball2.getCenterX(), ball2.getCenterY());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // compute
        compute();
        // update values
        r1 = DoublePendulum.getMap().get("Length 1").getValue();
        r2 = DoublePendulum.getMap().get("Length 2").getValue();
        m1 = DoublePendulum.getMap().get("Mass 1").getValue();
        m2 = DoublePendulum.getMap().get("Mass 2").getValue();
        gravity = DoublePendulum.getMap().get("Gravity").getValue();
        // changing the size
        ball1.setFrame(ball1.getX(), ball1.getY(), m1, m1);
        ball2.setFrame(ball2.getX(), ball2.getY(), m2, m2);
        repaint();
    }

    private void setPosition(Ellipse2D ball, double x, double y) {
        ball.setFrame(x - ball.getWidth() / 2, y - ball.getWidth() / 2, ball.getWidth(), ball.getHeight());
    }
}
