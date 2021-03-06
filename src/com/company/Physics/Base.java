package com.company.Physics;

import javax.swing.*;
import java.awt.*;

public class Base extends JFrame {
    public Base(String name, int width, int height) {
        super(name);
        setSize(width, height);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public Base(String name) {
        this(name, 640, 480);
    }

    public void addComponent(Component comp) {
        super.add(comp);
        comp.setFocusable(true);
        comp.requestFocus();
    }
}
