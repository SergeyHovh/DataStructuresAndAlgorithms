package com.company.Numerical.FFT;

import com.company.Physics.Base;

public class FourierSeries extends Base {
    public FourierSeries(String name, int width, int height) {
        super(name, width, height);
        Draw d = new Draw(5);
        add(d);
    }
}
