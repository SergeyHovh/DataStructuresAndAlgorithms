package com.company.PathFinding.A_Star;

import com.company.Physics.Base;

public class A_Star extends Base {
    public A_Star(String name) {
        super(name, 1000, 750);
//        setResizable(false);
        int N = 25;
        Draw draw = new Draw(N, getWidth(), getHeight());
        add(draw);
    }
}
