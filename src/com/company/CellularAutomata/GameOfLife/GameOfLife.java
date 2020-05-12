package com.company.CellularAutomata.GameOfLife;

import com.company.Physics.Base;

public class GameOfLife extends Base {

    public GameOfLife(String name, int side) {
        super(name, side, side);
        setResizable(true);
        Draw d = new Draw(125, getWidth(), getHeight(), true);
        add(d);
        d.setFocusable(true);
        d.requestFocus();
    }
}
