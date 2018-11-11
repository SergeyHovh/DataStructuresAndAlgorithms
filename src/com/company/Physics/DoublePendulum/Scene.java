package com.company.Physics.DoublePendulum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Scene extends JPanel implements ActionListener {
    private static int unitLength = 30;
    // values
    private double offsetY;
    private double offsetX;
    private double m1 = 8, m2 = 10;
    private double L_1 = 3, L_2 = 5;
    private double r1 = L_1 * unitLength, r2 = L_2 * unitLength;
    private double theta1 = 1, theta2 = 0, theta_v1 = 0, theta_v2 = 0, theta_a1 = 0, theta_a2 = 0;
    private double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
    private double gravity = 10;
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
        Timer timer = new Timer(25, this);
        timer.start();
    }

    static void reset() {
        DoublePendulum.getMap().get("Length 1").setValue(3 * unitLength);
        DoublePendulum.getMap().get("Length 2").setValue(5 * unitLength);
        DoublePendulum.getMap().get("Mass 1").setValue(8);
        DoublePendulum.getMap().get("Mass 2").setValue(10);
        DoublePendulum.getMap().get("Gravity").setValue(10);
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
        // initial values
        reset();
    }

    private void compute() {
        // math & physics
        gravity /= 10;
        x1 = r1 * Math.sin(theta1) + offsetX;
        y1 = r1 * Math.cos(theta1) + offsetY;
        x2 = x1 + r2 * Math.sin(theta2);
        y2 = y1 + r2 * Math.cos(theta2);
        theta1 += theta_v1;
        theta2 += theta_v2;
        theta_v1 += theta_a1;
        theta_v2 += theta_a2;
        // theta 1 double dot calculation
        double num1 = -gravity * (2 * m1 + m2) * Math.sin(theta1);
        double num2 = -gravity * m2 * Math.sin(theta1 - 2 * theta2);
        double num3 = -2 * m2 * Math.sin(theta1 - theta2);
        double num4 = theta_v2 * theta_v2 * r2 + theta_v1 * theta_v1 * r1 * Math.cos(theta1 - theta2);
        double den1 = r1 * (2 * m1 + m2 - m2 * Math.cos(2 * (theta1 - theta2)));
        theta_a1 = (num1 + num2 + num3 * num4) / den1;
        // theta 2 double dot calculation
        num1 = 2 * Math.sin(theta1 - theta2);
        num2 = theta_v1 * theta_v1 * r1 * (m1 + m2);
        num3 = gravity * (m1 + m2) * Math.cos(theta1);
        num4 = theta_v2 * theta_v2 * r2 * m2 * Math.cos(theta1 - theta2);
        theta_a2 = num1 * (num2 + num3 + num4) / (den1 * r2 / r1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        compute();
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
        offsetY = getSize().height * 0.5;
        offsetX = getSize().width * 0.5;
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
