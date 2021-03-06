package com.company.Physics.Pendulums.DoublePendulum;

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
        Scene scene = new Scene();
        JButton reset = new JButton("Reset");
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        start.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scene.start();
            }
        });
        stop.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scene.stop();
            }
        });
        reset.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scene.reset();
            }
        });
        JPanel control = new JPanel(new GridLayout(1, 0));
        control.add(start);
        control.add(stop);
        control.add(reset);
        controlPanel.add(control);
        add(scene);
        add(controlPanel);
    }

    static Map<String, JSlider> getMap() {
        return map;
    }
}
