package com.company.CellularAutomata.GameOfLife;

import com.company.Physics.Base;

public class GameOfLife extends Base {

    public GameOfLife(String name, int side) {
        super(name, side, side);
        setResizable(false);
        Draw d = new Draw(75, getWidth(), getHeight());
        add(d);
        d.setFocusable(true);
        d.requestFocus();
    }
}
