/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetintegre;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.Painter;

/**
 *
 * @author PC GAMER
 */
public class FillPainter implements Painter<JComponent> {

    private final Color color;

    public FillPainter(Color c) { color = c; }

    @Override
    public void paint(Graphics2D g, JComponent object, int width, int height) {
        g.setColor(color);
        g.fillRect(0, 0, width-1, height-1);
    }

}
