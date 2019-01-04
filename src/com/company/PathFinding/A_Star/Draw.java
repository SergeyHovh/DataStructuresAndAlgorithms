package com.company.PathFinding.A_Star;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import static java.lang.Math.abs;
import static java.lang.Math.hypot;

public class Draw extends JPanel implements ActionListener, MouseListener {
    private boolean diagonal = true;

    private Spot[][] grid;
    private double scaleX, scaleY;
    private int N;
    private Vector<Spot> openSet = new Vector<>(), closedSet = new Vector<>();
    private LinkedList<Spot> path = new LinkedList<>();
    private Spot start;
    private Spot end;
    private Timer timer = new Timer(50, this);
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
                if (abs(r.nextGaussian()) <= 0.7) {
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
                graphics2D.setColor(spot.getColor());
                graphics2D.fill(spot);
                graphics2D.setColor(Color.BLACK);
                graphics2D.draw(spot);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();

        if (!openSet.isEmpty()) {
            int winner = 0;
            for (int i = 0; i < openSet.size(); i++) {
                Spot spot = openSet.get(i);
                if (spot.f <= openSet.get(winner).f) winner = i;
            }
            Spot current = openSet.get(winner);

            if (current.equals(end)) {
                Spot temp = current;
                path.addFirst(temp);
                while (temp.prev != null) {
                    path.addLast(temp.prev);
                    temp = temp.prev;
                }
                System.out.println("DONE");
                timer.stop();
            }

            openSet.removeElement(current);
            closedSet.add(current);
            for (Spot neighbor : current.getNeighbors(grid, diagonal)) {
                if (!closedSet.contains(neighbor) && !neighbor.isWall()) {
                    double tempG = current.g + 1;
                    boolean newPath = false;
                    if (openSet.contains(neighbor)) {
                        if (tempG < neighbor.g) {
                            neighbor.g = tempG;
                            newPath = true;
                        }
                    } else {
                        neighbor.g = tempG;
                        newPath = true;
                        openSet.add(neighbor);
                    }
                    if (newPath) {
                        neighbor.h = dist(neighbor, end);
                        neighbor.f = neighbor.g + neighbor.h;
                        neighbor.prev = current;

                    }
                }
            }
            for (Spot spot : openSet) {
                spot.setColor(Color.GREEN);
            }
            for (Spot spot : closedSet) {
                spot.setColor(Color.RED);
            }
            for (Spot spot : path) {
                spot.setColor(Color.BLUE);
            }
        } else {
            timer.stop();
            System.out.println("NO SOLUTION");
        }
        Color color = new Color(47, 67, 255);
        start.setColor(color);
        end.setColor(color);
    }

    private double dist(Spot neighbor, Spot end) {
        return hypot(neighbor.x - end.x, neighbor.y - end.y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int i = (int) (e.getX() / scaleX);
        int j = (int) (e.getY() / scaleY);
        if (i < N && j < N) {
            Spot current = grid[i][j];
            if (!current.isWall())
                if (count % 2 == 0) {
                    openSet.clear();
                    closedSet.clear();
                    path.clear();

                    start = current;
                    start.setWall(false);
                    openSet.add(start);
                } else {
                    end = current;
                    end.setWall(false);
                    timer.restart();
                }
            count++;
        }
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
