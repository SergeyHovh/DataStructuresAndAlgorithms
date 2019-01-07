package com.company.Numerical.Fourier.Series;

import com.company.Physics.Base;

public class FourierSeries extends Base {
    public FourierSeries(String name, int width, int height) {
        super(name, width, height);
        Draw d = new Draw(20);
        add(d);
    }
}
