package com.company.CellularAutomata;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Objects;
import java.util.Vector;

public class Cell extends Rectangle2D.Double {
    private Color color;
    private int i, j;

    public Cell(double x, double y, double w, double h) {
        super(x * w, y * h, w, h);
        this.i = (int) x;
        this.j = (int) y;
        color = Color.WHITE;
    }

    public Vector<Cell> getNeighbors(Cell[][] grid) {
        Vector<Cell> result = new Vector<>();
        int rows = grid.length;
        int cols = grid[0].length;
        if (i > 0) result.add(grid[i - 1][j]);
        if (i < rows - 1) result.add(grid[i + 1][j]);
        if (j > 0) result.add(grid[i][j - 1]);
        if (j < cols - 1) result.add(grid[i][j + 1]);
        if (i > 0 && j > 0) result.add(grid[i - 1][j - 1]);
        if (i > 0 && j < cols - 1) result.add(grid[i - 1][j + 1]);
        if (i < rows - 1 && j > 0) result.add(grid[i + 1][j - 1]);
        if (i < rows - 1 && j < cols - 1) result.add(grid[i + 1][j + 1]);
        return result;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cell cell = (Cell) o;
        return i == cell.i &&
                j == cell.j &&
                Objects.equals(color, cell.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color, i, j);
    }
}
