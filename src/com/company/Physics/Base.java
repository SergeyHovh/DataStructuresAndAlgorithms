package com.company.Physics;

import javax.swing.*;

public class Base extends JFrame {
    public Base(String name, int width, int height) {
        super(name);
        setSize(width, height);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
