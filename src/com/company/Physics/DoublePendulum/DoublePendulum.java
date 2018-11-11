package com.company.Physics.DoublePendulum;

import com.company.Physics.Base;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DoublePendulum extends Base {

    static Map<String, JSlider> map = new LinkedHashMap<>();

    public DoublePendulum(String name, int width, int height) {
        super(name, width, height);
        setLayout(new GridLayout(1, 1));
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(0, 1));
        map.put("Mass 1", new JSlider());
        map.put("Mass 2", new JSlider());
        map.put("Length 1", new JSlider());
        map.put("Length 2", new JSlider());
        map.put("Gravity", new JSlider());
        for (String s : map.keySet()) {
            controlPanel.add(new JLabel(s));
            controlPanel.add(map.get(s));
        }
        add(new Scene());
        add(controlPanel);
    }

    public static Map<String, JSlider> getMap() {
        return map;
    }
}
