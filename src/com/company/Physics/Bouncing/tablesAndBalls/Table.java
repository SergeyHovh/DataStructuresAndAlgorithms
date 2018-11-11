package com.company.Physics.Bouncing.tablesAndBalls;

import java.util.Vector;

public interface Table {
    int unitSize = 200;

    void add(Ball ball, boolean nextToEachOther);

    void remove(Ball ball);

    Vector<Ball> getBalls();

    double[] place(boolean nextToEachOther);
}
