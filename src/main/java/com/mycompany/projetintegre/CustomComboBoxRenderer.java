/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetintegre;

/**
 *
 * @author PC GAMER
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
 
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
 
public class CustomComboBoxRenderer extends JLabel implements ListCellRenderer {
 
    public CustomComboBoxRenderer(Color fg, Color bg) {
        setOpaque(true);
        setFont(new Font("Segoe UI", 0, 11));
        setBackground(bg);
        setForeground(fg);
    }
     
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        return this;
    }
 
}