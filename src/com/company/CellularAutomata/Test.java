package com.company.CellularAutomata;

import com.company.Physics.Base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

public class Test extends GridPanel implements ActionListener {
    protected Color alive = Color.BLACK;
    protected Color dead = Color.WHITE;
    Timer timer = new Timer(100, this);

    protected Test(int N, double w, double h) {
        super(N, w, h);
        Random r = new Random();
        for (Cell[] cells : getGrid()) {
            for (Cell cell : cells) {
                if (r.nextDouble() < 0.5)
                    cell.setColor(alive);
            }
        }
        timer.start();
    }

    public static void main(String[] args) {
        Base base = new Base("test", 750, 750);
        base.addComponent(new Test(125, 750, 750));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        updateGrid((i, j, grid) -> {
            Cell cell = grid[i][j];
            int aliveNeighbor = 0;
            Vector<Cell> neighbors = cell.getNeighbors(getGrid());
            for (Cell neighbor : neighbors) {
                if (neighbor.getColor().equals(alive)) aliveNeighbor++;
            }
            if (grid[i][j].getColor().equals(alive)) { // alive cells
                if (aliveNeighbor < 2) grid[i][j].setColor(dead);
                if (aliveNeighbor > 3) grid[i][j].setColor(dead);
                if (aliveNeighbor == 2 || aliveNeighbor == 3) grid[i][j].setColor(alive);
            } else { // dead cells
                if (aliveNeighbor == 3) grid[i][j].setColor(alive);
            }
        });
    }
}
