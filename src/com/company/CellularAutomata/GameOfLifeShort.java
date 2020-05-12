package com.company.CellularAutomata;

import com.company.Physics.Base;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class GameOfLifeShort extends CellularAutomata {
    protected Color alive = Color.BLACK;
    protected Color dead = Color.WHITE;

    protected GameOfLifeShort(int N, double w, double h) {
        super(N, w, h);
        Random r = new Random();
        for (Cell[] cells : getGrid()) {
            for (Cell cell : cells) {
                if (r.nextDouble() < 0.5)
                    cell.setColor(alive);
            }
        }
    }

    public static void main(String[] args) {
        int side = 750;
        Base base = new Base("Game of Life", side, side);
        base.addComponent(new GameOfLifeShort(125, side, side));
    }

    @Override
    void rules(int i, int j, Cell[][] grid) {
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
    }
}
