package com.company.Physics.DoublePendulum;

import com.company.Physics.Base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Map;

class DoublePendulum extends Base {

    private static Map<String, JSlider> map = new LinkedHashMap<>();

    DoublePendulum(String name, int width, int height) {
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
        JButton reset = new JButton("Reset");
        reset.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Scene.reset();
            }
        });
        controlPanel.add(reset);
        add(new Scene());
        add(controlPanel);
    }

    static Map<String, JSlider> getMap() {
        return map;
    }
}
