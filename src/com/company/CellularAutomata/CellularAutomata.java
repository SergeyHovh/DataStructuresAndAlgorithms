package com.company.CellularAutomata;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract public class CellularAutomata extends GridPanel implements ActionListener {
    Timer timer = new Timer(100, this);

    protected CellularAutomata(int N, double w, double h) {
        super(N, w, h);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        updateGrid(this::rules);
    }

    abstract void rules(int i, int j, Cell[][] grid);
}
