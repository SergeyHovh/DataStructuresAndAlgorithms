package com.company.PathFinding.A_Star;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Objects;
import java.util.Vector;

public class Spot extends Rectangle2D.Double {
    public Spot prev = null;
    double f = 0, g = 0, h = 0;
    private Color color;
    private boolean isWall = false;
    private int i, j;

    public Spot(double x, double y, double w, double h) {
        super(x * w, y * h, w, h);
        this.i = (int) x;
        this.j = (int) y;
        color = Color.WHITE;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    Vector<Spot> getNeighbors(Spot[][] grid, boolean diagonals) {
        Vector<Spot> result = new Vector<>();
        int rows = grid.length;
        int cols = grid[0].length;
        if (i > 0) result.add(grid[i - 1][j]);
        if (i < rows - 1) result.add(grid[i + 1][j]);
        if (j > 0) result.add(grid[i][j - 1]);
        if (j < cols - 1) result.add(grid[i][j + 1]);
        // diagonals
        if (diagonals) {
            if (i > 0 && j > 0) result.add(grid[i - 1][j - 1]);
            if (i > 0 && j < cols - 1) result.add(grid[i - 1][j + 1]);
            if (i < rows - 1 && j > 0) result.add(grid[i + 1][j - 1]);
            if (i < rows - 1 && j < cols - 1) result.add(grid[i + 1][j + 1]);
        }
        return result;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
        if (wall) setColor(Color.BLACK);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Spot spot = (Spot) o;
        return isWall == spot.isWall &&
                i == spot.i &&
                j == spot.j &&
                java.lang.Double.compare(spot.f, f) == 0 &&
                java.lang.Double.compare(spot.g, g) == 0 &&
                java.lang.Double.compare(spot.h, h) == 0 &&
                Objects.equals(color, spot.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color, isWall, i, j, f, g, h);
    }
}
