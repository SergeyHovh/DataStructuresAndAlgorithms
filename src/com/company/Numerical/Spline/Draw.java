package com.company.Numerical.Spline;

import javax.swing.*;

public class Draw extends JFrame {
    Draw() {
        super("Spline Interpolation");
        setup();
        DrawPanel panel = new DrawPanel();
        add(panel);
    }

    private void setup() {
        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
