package com.company.Physics.Bouncing.util;

import com.company.Physics.Bouncing.tablesAndBalls.Ball;
import com.company.Physics.Bouncing.tablesAndBalls.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Scene extends JPanel implements ActionListener {
    private Table table;
    private Color defaultColor = Color.BLACK;
    private Timer timer = new Timer(5, this);

    public Scene(Table table) {
        this.table = table;
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        draw((Shape) table, Color.ORANGE, graphics2D);
        Vector<Ball> balls = table.getBalls();
        for (int i = 0, ballsSize = balls.size(); i < ballsSize; i++) {
            Ball ball = balls.get(i);
            draw(ball, Color.BLACK, graphics2D);
            ball.bounce((Shape) table, graphics2D);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private void fill(int N, double size, Table table, boolean nextToEachOther) {
        for (int i = 0; i < N; i++) {
            table.add(new Ball(size), nextToEachOther);
        }
    }

    public void addParticles(int numberOfParticles, int particleSize, boolean nextToEachOther) {
        fill(numberOfParticles, particleSize, table, nextToEachOther);
    }

    private void draw(Shape shape, Color color, Graphics2D graphics2D) {
        graphics2D.setColor(color);
        graphics2D.fill(shape);
        graphics2D.setColor(defaultColor);
    }
    // setters/getters

    public void setTable(Table table) {
        this.table = table;
    }
}
