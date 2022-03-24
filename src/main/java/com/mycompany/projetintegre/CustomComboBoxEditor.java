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
import java.awt.FlowLayout;
import java.awt.Font;
 
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxEditor;
 
public class CustomComboBoxEditor extends BasicComboBoxEditor {
    private JLabel label = new JLabel();
    private JPanel panel = new JPanel();
    private Object selectedItem;
     
    public CustomComboBoxEditor(Color fg, Color bg) {
         
        label.setOpaque(false);
        label.setFont(new Font("Segoe UI", 0, 11));
        label.setForeground(fg);
         
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        panel.add(label);
        panel.setBackground(bg);
    }
     
    public Component getEditorComponent() {
        return this.panel;
    }
     
    public Object getItem() {
        return "" + this.selectedItem.toString() + "";
    }
     
    public void setItem(Object anObject) {
        this.selectedItem = anObject;
        if (anObject != null)label.setText(anObject.toString());
        
    }
     
}