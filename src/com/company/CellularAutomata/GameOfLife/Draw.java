package com.company.CellularAutomata.GameOfLife;

import com.company.CellularAutomata.Cell;
import com.company.CellularAutomata.GridPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class Draw extends GridPanel implements ActionListener, KeyListener {
    private int map[][];
    private boolean start = false;
    private Timer timer = new Timer(100, this);
    private Color alive = Color.BLACK, dead = Color.WHITE;
    private int counter = 0;

    Draw(int N, double w, double h) {
        super(N, w, h);
        addKeyListener(this);
        map = new int[N][N]; // dead 0, alive 1
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        if (start) {
            Cell[][] grid = getGrid();
            for (int i = 0; i < grid.length; i++) {
                Cell[] cells = grid[i];
                for (int j = 0; j < cells.length; j++) {
                    Cell cell = cells[j];
                    int aliveNeighbor = 0;
                    Vector<Cell> neighbors = cell.getNeighbors(getGrid());
                    for (Cell neighbor : neighbors) {
                        if (neighbor.getColor().equals(alive)) aliveNeighbor++;
                    }
                    if (cell.getColor().equals(alive)) { // alive cells
                        if (aliveNeighbor < 2) map[i][j] = 0;
                        if (aliveNeighbor > 3) map[i][j] = 0;
                        if (aliveNeighbor == 2 || aliveNeighbor == 3) map[i][j] = 1;
                    } else { // dead cells
                        if (aliveNeighbor == 3) map[i][j] = 1;
                    }
                }
            }
            Cell[][] grid1 = getGrid();
            for (int i = 0; i < grid1.length; i++) {
                Cell[] cells = grid1[i];
                for (int j = 0; j < cells.length; j++) {
                    if (map[i][j] == 0) getGrid()[i][j].setColor(dead);
                    else if (map[i][j] == 1) getGrid()[i][j].setColor(alive);
                }
            }
        } else {
            int clickedI = getClickedI();
            int clickedJ = getClickedJ();
            map[clickedI][clickedJ] = 1;
            Cell clickedCell = getGrid()[clickedI][clickedJ];
            clickedCell.setColor(alive);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            start = counter % 2 == 0;
        }
        counter++;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
