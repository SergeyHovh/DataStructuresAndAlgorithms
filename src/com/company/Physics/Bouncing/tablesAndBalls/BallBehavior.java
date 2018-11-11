package com.company.Physics.Bouncing.tablesAndBalls;

import java.awt.*;

public interface BallBehavior {
    double momentumModule = 1;

    void move();

    void move(double x, double y);

    void stop();

    void setMomentum(double momentumX, double momentumY);

    void bounce(Shape table, Graphics2D graphics2D);
}
