package com.company.CellularAutomata;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridPanel extends JPanel implements MouseListener {
    private int N;
    private Cell[][] grid;
    private double scaleX, scaleY;
    private int I, J;

    protected GridPanel(int N, double w, double h) {
        this.N = N;
        this.grid = new Cell[N][N];
        this.scaleX = (w - 10) / N;
        this.scaleY = (h - 35) / N;
        addMouseListener(this);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Cell(i, j, scaleX, scaleY);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for (Cell[] rectangle2DS : grid) {
            for (Cell rectangle2D : rectangle2DS) {
                graphics2D.setColor(rectangle2D.getColor());
                graphics2D.fill(rectangle2D);
                graphics2D.setColor(Color.BLACK);
                graphics2D.draw(rectangle2D);
            }
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public int getN() {
        return N;
    }

    public int getClickedI() {
        return I;
    }

    public int getClickedJ() {
        return J;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.I = (int) (e.getX() / scaleX);
        this.J = (int) (e.getY() / scaleY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
