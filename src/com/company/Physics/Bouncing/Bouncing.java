package com.company.Physics.Bouncing;

import com.company.Physics.Base;
import com.company.Physics.Bouncing.tablesAndBalls.StadiumTable;
import com.company.Physics.Bouncing.util.Scene;

class Bouncing extends Base {
    Bouncing(String name, int width, int height) {
        super(name, width, height);
        StadiumTable table = new StadiumTable(50, 10, 1, 1);
        Scene StadiumTableScene = new Scene(table);
        StadiumTableScene.addParticles(1, 10, false);
        add(StadiumTableScene);
    }
}
