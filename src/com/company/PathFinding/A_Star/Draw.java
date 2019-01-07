package com.company.PathFinding.A_Star;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.util.Random;
import java.util.Vector;

import static java.lang.Math.abs;
import static java.lang.Math.hypot;

public class Draw extends JPanel implements ActionListener, MouseListener {
    private boolean diagonal = true, started = false;

    private Spot[][] grid;
    private double scaleX, scaleY;
    private int N;
    private Vector<Spot> openSet = new Vector<>(), closedSet = new Vector<>();
    private Path2D path2D = new Path2D.Double();
    private Spot start;
    private Spot end;
    private Timer timer = new Timer(10, this);
    private int count = 0;

    Draw(int N, double w, double h) {
        this.N = N;
        Random r = new Random();
        addMouseListener(this);
        scaleX = (w - 10) / N;
        scaleY = (h - 30) / N;
        grid = new Spot[N][N];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Spot(i, j, scaleX, scaleY);
                if (abs(r.nextDouble()) <= 0.3) {
                    grid[i][j].setWall(true);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for (Spot[] rectangle2DS : grid) {
            for (Spot spot : rectangle2DS) {
                graphics2D.setStroke(new BasicStroke(0));
                graphics2D.setColor(spot.getColor());
                graphics2D.fill(spot);
                graphics2D.setColor(Color.blue);
                graphics2D.setStroke(new BasicStroke((float) scaleX / 5));
                graphics2D.draw(path2D);
                if (!started) {
                    graphics2D.setStroke(new BasicStroke(0));
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.draw(spot);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        Color color = new Color(255, 255, 0);
        start.setColor(color);
        end.setColor(color);
        A_Star(diagonal);
    }

    private void A_Star(boolean allowDiagonals) {
        started = true;
        if (!openSet.isEmpty()) {
            Spot current = getSpotWithMinimalScoreF();
            if (current.equals(end)) {
                System.out.println("DONE");
                started = false;
                timer.stop();
                end.setColor(Color.WHITE);
                start.setColor(Color.WHITE);
            }
            openSet.removeElement(current);
            closedSet.add(current);
            lookForNeighbors(current, allowDiagonals);
            drawPath(current);
        } else {
            started = false;
            end.setColor(Color.RED);
            start.setColor(Color.RED);
            timer.stop();
            System.out.println("NO SOLUTION");
        }
    }

    private Spot getSpotWithMinimalScoreF() {
        int winner = 0;
        for (int i = 0; i < openSet.size(); i++) {
            Spot spot = openSet.get(i);
            if (spot.f <= openSet.get(winner).f) winner = i;
        }
        return openSet.get(winner);
    }

    private void lookForNeighbors(Spot current, boolean diagonal) {
        for (Spot neighbor : current.getNeighbors(grid, diagonal)) { // for each neighbor
            if (!closedSet.contains(neighbor) //if it is not closed
                    && !neighbor.isWall() // if it is not a wall
                    && (!diagonal || isValidNeighbor(grid, current, neighbor))) { //  if it is a valid move
                double dist = dist(neighbor, current);
                double tempG = current.g + dist; // calculate the G score
                boolean newPath = false;
                if (openSet.contains(neighbor)) {
                    if (tempG < neighbor.g) { // if found better path
                        neighbor.g = tempG;
                        newPath = true;
                    }
                } else { // if is not in the open set
                    neighbor.g = tempG;
                    newPath = true;
                    openSet.add(neighbor);
                }
                if (newPath) {
                    neighbor.h = dist(neighbor, end);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.prev = current; // add to path
                }
            }
        }
    }

    private void drawPath(Spot current) {
        path2D = new Path2D.Double();
        Spot temp = current;
        path2D.moveTo(current.getCenterX(), current.getCenterY());
        while (temp.prev != null) {
            path2D.lineTo(temp.prev.getCenterX(), temp.prev.getCenterY());
            temp = temp.prev;
        }
    }

    private double dist(Spot A, Spot B) {
        return hypot(A.getCenterX() - B.getCenterX(), A.getCenterY() - B.getCenterY());
    }

    private boolean isValidNeighbor(Spot[][] grid, Spot prev, Spot next) {
        int i = prev.getI();
        int j = prev.getJ();
        int nextI = next.getI();
        int nextJ = next.getJ();
        boolean A = true, B = true;
        if (nextI > 0 && nextI < grid.length - 1) {
            A = !grid[nextI][j].isWall();
        }
        if (nextJ > 0 && nextJ < grid[0].length - 1) {
            B = !grid[i][nextJ].isWall();
        }
        return A || B;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int i = (int) (e.getX() / scaleX);
        int j = (int) (e.getY() / scaleY);
        if (i < N && j < N) {
            Spot current = grid[i][j];
            if (!current.isWall())
                if (count % 2 == 0) {
                    openSet = new Vector<>();
                    closedSet = new Vector<>();
                    path2D = new Path2D.Double();

                    start = current;
                    start.setWall(false);
                    openSet.add(start);
                } else {
                    end = current;
                    end.setWall(false);
                    timer.start();
                }
            count++;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
