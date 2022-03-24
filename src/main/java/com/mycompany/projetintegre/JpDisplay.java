/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetintegre;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author PC GAMER
 */
public class JpDisplay extends javax.swing.JPanel {
    
    //CONNECTED
    
    public static int idConnected;
    public static String poste;
    public static String email;
    
    //IDS
    public static int idDemandEdit;
    public static int idSite;
    public static LinkedList<String> idsSite;
    public static int idEditSiteAdmin;
    public static int idEditSiteSA;
    
    //Colors
    
    //color1 Primary
    Color color1_255;
    Color color1_80;
    
    //color2 Surface
    Color color2_255;
    Color color2_80;
    
    //color3(BG)
    Color colorBG;
    
    //color4 Secondary
    Color colorSecondary;
    Color colorSecondary_80;
    //color5 onBackground
    Color colorOnBackground;
    //color6 onSurface
    Color colorOnSurface;
    Color colorOnSurface_80;
    //color7 onPrimary
    Color colorOnPrimary;
    //color8 onSecondary
    Color colorOnSecondary;
    Color colorOnSecondary_80;
    //color9 error
    Color colorError;
    //END Colors
    
    //THEMES
    
    public void selectTheme(){
        if (JfMain.darkTheme){
            color1_255 = new Color(1, 115, 116, 255);
            color1_80  = new Color(1, 115, 116, 80);

            //color2 Surface
            color2_255 = new Color(49, 49, 49, 255);
            color2_80 = new Color(49, 49, 49, 80);

            //color3(BG)
            colorBG = new Color(33, 33, 33, 255);

            //color4 Secondary
            colorSecondary = new Color(0, 179, 166, 255);
            colorSecondary_80  = new Color(3, 218, 197, 80);
            //color5 onBackground
            colorOnBackground = new Color(255, 255, 255, 255);
            //color6 onSurface
            colorOnSurface = new Color(255, 255, 255, 255);
            colorOnSurface_80 = new Color(255, 255, 255, 80);
            //color7 onPrimary
            colorOnPrimary = new Color(255, 255, 255, 255);
            //color8 onSecondary
            colorOnSecondary = new Color(255, 255, 255, 255);
            colorOnSecondary_80 = new Color(255, 255, 255, 80);
            //color9 error
            colorError = new Color(200, 0, 32, 255);
            //END Colors
        }else{
            color1_255 = new Color(1, 115, 116, 255);
            color1_80  = new Color(1, 115, 116, 80);

            //color2 Surface
            color2_255 = new Color(222, 228, 231, 255);
            color2_80 = new Color(222, 228, 231, 80);

            //color3(BG)
            colorBG = new Color(220, 220, 220, 255);

            //color4 Secondary
            colorSecondary = new Color(0, 179, 166, 255);
            colorSecondary_80  = new Color(3, 218, 197, 80);
            //color5 onBackground
            colorOnBackground = new Color(33, 33, 33, 255);
            //color6 onSurface
            colorOnSurface = new Color(33, 33, 33, 255);
            colorOnSurface_80 = new Color(33, 33, 33, 80);
            //color7 onPrimary
            colorOnPrimary = new Color(255, 255, 255, 255);
            //color8 onSecondary
            colorOnSecondary = new Color(33, 33, 33, 255);
            colorOnSecondary_80 = new Color(33, 33, 33, 80);
            //color9 error
            colorError = new Color(200, 0, 32, 255);
            //END Colors
        }
    }
    //END THEMES
    
    public int viewListState;
    public boolean siteEditable = false;
    public boolean siteEditableAdmin = false;
    
        class ButtonRenderer extends JButton implements TableCellRenderer {
            
            public Icon icon;

      public ButtonRenderer(Icon icon) {
        setOpaque(true);
        this.icon = icon;
      }

      public Component getTableCellRendererComponent(JTable table, Object value,
          boolean isSelected, boolean hasFocus, int row, int column) {
          setOpaque(true);
          setContentAreaFilled(false);
        if (isSelected) {
          setForeground(table.getSelectionForeground());
          setBackground(colorSecondary_80);
        } else {
          setForeground(table.getForeground());
          setBackground(colorBG);
        }
        setIcon(icon);
        return this;
      }
    }
    
    public void setEditSite(){
        int id = Integer.parseInt(jTable4.getValueAt(jTable4.getSelectedRow(), 0) + "");
        try{
            Connection conn = SingletonConnection.getConnection();
            String req = "select * from site natural join compte where idsite = ?;";
            PreparedStatement pstmt = conn.prepareStatement(req);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt("id") != 0) jtfMSIDAdmin.setText(rs.getInt("id") + "- " + rs.getString("nom") + " " + rs.getString("prenom"));
            else jtfMSIDAdmin.setText("None");
            req = "select * from responsablesite natural join compte where idsite = ?;";
            pstmt = conn.prepareStatement(req);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) jtfMSIDMan.setText(rs.getInt("id") + "- " + rs.getString("nom") + " " + rs.getString("prenom"));
            else jtfMSIDMan.setText("None");
            req = "select * from guerite natural join compte where idSite = ?;";
            pstmt = conn.prepareStatement(req);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            jcbSEditGH.removeAllItems();
            jcbSEditGH.addItem("--Select--");
            while (rs.next()){
                jcbSEditGH.addItem(rs.getString("id") + "- " + rs.getString("nom") + " " + rs.getString("prenom"));
            }
            req = "select * from entreprise where idSite = ?;";
            pstmt = conn.prepareStatement(req);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            jcbSEditC.removeAllItems();
            jcbSEditC.addItem("--Select--");
            while (rs.next()){
                jcbSEditC.addItem(rs.getString("idEntreprise") + "- " + rs.getString("nom"));
            }
        }catch(SQLException e){
            
        }
    }
        
    class ButtonEditor extends DefaultCellEditor {
      protected JButton button;

      private String label;

      private boolean isPushed;
      
      public Icon icon;

      public ButtonEditor(JCheckBox checkBox, Icon icon) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
          }
        });
        this.icon = icon;
      }

      public Component getTableCellEditorComponent(JTable table, Object value,
          boolean isSelected, int row, int column) {
        if (isSelected) {
          button.setForeground(table.getSelectionForeground());
          button.setBackground(colorSecondary_80);
        } else {
          button.setForeground(table.getForeground());
          button.setBackground(colorBG);
        }
        label = (value == null) ? "" : value.toString();
        button.setIcon(icon);
        isPushed = true;
        return button;
      }

      public Object getCellEditorValue() {
        if (isPushed) {
          // 
          //
          
          //JOptionPane.showMessageDialog(button, label + ": Ouch!");
          if (label.equals("edit")){
              siteEditable = true;
              int row = jTable4.getSelectedRow();
              idEditSiteSA = Integer.parseInt(jTable4.getValueAt(row, 0) + "");
        if (row >= 0){
            setEditSite();
            allowTextField(jlMSIDAdmin, jtfMSIDAdmin, false, jlErrName);
            allowTextField(jlMSIDMan, jtfMSIDMan, false, jlErrName);
            jlMSVL.setForeground(colorOnSurface);
            jlMSVL.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
            jlMSVL.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jlMSVL1.setForeground(colorOnSurface);
            jlMSVL1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
            jlMSVL1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jpMSSave.setBackground(colorSecondary);
            jlMSSave.setForeground(colorOnSurface);
            jpMSSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jpMSDefault.setBackground(colorSecondary);
            jlMSDefault.setForeground(colorOnSurface);
            jpMSDefault.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            allowJCB(jlMSIDGh, jcbSEditGH, jlMSIDGh);
            allowLabel(jlAddG);
            allowLabel(jlDelG);
            allowJCB(jlMSIDC, jcbSEditC, jlMSIDC);
            allowLabel(jlAddC);
            allowLabel(jlDelC);
        }else{
            
        }
          }else if (label.equals("delete")){
              try{
                  int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
                if (res == 0){
                    int id = Integer.parseInt(jTable4.getValueAt(jTable4.getSelectedRow(), 0) + "");
                int r = 0;
                Connection conn = SingletonConnection.getConnection();
                String req = "update responsablesite set idsite = null where id = (select id from responsablesite where idsite = ?);";
                PreparedStatement pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, id);
                r = pstmt.executeUpdate();
                req = "update guerite set idsite = null where id = (select id from guerite where idsite = ?);";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, id);
                r = pstmt.executeUpdate();
                req = "delete from site where idsite = ?";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, id);
                r = pstmt.executeUpdate();
                consultEditDeleteSite(searchFilterSite(jcbCityEdit, jcbCompEdit, jtfSFNameEdit));
                jtableEditSitesRestyle();
                blockSitesEdit();
                init_filter1SiteConsult(jcbCityEdit);
                init_filter2SiteConsult(jcbCompEdit);
                }
                
            }catch(SQLException e){
                
            }
          }else if (label.equals("editD")){
              allowEditDemand();
          }else if (label.equals("cancelD")){
              int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
              if (res == 0){
                  deleteDemand();
              blockEditDemandForm();
              displayDemandsEdit();
              }
          }else if (label.equals("accept")){
              int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
              if (res == 0){
                    int row = jTable7.getSelectedRow();
                    int r = 0;
                    if (row >= 0){
                        try{
                            int id = Integer.parseInt(jTable7.getValueAt(row, 0) + "");
                            Connection conn = SingletonConnection.getConnection();
                            String req = "update demande set statutD = 1 where numDemande = ?;";
                            PreparedStatement pstmt = conn.prepareStatement(req);
                            pstmt.setInt(1, id);
                            r = pstmt.executeUpdate();
                            try {
                              showPendingDemands("select * from demande join compte where statutD = 2 and demande.id = compte.id;");
                            } catch (ParseException ex) {
                              Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            SendEmail.sendAcceptEmail(email, "projet.inte@gmail.com", "projetint2000", "Demand accepted");
                        }catch(SQLException e){

                        }
                    }
              }
              
          }else if (label.equals("refuse")){
              int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
              if (res == 0){
                  int row = jTable7.getSelectedRow();
              int r = 0;
              if (row >= 0){
                  try{
                      int id = Integer.parseInt(jTable7.getValueAt(row, 0) + "");
                      Connection conn = SingletonConnection.getConnection();
                      String req = "update demande set statutD = 0 where numDemande = ?;";
                      PreparedStatement pstmt = conn.prepareStatement(req);
                      pstmt.setInt(1, id);
                      r = pstmt.executeUpdate();
                      try {
                        showPendingDemands("select * from demande join compte where statutD = 2 and demande.id = compte.id;");
                      } catch (ParseException ex) {
                        Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
                      }
                      SendEmail.sendRefuseEmail(email, "projet.inte@gmail.com", "projetint2000", "Demand refused");
                  }catch(SQLException e){
                      
                  }
              }
              }
              
          }else if (label.equals("visited")){
              int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
              if (res == 0){
                  int row = jTable8.getSelectedRow();
              int r = 0;
              if (row >= 0){
                  try{
                      int id = Integer.parseInt(jTable8.getValueAt(row, 0) + "");
                      Connection conn = SingletonConnection.getConnection();
                      String req = "update demande set statutD = 3 where numDemande = ?;";
                      PreparedStatement pstmt = conn.prepareStatement(req);
                      pstmt.setInt(1, id);
                      r = pstmt.executeUpdate();
                      try {
                        showAcceptedDemands("select * from demande join compte where statutD = 1 and demande.id = compte.id;");
                      } catch (ParseException ex) {
                        Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
                      }
                      SendEmail.sendVisitedEmail(email, "projet.inte@gmail.com", "projetint2000", "Site visited");
                  }catch(SQLException e){
                      
                  }
              }
              }
              
          }else if (label.equals("editSA")){
              allowEditSiteAdmin();
              int row = jTable9.getSelectedRow();
              idEditSiteAdmin = Integer.parseInt(jTable9.getValueAt(row, 0) + "");
              setEditSiteAdmin();
          }else if (label.equals("Active")){
              int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
                if (res == 0){
                    activateAccount();
                LinkedList<Compte> listeC = init_ADUserAccounts("select * from compte;");
                jlDisplayX1.setText("1");
                jlDisplayY1.setText(jcbNum1.getSelectedItem() + "");
                jlDisplayMax1.setText("from " + listeC.size());
                displayADUserAccounts(listeC, 0, Integer.parseInt(jcbNum1.getSelectedItem() + ""));
                jTable1.getColumn("").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\activateAccount.png")));
                jTable1.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\activateAccount.png")));
                jTable1.getColumn(" ").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\deactivateAccount.png")));
                jTable1.getColumn(" ").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\deactivateAccount.png")));
                try{
                    int row = jTable7.getSelectedRow();
                    Connection conn = SingletonConnection.getConnection();
                    String req = "select * from compte where id = ?;";
                    PreparedStatement pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, Integer.parseInt(jTable7.getValueAt(row, 0) + ""));
                    ResultSet rs = pstmt.executeQuery();
                    rs.next();
                    SendEmail.sendActivatedEmail(rs.getString("email"), "projet.inte@gmail.com", "projetint2000", "Account reactivated");
                }catch(SQLException e){
                    
                }
                }
                
          }else if (label.equals("Inactive")){
              int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
                if (res == 0){
                    deactivateAccount();
                LinkedList<Compte> listeC = init_ADUserAccounts("select * from compte;");
                jlDisplayX1.setText("1");
                jlDisplayY1.setText(jcbNum1.getSelectedItem() + "");
                jlDisplayMax1.setText("from " + listeC.size());
                displayADUserAccounts(listeC, 0, Integer.parseInt(jcbNum1.getSelectedItem() + ""));
                jTable1.getColumn("").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\activateAccount.png")));
                jTable1.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\activateAccount.png")));
                jTable1.getColumn(" ").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\deactivateAccount.png")));
                jTable1.getColumn(" ").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\deactivateAccount.png")));
                try{
                    int row = jTable7.getSelectedRow();
                    Connection conn = SingletonConnection.getConnection();
                    String req = "select * from compte where id = ?;";
                    PreparedStatement pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, Integer.parseInt(jTable7.getValueAt(row, 0) + ""));
                    ResultSet rs = pstmt.executeQuery();
                    rs.next();
                    SendEmail.sendDeactivatedEmail(rs.getString("email"), "projet.inte@gmail.com", "projetint2000", "Account deactivated");
                }catch(SQLException e){
                    
                }
                }
                
          }
        }
        isPushed = false;
        return new String(label);
      }

      public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
      }

      protected void fireEditingStopped() {
        super.fireEditingStopped();
      }
    }
    
    public void jtableDesign(JTable jt, JScrollPane jsp){
        jt.getTableHeader().setFont(new Font("Segoe UI", 0, 12));
        jt.getTableHeader().setOpaque(false);
        jt.getTableHeader().setBackground(colorBG);
        jt.getTableHeader().setForeground(colorOnBackground);
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(color1_255);
        //headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < jt.getModel().getColumnCount(); i++) {
                jt.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        jsp.getViewport().setBackground(colorBG);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        //centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jt.setDefaultRenderer(String.class, centerRenderer);
        jt.setDefaultRenderer(Integer.class, centerRenderer);
    }
    
    public void jcomboboxDesign(JComboBox jcb, Color color1, Color color2, ImageIcon image){
        jcb.setRenderer(new CustomComboBoxRenderer(color1, color2));
        jcb.setEditor(new CustomComboBoxEditor(color1, color2));
        jcb.setUI(new BasicComboBoxUI() {
        @Override
        protected JButton createArrowButton() {
            Icon icon = image;;
            JButton b = new JButton(icon);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
            b.setContentAreaFilled(false);
            b.setPreferredSize(new Dimension(1, 1));
            b.setBorderPainted(true);
            return b; 
        }
        });
         Object popup = jcb.getUI().getAccessibleChild(jcb, 0);
      Component c = ((Container) popup).getComponent(0);
      if (c instanceof JScrollPane)
      {
         JScrollPane scrollpane = (JScrollPane) c;
         JScrollBar scrollBar = scrollpane.getVerticalScrollBar();
         Dimension scrollBarDim = new Dimension(4, scrollBar
               .getPreferredSize().height);
         scrollBar.setPreferredSize(scrollBarDim);
      }
    }
    public void jcomboboxDesign2(JComboBox jcb, Color color1, Color color2, ImageIcon image){
        jcb.setRenderer(new CustomComboBoxRenderer(color1, color2));
        jcb.setEditor(new CustomComboBoxEditor(color1, color2));
        jcb.setUI(new BasicComboBoxUI() {
        @Override
        protected JButton createArrowButton() {
                return new JButton() {
                    @Override
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        jcb.remove(jcb.getComponent(0));
         Object popup = jcb.getUI().getAccessibleChild(jcb, 0);
      Component c = ((Container) popup).getComponent(0);
      if (c instanceof JScrollPane)
      {
         JScrollPane scrollpane = (JScrollPane) c;
         JScrollBar scrollBar = scrollpane.getVerticalScrollBar();
         Dimension scrollBarDim = new Dimension(4, scrollBar
               .getPreferredSize().height);
         scrollBar.setPreferredSize(scrollBarDim);
      }
    }
    
    NumberFormatter formatter;
    
    /**
     * Creates new form JpTemp
     */
    public JpDisplay() {
        selectTheme();
        initComponents();
        
        if (poste.equals("superadmin")){
            jcomboboxDesign(jcbFAPos, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbFAState, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbNum1, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbCity, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbComp, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbCityEdit, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbCompEdit, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbNumES, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbSComp, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbSG, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jtableDesign(jTable1, jScrollPane1);
            jtableDesign(jTable2, jScrollPane2);
            jtableDesign(jTable3, jScrollPane3);
            jtableDesign(jTable4, jScrollPane4);

            jTable4.getColumn("Delete").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\delete.png")));
            jTable4.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\delete.png")));
            jTable4.getColumn("Edit").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\edit.png")));
            jTable4.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\edit.png")));
            jrbCU.doClick();


            jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(4, 0));
            JPanel panel = new JPanel();
            panel.setBackground(color1_255);
            jScrollPane1.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
            jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(4, 0));
            panel = new JPanel();
            panel.setBackground(color1_255);
            setDashboard("superadmin");
            jScrollPane2.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
            CardLayout c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card17");
            jLabel9.setText("Administration - Create new user account");
            c = (CardLayout) jPanel1.getLayout();
            c.show(jPanel1, "card1");
        }else if (poste.equals("admin")){
            jcomboboxDesign(jcbSEditC1, colorOnSurface_80, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDownFaded.png"));
            jcomboboxDesign(jcbCityEdit1, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbSEditGH1, colorOnSurface_80, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDownFaded.png"));
            
            
            jTable9.getColumn("Edit").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\edit.png")));
            jTable9.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\edit.png")));
            setDashboard("admin");
            CardLayout c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card17");
            jLabel9.setText("Administration - Create new user account");
            c = (CardLayout) jPanel1.getLayout();
            c.show(jPanel1, "card6");
        }else if (poste.equals("utilisateurentreprise")){
            jcomboboxDesign(jcbDayE, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbMonthE, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbYearE, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign2(jcbMinS, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign2(jcbHourS, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbDayS, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbMonthS, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbYearS, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign2(jcbMinE, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign2(jcbHourE, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbNum2, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            
            jtfNumV.setTransferHandler(null);
            setDashboard("utilisateurentreprise");
            CardLayout c = (CardLayout) jPanel1.getLayout();
            c.show(jPanel1, "card2");
            c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card16");
            jLabel9.setText("Company user - Home");
        }else if (poste.equals("guerite")){
            jTable7.getColumn("Accept").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\accept.png")));
            jTable7.getColumn("Accept").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\accept.png")));
            jTable7.getColumn("Refuse").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\refuse.png")));
            jTable7.getColumn("Refuse").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\refuse.png")));
            jcomboboxDesign(jcbNum2, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            
            try {
                showPendingDemands("select * from demande join compte where statutD = 2 and demande.id = compte.id;");
            } catch (ParseException ex) {
                Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
            }
            setDashboard("guerite");
            CardLayout c = (CardLayout) jPanel1.getLayout();
            c.show(jPanel1, "card4");
            c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card16");
            jLabel9.setText("Gatehouse - Home");
        }else if (poste.equals("responsablesite")){
            jcomboboxDesign(jcbSG1, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbSComp1, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            jcomboboxDesign(jcbNum2, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
            setEditSiteMan();
            setDashboard("responsablesite");
            CardLayout c = (CardLayout) jPanel1.getLayout();
            c.show(jPanel1, "card5");
            c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card16");
            jLabel9.setText("Site manager - Home");
        }
        //int r = JOptionPane.showConfirmDialog(null, "choose one", "choose one", JOptionPane.YES_NO_OPTION);
    }
    
    public void blockTextField(JLabel jl, JTextField jtf, JLabel jlErr){
        jl.setForeground(colorOnSurface_80);
        jlErr.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlErr.setForeground(colorOnSurface_80);
        jtf.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jtf.setEditable(false);
        jtf.setText("");
        if (jl.getText().equals("Business")){
            jlabelCABS.setForeground(colorOnSurface_80);
            jlabelCABS.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
            jlabelCABS.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        }
    }
    
    public void allowTextField(JLabel jl, JTextField jtf, boolean editable, JLabel jlErr){
        jl.setForeground(colorOnSurface);
        jlErr.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErr.setForeground(colorOnSurface);
        jtf.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        if (!jl.getText().equals("Business")) jtf.setEditable(editable);
        if (jl.getText().equals("Business")){
            jlabelCABS.setForeground(colorOnSurface);
            jlabelCABS.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
            jlabelCABS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
    }
    
    public void blockJCB(JLabel jl, JComboBox jcb, JLabel jlSelect){
        jl.setForeground(colorOnSurface_80);
        jlSelect.setForeground(colorOnSurface_80);
        jcb.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jcb.setSelectedIndex(0);
        jcb.setEnabled(false);
        jcomboboxDesign(jcb, colorOnSurface_80, color2_80, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDownFaded.png"));
    }
    
    public void allowJCB(JLabel jl, JComboBox jcb, JLabel jlSelect){
        jl.setForeground(colorOnSurface);
        jlSelect.setForeground(colorOnSurface);
        jcb.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jcb.setSelectedIndex(0);
        jcb.setEnabled(true);
        jcomboboxDesign(jcb, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
    }
    
    public void blockLabel(JLabel jl){
        jl.setForeground(colorOnSurface_80);
        jl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jl.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    public void allowLabel(JLabel jl){
        jl.setForeground(colorOnSurface);
        jl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel8 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel9 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel10 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel11 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel13 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jPanel37 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel38 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jPanel42 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jPanel97 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel63 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jPanel55 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jPanel56 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jPanel60 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jPanel62 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jPanel64 = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jPanel67 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jPanel69 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jPanel70 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jPanel74 = new javax.swing.JPanel();
        jPanel75 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jPanel76 = new RoundedPanel(new Dimension(10, 10), 6, 3, 150);
        jLabel123 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jrbCU = new javax.swing.JRadioButton();
        jrbSM = new javax.swing.JRadioButton();
        jrbG = new javax.swing.JRadioButton();
        jrbA = new javax.swing.JRadioButton();
        jtfTel = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jtfFName = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jtfEmail = new javax.swing.JTextField();
        jtfName = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jtfB = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jtfPos = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jtfCIN = new javax.swing.JTextField();
        jPanel16 = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jLabel31 = new javax.swing.JLabel();
        jlErrTel = new javax.swing.JLabel();
        jlErrB = new javax.swing.JLabel();
        jlErrPos = new javax.swing.JLabel();
        jlErrName = new javax.swing.JLabel();
        jlErrFName = new javax.swing.JLabel();
        jlErrEmail = new javax.swing.JLabel();
        jlErrCIN = new javax.swing.JLabel();
        jlabelCABS = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel17 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jScrollPane1 = new JScrollPane();
        jTable1 = new javax.swing.JTable();
        jcbFAPos = new javax.swing.JComboBox<>();
        jtfFAName = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jcbFAState = new javax.swing.JComboBox<>();
        jcbNum1 = new javax.swing.JComboBox<>();
        jpNext1 = new javax.swing.JPanel();
        jlNext1 = new javax.swing.JLabel();
        jpPrevious1 = new javax.swing.JPanel();
        jlPrevious1 = new javax.swing.JLabel();
        jlDisplayX1 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jlDisplayY1 = new javax.swing.JLabel();
        jlDisplayMax1 = new javax.swing.JLabel();
        jPanel90 = new javax.swing.JPanel();
        jLabel136 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jLabel32 = new javax.swing.JLabel();
        jtfSName = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jtfSCity = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jtfSIDA = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jPanel20 = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jLabel37 = new javax.swing.JLabel();
        jlErrSName = new javax.swing.JLabel();
        jlErrSCity = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jtfSIDM = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jlErrSComp1 = new javax.swing.JLabel();
        jlErrSComp3 = new javax.swing.JLabel();
        jcbSG = new javax.swing.JComboBox<>();
        jlDelG1 = new javax.swing.JLabel();
        jlAddG1 = new javax.swing.JLabel();
        jcbSComp = new javax.swing.JComboBox<>();
        jlDelC1 = new javax.swing.JLabel();
        jlAddC1 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jtfSearchFilterRS = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jlSearchFilter = new javax.swing.JLabel();
        jPanel27 = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jLabel51 = new javax.swing.JLabel();
        jPanel32 = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jLabel56 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jPanel30 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jcbCity = new javax.swing.JComboBox<>();
        jtfSFName = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jcbComp = new javax.swing.JComboBox<>();
        jPanel24 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel35 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jcbCityEdit = new javax.swing.JComboBox<>();
        jtfSFNameEdit = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jcbCompEdit = new javax.swing.JComboBox<>();
        jlMSIDGh = new javax.swing.JLabel();
        jpMSSave = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jlMSSave = new javax.swing.JLabel();
        jpMSDefault = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jlMSDefault = new javax.swing.JLabel();
        jcbSEditGH = new javax.swing.JComboBox<>();
        jlAddG = new javax.swing.JLabel();
        jlDelG = new javax.swing.JLabel();
        jlMSIDC = new javax.swing.JLabel();
        jcbSEditC = new javax.swing.JComboBox<>();
        jlDelC = new javax.swing.JLabel();
        jlAddC = new javax.swing.JLabel();
        jlMSIDAdmin = new javax.swing.JLabel();
        jtfMSIDAdmin = new javax.swing.JTextField();
        jlMSVL = new javax.swing.JLabel();
        jlMSVL1 = new javax.swing.JLabel();
        jtfMSIDMan = new javax.swing.JTextField();
        jlMSIDMan = new javax.swing.JLabel();
        jcbNumES = new javax.swing.JComboBox<>();
        jlPreviousES = new javax.swing.JLabel();
        jlDisplayXES = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jlDisplayYES = new javax.swing.JLabel();
        jlDisplayMaxES = new javax.swing.JLabel();
        jlNextES = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jPanel28 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jPanel33 = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jLabel50 = new javax.swing.JLabel();
        jlErrEmailReset = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jtfEmailReset = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel41 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jLabel71 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jtfNameP = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jtfEmailP = new javax.swing.JTextField();
        jPanel43 = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jLabel75 = new javax.swing.JLabel();
        jlErrP = new javax.swing.JLabel();
        jlErrEmailP = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jtfNumV = new javax.swing.JTextField();
        jlErrNumV = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jcbDayS = new javax.swing.JComboBox<>();
        jcbMonthS = new javax.swing.JComboBox<>();
        jcbYearS = new javax.swing.JComboBox<>();
        jcbHourS = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jcbMinS = new javax.swing.JComboBox<>();
        jPanel44 = new javax.swing.JPanel();
        jcbDayE = new javax.swing.JComboBox<>();
        jcbMonthE = new javax.swing.JComboBox<>();
        jcbYearE = new javax.swing.JComboBox<>();
        jcbMinE = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jcbHourE = new javax.swing.JComboBox<>();
        jlErrSD = new javax.swing.JLabel();
        jlErrED = new javax.swing.JLabel();
        jlAlertED = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jPanel40 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jScrollPane5 = new JScrollPane();
        jTable5 = new javax.swing.JTable();
        jtfFAName1 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jcbNum2 = new javax.swing.JComboBox<>();
        jpNext2 = new javax.swing.JPanel();
        jlNext2 = new javax.swing.JLabel();
        jpPrevious2 = new javax.swing.JPanel();
        jlPrevious2 = new javax.swing.JLabel();
        jlDisplayX2 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jlDisplayY2 = new javax.swing.JLabel();
        jlDisplayMax2 = new javax.swing.JLabel();
        jPanel54 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jPanel57 = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jScrollPane6 = new JScrollPane();
        jTable6 = new javax.swing.JTable();
        jtfFAName2 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jtfNamePE = new javax.swing.JTextField();
        jlErrP1 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        jcbDaySE = new javax.swing.JComboBox<>();
        jcbMonthSE = new javax.swing.JComboBox<>();
        jcbYearSE = new javax.swing.JComboBox<>();
        jcbHourSE = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        jcbMinSE = new javax.swing.JComboBox<>();
        jLabel80 = new javax.swing.JLabel();
        jtfEmailPE = new javax.swing.JTextField();
        jlErrP2 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jtfNumVE = new javax.swing.JTextField();
        jlErrP3 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jPanel49 = new javax.swing.JPanel();
        jcbDayEE = new javax.swing.JComboBox<>();
        jcbMonthEE = new javax.swing.JComboBox<>();
        jcbYearEE = new javax.swing.JComboBox<>();
        jcbHourEE = new javax.swing.JComboBox<>();
        jLabel65 = new javax.swing.JLabel();
        jcbMinEE = new javax.swing.JComboBox<>();
        jlErrSD1 = new javax.swing.JLabel();
        jlAlertED1 = new javax.swing.JLabel();
        jPanel50 = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jLabel83 = new javax.swing.JLabel();
        jPanel51 = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jLabel84 = new javax.swing.JLabel();
        jlErrSD2 = new javax.swing.JLabel();
        jlAlertED2 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jPanel53 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jScrollPane7 = new JScrollPane();
        jTable7 = new javax.swing.JTable();
        jcbFAPos2 = new javax.swing.JComboBox<>();
        jtfFAName5 = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        jPanel63 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jScrollPane8 = new JScrollPane();
        jTable8 = new javax.swing.JTable();
        jtfFAName6 = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jtfFAName7 = new javax.swing.JTextField();
        jLabel137 = new javax.swing.JLabel();
        jPanel68 = new javax.swing.JPanel();
        jPanel71 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jLabel119 = new javax.swing.JLabel();
        jPanel72 = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jLabel122 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jcbSG1 = new javax.swing.JComboBox<>();
        jlDelG2 = new javax.swing.JLabel();
        jlAddG2 = new javax.swing.JLabel();
        jcbSComp1 = new javax.swing.JComboBox<>();
        jlDelC2 = new javax.swing.JLabel();
        jlAddC2 = new javax.swing.JLabel();
        jPanel73 = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jLabel126 = new javax.swing.JLabel();
        jPanel61 = new javax.swing.JPanel();
        jPanel77 = new javax.swing.JPanel();
        jPanel78 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jcbCityEdit1 = new javax.swing.JComboBox<>();
        jtfSFNameEdit1 = new javax.swing.JTextField();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jlMSIDGh1 = new javax.swing.JLabel();
        jpMSSave1 = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jlMSSave1 = new javax.swing.JLabel();
        jpMSDefault1 = new RoundedPanel(new Dimension(8, 8), 5, 2, 30);
        jlMSDefault1 = new javax.swing.JLabel();
        jcbSEditGH1 = new javax.swing.JComboBox<>();
        jlAddG4 = new javax.swing.JLabel();
        jlDelG4 = new javax.swing.JLabel();
        jlMSIDC1 = new javax.swing.JLabel();
        jcbSEditC1 = new javax.swing.JComboBox<>();
        jlDelC4 = new javax.swing.JLabel();
        jlAddC4 = new javax.swing.JLabel();
        jlMSIDAdmin1 = new javax.swing.JLabel();
        jlMSVL3 = new javax.swing.JLabel();
        jtfMSIDMan1 = new javax.swing.JTextField();
        jlErrSComp4 = new javax.swing.JLabel();
        jPanel65 = new javax.swing.JPanel();
        jPanel66 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jLabel101 = new javax.swing.JLabel();
        jPanel79 = new javax.swing.JPanel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jPanel80 = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jPanel81 = new javax.swing.JPanel();
        jLabel110 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jPanel82 = new javax.swing.JPanel();
        jLabel128 = new javax.swing.JLabel();
        jPanel83 = new javax.swing.JPanel();
        jLabel129 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jPanel84 = new javax.swing.JPanel();
        jLabel133 = new javax.swing.JLabel();
        jPanel85 = new javax.swing.JPanel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jPanel86 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel87 = new RoundedPanel(new Dimension(25, 25), 5, 2, 30);
        jLabel138 = new javax.swing.JLabel();
        jPanel88 = new javax.swing.JPanel();
        jLabel139 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jPanel89 = new javax.swing.JPanel();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jPanel91 = new javax.swing.JPanel();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jPanel92 = new javax.swing.JPanel();
        jLabel145 = new javax.swing.JLabel();
        jPanel93 = new javax.swing.JPanel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jPanel94 = new javax.swing.JPanel();
        jLabel148 = new javax.swing.JLabel();
        jPanel95 = new javax.swing.JPanel();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        jPanel96 = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(2, 71, 94));
        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel4.setBackground(color2_255);

        jPanel7.setBackground(colorBG);
        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7MouseExited(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\addIcon.png")); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel12.setForeground(colorOnBackground);
        jLabel12.setText("CREATE NEW SITE");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel13.setForeground(colorSecondary);
        jLabel13.setText("SITES MANAGEMENT");

        jPanel8.setBackground(colorBG);
        jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel8MouseExited(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\addIcon.png")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setForeground(colorOnBackground);
        jLabel2.setText("CREATE NEW USER ACCOUNT");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel9.setBackground(colorBG);
        jPanel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel9MouseExited(evt);
            }
        });

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\editIcon.png")); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel15.setForeground(colorOnBackground);
        jLabel15.setText("EDIT/DELETE SITE");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel10.setBackground(colorBG);
        jPanel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel10MouseExited(evt);
            }
        });

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\consultIcon.png")); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel19.setForeground(colorOnBackground);
        jLabel19.setText("CONSULT SITES");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 40)); // NOI18N
        jLabel8.setForeground(colorOnSurface);
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setForeground(colorSecondary);
        jLabel3.setText("USERS MANAGEMENT");

        jPanel11.setBackground(colorBG);
        jPanel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel11MouseExited(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\activate.png")); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setForeground(colorOnBackground);
        jLabel5.setText("ACTIVATE/DEACTIVATE USER ACCOUNT");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel13.setBackground(colorBG);
        jPanel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel13MouseExited(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\addIcon.png")); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel7.setForeground(colorOnBackground);
        jLabel7.setText("HOME");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel8)
                .addGap(51, 51, 51)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, "card2");

        jPanel34.setBackground(color2_255);

        jPanel37.setBackground(colorBG);
        jPanel37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel37MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel37MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel37MouseExited(evt);
            }
        });

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\consultIcon.png")); // NOI18N

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel42.setForeground(colorOnSurface);
        jLabel42.setText("CONSULT DEMANDS");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel38.setBackground(colorBG);
        jPanel38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel38MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel38MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel38MouseExited(evt);
            }
        });

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\addIcon.png")); // NOI18N

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel47.setForeground(colorOnSurface);
        jLabel47.setText("CREATE NEW DEMAND");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel66.setFont(new java.awt.Font("Segoe UI", 0, 40)); // NOI18N
        jLabel66.setForeground(colorOnSurface);
        jLabel66.setText("LOGO");

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel67.setForeground(colorOnSurface);
        jLabel67.setText("DEMAND MANAGEMENT");

        jPanel42.setBackground(colorBG);
        jPanel42.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel42MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel42MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel42MouseExited(evt);
            }
        });

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel68.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\editIcon.png")); // NOI18N

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel69.setForeground(colorOnSurface);
        jLabel69.setText("EDIT DEMANDS");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel68, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel97.setBackground(colorBG);
        jPanel97.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel97.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel97MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel97MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel97MouseExited(evt);
            }
        });

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\addIcon.png")); // NOI18N

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel70.setForeground(colorOnSurface);
        jLabel70.setText("HOME");

        javax.swing.GroupLayout jPanel97Layout = new javax.swing.GroupLayout(jPanel97);
        jPanel97.setLayout(jPanel97Layout);
        jPanel97Layout.setHorizontalGroup(
            jPanel97Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel97Layout.createSequentialGroup()
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
        );
        jPanel97Layout.setVerticalGroup(
            jPanel97Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel97Layout.createSequentialGroup()
                .addGroup(jPanel97Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel97, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(jPanel97, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(309, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel34, "card2");

        jPanel52.setBackground(color2_255);

        jPanel55.setBackground(colorBG);
        jPanel55.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel55MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel55MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel55MouseExited(evt);
            }
        });

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel87.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\addIcon.png")); // NOI18N

        jLabel88.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel88.setForeground(colorOnSurface);
        jLabel88.setText("HOME");

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel87, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel95.setFont(new java.awt.Font("Segoe UI", 0, 40)); // NOI18N
        jLabel95.setForeground(colorOnSurface);
        jLabel95.setText("LOGO");

        jLabel96.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel96.setForeground(colorOnSurface);
        jLabel96.setText("DEMAND MANAGEMENT");

        jPanel56.setBackground(colorBG);
        jPanel56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel56MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel56MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel56MouseExited(evt);
            }
        });

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel97.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\editIcon.png")); // NOI18N

        jLabel98.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel98.setForeground(colorOnSurface);
        jLabel98.setText("EDIT DEMANDS");

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel56Layout.createSequentialGroup()
                .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel97, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel60.setBackground(colorBG);
        jPanel60.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel60MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel60MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel60MouseExited(evt);
            }
        });

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel99.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\consultIcon.png")); // NOI18N

        jLabel100.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel100.setForeground(colorOnSurface);
        jLabel100.setText("CONSULT DEMANDS");

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel60Layout.createSequentialGroup()
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel99, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel62.setBackground(colorBG);
        jPanel62.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel62.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel62MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel62MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel62MouseExited(evt);
            }
        });

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel103.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\consultIcon.png")); // NOI18N

        jLabel104.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel104.setForeground(colorOnSurface);
        jLabel104.setText("TOGGLE DEMAND");

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel62Layout.createSequentialGroup()
                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel103, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel96, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel52Layout.createSequentialGroup()
                                .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel60, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(297, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel52, "card4");

        jPanel64.setBackground(color2_255);

        jLabel105.setFont(new java.awt.Font("Segoe UI", 0, 40)); // NOI18N
        jLabel105.setForeground(colorOnSurface);
        jLabel105.setText("LOGO");

        jLabel106.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel106.setForeground(colorOnSurface);
        jLabel106.setText("MANAGEMENT");

        jPanel67.setBackground(colorBG);
        jPanel67.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel67MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel67MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel67MouseExited(evt);
            }
        });

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel111.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\editIcon.png")); // NOI18N

        jLabel112.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel112.setForeground(colorOnSurface);
        jLabel112.setText("EDIT SITE");

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel67Layout.createSequentialGroup()
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel111, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel69.setBackground(colorBG);
        jPanel69.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel69.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel69MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel69MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel69MouseExited(evt);
            }
        });

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel115.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\consultIcon.png")); // NOI18N

        jLabel116.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel116.setForeground(colorOnSurface);
        jLabel116.setText("CONSULT DEMANDS");

        javax.swing.GroupLayout jPanel69Layout = new javax.swing.GroupLayout(jPanel69);
        jPanel69.setLayout(jPanel69Layout);
        jPanel69Layout.setHorizontalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel69Layout.createSequentialGroup()
                .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel116, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel69Layout.setVerticalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel69Layout.createSequentialGroup()
                .addGroup(jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel115, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel116, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel70.setBackground(colorBG);
        jPanel70.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel70.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel70MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel70MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel70MouseExited(evt);
            }
        });

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel117.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\addIcon.png")); // NOI18N

        jLabel118.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel118.setForeground(colorOnSurface);
        jLabel118.setText("HOME");

        javax.swing.GroupLayout jPanel70Layout = new javax.swing.GroupLayout(jPanel70);
        jPanel70.setLayout(jPanel70Layout);
        jPanel70Layout.setHorizontalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel118, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
        );
        jPanel70Layout.setVerticalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel70Layout.createSequentialGroup()
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel117, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel118, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel64Layout.createSequentialGroup()
                        .addComponent(jPanel70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel64Layout.createSequentialGroup()
                        .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel69, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel64Layout.createSequentialGroup()
                                .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(jPanel70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(350, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel64, "card5");

        jPanel74.setBackground(color2_255);

        jPanel75.setBackground(colorBG);
        jPanel75.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel75.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel75MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel75MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel75MouseExited(evt);
            }
        });

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel120.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\addIcon.png")); // NOI18N

        jLabel121.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel121.setForeground(colorOnSurface);
        jLabel121.setText("HOME");

        javax.swing.GroupLayout jPanel75Layout = new javax.swing.GroupLayout(jPanel75);
        jPanel75.setLayout(jPanel75Layout);
        jPanel75Layout.setHorizontalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel75Layout.createSequentialGroup()
                .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel121, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
        );
        jPanel75Layout.setVerticalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel75Layout.createSequentialGroup()
                .addGroup(jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel120, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel121, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel113.setFont(new java.awt.Font("Segoe UI", 0, 40)); // NOI18N
        jLabel113.setForeground(colorOnSurface);
        jLabel113.setText("LOGO");

        jLabel114.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel114.setForeground(colorOnSurface);
        jLabel114.setText("MANAGEMENT");

        jPanel76.setBackground(colorBG);
        jPanel76.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel76.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel76MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel76MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel76MouseExited(evt);
            }
        });

        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel123.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\editIcon.png")); // NOI18N

        jLabel124.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel124.setForeground(colorOnSurface);
        jLabel124.setText("EDIT SITE");

        javax.swing.GroupLayout jPanel76Layout = new javax.swing.GroupLayout(jPanel76);
        jPanel76.setLayout(jPanel76Layout);
        jPanel76Layout.setHorizontalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel76Layout.createSequentialGroup()
                .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel124, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel76Layout.setVerticalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel76Layout.createSequentialGroup()
                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel123, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel124, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel74Layout = new javax.swing.GroupLayout(jPanel74);
        jPanel74.setLayout(jPanel74Layout);
        jPanel74Layout.setHorizontalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel74Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel74Layout.createSequentialGroup()
                        .addComponent(jPanel75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel74Layout.createSequentialGroup()
                        .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel74Layout.createSequentialGroup()
                                .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel74Layout.setVerticalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel74Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(jPanel75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(407, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel74, "card6");

        jPanel2.setBackground(color1_255);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel9.setForeground(colorOnPrimary);
        jLabel9.setText("Administration - Create new user account");

        jButton1.setText("logout");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("ct");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(29, 29, 29)
                .addComponent(jButton1)
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(250, 243, 243));
        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel14.setBackground(colorBG);

        jPanel15.setBackground(color2_255);

        buttonGroup1.add(jrbCU);
        jrbCU.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jrbCU.setForeground(colorOnSurface);
        jrbCU.setText("Company user");
        jrbCU.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\uncheckedCheckMini.png")); // NOI18N
        jrbCU.setOpaque(false);
        jrbCU.setRolloverEnabled(false);
        jrbCU.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\checkedCheckMini.png")); // NOI18N
        jrbCU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbCUMouseClicked(evt);
            }
        });

        buttonGroup1.add(jrbSM);
        jrbSM.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jrbSM.setForeground(colorOnSurface);
        jrbSM.setText(" Site manager");
        jrbSM.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\uncheckedCheckMini.png")); // NOI18N
        jrbSM.setOpaque(false);
        jrbSM.setRolloverEnabled(false);
        jrbSM.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\checkedCheckMini.png")); // NOI18N
        jrbSM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbSMMouseClicked(evt);
            }
        });

        buttonGroup1.add(jrbG);
        jrbG.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jrbG.setForeground(colorOnSurface);
        jrbG.setText("Gatehouse");
        jrbG.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\uncheckedCheckMini.png")); // NOI18N
        jrbG.setOpaque(false);
        jrbG.setRolloverEnabled(false);
        jrbG.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\checkedCheckMini.png")); // NOI18N
        jrbG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbGMouseClicked(evt);
            }
        });

        buttonGroup1.add(jrbA);
        jrbA.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jrbA.setForeground(colorOnSurface);
        jrbA.setText("Admin");
        jrbA.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\uncheckedCheckMini.png")); // NOI18N
        jrbA.setOpaque(false);
        jrbA.setRolloverEnabled(false);
        jrbA.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\checkedCheckMini.png")); // NOI18N
        jrbA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbAMouseClicked(evt);
            }
        });

        jtfTel.setBackground(color2_255);
        jtfTel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfTel.setForeground(colorBG);
        jtfTel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfTel.setOpaque(false);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel10.setForeground(colorOnSurface);
        jLabel10.setText("First name");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel16.setForeground(colorOnSurface);
        jLabel16.setText("Family name");

        jtfFName.setBackground(color2_255);
        jtfFName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfFName.setForeground(colorBG);
        jtfFName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfFName.setOpaque(false);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel21.setForeground(colorOnSurface);
        jLabel21.setText("Email");

        jtfEmail.setBackground(color2_255);
        jtfEmail.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfEmail.setForeground(colorBG);
        jtfEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfEmail.setOpaque(false);
        jtfEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfEmailActionPerformed(evt);
            }
        });

        jtfName.setBackground(color2_255);
        jtfName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfName.setForeground(colorBG);
        jtfName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfName.setOpaque(false);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel27.setForeground(colorOnSurface);
        jLabel27.setText("Phone number");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel28.setForeground(colorOnSurface);
        jLabel28.setText("Business");

        jtfB.setEditable(false);
        jtfB.setBackground(color2_255);
        jtfB.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfB.setForeground(colorBG);
        jtfB.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfB.setOpaque(false);

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel29.setForeground(colorOnSurface);
        jLabel29.setText("Position");

        jtfPos.setBackground(color2_255);
        jtfPos.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfPos.setForeground(colorBG);
        jtfPos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfPos.setOpaque(false);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel30.setForeground(colorOnSurface_80);
        jLabel30.setText("CIN");

        jtfCIN.setEditable(false);
        jtfCIN.setBackground(new Color(0,0,0,0));
        jtfCIN.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfCIN.setForeground(colorBG);
        jtfCIN.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jtfCIN.setOpaque(false);

        jPanel16.setBackground(colorSecondary);
        jPanel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setForeground(colorOnSecondary);
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("VALIDATE");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jlErrTel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrTel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrTelMouseClicked(evt);
            }
        });

        jlErrB.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrBMouseClicked(evt);
            }
        });

        jlErrPos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrPos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrPosMouseClicked(evt);
            }
        });

        jlErrName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrNameMouseClicked(evt);
            }
        });

        jlErrFName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrFName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrFNameMouseClicked(evt);
            }
        });

        jlErrEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrEmailMouseClicked(evt);
            }
        });

        jlErrCIN.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlErrCIN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrCINMouseClicked(evt);
            }
        });

        jlabelCABS.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlabelCABS.setForeground(colorOnSurface);
        jlabelCABS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlabelCABS.setText("Select");
        jlabelCABS.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlabelCABS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlabelCABS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabelCABSMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel16)
                            .addComponent(jLabel10))
                        .addGap(0, 370, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtfName, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                                    .addComponent(jtfFName)
                                    .addComponent(jtfEmail))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlErrName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlErrFName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlErrEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jrbSM)
                                    .addComponent(jrbCU))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jrbA)
                                    .addComponent(jrbG))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel27)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                            .addComponent(jtfB, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                            .addGap(0, 0, 0)
                            .addComponent(jlErrB, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(jlabelCABS))
                        .addGroup(jPanel15Layout.createSequentialGroup()
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jtfPos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                .addComponent(jtfTel, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jtfCIN))
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jlErrPos, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jlErrTel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jlErrCIN, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(38, 38, 38))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(313, 313, 313)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 315, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfTel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlErrTel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlErrName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfFName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfB, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jlErrB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlErrFName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jlabelCABS, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtfPos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlErrPos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlErrEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtfCIN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlErrCIN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jrbCU)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jrbSM))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jrbA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jrbG)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(430, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(407, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel14, "card2");

        jPanel12.setBackground(colorBG);

        jPanel17.setBackground(color2_255);

        jScrollPane1.setBackground(colorBG);
        jScrollPane1.setBorder(null);

        jTable1.setBackground(colorBG);
        jTable1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTable1.setForeground(colorOnSurface);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "First name", "Family name", "Position", "Active"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.setGridColor(colorBG);
        jTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable1.setOpaque(false);
        jTable1.setRowHeight(25);
        jTable1.setSelectionBackground(colorSecondary_80);
        jTable1.setSelectionForeground(colorOnBackground);
        jTable1.setShowVerticalLines(false);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(40);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

        jcbFAPos.setBackground(color2_255);
        jcbFAPos.setEditable(true);
        jcbFAPos.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbFAPos.setForeground(color1_255);
        jcbFAPos.setMaximumRowCount(5);
        jcbFAPos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Position--", "Admin", "Site manager", "Gatehouse", "Company user" }));
        jcbFAPos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jcbFAPos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbFAPos.setOpaque(false);
        jcbFAPos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbFAPosActionPerformed(evt);
            }
        });

        jtfFAName.setBackground(color2_255);
        jtfFAName.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfFAName.setForeground(color1_255);
        jtfFAName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jtfFAName.setOpaque(false);
        jtfFAName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfFANameKeyReleased(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel22.setForeground(colorOnSurface);
        jLabel22.setText("Search by");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel23.setForeground(colorOnSurface);
        jLabel23.setText("First or Family name");

        jcbFAState.setBackground(color2_255);
        jcbFAState.setEditable(true);
        jcbFAState.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbFAState.setForeground(color1_255);
        jcbFAState.setMaximumRowCount(5);
        jcbFAState.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--State--", "Activated", "Deactivated" }));
        jcbFAState.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jcbFAState.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbFAState.setOpaque(false);
        jcbFAState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbFAStateActionPerformed(evt);
            }
        });

        jcbNum1.setBackground(color2_255);
        jcbNum1.setEditable(true);
        jcbNum1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbNum1.setForeground(color1_255);
        jcbNum1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10", "15", "20", "25" }));
        jcbNum1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jcbNum1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbNum1.setOpaque(false);
        jcbNum1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbNum1ActionPerformed(evt);
            }
        });

        jpNext1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpNext1.setOpaque(false);
        jpNext1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpNext1MouseClicked(evt);
            }
        });

        jlNext1.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\rightArrowFaded.png")); // NOI18N

        javax.swing.GroupLayout jpNext1Layout = new javax.swing.GroupLayout(jpNext1);
        jpNext1.setLayout(jpNext1Layout);
        jpNext1Layout.setHorizontalGroup(
            jpNext1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpNext1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlNext1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpNext1Layout.setVerticalGroup(
            jpNext1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpNext1Layout.createSequentialGroup()
                .addComponent(jlNext1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpPrevious1.setOpaque(false);
        jpPrevious1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpPrevious1MouseClicked(evt);
            }
        });

        jlPrevious1.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\leftArrowFaded.png")); // NOI18N

        javax.swing.GroupLayout jpPrevious1Layout = new javax.swing.GroupLayout(jpPrevious1);
        jpPrevious1.setLayout(jpPrevious1Layout);
        jpPrevious1Layout.setHorizontalGroup(
            jpPrevious1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrevious1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlPrevious1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpPrevious1Layout.setVerticalGroup(
            jpPrevious1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrevious1Layout.createSequentialGroup()
                .addComponent(jlPrevious1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jlDisplayX1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jlDisplayX1.setForeground(colorOnSurface);
        jlDisplayX1.setText("x");

        jLabel44.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel44.setForeground(colorOnSurface);
        jLabel44.setText("-");

        jlDisplayY1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jlDisplayY1.setForeground(colorOnSurface);
        jlDisplayY1.setText("y");

        jlDisplayMax1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jlDisplayMax1.setForeground(colorOnSurface);
        jlDisplayMax1.setText("from max");

        jPanel90.setBackground(new Color(179, 0, 0, 70));

        javax.swing.GroupLayout jPanel90Layout = new javax.swing.GroupLayout(jPanel90);
        jPanel90.setLayout(jPanel90Layout);
        jPanel90Layout.setHorizontalGroup(
            jPanel90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        jPanel90Layout.setVerticalGroup(
            jPanel90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel136.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel136.setForeground(colorOnSurface);
        jLabel136.setText("Inactive");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                                .addComponent(jPanel90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel136)
                                .addGap(288, 288, 288)
                                .addComponent(jpPrevious1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlDisplayX1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlDisplayY1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlDisplayMax1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jpNext1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                                .addComponent(jcbFAPos, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jcbFAState, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                                    .addComponent(jtfFAName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jcbNum1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel22))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbFAPos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfFAName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbFAState, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jcbNum1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlDisplayX1)
                            .addComponent(jLabel44)
                            .addComponent(jlDisplayY1)
                            .addComponent(jlDisplayMax1)))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpNext1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpPrevious1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel136, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel90, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(1713, 1713, 1713))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(698, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(96, Short.MAX_VALUE)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel12, "card3");

        jPanel18.setBackground(colorBG);

        jPanel19.setBackground(color2_255);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel32.setForeground(colorOnSurface);
        jLabel32.setText("Name");

        jtfSName.setBackground(color2_255);
        jtfSName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jtfSName.setForeground(colorBG);
        jtfSName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfSName.setOpaque(false);

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel33.setForeground(colorOnSurface);
        jLabel33.setText("City");

        jtfSCity.setBackground(color2_255);
        jtfSCity.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jtfSCity.setForeground(colorBG);
        jtfSCity.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfSCity.setOpaque(false);

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel34.setForeground(colorOnSurface);
        jLabel34.setText("Company");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel35.setForeground(colorOnSurface);
        jLabel35.setText("Admin");

        jtfSIDA.setEditable(false);
        jtfSIDA.setBackground(color2_255);
        jtfSIDA.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jtfSIDA.setForeground(colorBG);
        jtfSIDA.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfSIDA.setOpaque(false);

        jLabel36.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel36.setForeground(colorOnSurface);
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Select");
        jLabel36.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jLabel36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });

        jPanel20.setBackground(colorSecondary);
        jPanel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel20MouseClicked(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel37.setForeground(colorOnSecondary);
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("VALIDATE");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jlErrSName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrSName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrSNameMouseClicked(evt);
            }
        });

        jlErrSCity.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrSCity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrSCityMouseClicked(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel38.setForeground(colorOnSurface);
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Select");
        jLabel38.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jLabel38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel39.setForeground(colorOnSurface);
        jLabel39.setText("Site manager");

        jtfSIDM.setEditable(false);
        jtfSIDM.setBackground(color2_255);
        jtfSIDM.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jtfSIDM.setForeground(colorBG);
        jtfSIDM.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfSIDM.setOpaque(false);
        jtfSIDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfSIDMActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel41.setForeground(colorOnSurface);
        jLabel41.setText("Gatehouse");

        jlErrSComp1.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\clearField.png")); // NOI18N
        jlErrSComp1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrSComp1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrSComp1MouseClicked(evt);
            }
        });

        jlErrSComp3.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\clearField.png")); // NOI18N
        jlErrSComp3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrSComp3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrSComp3MouseClicked(evt);
            }
        });

        jcbSG.setBackground(color2_255);
        jcbSG.setEditable(true);
        jcbSG.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbSG.setForeground(color1_255);
        jcbSG.setMaximumRowCount(5);
        jcbSG.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--" }));
        jcbSG.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jcbSG.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbSG.setOpaque(false);
        jcbSG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSGActionPerformed(evt);
            }
        });

        jlDelG1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlDelG1.setForeground(colorOnSurface);
        jlDelG1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlDelG1.setText("Delete");
        jlDelG1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlDelG1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlDelG1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlDelG1MouseClicked(evt);
            }
        });

        jlAddG1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlAddG1.setForeground(colorOnSurface);
        jlAddG1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlAddG1.setText(" | Add");
        jlAddG1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlAddG1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlAddG1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlAddG1MouseClicked(evt);
            }
        });

        jcbSComp.setBackground(color2_255);
        jcbSComp.setEditable(true);
        jcbSComp.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbSComp.setForeground(color1_255);
        jcbSComp.setMaximumRowCount(5);
        jcbSComp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--" }));
        jcbSComp.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jcbSComp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbSComp.setOpaque(false);
        jcbSComp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSCompActionPerformed(evt);
            }
        });

        jlDelC1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlDelC1.setForeground(colorOnSurface);
        jlDelC1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlDelC1.setText("Delete");
        jlDelC1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlDelC1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlDelC1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlDelC1MouseClicked(evt);
            }
        });

        jlAddC1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlAddC1.setForeground(colorOnSurface);
        jlAddC1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlAddC1.setText(" | Add");
        jlAddC1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlAddC1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlAddC1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlAddC1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel19Layout.createSequentialGroup()
                            .addComponent(jLabel39)
                            .addGap(314, 314, 314)
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addComponent(jcbSG, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(0, 0, 0)
                                    .addComponent(jlDelG1)
                                    .addGap(0, 0, 0)
                                    .addComponent(jlAddG1))
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(jPanel19Layout.createSequentialGroup()
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jtfSCity, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                                        .addComponent(jtfSName, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jlErrSName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jlErrSCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                    .addComponent(jtfSIDM, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(jlErrSComp3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel32)
                                        .addComponent(jLabel33))
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addGap(134, 134, 134)
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel19Layout.createSequentialGroup()
                                            .addComponent(jtfSIDA)
                                            .addGap(0, 0, 0)
                                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel34))
                                    .addGap(0, 0, 0)
                                    .addComponent(jlErrSComp1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel19Layout.createSequentialGroup()
                                            .addComponent(jcbSComp, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, 0)
                                            .addComponent(jlDelC1)
                                            .addGap(0, 0, 0)
                                            .addComponent(jlAddC1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(0, 0, Short.MAX_VALUE))))))
                .addGap(16, 16, 16))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jlErrSName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtfSName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jtfSIDA, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlErrSComp1)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfSCity, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlErrSCity, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbSComp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlDelC1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlAddC1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(jLabel41))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfSIDM, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jcbSG, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jlDelG1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jlAddG1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jlErrSComp3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(454, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(455, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(202, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel18, "card4");

        jPanel21.setBackground(colorBG);

        jPanel25.setBackground(colorBG);

        jPanel26.setBackground(color2_255);
        jPanel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel26MouseClicked(evt);
            }
        });

        jScrollPane2.setBackground(colorBG);
        jScrollPane2.setBorder(null);

        jTable2.setBackground(colorBG);
        jTable2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTable2.setForeground(colorOnSurface);
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "First name", "Family name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable2.setFocusable(false);
        jTable2.setGridColor(colorBG);
        jTable2.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable2.setOpaque(false);
        jTable2.setRowHeight(25);
        jTable2.setSelectionBackground(colorSecondary_80);
        jTable2.setSelectionForeground(colorOnSurface);
        jTable2.setShowVerticalLines(false);
        jTable2.getTableHeader().setResizingAllowed(false);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMinWidth(40);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable2.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
        }

        jtfSearchFilterRS.setBackground(color2_255);
        jtfSearchFilterRS.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfSearchFilterRS.setForeground(color1_255);
        jtfSearchFilterRS.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jtfSearchFilterRS.setOpaque(false);
        jtfSearchFilterRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfSearchFilterRSKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfSearchFilterRSKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfSearchFilterRSKeyTyped(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel48.setForeground(colorOnSurface);
        jLabel48.setText("Search by");

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel49.setForeground(colorOnSurface);
        jLabel49.setText("First or Family name");

        jlSearchFilter.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jlSearchFilter.setForeground(colorOnSurface);
        jlSearchFilter.setText("Only site managers are visible");

        jPanel27.setBackground(colorSecondary);
        jPanel27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel27MouseClicked(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel51.setForeground(colorOnSurface);
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("SELECT");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel32.setBackground(colorSecondary);
        jPanel32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel32MouseClicked(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel56.setForeground(colorOnSurface);
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("BACK");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jlSearchFilter)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfSearchFilterRS, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfSearchFilterRS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel49)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jlSearchFilter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(180, 180, 180))
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(134, Short.MAX_VALUE)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1634, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel21Layout.createSequentialGroup()
                    .addGap(0, 405, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 406, Short.MAX_VALUE)))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel21Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel3.add(jPanel21, "card5");

        jPanel29.setBackground(colorBG);

        jPanel30.setBackground(color2_255);

        jScrollPane3.setBackground(colorBG);
        jScrollPane3.setBorder(null);

        jTable3.setBackground(colorBG);
        jTable3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable3.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTable3.setForeground(colorOnSurface);
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "City", "Admin's ID", "Site manager's ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable3.setFocusable(false);
        jTable3.setGridColor(colorBG);
        jTable3.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable3.setOpaque(false);
        jTable3.setRowHeight(25);
        jTable3.setSelectionBackground(color1_255);
        jTable3.setSelectionForeground(color2_255);
        jTable3.setShowVerticalLines(false);
        jTable3.getTableHeader().setResizingAllowed(false);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setMinWidth(40);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable3.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable3.getColumnModel().getColumn(1).setResizable(false);
            jTable3.getColumnModel().getColumn(2).setResizable(false);
            jTable3.getColumnModel().getColumn(3).setMinWidth(60);
            jTable3.getColumnModel().getColumn(3).setMaxWidth(60);
            jTable3.getColumnModel().getColumn(4).setMinWidth(100);
            jTable3.getColumnModel().getColumn(4).setMaxWidth(100);
        }

        jcbCity.setBackground(color2_255);
        jcbCity.setEditable(true);
        jcbCity.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbCity.setForeground(color1_255);
        jcbCity.setMaximumRowCount(5);
        jcbCity.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--City--" }));
        jcbCity.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jcbCity.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbCity.setOpaque(false);
        jcbCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCityActionPerformed(evt);
            }
        });

        jtfSFName.setBackground(color2_255);
        jtfSFName.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfSFName.setForeground(color1_255);
        jtfSFName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jtfSFName.setOpaque(false);
        jtfSFName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfSFNameKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfSFNameKeyTyped(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel52.setForeground(colorOnSurface);
        jLabel52.setText("Search by");

        jLabel53.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel53.setForeground(colorOnSurface);
        jLabel53.setText("Name");

        jcbComp.setBackground(color2_255);
        jcbComp.setEditable(true);
        jcbComp.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbComp.setForeground(color1_255);
        jcbComp.setMaximumRowCount(5);
        jcbComp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Company--" }));
        jcbComp.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jcbComp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbComp.setOpaque(false);
        jcbComp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCompActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel52)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jcbCity, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcbComp, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(jtfSFName)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbCity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfSFName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbComp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(231, 231, 231))
        );

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(406, Short.MAX_VALUE)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(406, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(96, Short.MAX_VALUE)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1634, Short.MAX_VALUE)
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel22, "card6");

        jPanel31.setBackground(colorBG);

        jPanel35.setBackground(color2_255);

        jScrollPane4.setBackground(colorBG);
        jScrollPane4.setBorder(null);

        jTable4.setBackground(colorBG);
        jTable4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable4.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTable4.setForeground(colorOnSurface);
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "City", "Delete", "Edit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.setFocusable(false);
        jTable4.setGridColor(color2_255);
        jTable4.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable4.setOpaque(false);
        jTable4.setRowHeight(25);
        jTable4.setSelectionBackground(colorSecondary_80);
        jTable4.setSelectionForeground(colorOnSurface);
        jTable4.setShowVerticalLines(false);
        jTable4.getTableHeader().setResizingAllowed(false);
        jTable4.getTableHeader().setReorderingAllowed(false);
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setMinWidth(40);
            jTable4.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable4.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable4.getColumnModel().getColumn(1).setResizable(false);
            jTable4.getColumnModel().getColumn(2).setResizable(false);
            jTable4.getColumnModel().getColumn(3).setMinWidth(50);
            jTable4.getColumnModel().getColumn(3).setMaxWidth(50);
            jTable4.getColumnModel().getColumn(4).setMinWidth(50);
            jTable4.getColumnModel().getColumn(4).setMaxWidth(50);
        }

        jcbCityEdit.setBackground(color2_255);
        jcbCityEdit.setEditable(true);
        jcbCityEdit.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbCityEdit.setForeground(colorOnSurface);
        jcbCityEdit.setMaximumRowCount(5);
        jcbCityEdit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--City--" }));
        jcbCityEdit.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jcbCityEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbCityEdit.setOpaque(false);
        jcbCityEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCityEditActionPerformed(evt);
            }
        });

        jtfSFNameEdit.setBackground(color2_255);
        jtfSFNameEdit.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfSFNameEdit.setForeground(color1_255);
        jtfSFNameEdit.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jtfSFNameEdit.setOpaque(false);
        jtfSFNameEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfSFNameEditKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfSFNameEditKeyTyped(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel54.setForeground(colorOnSurface);
        jLabel54.setText("Search by");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel59.setForeground(colorOnSurface);
        jLabel59.setText("Name");

        jcbCompEdit.setBackground(color2_255);
        jcbCompEdit.setEditable(true);
        jcbCompEdit.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbCompEdit.setForeground(colorOnSurface);
        jcbCompEdit.setMaximumRowCount(5);
        jcbCompEdit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Company--" }));
        jcbCompEdit.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jcbCompEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbCompEdit.setOpaque(false);
        jcbCompEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCompEditActionPerformed(evt);
            }
        });

        jlMSIDGh.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jlMSIDGh.setForeground(colorOnSurface_80);
        jlMSIDGh.setText("Gatehouses");

        jpMSSave.setBackground(colorSecondary_80);
        jpMSSave.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpMSSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpMSSaveMouseClicked(evt);
            }
        });

        jlMSSave.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlMSSave.setForeground(colorOnSurface_80);
        jlMSSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlMSSave.setText("SAVE");

        javax.swing.GroupLayout jpMSSaveLayout = new javax.swing.GroupLayout(jpMSSave);
        jpMSSave.setLayout(jpMSSaveLayout);
        jpMSSaveLayout.setHorizontalGroup(
            jpMSSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMSSaveLayout.createSequentialGroup()
                .addComponent(jlMSSave, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpMSSaveLayout.setVerticalGroup(
            jpMSSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMSSaveLayout.createSequentialGroup()
                .addComponent(jlMSSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpMSDefault.setBackground(colorSecondary_80);
        jpMSDefault.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpMSDefault.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpMSDefaultMouseClicked(evt);
            }
        });

        jlMSDefault.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlMSDefault.setForeground(colorOnSurface_80);
        jlMSDefault.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlMSDefault.setText("DEFAULT");

        javax.swing.GroupLayout jpMSDefaultLayout = new javax.swing.GroupLayout(jpMSDefault);
        jpMSDefault.setLayout(jpMSDefaultLayout);
        jpMSDefaultLayout.setHorizontalGroup(
            jpMSDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMSDefaultLayout.createSequentialGroup()
                .addComponent(jlMSDefault, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpMSDefaultLayout.setVerticalGroup(
            jpMSDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMSDefaultLayout.createSequentialGroup()
                .addComponent(jlMSDefault, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jcbSEditGH.setBackground(color2_255);
        jcbSEditGH.setEditable(true);
        jcbSEditGH.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbSEditGH.setForeground(colorOnSurface_80);
        jcbSEditGH.setMaximumRowCount(5);
        jcbSEditGH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--" }));
        jcbSEditGH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface_80));
        jcbSEditGH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbSEditGH.setOpaque(false);
        jcbSEditGH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSEditGHActionPerformed(evt);
            }
        });

        jlAddG.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlAddG.setForeground(colorOnSurface_80);
        jlAddG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlAddG.setText(" | Add");
        jlAddG.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlAddG.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlAddG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlAddGMouseClicked(evt);
            }
        });

        jlDelG.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlDelG.setForeground(colorOnSurface_80);
        jlDelG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlDelG.setText("Delete");
        jlDelG.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlDelG.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlDelG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlDelGMouseClicked(evt);
            }
        });

        jlMSIDC.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jlMSIDC.setForeground(color1_80);
        jlMSIDC.setText("Companies");

        jcbSEditC.setBackground(color2_255);
        jcbSEditC.setEditable(true);
        jcbSEditC.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbSEditC.setForeground(color1_255);
        jcbSEditC.setMaximumRowCount(5);
        jcbSEditC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--" }));
        jcbSEditC.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface_80));
        jcbSEditC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbSEditC.setOpaque(false);
        jcbSEditC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSEditCActionPerformed(evt);
            }
        });

        jlDelC.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlDelC.setForeground(colorOnSurface_80);
        jlDelC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlDelC.setText("Delete");
        jlDelC.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlDelC.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlDelC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlDelCMouseClicked(evt);
            }
        });

        jlAddC.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlAddC.setForeground(colorOnSurface_80);
        jlAddC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlAddC.setText(" | Add");
        jlAddC.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlAddC.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlAddC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlAddCMouseClicked(evt);
            }
        });

        jlMSIDAdmin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jlMSIDAdmin.setForeground(colorOnSurface_80);
        jlMSIDAdmin.setText("Admin");

        jtfMSIDAdmin.setEditable(false);
        jtfMSIDAdmin.setBackground(color2_255);
        jtfMSIDAdmin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfMSIDAdmin.setForeground(color1_255);
        jtfMSIDAdmin.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jtfMSIDAdmin.setOpaque(false);

        jlMSVL.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlMSVL.setForeground(colorOnSurface_80);
        jlMSVL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlMSVL.setText("Select");
        jlMSVL.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlMSVL.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlMSVL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlMSVLMouseClicked(evt);
            }
        });

        jlMSVL1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlMSVL1.setForeground(color1_80);
        jlMSVL1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlMSVL1.setText("Select");
        jlMSVL1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlMSVL1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlMSVL1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlMSVL1MouseClicked(evt);
            }
        });

        jtfMSIDMan.setEditable(false);
        jtfMSIDMan.setBackground(color2_255);
        jtfMSIDMan.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfMSIDMan.setForeground(color1_255);
        jtfMSIDMan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jtfMSIDMan.setOpaque(false);

        jlMSIDMan.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jlMSIDMan.setForeground(color1_80);
        jlMSIDMan.setText("Site manager");

        jcbNumES.setBackground(color2_255);
        jcbNumES.setEditable(true);
        jcbNumES.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbNumES.setForeground(colorOnSurface);
        jcbNumES.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15" }));
        jcbNumES.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jcbNumES.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbNumES.setOpaque(false);
        jcbNumES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbNumESActionPerformed(evt);
            }
        });

        jlPreviousES.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\leftArrowFaded.png")); // NOI18N
        jlPreviousES.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlPreviousESMouseClicked(evt);
            }
        });

        jlDisplayXES.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jlDisplayXES.setForeground(colorOnSurface);
        jlDisplayXES.setText("x");

        jLabel45.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel45.setForeground(colorOnSurface);
        jLabel45.setText("-");

        jlDisplayYES.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jlDisplayYES.setForeground(colorOnSurface);
        jlDisplayYES.setText("y");

        jlDisplayMaxES.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jlDisplayMaxES.setForeground(colorOnSurface);
        jlDisplayMaxES.setText("from max");

        jlNextES.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\rightArrowFaded.png")); // NOI18N
        jlNextES.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlNextESMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGap(352, 352, 352)
                        .addComponent(jpMSDefault, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jpMSSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGap(0, 37, Short.MAX_VALUE)
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel35Layout.createSequentialGroup()
                                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlMSIDMan)
                                    .addGroup(jPanel35Layout.createSequentialGroup()
                                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtfMSIDMan, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtfMSIDAdmin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jlMSVL)
                                            .addComponent(jlMSVL1)))
                                    .addComponent(jlMSIDAdmin))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel35Layout.createSequentialGroup()
                                            .addComponent(jcbSEditC, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, 0)
                                            .addComponent(jlDelC)
                                            .addGap(0, 0, 0)
                                            .addComponent(jlAddC))
                                        .addComponent(jlMSIDC, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                                        .addComponent(jcbSEditGH, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jlDelG)
                                        .addGap(0, 0, 0)
                                        .addComponent(jlAddG))
                                    .addComponent(jlMSIDGh)))
                            .addGroup(jPanel35Layout.createSequentialGroup()
                                .addComponent(jcbCityEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jcbCompEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                                    .addComponent(jtfSFNameEdit))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jcbNumES, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel35Layout.createSequentialGroup()
                                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel54)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(367, 367, 367)
                .addComponent(jlPreviousES, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlDisplayXES, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlDisplayYES)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlDisplayMaxES)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlNextES, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbCityEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfSFNameEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbCompEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbNumES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlNextES, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlPreviousES, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlDisplayXES)
                            .addComponent(jLabel45)
                            .addComponent(jlDisplayYES)
                            .addComponent(jlDisplayMaxES))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlMSIDGh)
                    .addComponent(jlMSIDAdmin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlAddG, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlDelG, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbSEditGH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlMSVL, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfMSIDAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlMSIDC)
                    .addComponent(jlMSIDMan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbSEditC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlDelC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlAddC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlMSVL1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfMSIDMan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpMSDefault, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpMSSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(116, 116, 116))
        );

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap(358, Short.MAX_VALUE)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(359, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1634, Short.MAX_VALUE)
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel24, "card7");

        jPanel23.setBackground(colorBG);

        jPanel28.setBackground(color2_255);

        jPanel33.setBackground(color1_255);
        jPanel33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel33MouseClicked(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel50.setForeground(color2_255);
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("SAVE");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jlErrEmailReset.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, color1_255));
        jlErrEmailReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrEmailResetMouseClicked(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel55.setForeground(color1_255);
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("Select");
        jLabel55.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, color1_255));
        jLabel55.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel55MouseClicked(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel57.setForeground(color1_255);
        jLabel57.setText("Email");

        jtfEmailReset.setEditable(false);
        jtfEmailReset.setBackground(color2_255);
        jtfEmailReset.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jtfEmailReset.setForeground(color1_255);
        jtfEmailReset.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, color1_255));
        jtfEmailReset.setOpaque(false);
        jtfEmailReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfEmailResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel57)
                            .addGroup(jPanel28Layout.createSequentialGroup()
                                .addComponent(jtfEmailReset, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlErrEmailReset, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfEmailReset, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlErrEmailReset, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(540, Short.MAX_VALUE)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(540, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(178, Short.MAX_VALUE)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel23, "card8");

        jPanel5.setBackground(colorBG);

        jPanel41.setBackground(color2_255);

        jLabel71.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel71.setForeground(colorOnSurface);
        jLabel71.setText("Start date");

        jLabel73.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel73.setForeground(colorOnSurface);
        jLabel73.setText("Full name of the person to see");

        jtfNameP.setBackground(color2_255);
        jtfNameP.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfNameP.setForeground(colorBG);
        jtfNameP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfNameP.setOpaque(false);

        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel74.setForeground(colorOnSurface);
        jLabel74.setText("Email of the person to see");

        jtfEmailP.setBackground(color2_255);
        jtfEmailP.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfEmailP.setForeground(colorBG);
        jtfEmailP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfEmailP.setOpaque(false);

        jPanel43.setBackground(colorSecondary);
        jPanel43.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel43MouseClicked(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel75.setForeground(colorOnSecondary);
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setText("VALIDATE");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel75, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jlErrP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrPMouseClicked(evt);
            }
        });

        jlErrEmailP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrEmailP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrEmailPMouseClicked(evt);
            }
        });

        jLabel76.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel76.setForeground(colorOnSurface);
        jLabel76.setText("Number of visitors");

        jtfNumV.setBackground(color2_255);
        jtfNumV.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfNumV.setForeground(colorBG);
        jtfNumV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jtfNumV.setOpaque(false);
        jtfNumV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfNumVActionPerformed(evt);
            }
        });
        jtfNumV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNumVKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfNumVKeyTyped(evt);
            }
        });

        jlErrNumV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrNumV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrNumVMouseClicked(evt);
            }
        });

        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel77.setForeground(colorOnSurface);
        jLabel77.setText("End date");

        jPanel39.setBackground(color2_255);
        jPanel39.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));

        jcbDayS.setBackground(color2_255);
        jcbDayS.setEditable(true);
        jcbDayS.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbDayS.setForeground(colorOnSurface);
        jcbDayS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Day--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        jcbDayS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbDayS.setOpaque(false);

        jcbMonthS.setBackground(color2_255);
        jcbMonthS.setEditable(true);
        jcbMonthS.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbMonthS.setForeground(colorOnSurface);
        jcbMonthS.setMaximumRowCount(13);
        jcbMonthS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Month--", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jcbMonthS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbMonthS.setOpaque(false);
        jcbMonthS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMonthSActionPerformed(evt);
            }
        });

        jcbYearS.setBackground(color2_255);
        jcbYearS.setEditable(true);
        jcbYearS.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbYearS.setForeground(colorOnSurface);
        jcbYearS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Year--", "2021", "2022" }));
        jcbYearS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbYearS.setOpaque(false);

        jcbHourS.setBackground(color2_255);
        jcbHourS.setEditable(true);
        jcbHourS.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbHourS.setForeground(colorOnSurface);
        jcbHourS.setMaximumRowCount(15);
        jcbHourS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jcbHourS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbHourS.setOpaque(false);
        jcbHourS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbHourSActionPerformed(evt);
            }
        });

        jLabel17.setForeground(colorOnSurface);
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText(":");

        jcbMinS.setBackground(color2_255);
        jcbMinS.setEditable(true);
        jcbMinS.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbMinS.setForeground(colorOnSurface);
        jcbMinS.setMaximumRowCount(15);
        jcbMinS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jcbMinS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbMinS.setOpaque(false);
        jcbMinS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMinSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jcbDayS, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jcbMonthS, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jcbYearS, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jcbHourS, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 5, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(jcbMinS, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbDayS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbMonthS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbYearS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbHourS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jcbMinS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel44.setBackground(color2_255);
        jPanel44.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));

        jcbDayE.setBackground(color2_255);
        jcbDayE.setEditable(true);
        jcbDayE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbDayE.setForeground(colorOnSurface);
        jcbDayE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Day--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        jcbDayE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbDayE.setOpaque(false);

        jcbMonthE.setBackground(color2_255);
        jcbMonthE.setEditable(true);
        jcbMonthE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbMonthE.setForeground(colorOnSurface);
        jcbMonthE.setMaximumRowCount(13);
        jcbMonthE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Month--", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jcbMonthE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbMonthE.setOpaque(false);
        jcbMonthE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMonthEActionPerformed(evt);
            }
        });

        jcbYearE.setBackground(color2_255);
        jcbYearE.setEditable(true);
        jcbYearE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbYearE.setForeground(colorOnSurface);
        jcbYearE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Year--", "2021", "2022" }));
        jcbYearE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbYearE.setOpaque(false);
        jcbYearE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbYearEActionPerformed(evt);
            }
        });

        jcbMinE.setBackground(color2_255);
        jcbMinE.setEditable(true);
        jcbMinE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbMinE.setForeground(colorOnSurface);
        jcbMinE.setMaximumRowCount(15);
        jcbMinE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jcbMinE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbMinE.setOpaque(false);
        jcbMinE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMinEActionPerformed(evt);
            }
        });

        jLabel20.setForeground(colorOnSurface);
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText(":");

        jcbHourE.setBackground(color2_255);
        jcbHourE.setEditable(true);
        jcbHourE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbHourE.setForeground(colorOnSurface);
        jcbHourE.setMaximumRowCount(15);
        jcbHourE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jcbHourE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbHourE.setOpaque(false);
        jcbHourE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbHourEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addComponent(jcbDayE, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jcbMonthE, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jcbYearE, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jcbHourE, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 5, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(jcbMinE, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbDayE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbMonthE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbYearE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbMinE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jcbHourE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jlErrSD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrSDMouseClicked(evt);
            }
        });

        jlErrED.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jlErrED.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrEDMouseClicked(evt);
            }
        });

        jlAlertED.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jlAlertED.setForeground(colorError);
        jlAlertED.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(363, 363, 363))
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addComponent(jtfNameP, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlErrP, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel74))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addComponent(jLabel71)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jlErrSD, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel73)
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel76)
                                    .addGroup(jPanel41Layout.createSequentialGroup()
                                        .addComponent(jtfNumV, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jlErrNumV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel41Layout.createSequentialGroup()
                                        .addComponent(jtfEmailP, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jlErrEmailP, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(139, 139, 139)
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel41Layout.createSequentialGroup()
                                        .addComponent(jLabel77)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jlAlertED, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jlErrED, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(26, 26, 26))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel73)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfNameP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlErrP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45)
                                .addComponent(jLabel74))
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel71)
                                    .addComponent(jlErrSD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfEmailP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlErrEmailP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addComponent(jLabel76))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel77))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlErrED, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlAlertED, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfNumV, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlErrNumV, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(365, Short.MAX_VALUE)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(365, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(151, Short.MAX_VALUE)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel5, "card9");

        jPanel36.setBackground(colorBG);

        jPanel40.setBackground(color2_255);

        jScrollPane5.setBackground(colorBG);
        jScrollPane5.setBorder(null);

        jTable5.setBackground(colorBG);
        jTable5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable5.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTable5.setForeground(colorOnSurface);
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Demander", "Person to see", "Email", "Start date", "End date", "Visitors"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable5.setFocusable(false);
        jTable5.setGridColor(colorBG);
        jTable5.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable5.setOpaque(false);
        jTable5.setRowHeight(25);
        jTable5.setSelectionBackground(colorSecondary_80);
        jTable5.setSelectionForeground(colorOnBackground);
        jTable5.setShowVerticalLines(false);
        jTable5.getTableHeader().setResizingAllowed(false);
        jTable5.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(jTable5);
        if (jTable5.getColumnModel().getColumnCount() > 0) {
            jTable5.getColumnModel().getColumn(0).setMinWidth(40);
            jTable5.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable5.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable5.getColumnModel().getColumn(1).setResizable(false);
            jTable5.getColumnModel().getColumn(2).setResizable(false);
            jTable5.getColumnModel().getColumn(3).setResizable(false);
            jTable5.getColumnModel().getColumn(4).setResizable(false);
            jTable5.getColumnModel().getColumn(5).setResizable(false);
            jTable5.getColumnModel().getColumn(6).setMinWidth(60);
            jTable5.getColumnModel().getColumn(6).setMaxWidth(60);
        }

        jtfFAName1.setBackground(color2_255);
        jtfFAName1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfFAName1.setForeground(color1_255);
        jtfFAName1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jtfFAName1.setOpaque(false);
        jtfFAName1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfFAName1KeyReleased(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel43.setForeground(colorOnSurface);
        jLabel43.setText("Search by");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel58.setForeground(colorOnSurface);
        jLabel58.setText("Person to see");

        jcbNum2.setBackground(color2_255);
        jcbNum2.setEditable(true);
        jcbNum2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbNum2.setForeground(color1_255);
        jcbNum2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10", "15", "20", "25" }));
        jcbNum2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jcbNum2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbNum2.setOpaque(false);
        jcbNum2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbNum2ActionPerformed(evt);
            }
        });

        jpNext2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpNext2.setOpaque(false);
        jpNext2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpNext2MouseClicked(evt);
            }
        });

        jlNext2.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\rightArrowFaded.png")); // NOI18N

        javax.swing.GroupLayout jpNext2Layout = new javax.swing.GroupLayout(jpNext2);
        jpNext2.setLayout(jpNext2Layout);
        jpNext2Layout.setHorizontalGroup(
            jpNext2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpNext2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlNext2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpNext2Layout.setVerticalGroup(
            jpNext2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpNext2Layout.createSequentialGroup()
                .addComponent(jlNext2, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpPrevious2.setOpaque(false);
        jpPrevious2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpPrevious2MouseClicked(evt);
            }
        });

        jlPrevious2.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\leftArrowFaded.png")); // NOI18N

        javax.swing.GroupLayout jpPrevious2Layout = new javax.swing.GroupLayout(jpPrevious2);
        jpPrevious2.setLayout(jpPrevious2Layout);
        jpPrevious2Layout.setHorizontalGroup(
            jpPrevious2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrevious2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlPrevious2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpPrevious2Layout.setVerticalGroup(
            jpPrevious2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrevious2Layout.createSequentialGroup()
                .addComponent(jlPrevious2, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jlDisplayX2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jlDisplayX2.setForeground(colorOnSurface);
        jlDisplayX2.setText("x");

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel60.setForeground(colorOnSurface);
        jLabel60.setText("-");

        jlDisplayY2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jlDisplayY2.setForeground(colorOnSurface);
        jlDisplayY2.setText("y");

        jlDisplayMax2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jlDisplayMax2.setForeground(colorOnSurface);
        jlDisplayMax2.setText("from max");

        jPanel54.setBackground(new Color(0, 179, 0, 120));

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jLabel72.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel72.setForeground(colorOnSurface);
        jLabel72.setText("Accepted");

        jPanel57.setBackground(new Color(255, 143, 0, 120));

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jPanel58.setBackground(new Color(179, 0, 0, 120));

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jLabel85.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel85.setForeground(colorOnSurface);
        jLabel85.setText("Pending");

        jLabel86.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel86.setForeground(colorOnSurface);
        jLabel86.setText("Refused");

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel40Layout.createSequentialGroup()
                                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel40Layout.createSequentialGroup()
                                        .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel86))
                                    .addGroup(jPanel40Layout.createSequentialGroup()
                                        .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel85))
                                    .addGroup(jPanel40Layout.createSequentialGroup()
                                        .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel72)))
                                .addGap(251, 251, 251)
                                .addComponent(jpPrevious2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlDisplayX2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel60)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlDisplayY2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlDisplayMax2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jpNext2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel40Layout.createSequentialGroup()
                                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jcbNum2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfFAName1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtfFAName1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(jcbNum2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlDisplayX2)
                            .addComponent(jLabel60)
                            .addComponent(jlDisplayY2)
                            .addComponent(jlDisplayMax2)))
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpNext2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpPrevious2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel40Layout.createSequentialGroup()
                                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel40Layout.createSequentialGroup()
                                        .addComponent(jLabel72)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel85))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel40Layout.createSequentialGroup()
                                        .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel86))))))
                .addGap(1637, 1637, 1637))
        );

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap(397, Short.MAX_VALUE)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(396, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap(96, Short.MAX_VALUE)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel36, "card10");

        jPanel45.setBackground(colorBG);

        jPanel46.setBackground(color2_255);

        jScrollPane6.setBackground(colorBG);
        jScrollPane6.setBorder(null);

        jTable6.setBackground(colorBG);
        jTable6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable6.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTable6.setForeground(colorOnSurface);
        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Demander", "Person to see", "Email", "Start date", "End date", "Visitors", "Delete", "Edit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable6.setFocusable(false);
        jTable6.setGridColor(colorBG);
        jTable6.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable6.setOpaque(false);
        jTable6.setRowHeight(25);
        jTable6.setSelectionBackground(colorSecondary_80);
        jTable6.setSelectionForeground(colorOnBackground);
        jTable6.setShowVerticalLines(false);
        jTable6.getTableHeader().setResizingAllowed(false);
        jTable6.getTableHeader().setReorderingAllowed(false);
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable6MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTable6);
        if (jTable6.getColumnModel().getColumnCount() > 0) {
            jTable6.getColumnModel().getColumn(0).setMinWidth(40);
            jTable6.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable6.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable6.getColumnModel().getColumn(1).setResizable(false);
            jTable6.getColumnModel().getColumn(2).setResizable(false);
            jTable6.getColumnModel().getColumn(3).setResizable(false);
            jTable6.getColumnModel().getColumn(4).setResizable(false);
            jTable6.getColumnModel().getColumn(5).setResizable(false);
            jTable6.getColumnModel().getColumn(6).setMinWidth(60);
            jTable6.getColumnModel().getColumn(6).setMaxWidth(60);
        }

        jtfFAName2.setBackground(color2_255);
        jtfFAName2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfFAName2.setForeground(color1_255);
        jtfFAName2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jtfFAName2.setOpaque(false);
        jtfFAName2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfFAName2KeyReleased(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel61.setForeground(colorOnSurface);
        jLabel61.setText("Search by");

        jLabel62.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel62.setForeground(colorOnSurface);
        jLabel62.setText("Person to see");

        jLabel78.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel78.setForeground(colorOnSurface_80);
        jLabel78.setText("Full name of the person to see");

        jtfNamePE.setBackground(color2_255);
        jtfNamePE.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfNamePE.setForeground(colorBG);
        jtfNamePE.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jtfNamePE.setOpaque(false);

        jlErrP1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlErrP1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrP1MouseClicked(evt);
            }
        });

        jLabel79.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel79.setForeground(colorOnSurface_80);
        jLabel79.setText("Start date");

        jPanel48.setBackground(color2_255);
        jPanel48.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));

        jcbDaySE.setBackground(color2_255);
        jcbDaySE.setEditable(true);
        jcbDaySE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbDaySE.setForeground(colorOnSurface);
        jcbDaySE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Day--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        jcbDaySE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbDaySE.setOpaque(false);

        jcbMonthSE.setBackground(color2_255);
        jcbMonthSE.setEditable(true);
        jcbMonthSE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbMonthSE.setForeground(colorOnSurface);
        jcbMonthSE.setMaximumRowCount(13);
        jcbMonthSE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Month--", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jcbMonthSE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbMonthSE.setOpaque(false);
        jcbMonthSE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMonthSEActionPerformed(evt);
            }
        });

        jcbYearSE.setBackground(color2_255);
        jcbYearSE.setEditable(true);
        jcbYearSE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbYearSE.setForeground(colorOnSurface);
        jcbYearSE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Year--", "2021", "2022" }));
        jcbYearSE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbYearSE.setOpaque(false);

        jcbHourSE.setBackground(color2_255);
        jcbHourSE.setEditable(true);
        jcbHourSE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbHourSE.setForeground(colorOnSurface);
        jcbHourSE.setMaximumRowCount(15);
        jcbHourSE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jcbHourSE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbHourSE.setOpaque(false);
        jcbHourSE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbHourSEActionPerformed(evt);
            }
        });

        jLabel64.setForeground(colorOnSurface_80);
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText(":");

        jcbMinSE.setBackground(color2_255);
        jcbMinSE.setEditable(true);
        jcbMinSE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbMinSE.setForeground(colorOnSurface);
        jcbMinSE.setMaximumRowCount(15);
        jcbMinSE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jcbMinSE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbMinSE.setOpaque(false);
        jcbMinSE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMinSEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jcbDaySE, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jcbMonthSE, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jcbYearSE, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jcbHourSE, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, 5, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(jcbMinSE, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbDaySE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbMonthSE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbYearSE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbHourSE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64)
                    .addComponent(jcbMinSE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel80.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel80.setForeground(colorOnSurface_80);
        jLabel80.setText("Email of the person to see");

        jtfEmailPE.setBackground(color2_255);
        jtfEmailPE.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfEmailPE.setForeground(colorBG);
        jtfEmailPE.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jtfEmailPE.setOpaque(false);

        jlErrP2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlErrP2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrP2MouseClicked(evt);
            }
        });

        jLabel81.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel81.setForeground(colorOnSurface_80);
        jLabel81.setText("Number of visitors");

        jtfNumVE.setBackground(color2_255);
        jtfNumVE.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfNumVE.setForeground(colorBG);
        jtfNumVE.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jtfNumVE.setOpaque(false);
        jtfNumVE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfNumVEKeyTyped(evt);
            }
        });

        jlErrP3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlErrP3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrP3MouseClicked(evt);
            }
        });

        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel82.setForeground(colorOnSurface_80);
        jLabel82.setText("End date");

        jPanel49.setBackground(color2_255);
        jPanel49.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));

        jcbDayEE.setBackground(color2_255);
        jcbDayEE.setEditable(true);
        jcbDayEE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbDayEE.setForeground(colorOnSurface);
        jcbDayEE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Day--", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        jcbDayEE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbDayEE.setOpaque(false);

        jcbMonthEE.setBackground(color2_255);
        jcbMonthEE.setEditable(true);
        jcbMonthEE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbMonthEE.setForeground(colorOnSurface);
        jcbMonthEE.setMaximumRowCount(13);
        jcbMonthEE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Month--", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jcbMonthEE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbMonthEE.setOpaque(false);
        jcbMonthEE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMonthEEActionPerformed(evt);
            }
        });

        jcbYearEE.setBackground(color2_255);
        jcbYearEE.setEditable(true);
        jcbYearEE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbYearEE.setForeground(colorOnSurface);
        jcbYearEE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Year--", "2021", "2022" }));
        jcbYearEE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbYearEE.setOpaque(false);

        jcbHourEE.setBackground(color2_255);
        jcbHourEE.setEditable(true);
        jcbHourEE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbHourEE.setForeground(colorOnSurface);
        jcbHourEE.setMaximumRowCount(15);
        jcbHourEE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jcbHourEE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbHourEE.setOpaque(false);
        jcbHourEE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbHourEEActionPerformed(evt);
            }
        });

        jLabel65.setForeground(colorOnSurface_80);
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText(":");

        jcbMinEE.setBackground(color2_255);
        jcbMinEE.setEditable(true);
        jcbMinEE.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jcbMinEE.setForeground(colorOnSurface);
        jcbMinEE.setMaximumRowCount(15);
        jcbMinEE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jcbMinEE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbMinEE.setOpaque(false);
        jcbMinEE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMinEEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jcbDayEE, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jcbMonthEE, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jcbYearEE, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jcbHourEE, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, 5, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(jcbMinEE, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbDayEE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbMonthEE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbYearEE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbHourEE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65)
                    .addComponent(jcbMinEE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jlErrSD1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrSD1MouseClicked(evt);
            }
        });

        jlAlertED1.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jlAlertED1.setForeground(colorError);
        jlAlertED1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel50.setBackground(colorSecondary_80);
        jPanel50.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel50MouseClicked(evt);
            }
        });

        jLabel83.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel83.setForeground(colorOnSecondary_80);
        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel83.setText("DEFAULT");

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel83, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel51.setBackground(colorSecondary_80);
        jPanel51.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel51MouseClicked(evt);
            }
        });

        jLabel84.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel84.setForeground(colorOnSecondary_80);
        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel84.setText("SAVE");

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel84, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jlErrSD2.setToolTipText("");
        jlErrSD2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jlErrSD2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrSD2MouseClicked(evt);
            }
        });

        jlAlertED2.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jlAlertED2.setForeground(colorError);
        jlAlertED2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel46Layout.createSequentialGroup()
                                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel46Layout.createSequentialGroup()
                                        .addComponent(jtfNumVE, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jlErrP3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel46Layout.createSequentialGroup()
                                                .addGap(0, 28, Short.MAX_VALUE)
                                                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(93, 93, 93)
                                                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel46Layout.createSequentialGroup()
                                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel80)
                                            .addGroup(jPanel46Layout.createSequentialGroup()
                                                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(jtfEmailPE, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                                                    .addComponent(jtfNamePE))
                                                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jlErrP2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jlErrP1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(jLabel81)
                                            .addComponent(jLabel78))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel46Layout.createSequentialGroup()
                                        .addComponent(jLabel79)
                                        .addGap(98, 98, 98)
                                        .addComponent(jlAlertED1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jlErrSD1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel46Layout.createSequentialGroup()
                                        .addComponent(jLabel82)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jlAlertED2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jlErrSD2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane6))
                        .addGap(22, 22, 22))
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel61)
                            .addGroup(jPanel46Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfFAName2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(855, Short.MAX_VALUE))))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel46Layout.createSequentialGroup()
                                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel79)
                                    .addComponent(jlErrSD1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlAlertED1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46))
                            .addGroup(jPanel46Layout.createSequentialGroup()
                                .addComponent(jtfFAName2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel62)
                                .addGap(19, 19, 19)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(jLabel78)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfNamePE, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlErrP1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(jLabel80)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfEmailPE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlErrP2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel82)
                            .addComponent(jlAlertED2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jlErrSD2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addComponent(jLabel81)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNumVE, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlErrP3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addContainerGap(300, Short.MAX_VALUE)
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(300, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel45, "card11");

        jPanel47.setBackground(colorBG);

        jPanel53.setBackground(color2_255);

        jScrollPane7.setBackground(colorBG);
        jScrollPane7.setBorder(null);

        jTable7.setBackground(colorBG);
        jTable7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable7.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTable7.setForeground(colorOnSurface);
        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Demander", "Person to see", "Email", "Start date", "End date", "Visitors", "Accept", "Refuse"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable7.setFocusable(false);
        jTable7.setGridColor(colorBG);
        jTable7.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable7.setOpaque(false);
        jTable7.setRowHeight(25);
        jTable7.setSelectionBackground(colorSecondary_80);
        jTable7.setSelectionForeground(colorOnBackground);
        jTable7.setShowVerticalLines(false);
        jTable7.getTableHeader().setResizingAllowed(false);
        jTable7.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(jTable7);
        if (jTable7.getColumnModel().getColumnCount() > 0) {
            jTable7.getColumnModel().getColumn(0).setMinWidth(40);
            jTable7.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable7.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable7.getColumnModel().getColumn(1).setResizable(false);
            jTable7.getColumnModel().getColumn(2).setResizable(false);
            jTable7.getColumnModel().getColumn(3).setResizable(false);
            jTable7.getColumnModel().getColumn(4).setResizable(false);
            jTable7.getColumnModel().getColumn(5).setResizable(false);
            jTable7.getColumnModel().getColumn(6).setMinWidth(60);
            jTable7.getColumnModel().getColumn(6).setMaxWidth(60);
        }

        jcbFAPos2.setBackground(color2_255);
        jcbFAPos2.setEditable(true);
        jcbFAPos2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbFAPos2.setForeground(color1_255);
        jcbFAPos2.setMaximumRowCount(5);
        jcbFAPos2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Position--", "Admin", "Site manager", "Gatehouse", "Company user" }));
        jcbFAPos2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jcbFAPos2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbFAPos2.setOpaque(false);
        jcbFAPos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbFAPos2ActionPerformed(evt);
            }
        });

        jtfFAName5.setBackground(color2_255);
        jtfFAName5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfFAName5.setForeground(color1_255);
        jtfFAName5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jtfFAName5.setOpaque(false);
        jtfFAName5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfFAName5KeyReleased(evt);
            }
        });

        jLabel89.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel89.setForeground(colorOnSurface);
        jLabel89.setText("Search by");

        jLabel90.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel90.setForeground(colorOnSurface);
        jLabel90.setText("First or Family name");

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addComponent(jcbFAPos2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfFAName5, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel89)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 951, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 13, Short.MAX_VALUE))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel89)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbFAPos2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfFAName5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel90)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4504, 4504, 4504))
        );

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                .addContainerGap(326, Short.MAX_VALUE)
                .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(334, Short.MAX_VALUE))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                .addContainerGap(96, Short.MAX_VALUE)
                .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel47, "card12");

        jPanel59.setBackground(colorBG);

        jPanel63.setBackground(color2_255);

        jScrollPane8.setBackground(colorBG);
        jScrollPane8.setBorder(null);

        jTable8.setBackground(colorBG);
        jTable8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable8.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTable8.setForeground(colorOnSurface);
        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Demander", "Person to see", "Email", "Start date", "End date", "Visitors", "Visited"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable8.setFocusable(false);
        jTable8.setGridColor(colorBG);
        jTable8.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable8.setOpaque(false);
        jTable8.setRowHeight(25);
        jTable8.setSelectionBackground(colorSecondary_80);
        jTable8.setSelectionForeground(colorOnBackground);
        jTable8.setShowVerticalLines(false);
        jTable8.getTableHeader().setResizingAllowed(false);
        jTable8.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(jTable8);
        if (jTable8.getColumnModel().getColumnCount() > 0) {
            jTable8.getColumnModel().getColumn(0).setMinWidth(40);
            jTable8.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable8.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable8.getColumnModel().getColumn(1).setResizable(false);
            jTable8.getColumnModel().getColumn(2).setResizable(false);
            jTable8.getColumnModel().getColumn(3).setResizable(false);
            jTable8.getColumnModel().getColumn(4).setResizable(false);
            jTable8.getColumnModel().getColumn(5).setResizable(false);
            jTable8.getColumnModel().getColumn(6).setMinWidth(60);
            jTable8.getColumnModel().getColumn(6).setMaxWidth(60);
        }

        jtfFAName6.setBackground(color2_255);
        jtfFAName6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfFAName6.setForeground(color1_255);
        jtfFAName6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jtfFAName6.setOpaque(false);
        jtfFAName6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfFAName6KeyReleased(evt);
            }
        });

        jLabel92.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel92.setForeground(colorOnSurface);
        jLabel92.setText("Search by");

        jLabel93.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel93.setForeground(colorOnSurface);
        jLabel93.setText("Demander");

        jtfFAName7.setBackground(color2_255);
        jtfFAName7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfFAName7.setForeground(color1_255);
        jtfFAName7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jtfFAName7.setOpaque(false);
        jtfFAName7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfFAName7KeyReleased(evt);
            }
        });

        jLabel137.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel137.setForeground(colorOnSurface);
        jLabel137.setText("Person to see");

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel92)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1003, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel63Layout.createSequentialGroup()
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfFAName6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel137, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfFAName7, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 19, Short.MAX_VALUE))
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel63Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel92)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfFAName6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfFAName7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93)
                    .addComponent(jLabel137))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4155, 4155, 4155))
        );

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel59Layout.createSequentialGroup()
                .addContainerGap(299, Short.MAX_VALUE)
                .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(303, Short.MAX_VALUE))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel59Layout.createSequentialGroup()
                .addContainerGap(96, Short.MAX_VALUE)
                .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(155, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel59, "card13");

        jPanel68.setBackground(colorBG);

        jPanel71.setBackground(color2_255);

        jLabel119.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel119.setForeground(colorOnSurface);
        jLabel119.setText("Company");

        jPanel72.setBackground(colorSecondary);
        jPanel72.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel72MouseClicked(evt);
            }
        });

        jLabel122.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel122.setForeground(colorOnSecondary);
        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel122.setText("DEFAULT");

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel122, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel72Layout.createSequentialGroup()
                .addComponent(jLabel122, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel125.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel125.setForeground(colorOnSurface);
        jLabel125.setText("Gatehouse");

        jcbSG1.setBackground(color2_255);
        jcbSG1.setEditable(true);
        jcbSG1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbSG1.setForeground(color1_255);
        jcbSG1.setMaximumRowCount(5);
        jcbSG1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--" }));
        jcbSG1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jcbSG1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbSG1.setOpaque(false);
        jcbSG1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSG1ActionPerformed(evt);
            }
        });

        jlDelG2.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlDelG2.setForeground(colorOnSurface);
        jlDelG2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlDelG2.setText("Delete");
        jlDelG2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlDelG2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlDelG2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlDelG2MouseClicked(evt);
            }
        });

        jlAddG2.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlAddG2.setForeground(colorOnSurface);
        jlAddG2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlAddG2.setText(" | Add");
        jlAddG2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlAddG2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlAddG2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlAddG2MouseClicked(evt);
            }
        });

        jcbSComp1.setBackground(color2_255);
        jcbSComp1.setEditable(true);
        jcbSComp1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbSComp1.setForeground(color1_255);
        jcbSComp1.setMaximumRowCount(5);
        jcbSComp1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--" }));
        jcbSComp1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jcbSComp1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbSComp1.setOpaque(false);
        jcbSComp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSComp1ActionPerformed(evt);
            }
        });

        jlDelC2.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlDelC2.setForeground(colorOnSurface);
        jlDelC2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlDelC2.setText("Delete");
        jlDelC2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlDelC2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlDelC2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlDelC2MouseClicked(evt);
            }
        });

        jlAddC2.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlAddC2.setForeground(colorOnSurface);
        jlAddC2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlAddC2.setText(" | Add");
        jlAddC2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlAddC2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlAddC2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlAddC2MouseClicked(evt);
            }
        });

        jPanel73.setBackground(colorSecondary);
        jPanel73.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel73.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel73MouseClicked(evt);
            }
        });

        jLabel126.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel126.setForeground(colorOnSecondary);
        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel126.setText("SAVE");

        javax.swing.GroupLayout jPanel73Layout = new javax.swing.GroupLayout(jPanel73);
        jPanel73.setLayout(jPanel73Layout);
        jPanel73Layout.setHorizontalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel126, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        );
        jPanel73Layout.setVerticalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel73Layout.createSequentialGroup()
                .addComponent(jLabel126, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel71Layout = new javax.swing.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel71Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel71Layout.createSequentialGroup()
                        .addComponent(jcbSG1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jlDelG2)
                        .addGap(0, 0, 0)
                        .addComponent(jlAddG2))
                    .addGroup(jPanel71Layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addComponent(jPanel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel71Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel71Layout.createSequentialGroup()
                                .addComponent(jcbSComp1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlDelC2)
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jlAddC2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel71Layout.createSequentialGroup()
                                .addComponent(jLabel119)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(52, 52, 52))
                    .addGroup(jPanel71Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel71Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel119)
                    .addComponent(jLabel125))
                .addGap(14, 14, 14)
                .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbSComp1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlDelC2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlAddC2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbSG1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlDelG2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlAddG2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel68Layout.createSequentialGroup()
                .addContainerGap(423, Short.MAX_VALUE)
                .addComponent(jPanel71, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(422, Short.MAX_VALUE))
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel68Layout.createSequentialGroup()
                .addContainerGap(247, Short.MAX_VALUE)
                .addComponent(jPanel71, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel68, "card14");

        jPanel61.setBackground(colorBG);

        jPanel77.setBackground(colorBG);

        jPanel78.setBackground(color2_255);

        jScrollPane9.setBackground(colorBG);
        jScrollPane9.setBorder(null);

        jTable9.setBackground(colorBG);
        jTable9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable9.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTable9.setForeground(colorOnSurface);
        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "City", "Delete", "Edit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable9.setFocusable(false);
        jTable9.setGridColor(color2_255);
        jTable9.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable9.setOpaque(false);
        jTable9.setRowHeight(25);
        jTable9.setSelectionBackground(colorSecondary_80);
        jTable9.setSelectionForeground(colorOnSurface);
        jTable9.setShowVerticalLines(false);
        jTable9.getTableHeader().setResizingAllowed(false);
        jTable9.getTableHeader().setReorderingAllowed(false);
        jTable9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable9MouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(jTable9);
        if (jTable9.getColumnModel().getColumnCount() > 0) {
            jTable9.getColumnModel().getColumn(0).setMinWidth(40);
            jTable9.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable9.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable9.getColumnModel().getColumn(1).setResizable(false);
            jTable9.getColumnModel().getColumn(2).setResizable(false);
            jTable9.getColumnModel().getColumn(3).setMinWidth(50);
            jTable9.getColumnModel().getColumn(3).setMaxWidth(50);
            jTable9.getColumnModel().getColumn(4).setMinWidth(50);
            jTable9.getColumnModel().getColumn(4).setMaxWidth(50);
        }

        jcbCityEdit1.setBackground(color2_255);
        jcbCityEdit1.setEditable(true);
        jcbCityEdit1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbCityEdit1.setForeground(colorOnSurface);
        jcbCityEdit1.setMaximumRowCount(5);
        jcbCityEdit1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--City--" }));
        jcbCityEdit1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jcbCityEdit1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbCityEdit1.setOpaque(false);
        jcbCityEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCityEdit1ActionPerformed(evt);
            }
        });

        jtfSFNameEdit1.setBackground(color2_255);
        jtfSFNameEdit1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfSFNameEdit1.setForeground(color1_255);
        jtfSFNameEdit1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, colorOnSurface));
        jtfSFNameEdit1.setOpaque(false);
        jtfSFNameEdit1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfSFNameEdit1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfSFNameEdit1KeyTyped(evt);
            }
        });

        jLabel130.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel130.setForeground(colorOnSurface);
        jLabel130.setText("Search by");

        jLabel131.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel131.setForeground(colorOnSurface);
        jLabel131.setText("Name");

        jlMSIDGh1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jlMSIDGh1.setForeground(colorOnSurface_80);
        jlMSIDGh1.setText("Gatehouses");

        jpMSSave1.setBackground(colorSecondary_80);
        jpMSSave1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpMSSave1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpMSSave1MouseClicked(evt);
            }
        });

        jlMSSave1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlMSSave1.setForeground(colorOnSurface_80);
        jlMSSave1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlMSSave1.setText("SAVE");

        javax.swing.GroupLayout jpMSSave1Layout = new javax.swing.GroupLayout(jpMSSave1);
        jpMSSave1.setLayout(jpMSSave1Layout);
        jpMSSave1Layout.setHorizontalGroup(
            jpMSSave1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMSSave1Layout.createSequentialGroup()
                .addComponent(jlMSSave1, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpMSSave1Layout.setVerticalGroup(
            jpMSSave1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMSSave1Layout.createSequentialGroup()
                .addComponent(jlMSSave1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpMSDefault1.setBackground(colorSecondary_80);
        jpMSDefault1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpMSDefault1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpMSDefault1MouseClicked(evt);
            }
        });

        jlMSDefault1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlMSDefault1.setForeground(colorOnSurface_80);
        jlMSDefault1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlMSDefault1.setText("DEFAULT");

        javax.swing.GroupLayout jpMSDefault1Layout = new javax.swing.GroupLayout(jpMSDefault1);
        jpMSDefault1.setLayout(jpMSDefault1Layout);
        jpMSDefault1Layout.setHorizontalGroup(
            jpMSDefault1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMSDefault1Layout.createSequentialGroup()
                .addComponent(jlMSDefault1, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpMSDefault1Layout.setVerticalGroup(
            jpMSDefault1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMSDefault1Layout.createSequentialGroup()
                .addComponent(jlMSDefault1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jcbSEditGH1.setBackground(color2_255);
        jcbSEditGH1.setEditable(true);
        jcbSEditGH1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbSEditGH1.setForeground(colorOnSurface_80);
        jcbSEditGH1.setMaximumRowCount(5);
        jcbSEditGH1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--" }));
        jcbSEditGH1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jcbSEditGH1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbSEditGH1.setOpaque(false);
        jcbSEditGH1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSEditGH1ActionPerformed(evt);
            }
        });

        jlAddG4.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlAddG4.setForeground(colorOnSurface_80);
        jlAddG4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlAddG4.setText(" | Add");
        jlAddG4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlAddG4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlAddG4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlAddG4MouseClicked(evt);
            }
        });

        jlDelG4.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlDelG4.setForeground(colorOnSurface_80);
        jlDelG4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlDelG4.setText("Delete");
        jlDelG4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlDelG4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlDelG4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlDelG4MouseClicked(evt);
            }
        });

        jlMSIDC1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jlMSIDC1.setForeground(color1_80);
        jlMSIDC1.setText("Companies");

        jcbSEditC1.setBackground(color2_255);
        jcbSEditC1.setEditable(true);
        jcbSEditC1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jcbSEditC1.setForeground(color1_255);
        jcbSEditC1.setMaximumRowCount(5);
        jcbSEditC1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--" }));
        jcbSEditC1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jcbSEditC1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbSEditC1.setOpaque(false);
        jcbSEditC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSEditC1ActionPerformed(evt);
            }
        });

        jlDelC4.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlDelC4.setForeground(colorOnSurface_80);
        jlDelC4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlDelC4.setText("Delete");
        jlDelC4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlDelC4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlDelC4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlDelC4MouseClicked(evt);
            }
        });

        jlAddC4.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlAddC4.setForeground(colorOnSurface_80);
        jlAddC4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlAddC4.setText(" | Add");
        jlAddC4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlAddC4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlAddC4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlAddC4MouseClicked(evt);
            }
        });

        jlMSIDAdmin1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jlMSIDAdmin1.setForeground(colorOnSurface_80);
        jlMSIDAdmin1.setText("Site manager");

        jlMSVL3.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jlMSVL3.setForeground(color1_80);
        jlMSVL3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlMSVL3.setText("Select");
        jlMSVL3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlMSVL3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlMSVL3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlMSVL3MouseClicked(evt);
            }
        });

        jtfMSIDMan1.setEditable(false);
        jtfMSIDMan1.setBackground(color2_255);
        jtfMSIDMan1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfMSIDMan1.setForeground(color1_255);
        jtfMSIDMan1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jtfMSIDMan1.setOpaque(false);

        jlErrSComp4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlErrSComp4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlErrSComp4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel78Layout = new javax.swing.GroupLayout(jPanel78);
        jPanel78.setLayout(jPanel78Layout);
        jPanel78Layout.setHorizontalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel78Layout.createSequentialGroup()
                .addGap(0, 37, Short.MAX_VALUE)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel78Layout.createSequentialGroup()
                        .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlMSIDAdmin1)
                            .addGroup(jPanel78Layout.createSequentialGroup()
                                .addComponent(jtfMSIDMan1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlMSVL3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlErrSComp4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jlMSIDGh1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel78Layout.createSequentialGroup()
                                .addComponent(jlMSIDC1)
                                .addGap(211, 211, 211))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel78Layout.createSequentialGroup()
                                .addComponent(jcbSEditGH1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlDelG4)
                                .addGap(0, 0, 0)
                                .addComponent(jlAddG4))
                            .addComponent(jpMSSave1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel78Layout.createSequentialGroup()
                        .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel130)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel78Layout.createSequentialGroup()
                                .addComponent(jcbSEditC1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlDelC4)
                                .addGap(0, 0, 0)
                                .addComponent(jlAddC4)
                                .addGap(306, 306, 306)
                                .addComponent(jpMSDefault1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel78Layout.createSequentialGroup()
                                .addComponent(jcbCityEdit1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfSFNameEdit1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel78Layout.setVerticalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel78Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel130)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbCityEdit1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfSFNameEdit1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel131)
                .addGap(25, 25, 25)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlMSIDAdmin1)
                    .addComponent(jlMSIDC1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlAddG4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlDelG4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcbSEditGH1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtfMSIDMan1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlMSVL3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlErrSComp4))
                .addGap(9, 9, 9)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel78Layout.createSequentialGroup()
                        .addComponent(jlMSIDGh1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jcbSEditC1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jlDelC4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jlAddC4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jpMSDefault1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jpMSSave1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(206, 206, 206))
        );

        javax.swing.GroupLayout jPanel77Layout = new javax.swing.GroupLayout(jPanel77);
        jPanel77.setLayout(jPanel77Layout);
        jPanel77Layout.setHorizontalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel77Layout.createSequentialGroup()
                .addContainerGap(358, Short.MAX_VALUE)
                .addComponent(jPanel78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(359, Short.MAX_VALUE))
        );
        jPanel77Layout.setVerticalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel77Layout.createSequentialGroup()
                .addContainerGap(103, Short.MAX_VALUE)
                .addComponent(jPanel78, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(144, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1634, Short.MAX_VALUE)
            .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel77, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
            .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel77, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel61, "card15");

        jPanel65.setBackground(colorBG);

        jPanel66.setBackground(color2_255);

        jLabel101.setBackground(colorOnSurface);
        jLabel101.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel101.setForeground(colorOnSurface);
        jLabel101.setText("Welcome,");

        jPanel79.setBackground(colorBG);

        jLabel107.setBackground(colorOnSurface);
        jLabel107.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel107.setForeground(colorOnSurface);
        jLabel107.setText("129");

        jLabel108.setBackground(colorOnSurface);
        jLabel108.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel108.setForeground(colorOnSurface);
        jLabel108.setText("Total");
        jLabel108.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jPanel80.setBackground(colorSecondary);

        javax.swing.GroupLayout jPanel80Layout = new javax.swing.GroupLayout(jPanel80);
        jPanel80.setLayout(jPanel80Layout);
        jPanel80Layout.setHorizontalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel80Layout.setVerticalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel79Layout = new javax.swing.GroupLayout(jPanel79);
        jPanel79.setLayout(jPanel79Layout);
        jPanel79Layout.setHorizontalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel79Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(jPanel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel79Layout.setVerticalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel79Layout.createSequentialGroup()
                .addComponent(jPanel80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel102.setBackground(colorOnSurface);
        jLabel102.setForeground(colorOnSurface);
        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel102.setText("Demands");

        jLabel109.setBackground(colorOnSurface);
        jLabel109.setForeground(colorOnSurface);
        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel109.setText("Refused");

        jPanel81.setBackground(colorBG);

        jLabel110.setBackground(colorOnSurface);
        jLabel110.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel110.setForeground(colorOnSurface);
        jLabel110.setText("9");

        jLabel127.setBackground(colorOnSurface);
        jLabel127.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel127.setForeground(colorOnSurface);
        jLabel127.setText("Total");
        jLabel127.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jPanel82.setBackground(colorSecondary);

        javax.swing.GroupLayout jPanel82Layout = new javax.swing.GroupLayout(jPanel82);
        jPanel82.setLayout(jPanel82Layout);
        jPanel82Layout.setHorizontalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel82Layout.setVerticalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel81Layout = new javax.swing.GroupLayout(jPanel81);
        jPanel81.setLayout(jPanel81Layout);
        jPanel81Layout.setHorizontalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel81Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(jPanel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel81Layout.setVerticalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel81Layout.createSequentialGroup()
                .addComponent(jPanel82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel128.setBackground(colorOnSurface);
        jLabel128.setForeground(colorOnSurface);
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel128.setText("Accepted");

        jPanel83.setBackground(colorBG);

        jLabel129.setBackground(colorOnSurface);
        jLabel129.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel129.setForeground(colorOnSurface);
        jLabel129.setText("105");

        jLabel132.setBackground(colorOnSurface);
        jLabel132.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel132.setForeground(colorOnSurface);
        jLabel132.setText("Total");
        jLabel132.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jPanel84.setBackground(colorSecondary);

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel83Layout = new javax.swing.GroupLayout(jPanel83);
        jPanel83.setLayout(jPanel83Layout);
        jPanel83Layout.setHorizontalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel83Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(jPanel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel83Layout.setVerticalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel83Layout.createSequentialGroup()
                .addComponent(jPanel84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel133.setBackground(colorOnSurface);
        jLabel133.setForeground(colorOnSurface);
        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel133.setText("Pending");

        jPanel85.setBackground(colorBG);

        jLabel134.setBackground(colorOnSurface);
        jLabel134.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel134.setForeground(colorOnSurface);
        jLabel134.setText("15");

        jLabel135.setBackground(colorOnSurface);
        jLabel135.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel135.setForeground(colorOnSurface);
        jLabel135.setText("Total");
        jLabel135.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jPanel86.setBackground(colorSecondary);

        javax.swing.GroupLayout jPanel86Layout = new javax.swing.GroupLayout(jPanel86);
        jPanel86.setLayout(jPanel86Layout);
        jPanel86Layout.setHorizontalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel86Layout.setVerticalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel85Layout = new javax.swing.GroupLayout(jPanel85);
        jPanel85.setLayout(jPanel85Layout);
        jPanel85Layout.setHorizontalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel85Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(jPanel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel85Layout.setVerticalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel85Layout.createSequentialGroup()
                .addComponent(jPanel86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel128, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel133, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(37, 37, 37))))
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addComponent(jLabel109)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addComponent(jLabel102)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addComponent(jLabel128)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addComponent(jLabel133)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(165, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                .addContainerGap(430, Short.MAX_VALUE)
                .addComponent(jPanel66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(407, Short.MAX_VALUE))
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addComponent(jPanel66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel65, "card16");

        jPanel6.setBackground(colorBG);

        jPanel87.setBackground(color2_255);

        jLabel138.setBackground(colorOnSurface);
        jLabel138.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel138.setForeground(colorOnSurface);
        jLabel138.setText("Welcome,");

        jPanel88.setBackground(colorBG);

        jLabel139.setBackground(colorOnSurface);
        jLabel139.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel139.setForeground(colorOnSurface);
        jLabel139.setText("129");

        jLabel140.setBackground(colorOnSurface);
        jLabel140.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel140.setForeground(colorOnSurface);
        jLabel140.setText("Total");
        jLabel140.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jPanel89.setBackground(colorSecondary);

        javax.swing.GroupLayout jPanel89Layout = new javax.swing.GroupLayout(jPanel89);
        jPanel89.setLayout(jPanel89Layout);
        jPanel89Layout.setHorizontalGroup(
            jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel89Layout.setVerticalGroup(
            jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel88Layout = new javax.swing.GroupLayout(jPanel88);
        jPanel88.setLayout(jPanel88Layout);
        jPanel88Layout.setHorizontalGroup(
            jPanel88Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel88Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel88Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel139, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(jPanel89, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel88Layout.setVerticalGroup(
            jPanel88Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel88Layout.createSequentialGroup()
                .addComponent(jPanel89, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel139, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel141.setBackground(colorOnSurface);
        jLabel141.setForeground(colorOnSurface);
        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel141.setText("Sites");

        jLabel142.setBackground(colorOnSurface);
        jLabel142.setForeground(colorOnSurface);
        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel142.setText("Demands");

        jPanel91.setBackground(colorBG);

        jLabel143.setBackground(colorOnSurface);
        jLabel143.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel143.setForeground(colorOnSurface);
        jLabel143.setText("9");

        jLabel144.setBackground(colorOnSurface);
        jLabel144.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel144.setForeground(colorOnSurface);
        jLabel144.setText("Total");
        jLabel144.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jPanel92.setBackground(colorSecondary);

        javax.swing.GroupLayout jPanel92Layout = new javax.swing.GroupLayout(jPanel92);
        jPanel92.setLayout(jPanel92Layout);
        jPanel92Layout.setHorizontalGroup(
            jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel92Layout.setVerticalGroup(
            jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel91Layout = new javax.swing.GroupLayout(jPanel91);
        jPanel91.setLayout(jPanel91Layout);
        jPanel91Layout.setHorizontalGroup(
            jPanel91Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel91Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel91Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel144, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel143, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(jPanel92, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel91Layout.setVerticalGroup(
            jPanel91Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel91Layout.createSequentialGroup()
                .addComponent(jPanel92, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel143, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel144, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel145.setBackground(colorOnSurface);
        jLabel145.setForeground(colorOnSurface);
        jLabel145.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel145.setText("Gatehouses");

        jPanel93.setBackground(colorBG);

        jLabel146.setBackground(colorOnSurface);
        jLabel146.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel146.setForeground(colorOnSurface);
        jLabel146.setText("105");

        jLabel147.setBackground(colorOnSurface);
        jLabel147.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel147.setForeground(colorOnSurface);
        jLabel147.setText("Total");
        jLabel147.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jPanel94.setBackground(colorSecondary);

        javax.swing.GroupLayout jPanel94Layout = new javax.swing.GroupLayout(jPanel94);
        jPanel94.setLayout(jPanel94Layout);
        jPanel94Layout.setHorizontalGroup(
            jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel94Layout.setVerticalGroup(
            jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel93Layout = new javax.swing.GroupLayout(jPanel93);
        jPanel93.setLayout(jPanel93Layout);
        jPanel93Layout.setHorizontalGroup(
            jPanel93Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel93Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel93Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel146, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(jPanel94, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel93Layout.setVerticalGroup(
            jPanel93Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel93Layout.createSequentialGroup()
                .addComponent(jPanel94, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel146, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel148.setBackground(colorOnSurface);
        jLabel148.setForeground(colorOnSurface);
        jLabel148.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel148.setText("Site managers");

        jPanel95.setBackground(colorBG);

        jLabel149.setBackground(colorOnSurface);
        jLabel149.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel149.setForeground(colorOnSurface);
        jLabel149.setText("15");

        jLabel150.setBackground(colorOnSurface);
        jLabel150.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel150.setForeground(colorOnSurface);
        jLabel150.setText("Total");
        jLabel150.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jPanel96.setBackground(colorSecondary);

        javax.swing.GroupLayout jPanel96Layout = new javax.swing.GroupLayout(jPanel96);
        jPanel96.setLayout(jPanel96Layout);
        jPanel96Layout.setHorizontalGroup(
            jPanel96Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel96Layout.setVerticalGroup(
            jPanel96Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel95Layout = new javax.swing.GroupLayout(jPanel95);
        jPanel95.setLayout(jPanel95Layout);
        jPanel95Layout.setHorizontalGroup(
            jPanel95Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel95Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel95Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel150, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(jPanel96, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel95Layout.setVerticalGroup(
            jPanel95Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel95Layout.createSequentialGroup()
                .addComponent(jPanel96, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel150, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel87Layout = new javax.swing.GroupLayout(jPanel87);
        jPanel87.setLayout(jPanel87Layout);
        jPanel87Layout.setHorizontalGroup(
            jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel87Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel87Layout.createSequentialGroup()
                        .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel87Layout.createSequentialGroup()
                        .addGroup(jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel88, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel141, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel93, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel145, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addGroup(jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel95, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel148, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel91, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel142, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(37, 37, 37))))
        );
        jPanel87Layout.setVerticalGroup(
            jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel87Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108)
                .addGroup(jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel87Layout.createSequentialGroup()
                        .addComponent(jLabel142)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel91, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel87Layout.createSequentialGroup()
                        .addComponent(jLabel141)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel88, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel87Layout.createSequentialGroup()
                        .addComponent(jLabel145)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel93, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel87Layout.createSequentialGroup()
                        .addComponent(jLabel148)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel95, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(170, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(430, Short.MAX_VALUE)
                .addComponent(jPanel87, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(407, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addComponent(jPanel87, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel6, "card17");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseEntered
        // TODO add your handling code here:
        jPanel8.setBackground(color1_255);
    }//GEN-LAST:event_jPanel8MouseEntered

    private void jPanel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseExited
        // TODO add your handling code here:
        jPanel8.setBackground(colorBG);
    }//GEN-LAST:event_jPanel8MouseExited

    private void jPanel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseEntered
        // TODO add your handling code here:
        jPanel7.setBackground(color1_255);
    }//GEN-LAST:event_jPanel7MouseEntered

    private void jPanel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseExited
        // TODO add your handling code here:
        jPanel7.setBackground(colorBG);
    }//GEN-LAST:event_jPanel7MouseExited

    private void jPanel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseEntered
        // TODO add your handling code here:
        jPanel9.setBackground(color1_255);
    }//GEN-LAST:event_jPanel9MouseEntered

    private void jPanel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseExited
        // TODO add your handling code here:
        jPanel9.setBackground(colorBG);
    }//GEN-LAST:event_jPanel9MouseExited

    private void jPanel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseEntered
        // TODO add your handling code here:
        jPanel10.setBackground(color1_255);
    }//GEN-LAST:event_jPanel10MouseEntered

    private void jPanel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseExited
        // TODO add your handling code here:
        jPanel10.setBackground(colorBG);
    }//GEN-LAST:event_jPanel10MouseExited

    private void jPanel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseEntered
        // TODO add your handling code here:
        jPanel11.setBackground(color1_255);
    }//GEN-LAST:event_jPanel11MouseEntered

    private void jPanel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseExited
        // TODO add your handling code here:
        jPanel11.setBackground(colorBG);
    }//GEN-LAST:event_jPanel11MouseExited

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        // TODO add your handling code here:
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card2");
        jLabel9.setText("Administration - Create new user account");
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        // TODO add your handling code here:
        LinkedList<Compte> listeC = init_ADUserAccounts("select * from compte;");
        jlDisplayX1.setText("1");
        jlDisplayY1.setText(jcbNum1.getSelectedItem() + "");
        jlDisplayMax1.setText("from " + listeC.size());
        displayADUserAccounts(listeC, 0, Integer.parseInt(jcbNum1.getSelectedItem() + ""));
        jTable1.getColumn("").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\activateAccount.png")));
        jTable1.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\activateAccount.png")));
        jTable1.getColumn(" ").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\deactivateAccount.png")));
        jTable1.getColumn(" ").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\deactivateAccount.png")));
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card3");
        jLabel9.setText("Administration - Activate/Deactivate user account");
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jcbFAStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbFAStateActionPerformed
        // TODO add your handling code here:
        LinkedList<Compte> listeC = init_ADUserAccounts(searchFilterAccount(jcbFAPos, jcbFAState, jtfFAName));
        jlDisplayX1.setText("1");
        jlDisplayY1.setText(jcbNum1.getSelectedItem() + "");
        jlDisplayMax1.setText("from " + listeC.size());
        displayADUserAccounts(listeC, 0, Integer.parseInt(jcbNum1.getSelectedItem() + ""));
    }//GEN-LAST:event_jcbFAStateActionPerformed

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here:
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card4");
        jLabel9.setText("Administration - Create new site");
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel26MouseClicked
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_jPanel26MouseClicked

    private void jcbCompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCompActionPerformed
        // TODO add your handling code here:
        init_sitesConsult(searchFilterSite(jcbCity, jcbComp, jtfSFName));
        jtableDesign(jTable3, jScrollPane3);
    }//GEN-LAST:event_jcbCompActionPerformed

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        // TODO add your handling code here:
        init_sitesConsult("select * from site;");
        init_filter1SiteConsult(jcbCity);
        init_filter2SiteConsult(jcbComp);
        jtableDesign(jTable3, jScrollPane3);
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card6");
        jLabel49.setText("First of family name");
        jLabel9.setText("Administration - Consult sites");
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        // TODO add your handling code here:
        blockJCB(jlMSIDGh, jcbSEditGH, jlMSIDGh);
        blockLabel(jlAddG);
        blockLabel(jlDelG);
        blockJCB(jlMSIDC, jcbSEditC, jlMSIDC);
        blockLabel(jlAddC);
        blockLabel(jlDelC);
        init_filter1SiteConsult(jcbCityEdit);
        init_filter2SiteConsult(jcbCompEdit);
        blockSitesEdit();
        LinkedList<Site> listeS = getSiteList(searchFilterSite(jcbCityEdit, jcbCompEdit, jtfSFNameEdit));
        jlDisplayMaxES.setText("from " + listeS.size());
        jtableEditSitesRestyle();
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card7");
        jLabel9.setText("Administration - Edite/Delete site");
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jcbCompEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCompEditActionPerformed
        // TODO add your handling code here:
        LinkedList<Site> listeS = getSiteList(searchFilterSite(jcbCityEdit, jcbCompEdit, jtfSFNameEdit));
        displaySites(listeS, 0, Integer.parseInt(jcbNumES.getSelectedItem() + ""));
        jtableEditSitesRestyle();
        blockSitesEdit();
    }//GEN-LAST:event_jcbCompEditActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTable4MouseClicked

    private void jpMSDefaultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpMSDefaultMouseClicked
        // TODO add your handling code here:
        if (siteEditable) setEditSite();
        
    }//GEN-LAST:event_jpMSDefaultMouseClicked

    private void jPanel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseClicked
        // TODO add your handling code here:
        removeErrIcon("addsite");
        if (!isTFValid(jtfSName, "normal") || !isTFValid(jtfSCity, "normal")){
            //JOptionPane.showMessageDialog(null, "alert", "bad", JOptionPane.ERROR_MESSAGE);
           
            if(!isTFValid(jtfSName, "normal"))jlErrSName.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!isTFValid(jtfSCity, "normal"))jlErrSCity.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            
            }else{
            int r = 0;
            int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
                if (res == 0){
                    try{
                
                Connection conn = SingletonConnection.getConnection();
                String req;
                PreparedStatement pstmt;
                if (jtfSIDA.getText().equals("")){
                    req = "insert into site values(?, ?, null, null);";
                    pstmt = (PreparedStatement) conn.prepareStatement(req);
                }
                else{
                    req = "insert into site values(?, ?, null, (select id from admin where id = ?));";
                    pstmt = (PreparedStatement) conn.prepareStatement(req);
                    pstmt.setInt(3, Integer.parseInt(jtfSIDA.getText().split("-", 2)[0]));
                }
                pstmt.setString(1, jtfSName.getText());
                pstmt.setString(2, jtfSCity.getText());
                
                
                r = pstmt.executeUpdate();
                
                if (!jtfSIDM.getText().equals("")){
                    req = "update responsablesite set idSite = (SELECT max(idsite) FROM site) where id = ?;";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, Integer.parseInt(jtfSIDM.getText().split("-", 2)[0]));
                    r = pstmt.executeUpdate();
                }
                
                if (jcbSComp.getItemCount() > 1){
                    for (int i = 1; i < jcbSComp.getItemCount(); i++){
                        req = "update entreprise set idsite = (SELECT max(idsite) FROM site) where idEntreprise = ?";
                        pstmt = conn.prepareStatement(req);
                        pstmt.setInt(1, Integer.parseInt(jcbSComp.getItemAt(i).split("-", 2)[0]));
                        r = pstmt.executeUpdate();
                    }
                }
                
                if (jcbSG.getItemCount() > 1){
                    for (int i = 1; i < jcbSG.getItemCount(); i++){
                        req = "update guerite set idsite = (SELECT max(idsite) FROM site) where id = ?";
                        pstmt = conn.prepareStatement(req);
                        pstmt.setInt(1, Integer.parseInt(jcbSG.getItemAt(i).split("-", 2)[0]));
                        r = pstmt.executeUpdate();
                    }
                }
            }catch(SQLException e){
                System.err.println("ERROR!");
            }
                }
            
        }
    }//GEN-LAST:event_jPanel20MouseClicked

    private void jtfSearchFilterRSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfSearchFilterRSKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jtfSearchFilterRSKeyTyped

    private void jtfSearchFilterRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfSearchFilterRSKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jtfSearchFilterRSKeyPressed

    private void jlErrSNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrSNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrSNameMouseClicked

    private void jlErrSCityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrSCityMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrSCityMouseClicked

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked
        // TODO add your handling code here:
        searchFilter("responsablesite");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Administration - Select site manager");
        jlSearchFilter.setText("Only valid site managers are visible");
        jLabel49.setText("First of family name");
        viewListState = 1;
    }//GEN-LAST:event_jLabel38MouseClicked

    private void jcbCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCityActionPerformed
        // TODO add your handling code here:
        init_sitesConsult(searchFilterSite(jcbCity, jcbComp, jtfSFName));
        jtableDesign(jTable3, jScrollPane3);
    }//GEN-LAST:event_jcbCityActionPerformed

    private void jtfSFNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfSFNameKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jtfSFNameKeyTyped

    private void jPanel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel32MouseClicked
        // TODO add your handling code here:
        if (viewListState == 1  || viewListState == 6  || viewListState == 7){
            CardLayout c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card4");
            jLabel9.setText("Administration - Create new site");
        }else if (viewListState == 0){
            CardLayout c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card7");
            jLabel9.setText("Administration - Edite/Delete site");
        }else if (viewListState == 2){
            CardLayout c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card8");
            jLabel9.setText("Administration - Reset password");
        }else if (viewListState == 3){
            CardLayout c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card2");
            jLabel9.setText("Administration - Create new user account");
        }else if (viewListState == 4 || viewListState == 5){
            CardLayout c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card7");
            jLabel9.setText("Administration - Edite/Delete site");
        }else if (viewListState == 8 || viewListState == 9){
            CardLayout c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card14");
            jLabel9.setText("Site manager - Edit site");
        }else if (viewListState == 10 || viewListState == 11 || viewListState == 12){
            CardLayout c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card15");
            jLabel9.setText("Administration - Edit site");
        }
    }//GEN-LAST:event_jPanel32MouseClicked

    private void jPanel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel27MouseClicked
        // TODO add your handling code here:
        int row = jTable2.getSelectedRow();
        if (row >= 0){
            if (viewListState == 1){
                if (jlSearchFilter.getText().equals("Only admins are visible")) jtfSIDA.setText(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1) + " " + jTable2.getValueAt(row, 2));
                else if (jlSearchFilter.getText().equals("Only valid site managers are visible")) jtfSIDM.setText(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1) + " " + jTable2.getValueAt(row, 2));
                
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card4");
                jLabel9.setText("Administration - Create new site");
            }else if (viewListState == 0){
                if (jlSearchFilter.getText().equals("Only admins are visible")) jtfMSIDAdmin.setText(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1) + " " + jTable2.getValueAt(row, 2));
                else if (jlSearchFilter.getText().equals("Only valid site managers are visible")) jtfMSIDMan.setText(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1) + " " + jTable2.getValueAt(row, 2));
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card7");
                jLabel9.setText("Administration - Edite/Delete site");
            }else if (viewListState == 2){
                jtfEmailReset.setText(jTable2.getValueAt(row, 3) + "");
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card8");
                jLabel9.setText("Administration - Reset password");
            }else if (viewListState == 3){
                jtfB.setText(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1));
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card2");
                jLabel9.setText("Administration - Create new user account");
            }else if (viewListState == 4){
                jcbSEditGH.addItem(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1) + " " + jTable2.getValueAt(row, 2));
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card7");
                jLabel9.setText("Administration - Edite/Delete site");
            }else if (viewListState == 5){
                jcbSEditC.addItem(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1));
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card7");
                jLabel9.setText("Administration - Edite/Delete site");
            }else if (viewListState == 7){
                jcbSComp.addItem(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1));
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card4");
                jLabel9.setText("Administration - Create new site");
            }else if (viewListState == 6){
                jcbSG.addItem(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1) + " " + jTable2.getValueAt(row, 2));
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card4");
                jLabel9.setText("Administration - Create new site");
            }else if (viewListState == 8){
                jcbSG1.addItem(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1) + " " + jTable2.getValueAt(row, 2));
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card14");
                jLabel9.setText("Site manager - Edit site");
            }
            else if (viewListState == 9){
                jcbSComp1.addItem(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1));
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card14");
                jLabel9.setText("Site manager - Edit site");
            }else if (viewListState == 10){
                jtfMSIDMan1.setText(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1) + " " + jTable2.getValueAt(row, 2));
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card15");
                jLabel9.setText("Administration - Edit site");
            }else if (viewListState == 11){
                jcbSEditC1.addItem(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1) + " " + jTable2.getValueAt(row, 2));
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card15");
                jLabel9.setText("Administration - Edit site");
            }else if (viewListState == 12){
                jcbSEditGH1.addItem(jTable2.getValueAt(row, 0) + "- " + jTable2.getValueAt(row, 1));
                CardLayout c = (CardLayout) jPanel3.getLayout();
                c.show(jPanel3, "card15");
                jLabel9.setText("Administration - Edit site");
            }
            
        }else{
            //alert
        }
    }//GEN-LAST:event_jPanel27MouseClicked

    private void jpMSSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpMSSaveMouseClicked
        // TODO add your handling code here:
        if (siteEditable){
            int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
                if (res == 0){
                    int r = 0;
            try{
                int id = idEditSiteSA;
                Connection conn = SingletonConnection.getConnection();
                String req;
                PreparedStatement pstmt;
                if (!jtfMSIDAdmin.getText().equals("None")){
                    req = "update site set id = (select id from admin where id = ?) where idsite = ?;";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, Integer.parseInt(jtfMSIDAdmin.getText().split("-", 2)[0]));
                    pstmt.setInt(2, id);
                    r = pstmt.executeUpdate();
                }
                if (!jtfMSIDMan.getText().equals("None")){
                    req = "update responsablesite set idsite = null where id = (select id from responsablesite where idsite = ?);";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, id);
                    r = pstmt.executeUpdate();
                    req = "update responsablesite set idsite = (select idsite from site where idsite = ?) where id = ?;";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, id);
                    pstmt.setInt(2, Integer.parseInt(jtfMSIDMan.getText().split("-", 2)[0]));
                    r = pstmt.executeUpdate();
                }
                req = "update guerite set idSite = null where idSite = ?;";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, id);
                r = pstmt.executeUpdate();
                for (int i = 1; i < jcbSEditGH.getItemCount() ; i++){
                    req = "update guerite set idsite = (select idsite from site where idsite = ?) where id = ?;";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, id);
                    pstmt.setInt(2, Integer.parseInt(jcbSEditGH.getItemAt(i).split("-", 2)[0]));
                    r = pstmt.executeUpdate();
                }
                req = "update entreprise set idSite = null where idSite = ?;";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, id);
                r = pstmt.executeUpdate();
                for (int i = 1; i < jcbSEditC.getItemCount() ; i++){
                    req = "update entreprise set idsite = (select idsite from site where idsite = ?) where identreprise = ?;";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, id);
                    pstmt.setInt(2, Integer.parseInt(jcbSEditC.getItemAt(i).split("-", 2)[0]));
                    r = pstmt.executeUpdate();
                }
            }catch(SQLException e){
                
            }
                }
            
        }
    }//GEN-LAST:event_jpMSSaveMouseClicked

    private void jcbCityEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCityEditActionPerformed
        // TODO add your handling code here:
        LinkedList<Site> listeS = getSiteList(searchFilterSite(jcbCityEdit, jcbCompEdit, jtfSFNameEdit));
        displaySites(listeS, 0, Integer.parseInt(jcbNumES.getSelectedItem() + ""));
        jtableEditSitesRestyle();
        blockSitesEdit();
        
    }//GEN-LAST:event_jcbCityEditActionPerformed

    private void jtfSFNameEditKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfSFNameEditKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jtfSFNameEditKeyTyped

    private void jtfSFNameEditKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfSFNameEditKeyReleased
        // TODO add your handling code here:
        LinkedList<Site> listeS = getSiteList(searchFilterSite(jcbCityEdit, jcbCompEdit, jtfSFNameEdit));
        displaySites(listeS, 0, Integer.parseInt(jcbNumES.getSelectedItem() + ""));
        jtableEditSitesRestyle();
        blockSitesEdit();
    }//GEN-LAST:event_jtfSFNameEditKeyReleased

    private void jtfSFNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfSFNameKeyReleased
        // TODO add your handling code here:
        init_sitesConsult(searchFilterSite(jcbCity, jcbComp, jtfSFName));
        jtableDesign(jTable3, jScrollPane3);
    }//GEN-LAST:event_jtfSFNameKeyReleased

    private void jpNext1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpNext1MouseClicked
        // TODO add your handling code here:
        if (!("from " + jlDisplayY1.getText()).equals(jlDisplayMax1.getText())) displayADUserAccounts(init_ADUserAccounts("select * from compte;"), Integer.parseInt(jlDisplayY1.getText()), Integer.parseInt(jcbNum1.getSelectedItem() + ""));
    }//GEN-LAST:event_jpNext1MouseClicked

    private void jpPrevious1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpPrevious1MouseClicked
        // TODO add your handling code here:
        if (!jlDisplayX1.getText().equals("1")) displayADUserAccounts(init_ADUserAccounts("select * from compte;"), Integer.parseInt(jlDisplayX1.getText()) - Integer.parseInt(jcbNum1.getSelectedItem() + "") - 1, Integer.parseInt(jcbNum1.getSelectedItem() + ""));
    }//GEN-LAST:event_jpPrevious1MouseClicked

    private void jcbNum1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbNum1ActionPerformed
        // TODO add your handling code here:
        LinkedList<Compte> listeC = init_ADUserAccounts("select * from compte;");
        jlDisplayX1.setText("1");
        jlDisplayY1.setText(jcbNum1.getSelectedItem() + "");
        jlDisplayMax1.setText("from " + listeC.size());
        displayADUserAccounts(listeC, 0, Integer.parseInt(jcbNum1.getSelectedItem() + ""));
        jTable1.getColumn("").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\activateAccount.png")));
        jTable1.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\activateAccount.png")));
        jTable1.getColumn(" ").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\deactivateAccount.png")));
        jTable1.getColumn(" ").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\deactivateAccount.png")));
    }//GEN-LAST:event_jcbNum1ActionPerformed

    private void jlErrSComp1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrSComp1MouseClicked
        // TODO add your handling code here:
        jtfSIDA.setText("");
    }//GEN-LAST:event_jlErrSComp1MouseClicked

    private void jlErrSComp3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrSComp3MouseClicked
        // TODO add your handling code here:
        jtfSIDM.setText("");
    }//GEN-LAST:event_jlErrSComp3MouseClicked

    private void jtfSIDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfSIDMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfSIDMActionPerformed

    private void jcbFAPosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbFAPosActionPerformed
        // TODO add your handling code here:
        LinkedList<Compte> listeC = init_ADUserAccounts(searchFilterAccount(jcbFAPos, jcbFAState, jtfFAName));
        jlDisplayX1.setText("1");
        jlDisplayY1.setText(jcbNum1.getSelectedItem() + "");
        jlDisplayMax1.setText("from " + listeC.size());
        displayADUserAccounts(listeC, 0, Integer.parseInt(jcbNum1.getSelectedItem() + ""));
    }//GEN-LAST:event_jcbFAPosActionPerformed

    private void jtfFANameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfFANameKeyReleased
        // TODO add your handling code here:
        LinkedList<Compte> listeC = init_ADUserAccounts(searchFilterAccount(jcbFAPos, jcbFAState, jtfFAName));
        jlDisplayX1.setText("1");
        jlDisplayY1.setText(jcbNum1.getSelectedItem() + "");
        jlDisplayMax1.setText("from " + listeC.size());
        displayADUserAccounts(listeC, 0, Integer.parseInt(jcbNum1.getSelectedItem() + ""));
    }//GEN-LAST:event_jtfFANameKeyReleased

    private void jPanel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel33MouseClicked
        // TODO add your handling code here:
        if (!jtfEmailReset.getText().equals("")){
            try{
                Connection conn = SingletonConnection.getConnection();
                String req = "select password from compte where email = ?;";
                PreparedStatement pstmt = conn.prepareStatement(req);
                pstmt.setString(1, jtfEmailReset.getText());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()){
                    SendEmail.sendResetEmail(jtfEmailReset.getText(), "projet.inte@gmail.com", "projetint2000", "Password Reset - OARFYM", rs.getString("password"));
                }
            }catch(SQLException e){
                
            }
            
        }else{
            //alert
        }
    }//GEN-LAST:event_jPanel33MouseClicked

    private void jLabel55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseClicked
        // TODO add your handling code here:
        searchFilter("all");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Administration - Select account");
        jlSearchFilter.setText("Only activated accounts are visible");
        viewListState = 2;
    }//GEN-LAST:event_jLabel55MouseClicked

    private void jtfEmailResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfEmailResetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfEmailResetActionPerformed

    private void jlErrEmailResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrEmailResetMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrEmailResetMouseClicked

    private void jlErrCINMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrCINMouseClicked
        // TODO add your handling code here:
        jtfCIN.requestFocus();
    }//GEN-LAST:event_jlErrCINMouseClicked

    private void jlErrEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrEmailMouseClicked
        // TODO add your handling code here:
        jtfEmail.requestFocus();
    }//GEN-LAST:event_jlErrEmailMouseClicked

    private void jlErrFNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrFNameMouseClicked
        // TODO add your handling code here:
        jtfFName.requestFocus();
    }//GEN-LAST:event_jlErrFNameMouseClicked

    private void jlErrNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrNameMouseClicked
        // TODO add your handling code here:
        jtfName.requestFocus();
    }//GEN-LAST:event_jlErrNameMouseClicked

    private void jlErrPosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrPosMouseClicked
        // TODO add your handling code here:
        jtfPos.requestFocus();
    }//GEN-LAST:event_jlErrPosMouseClicked

    private void jlErrBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrBMouseClicked
        // TODO add your handling code here:
        jtfB.requestFocus();
    }//GEN-LAST:event_jlErrBMouseClicked

    private void jlErrTelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrTelMouseClicked
        // TODO add your handling code here:
        jtfTel.requestFocus();
    }//GEN-LAST:event_jlErrTelMouseClicked

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        // TODO add your handling code here:
        removeErrIcon("signup");
        if (!isTFValid(jtfName, "normal") || !isTFValid(jtfFName, "normal") || !isTFValid(jtfEmail, "email") || (!isTFValid(jtfTel, "tel") && jtfTel.isEditable()) || (!isTFValid(jtfB, "normal") && jtfB.isEditable()) || (!isTFValid(jtfPos, "normal") && jtfPos.isEditable()) || (!isTFValid(jtfCIN, "normal") && jtfCIN.isEditable())){
            //JOptionPane.showMessageDialog(null, "alert", "bad", JOptionPane.ERROR_MESSAGE);
            if(!isTFValid(jtfName, "normal"))jlErrName.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!isTFValid(jtfFName, "normal"))jlErrFName.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!isTFValid(jtfB, "normal")&& jtfB.isEditable())jlErrB.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!isTFValid(jtfEmail, "email"))jlErrEmail.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!isTFValid(jtfTel, "tel") && jtfTel.isEditable())jlErrTel.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!isTFValid(jtfB, "normal") && jrbCU.isSelected())jlErrB.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!isTFValid(jtfPos, "normal") && jtfPos.isEditable())jlErrPos.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!isTFValid(jtfCIN, "normal") && jtfCIN.isEditable())jlErrCIN.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
        }else{
            int r = 0;
            try{
                int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
                if (res == 0){
                    String pass = randomString(10);
                SendEmail.sendEmail(jtfEmail.getText(), "projet.inte@gmail.com", "projetint2000", "Account created - OARFYM", pass, jtfName.getText() + " " + jtfFName.getText());
                Connection conn = SingletonConnection.getConnection();
                String sql = "insert into compte values(null, ?, ?, ?, ?, ?, ?);";
                PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
                pstmt.setString(1, jtfName.getText());
                pstmt.setString(2, jtfFName.getText());
                pstmt.setString(3, pass);
                pstmt.setInt(5, 1);
                pstmt.setString(6, jtfEmail.getText());
                if (jrbA.isSelected()){
                    pstmt.setString(4, "administrateur");
                    r = pstmt.executeUpdate();
                    sql = "insert into admin values((SELECT max(id) FROM compte), ?);";
                    pstmt = (PreparedStatement) conn.prepareStatement(sql);
                    pstmt.setInt(1, Integer.parseInt(jtfTel.getText()));
                    r = pstmt.executeUpdate();
                    resetFormTF("signup");
                }
                if (jrbCU.isSelected()){
                    pstmt.setString(4, "utilisateurentreprise");
                    r = pstmt.executeUpdate();
                    sql = "insert into utilisateurentrerprise values((SELECT max(id) FROM compte), (select idEntreprise from entreprise where idEntreprise = ?), ?, ?, 1);";
                    pstmt = (PreparedStatement) conn.prepareStatement(sql);
                    pstmt.setInt(1, Integer.parseInt(jtfB.getText().split("-", 2)[0]));
                    pstmt.setString(2, jtfPos.getText());
                    pstmt.setInt(3, Integer.parseInt(jtfTel.getText()));
                    r = pstmt.executeUpdate();
                    resetFormTF("signup");
                }
                if (jrbSM.isSelected()){
                    pstmt.setString(4, "responsablesite");
                    r = pstmt.executeUpdate();
                    sql = "insert into responsablesite values((SELECT max(id) FROM compte), null);";
                    pstmt = (PreparedStatement) conn.prepareStatement(sql);
                    r = pstmt.executeUpdate();
                    resetFormTF("signup");
                }
                else{
                    pstmt.setString(4, "guerite");
                    r = pstmt.executeUpdate();
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
                    String currentDateTime = format.format(date);
                    sql = "insert into guerite values((SELECT max(id) FROM compte), null, ?, ?);";
                    pstmt = (PreparedStatement) conn.prepareStatement(sql);
                    pstmt.setString(1, currentDateTime);
                    pstmt.setString(2, jtfCIN.getText());
                    r = pstmt.executeUpdate();
                    resetFormTF("signup");
                }
                }
                

            }catch(SQLException e){
                System.err.println("ERROR!");
            }
        }

    }//GEN-LAST:event_jPanel16MouseClicked

    private void jtfEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfEmailActionPerformed

    private void jrbAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbAMouseClicked
        // TODO add your handling code here:
        allowTextField(jLabel27, jtfTel, true, jlErrTel);
        blockTextField(jLabel28, jtfB, jlErrB);
        blockTextField(jLabel29, jtfPos, jlErrPos);
        blockTextField(jLabel30, jtfCIN, jlErrCIN);
        removeErrIcon("signup");
    }//GEN-LAST:event_jrbAMouseClicked

    private void jrbGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbGMouseClicked
        // TODO add your handling code here:
        blockTextField(jLabel27, jtfTel, jlErrTel);
        blockTextField(jLabel28, jtfB, jlErrB);
        blockTextField(jLabel29, jtfPos, jlErrPos);
        allowTextField(jLabel30, jtfCIN, true, jlErrCIN);
        removeErrIcon("signup");
    }//GEN-LAST:event_jrbGMouseClicked

    private void jrbSMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbSMMouseClicked
        // TODO add your handling code here:
        blockTextField(jLabel27, jtfTel, jlErrTel);
        blockTextField(jLabel28, jtfB, jlErrB);
        blockTextField(jLabel29, jtfPos, jlErrPos);
        blockTextField(jLabel30, jtfCIN, jlErrCIN);
        removeErrIcon("signup");
    }//GEN-LAST:event_jrbSMMouseClicked

    private void jrbCUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbCUMouseClicked
        // TODO add your handling code here:
        allowTextField(jLabel27, jtfTel, true, jlErrTel);
        allowTextField(jLabel28, jtfB, true, jlErrB);
        allowTextField(jLabel29, jtfPos, true, jlErrPos);
        blockTextField(jLabel30, jtfCIN, jlErrCIN);
        removeErrIcon("signup");
    }//GEN-LAST:event_jrbCUMouseClicked

    private void jcbSEditGHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSEditGHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbSEditGHActionPerformed

    private void jlAddGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlAddGMouseClicked
        // TODO add your handling code here:
        searchFilter("gueriteAdd");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Administration - Select gatehouse");
        jLabel49.setText("Fisrt or family name");
        jlSearchFilter.setText("Only gatehouses are visible");
        viewListState = 4;
    }//GEN-LAST:event_jlAddGMouseClicked

    private void jlDelGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDelGMouseClicked
        // TODO add your handling code here:
        if (!jcbSEditGH.getSelectedItem().equals("--Select--"))jcbSEditGH.removeItemAt(jcbSEditGH.getSelectedIndex());
    }//GEN-LAST:event_jlDelGMouseClicked

    private void jlabelCABSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabelCABSMouseClicked
        // TODO add your handling code here:
        searchFilterEntreprise("entreprise");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Administration - Select company");
        jlSearchFilter.setText("Only companies are visible");
        jLabel49.setText("Company or Site name");
        viewListState = 3;
    }//GEN-LAST:event_jlabelCABSMouseClicked

    private void jtfSearchFilterRSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfSearchFilterRSKeyReleased
        // TODO add your handling code here:
        if (viewListState == 0){
            if (jlSearchFilter.getText().equals("Only admins are visible")) searchFilter("admin");
            else if (jlSearchFilter.getText().equals("Only valid site managers are visible")) searchFilter("responsablesite");
        }else if (viewListState == 1){
            if (jlSearchFilter.getText().equals("Only admins are visible")) searchFilter("admin");
            else if (jlSearchFilter.getText().equals("Only valid site managers are visible")) searchFilter("responsablesite");
            else if (jlSearchFilter.getText().equals("Only valid gatehouses are visible")) searchFilter("guerite");
        }else if (viewListState == 2){
            searchFilter("all");
        }else if (viewListState == 3){
            searchFilterEntreprise("entreprise");
        }else if (viewListState == 4){
            searchFilter("gueriteAdd");
        }else if (viewListState == 5){
            searchFilterEntreprise("companyAdd");
        }
    }//GEN-LAST:event_jtfSearchFilterRSKeyReleased

    private void jcbSEditCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSEditCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbSEditCActionPerformed

    private void jlDelCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDelCMouseClicked
        // TODO add your handling code here:
        if (!jcbSEditC.getSelectedItem().equals("--Select--"))jcbSEditC.removeItemAt(jcbSEditC.getSelectedIndex());
    }//GEN-LAST:event_jlDelCMouseClicked

    private void jlAddCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlAddCMouseClicked
        // TODO add your handling code here:
        searchFilterEntreprise("companyAdd");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Administration - Select company");
        jLabel49.setText("Company name");
        jlSearchFilter.setText("Only companies are visible");
        viewListState = 5;
    }//GEN-LAST:event_jlAddCMouseClicked

    private void jlMSVL1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlMSVL1MouseClicked
        // TODO add your handling code here:
        int row = jTable4.getSelectedRow();
        if (row >= 0){
            searchFilter("responsablesite");
            CardLayout c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card5");
            jLabel9.setText("Administration - Select site manager");
            jlSearchFilter.setText("Only valid site managers are visible");
            jLabel49.setText("First of family name");
            viewListState = 0;
        }
    }//GEN-LAST:event_jlMSVL1MouseClicked

    private void jlMSVLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlMSVLMouseClicked
        // TODO add your handling code here:
        int row = jTable4.getSelectedRow();
        if (row >= 0){
            searchFilter("admin");
            CardLayout c = (CardLayout) jPanel3.getLayout();
            c.show(jPanel3, "card5");
            jLabel9.setText("Administration - Select admin");
            jlSearchFilter.setText("Only admins are visible");
            jLabel49.setText("First of family name");
            viewListState = 0;
        }
    }//GEN-LAST:event_jlMSVLMouseClicked

    private void jcbNumESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbNumESActionPerformed
        // TODO add your handling code here:
        LinkedList<Site> listeS = getSiteList(searchFilterSite(jcbCityEdit, jcbCompEdit, jtfSFNameEdit));
        displaySites(listeS, 0, Integer.parseInt(jcbNumES.getSelectedItem() + ""));
        jtableEditSitesRestyle();
        blockSitesEdit();
    }//GEN-LAST:event_jcbNumESActionPerformed

    private void jlPreviousESMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlPreviousESMouseClicked
        // TODO add your handling code here:
        if (!jlDisplayXES.getText().equals("1")) displaySites(getSiteList(searchFilterSite(jcbCityEdit, jcbCompEdit, jtfSFNameEdit)), Integer.parseInt(jlDisplayXES.getText()) - Integer.parseInt(jcbNumES.getSelectedItem() + "") - 1, Integer.parseInt(jcbNumES.getSelectedItem() + ""));
        jtableEditSitesRestyle();
        blockSitesEdit();
    }//GEN-LAST:event_jlPreviousESMouseClicked

    private void jlNextESMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlNextESMouseClicked
        // TODO add your handling code here:
        if (!("from " + jlDisplayYES.getText()).equals(jlDisplayMaxES.getText())) displaySites(getSiteList(searchFilterSite(jcbCityEdit, jcbCompEdit, jtfSFNameEdit)), Integer.parseInt(jlDisplayYES.getText()), Integer.parseInt(jcbNumES.getSelectedItem() + ""));
        jtableEditSitesRestyle();
        blockSitesEdit();
    }//GEN-LAST:event_jlNextESMouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        // TODO add your handling code here:
        searchFilter("admin");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Administration - Select admin");
        jlSearchFilter.setText("Only admins are visible");
        jLabel49.setText("First of family name");
        viewListState = 1;
    }//GEN-LAST:event_jLabel36MouseClicked

    private void jcbSGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbSGActionPerformed

    private void jlDelG1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDelG1MouseClicked
        // TODO add your handling code here:
        if (!jcbSG.getSelectedItem().equals("--Select--"))jcbSG.removeItemAt(jcbSG.getSelectedIndex());
    }//GEN-LAST:event_jlDelG1MouseClicked

    private void jlAddG1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlAddG1MouseClicked
        // TODO add your handling code here:
        searchFilter("gueriteAddC");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Administration - Select gatehouse");
        jLabel49.setText("Fisrt or family name");
        jlSearchFilter.setText("Only gatehouses are visible");
        viewListState = 6;
    }//GEN-LAST:event_jlAddG1MouseClicked

    private void jcbSCompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSCompActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbSCompActionPerformed

    private void jlDelC1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDelC1MouseClicked
        // TODO add your handling code here:
        if (!jcbSComp.getSelectedItem().equals("--Select--"))jcbSComp.removeItemAt(jcbSComp.getSelectedIndex());
    }//GEN-LAST:event_jlDelC1MouseClicked

    private void jlAddC1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlAddC1MouseClicked
        // TODO add your handling code here:
        searchFilterEntreprise("companyAddC");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Administration - Select company");
        jLabel49.setText("Company name");
        jlSearchFilter.setText("Only companies are visible");
        viewListState = 7;
    }//GEN-LAST:event_jlAddC1MouseClicked

    private void jPanel42MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel42MouseExited
        // TODO add your handling code here:
        jPanel42.setBackground(colorBG);
    }//GEN-LAST:event_jPanel42MouseExited

    private void jPanel42MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel42MouseEntered
        // TODO add your handling code here:
        jPanel42.setBackground(color1_255);
    }//GEN-LAST:event_jPanel42MouseEntered

    private void jPanel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel42MouseClicked
        // TODO add your handling code here:
        blockEditDemandForm();
        displayDemandsEdit();
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card11");
        jLabel9.setText("Company user - Edit demands");
    }//GEN-LAST:event_jPanel42MouseClicked

    private void jPanel38MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel38MouseExited
        // TODO add your handling code here:
        jPanel38.setBackground(colorBG);
    }//GEN-LAST:event_jPanel38MouseExited

    private void jPanel38MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel38MouseEntered
        // TODO add your handling code here:
        jPanel38.setBackground(color1_255);
    }//GEN-LAST:event_jPanel38MouseEntered

    private void jPanel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel38MouseClicked
        // TODO add your handling code here:
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card9");
        jLabel9.setText("Company user - Create new demand");
    }//GEN-LAST:event_jPanel38MouseClicked

    private void jPanel37MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel37MouseExited
        // TODO add your handling code here:
        jPanel37.setBackground(colorBG);
    }//GEN-LAST:event_jPanel37MouseExited

    private void jPanel37MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel37MouseEntered
        // TODO add your handling code here:
        jPanel37.setBackground(color1_255);
    }//GEN-LAST:event_jPanel37MouseEntered

    private void jPanel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel37MouseClicked
        // TODO add your handling code here:
        LinkedList<Demande> demandList;
        try {
            int n = Integer.parseInt(jcbNum2.getSelectedItem() + "");
            if (poste.equals("utilisateurentreprise"))demandList = getDemandList("select * from demande where id = ? and personne like '%" + jtfFAName1.getText() + "%';", "ue");
            else demandList = getDemandList("select * from demande natural join entreprise where idSite = ? and personne like '%" + jtfFAName1.getText() + "%';", "ue");
            displayDemands(demandList, 0, Integer.parseInt(jcbNum2.getSelectedItem() + ""));
            if (demandList.size() > 0) jlDisplayX2.setText("1");
            else jlDisplayX2.setText("0");
            if (demandList.size() > n) jlDisplayY2.setText(n + "");
            else jlDisplayY2.setText(demandList.size() + "");
            jlDisplayMax2.setText("from " + demandList.size());
        } catch (ParseException ex) {
            Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card10");
        jLabel9.setText("Company user - Consult demands");
    }//GEN-LAST:event_jPanel37MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        JpDisplay.idConnected = 0;
        JpDisplay.poste = "";
        JfMain.jfMain.setContentPane(new loginTestPanel());
        JfMain.jfMain.revalidate();
        JfMain.jfMain.pack();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jPanel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel43MouseClicked
        // TODO add your handling code here:
        
        removeErrIcon("createDemand");
        
        if (!isTFValid(jtfNumV, "normal") || !isTFValid(jtfNameP, "normal") || !isTFValid(jtfEmailP, "email") || !checkDateForm(jcbDayS, jcbMonthS, jcbYearS) || !checkDateForm(jcbDayE, jcbMonthE, jcbYearE) || (checkDateForm(jcbDayS, jcbMonthS, jcbYearS) && checkDateForm(jcbDayE, jcbMonthE, jcbYearE) && LocalDateTime.of(Integer.parseInt(jcbYearE.getSelectedItem() + ""), getMonth(jcbMonthE.getSelectedItem() + ""), Integer.parseInt(jcbDayE.getSelectedItem() + ""), Integer.parseInt(jcbHourE.getSelectedItem() + ""), Integer.parseInt(jcbMinE.getSelectedItem() + "")).isBefore(LocalDateTime.of(Integer.parseInt(jcbYearS.getSelectedItem() + ""), getMonth(jcbMonthS.getSelectedItem() + ""), Integer.parseInt(jcbDayS.getSelectedItem() + ""), Integer.parseInt(jcbHourS.getSelectedItem() + ""), Integer.parseInt(jcbMinS.getSelectedItem() + ""))))){
            //JOptionPane.showMessageDialog(null, "alert", "bad", JOptionPane.ERROR_MESSAGE);
            if(!isTFValid(jtfNumV, "normal"))jlErrNumV.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!isTFValid(jtfNameP, "normal"))jlErrP.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!isTFValid(jtfEmailP, "email"))jlErrEmailP.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!checkDateForm(jcbDayS, jcbMonthS, jcbYearS))jlErrSD.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!checkDateForm(jcbDayE, jcbMonthE, jcbYearE))jlErrED.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(checkDateForm(jcbDayS, jcbMonthS, jcbYearS) && checkDateForm(jcbDayE, jcbMonthE, jcbYearE) && LocalDateTime.of(Integer.parseInt(jcbYearE.getSelectedItem() + ""), getMonth(jcbMonthE.getSelectedItem() + ""), Integer.parseInt(jcbDayE.getSelectedItem() + ""), Integer.parseInt(jcbHourE.getSelectedItem() + ""), Integer.parseInt(jcbMinE.getSelectedItem() + "")).isBefore(LocalDateTime.of(Integer.parseInt(jcbYearS.getSelectedItem() + ""), getMonth(jcbMonthS.getSelectedItem() + ""), Integer.parseInt(jcbDayS.getSelectedItem() + ""), Integer.parseInt(jcbHourS.getSelectedItem() + ""), Integer.parseInt(jcbMinS.getSelectedItem() + "")))){
                jlErrED.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
                jlAlertED.setText("End date needs to be after Start date");
            }
        }else{
            int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
            if (res == 0){
                int r = 0;
                try{

                    DateTimeFormatter format;
                    String formatDateTime;
                    LocalDateTime datetime1;
                    Connection conn = SingletonConnection.getConnection();
                    String req = "insert into demande values(null, (select identreprise from utilisateurentrerprise where id = ?), ?, null, null, null, ?, ?, ?, ?, ?, ?, ?);";
                    PreparedStatement pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, idConnected);
                    pstmt.setInt(2, idConnected);
                    datetime1 = LocalDateTime.of(Integer.parseInt(jcbYearS.getSelectedItem() + ""), getMonth(jcbMonthS.getSelectedItem() + ""), Integer.parseInt(jcbDayS.getSelectedItem() + ""), Integer.parseInt(jcbHourS.getSelectedItem() + ""), Integer.parseInt(jcbMinS.getSelectedItem() + "")); 
                    format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
                    formatDateTime = datetime1.format(format);
                    pstmt.setString(3, formatDateTime);
                    datetime1 = LocalDateTime.of(Integer.parseInt(jcbYearE.getSelectedItem() + ""), getMonth(jcbMonthE.getSelectedItem() + ""), Integer.parseInt(jcbDayE.getSelectedItem() + ""), Integer.parseInt(jcbHourE.getSelectedItem() + ""), Integer.parseInt(jcbMinE.getSelectedItem() + "")); 
                    formatDateTime = datetime1.format(format);
                    pstmt.setString(4, formatDateTime);
                    pstmt.setString(5, jtfNameP.getText());
                    pstmt.setString(6, jtfEmailP.getText());
                    pstmt.setInt(7, Integer.parseInt(jtfNumV.getText()));
                    pstmt.setInt(8, 2);
                    Date date = new Date();
                    SimpleDateFormat formatt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
                    String currentDateTime = formatt.format(date);
                    pstmt.setString(9, currentDateTime);
                    r = pstmt.executeUpdate();

                }catch(SQLException e){

                }
            }
            
        }
    }//GEN-LAST:event_jPanel43MouseClicked

    private void jlErrPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrPMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrPMouseClicked

    private void jlErrEmailPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrEmailPMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrEmailPMouseClicked

    private void jlErrNumVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrNumVMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrNumVMouseClicked

    private void jcbYearEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbYearEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbYearEActionPerformed

    private void jcbMinSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMinSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbMinSActionPerformed

    private void jcbHourSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbHourSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbHourSActionPerformed

    private void jcbHourEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbHourEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbHourEActionPerformed

    private void jcbMinEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMinEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbMinEActionPerformed

    private void jcbMonthSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMonthSActionPerformed
        // TODO add your handling code here:
        int numDays = getDayCalendar(jcbMonthS);
        int selectedDay = 0;
        if (!(jcbDayS.getSelectedItem() + "").equals("--Day--")) selectedDay = Integer.parseInt(jcbDayS.getSelectedItem() + "");
        jcbDayS.removeAllItems();
        jcbDayS.addItem("--Day--");
        if (numDays == 0){
            for (int i = 1; i <= 9; i++) jcbDayS.addItem("0" + i);
            for (int i = 10; i <= 31; i++) jcbDayS.addItem(i + "");
        }
        else{
            for (int i = 1; i <= 9; i++)jcbDayS.addItem("0" + i);
            for (int i = 10; i <= numDays; i++) jcbDayS.addItem(i + "");
            if (selectedDay != 0 && selectedDay <= 9) jcbDayS.setSelectedItem("0" + selectedDay);
            else if (selectedDay != 0 && selectedDay <= numDays) jcbDayS.setSelectedItem(selectedDay);
        }
    }//GEN-LAST:event_jcbMonthSActionPerformed

    private void jcbMonthEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMonthEActionPerformed
        // TODO add your handling code here:
        int numDays = getDayCalendar(jcbMonthE);
        int selectedDay = 0;
        if (!(jcbDayE.getSelectedItem() + "").equals("--Day--")) selectedDay = Integer.parseInt(jcbDayE.getSelectedItem() + "");
        jcbDayE.removeAllItems();
        jcbDayE.addItem("--Day--");
        if (numDays == 0){
            for (int i = 1; i <= 9; i++) jcbDayE.addItem("0" + i);
            for (int i = 10; i <= 31; i++) jcbDayE.addItem(i + "");
        }
        else{
            for (int i = 1; i <= 9; i++)jcbDayE.addItem("0" + i);
            for (int i = 10; i <= numDays; i++) jcbDayE.addItem(i + "");
            if (selectedDay != 0 && selectedDay <= 9) jcbDayE.setSelectedItem("0" + selectedDay);
            else if (selectedDay != 0 && selectedDay <= numDays) jcbDayE.setSelectedItem(selectedDay);
        }
    }//GEN-LAST:event_jcbMonthEActionPerformed

    private void jlErrSDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrSDMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrSDMouseClicked

    private void jlErrEDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrEDMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrEDMouseClicked

    private void jtfNumVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNumVKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jtfNumVKeyPressed

    private void jtfNumVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfNumVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfNumVActionPerformed

    private void jtfNumVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNumVKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
        if (!(Character.isDigit(key)) || jtfNumV.getText().length() == 2){
            evt.consume();
        }
    }//GEN-LAST:event_jtfNumVKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //JfMain.setTheme();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jtfFAName1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfFAName1KeyReleased
        // TODO add your handling code here:
        LinkedList<Demande> demandList;
        try {
            int n = Integer.parseInt(jcbNum2.getSelectedItem() + "");
            if (poste.equals("utilisateurentreprise"))demandList = getDemandList("select * from demande where id = ? and personne like '%" + jtfFAName1.getText() + "%';", "ue");
            else demandList = getDemandList("select * from demande natural join entreprise where idSite = ? and personne like '%" + jtfFAName1.getText() + "%';", "grs");
            displayDemands(demandList, 0, Integer.parseInt(jcbNum2.getSelectedItem() + ""));
            if (demandList.size() > 0) jlDisplayX2.setText("1");
            else jlDisplayX2.setText("0");
            if (demandList.size() > n) jlDisplayY2.setText(n + "");
            else jlDisplayY2.setText(demandList.size() + "");
            jlDisplayMax2.setText("from " + demandList.size());
        } catch (ParseException ex) {
            Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfFAName1KeyReleased

    private void jcbNum2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbNum2ActionPerformed
        // TODO add your handling code here:
        LinkedList<Demande> demandList;
        try {
            int n = Integer.parseInt(jcbNum2.getSelectedItem() + "");
            if (poste.equals("utilisateurentreprise"))demandList = getDemandList("select * from demande where id = ? and personne like '%" + jtfFAName1.getText() + "%';", "ue");
            else demandList = getDemandList("select * from demande natural join entreprise where idSite = ? and personne like '%" + jtfFAName1.getText() + "%';", "grs");
            displayDemands(demandList, 0, Integer.parseInt(jcbNum2.getSelectedItem() + ""));
            if (demandList.size() > 0) jlDisplayX2.setText("1");
            else jlDisplayX2.setText("0");
            if (demandList.size() > n) jlDisplayY2.setText(n + "");
            else jlDisplayY2.setText(demandList.size() + "");
            jlDisplayMax2.setText("from " + demandList.size());
        } catch (ParseException ex) {
            Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jcbNum2ActionPerformed

    private void jpNext2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpNext2MouseClicked
        // TODO add your handling code here:
        if (!("from " + jlDisplayY2.getText()).equals(jlDisplayMax2.getText())) try {
            LinkedList<Demande> demandList;
            if (poste.equals("utilisateurentreprise"))demandList = getDemandList("select * from demande where id = ? and personne like '%" + jtfFAName1.getText() + "%';", "ue");
            else demandList = getDemandList("select * from demande natural join entreprise where idSite = ? and personne like '%" + jtfFAName1.getText() + "%';", "grs");
            displayDemands(demandList, Integer.parseInt(jlDisplayY2.getText()), Integer.parseInt(jcbNum2.getSelectedItem() + ""));
        } catch (ParseException ex) {
            Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jpNext2MouseClicked

    private void jpPrevious2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpPrevious2MouseClicked
        // TODO add your handling code here:
        if (!jlDisplayX2.getText().equals("1")) try {
            LinkedList<Demande> demandList;
            if (poste.equals("utilisateurentreprise"))demandList = getDemandList("select * from demande where id = ? and personne like '%" + jtfFAName1.getText() + "%';", "ue");
            else demandList = getDemandList("select * from demande natural join entreprise where idSite = ? and personne like '%" + jtfFAName1.getText() + "%';", "grs");
            displayDemands(demandList, Integer.parseInt(jlDisplayX2.getText()) - Integer.parseInt(jcbNum2.getSelectedItem() + "") - 1, Integer.parseInt(jcbNum2.getSelectedItem() + ""));
        } catch (ParseException ex) {
            Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jpPrevious2MouseClicked

    private void jtfFAName2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfFAName2KeyReleased
        // TODO add your handling code here:
        displayDemandsEdit();
    }//GEN-LAST:event_jtfFAName2KeyReleased

    private void jlErrP1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrP1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrP1MouseClicked

    private void jcbMonthSEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMonthSEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbMonthSEActionPerformed

    private void jcbHourSEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbHourSEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbHourSEActionPerformed

    private void jcbMinSEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMinSEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbMinSEActionPerformed

    private void jlErrP2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrP2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrP2MouseClicked

    private void jlErrP3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrP3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrP3MouseClicked

    private void jcbMonthEEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMonthEEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbMonthEEActionPerformed

    private void jcbHourEEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbHourEEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbHourEEActionPerformed

    private void jcbMinEEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMinEEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbMinEEActionPerformed

    private void jlErrSD1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrSD1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrSD1MouseClicked

    private void jPanel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel50MouseClicked
        // TODO add your handling code here:
        allowEditDemand();
    }//GEN-LAST:event_jPanel50MouseClicked

    private void jPanel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel51MouseClicked
        // TODO add your handling code here:
        removeErrIcon("createDemandED");
        
        if (!isTFValid(jtfNumVE, "normal") || !isTFValid(jtfNamePE, "normal") || !isTFValid(jtfEmailPE, "email") || !checkDateForm(jcbDaySE, jcbMonthSE, jcbYearSE) || !checkDateForm(jcbDayEE, jcbMonthEE, jcbYearEE) || (checkDateForm(jcbDaySE, jcbMonthSE, jcbYearSE) && checkDateForm(jcbDayEE, jcbMonthEE, jcbYearEE) && LocalDateTime.of(Integer.parseInt(jcbYearEE.getSelectedItem() + ""), getMonth(jcbMonthEE.getSelectedItem() + ""), Integer.parseInt(jcbDayEE.getSelectedItem() + ""), Integer.parseInt(jcbHourEE.getSelectedItem() + ""), Integer.parseInt(jcbMinEE.getSelectedItem() + "")).isBefore(LocalDateTime.of(Integer.parseInt(jcbYearSE.getSelectedItem() + ""), getMonth(jcbMonthSE.getSelectedItem() + ""), Integer.parseInt(jcbDaySE.getSelectedItem() + ""), Integer.parseInt(jcbHourSE.getSelectedItem() + ""), Integer.parseInt(jcbMinSE.getSelectedItem() + ""))))){
            //JOptionPane.showMessageDialog(null, "alert", "bad", JOptionPane.ERROR_MESSAGE);
            if(!isTFValid(jtfNumVE, "normal"))jlErrP3.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!isTFValid(jtfNamePE, "normal"))jlErrP1.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!isTFValid(jtfEmailPE, "email"))jlErrP2.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!checkDateForm(jcbDaySE, jcbMonthSE, jcbYearSE))jlErrSD.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(!checkDateForm(jcbDayEE, jcbMonthEE, jcbYearEE))jlErrED.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
            if(checkDateForm(jcbDaySE, jcbMonthSE, jcbYearSE) && checkDateForm(jcbDayEE, jcbMonthEE, jcbYearEE) && LocalDateTime.of(Integer.parseInt(jcbYearEE.getSelectedItem() + ""), getMonth(jcbMonthEE.getSelectedItem() + ""), Integer.parseInt(jcbDayEE.getSelectedItem() + ""), Integer.parseInt(jcbHourEE.getSelectedItem() + ""), Integer.parseInt(jcbMinEE.getSelectedItem() + "")).isBefore(LocalDateTime.of(Integer.parseInt(jcbYearSE.getSelectedItem() + ""), getMonth(jcbMonthSE.getSelectedItem() + ""), Integer.parseInt(jcbDaySE.getSelectedItem() + ""), Integer.parseInt(jcbHourSE.getSelectedItem() + ""), Integer.parseInt(jcbMinSE.getSelectedItem() + "")))){
                jlErrSD2.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\error.png"));
                jlAlertED2.setText("End date needs to be after Start date");
            }
        }else{
            int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
            if (res == 0){
                int r = 0;
            try{
                
                DateTimeFormatter format;
                String formatDateTime;
                LocalDateTime datetime1;
                Connection conn = SingletonConnection.getConnection();
                String req = "update demande set personne = ?, dateVisite = ?, dateFinVisite = ?, email = ?, nbrPersonnes = ? where numDemande = ?;";
                PreparedStatement pstmt = conn.prepareStatement(req);
                pstmt.setString(1, jtfNamePE.getText());
                datetime1 = LocalDateTime.of(Integer.parseInt(jcbYearSE.getSelectedItem() + ""), getMonth(jcbMonthSE.getSelectedItem() + ""), Integer.parseInt(jcbDaySE.getSelectedItem() + ""), Integer.parseInt(jcbHourSE.getSelectedItem() + ""), Integer.parseInt(jcbMinSE.getSelectedItem() + "")); 
                format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
                formatDateTime = datetime1.format(format);
                pstmt.setString(2, formatDateTime);
                datetime1 = LocalDateTime.of(Integer.parseInt(jcbYearEE.getSelectedItem() + ""), getMonth(jcbMonthEE.getSelectedItem() + ""), Integer.parseInt(jcbDayEE.getSelectedItem() + ""), Integer.parseInt(jcbHourEE.getSelectedItem() + ""), Integer.parseInt(jcbMinEE.getSelectedItem() + "")); 
                formatDateTime = datetime1.format(format);
                pstmt.setString(3, formatDateTime);
                pstmt.setString(4, jtfEmailPE.getText());
                pstmt.setInt(5, Integer.parseInt(jtfNumVE.getText()));
                pstmt.setInt(6, idDemandEdit);
                r = pstmt.executeUpdate();
                   displayDemandsEdit();
            }catch(SQLException e){

            }
            }
            
        }
    }//GEN-LAST:event_jPanel51MouseClicked

    private void jlErrSD2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrSD2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlErrSD2MouseClicked

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable6MouseClicked

    private void jtfNumVEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNumVEKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
        if (!(Character.isDigit(key)) || jtfNumVE.getText().length() == 2){
            evt.consume();
        }
    }//GEN-LAST:event_jtfNumVEKeyTyped

    private void jPanel56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel56MouseClicked
        // TODO add your handling code here:
        try {
            showPendingDemands("select * from demande natural join entreprise join compte where statutD = 2 and demande.id = compte.id and idSite = " + idSite + ";");
        } catch (ParseException ex) {
            Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card12");
        jLabel9.setText("Gatehouse - Edit demands");
    }//GEN-LAST:event_jPanel56MouseClicked
    
    public LinkedList<Demande> getPendingDemands(String req) throws ParseException{
        LinkedList<Demande> list = new LinkedList<Demande>();
        try{
            Connection conn = SingletonConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                list.add(new Demande(rs.getInt("numDemande"), rs.getInt("id"), rs.getString("nom") + " " + rs.getString("prenom"), rs.getString("nomC"), rs.getString("prenomC"), rs.getString("cnie"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("datevisite")), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("datefinvisite")), rs.getString("personne"), rs.getString("email"), rs.getInt("nbrPersonnes"), rs.getInt("statutD")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    
    public void showPendingDemands(String req) throws ParseException{
        LinkedList<Demande> demandList = getPendingDemands(req);
        Vector<Object> rows;
        Vector<Object> cols = new Vector<Object>();
        cols.add("ID");
        cols.add("Demander");
        cols.add("Person to see");
        cols.add("Email");
        cols.add("Start date");
        cols.add("End date");
        cols.add("Visitors");
        cols.add("Accept");
        cols.add("Refuse");
        
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        for (Demande d : demandList){
            rows = new Vector<Object>();
            rows.add(d.numDemande);
            rows.add(d.idDemander + "- " + d.nomDemandeur);
            rows.add(d.personne);
            rows.add(d.email);
            rows.add(d.dateVisite);
            rows.add(d.dateFinVisite);
            rows.add(d.nbrPersonnes);
            rows.add("accept");
            rows.add("refuse");
            data.add(rows);
        }
        jTable7.setModel(new DefaultTableModel(data, cols));
        jTable7.getColumn("ID").setMaxWidth(25);
        jTable7.getColumn("ID").setMinWidth(25);
        jTable7.getColumn("Accept").setMaxWidth(50);
        jTable7.getColumn("Accept").setMinWidth(50);
        jTable7.getColumn("Refuse").setMaxWidth(50);
        jTable7.getColumn("Refuse").setMinWidth(50);
        jTable7.getColumn("Visitors").setMaxWidth(50);
        jTable7.getColumn("Visitors").setMinWidth(50);
        jTable7.getColumn("Accept").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\accept.png")));
        jTable7.getColumn("Accept").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\accept.png")));
        jTable7.getColumn("Refuse").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\refuse.png")));
        jTable7.getColumn("Refuse").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\refuse.png")));
        jtableDesign(jTable7, jScrollPane7);
    }
    
    public void showAcceptedDemands(String req) throws ParseException{
        LinkedList<Demande> demandList = getPendingDemands(req);
        Vector<Object> rows;
        Vector<Object> cols = new Vector<Object>();
        cols.add("ID");
        cols.add("Demander");
        cols.add("Person to see");
        cols.add("Email");
        cols.add("Start date");
        cols.add("End date");
        cols.add("Visitors");
        cols.add("Visited");
        
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        for (Demande d : demandList){
            rows = new Vector<Object>();
            rows.add(d.numDemande);
            rows.add(d.idDemander + "- " + d.nomDemandeur);
            rows.add(d.personne);
            rows.add(d.email);
            rows.add(d.dateVisite);
            rows.add(d.dateFinVisite);
            rows.add(d.nbrPersonnes);
            rows.add("visited");
            data.add(rows);
        }
        jTable8.setModel(new DefaultTableModel(data, cols));
        jTable8.getColumn("ID").setMaxWidth(25);
        jTable8.getColumn("ID").setMinWidth(25);
        jTable8.getColumn("Visited").setMaxWidth(50);
        jTable8.getColumn("Visited").setMinWidth(50);
        jTable8.getColumn("Visitors").setMaxWidth(50);
        jTable8.getColumn("Visitors").setMinWidth(50);
        jTable8.getColumn("Visited").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\toggleVisited.png")));
        jTable8.getColumn("Visited").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\toggleVisited.png")));
        jtableDesign(jTable8, jScrollPane8);
    }
    
    private void jPanel56MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel56MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel56MouseEntered

    private void jPanel56MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel56MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel56MouseExited

    private void jPanel60MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel60MouseClicked
        // TODO add your handling code here:
        LinkedList<Demande> demandList;
        try {
            int n = Integer.parseInt(jcbNum2.getSelectedItem() + "");
            if (poste.equals("utilisateurentreprise"))demandList = getDemandList("select * from demande where id = ? and personne like '%" + jtfFAName1.getText() + "%';", "ue");
            else demandList = getDemandList("select * from demande natural join entreprise where idSite = ? and personne like '%" + jtfFAName1.getText() + "%';", "grs");
            displayDemands(demandList, 0, Integer.parseInt(jcbNum2.getSelectedItem() + ""));
            if (demandList.size() > 0) jlDisplayX2.setText("1");
            else jlDisplayX2.setText("0");
            if (demandList.size() > n) jlDisplayY2.setText(n + "");
            else jlDisplayY2.setText(demandList.size() + "");
            jlDisplayMax2.setText("from " + demandList.size());
        } catch (ParseException ex) {
            Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card10");
        jLabel9.setText("Company user - Consult demands");
    }//GEN-LAST:event_jPanel60MouseClicked

    private void jPanel60MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel60MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel60MouseEntered

    private void jPanel60MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel60MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel60MouseExited

    private void jPanel55MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel55MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel55MouseExited

    private void jPanel55MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel55MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel55MouseEntered

    private void jPanel55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel55MouseClicked
        // TODO add your handling code here:
        setDashboard("guerite");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card16");
        jLabel9.setText("Gatehouse - Home");
    }//GEN-LAST:event_jPanel55MouseClicked

    private void jcbFAPos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbFAPos2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbFAPos2ActionPerformed

    private void jtfFAName5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfFAName5KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfFAName5KeyReleased

    private void jPanel62MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel62MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel62MouseExited

    private void jPanel62MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel62MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel62MouseEntered

    private void jPanel62MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel62MouseClicked
        // TODO add your handling code here:
        try {
            showAcceptedDemands("select * from demande join compte where statutD = 1 and demande.id = compte.id;");
        } catch (ParseException ex) {
            Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card13");
        jLabel9.setText("Gatehouse - Toggle demands");
    }//GEN-LAST:event_jPanel62MouseClicked

    private void jtfFAName6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfFAName6KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfFAName6KeyReleased

    private void jPanel67MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel67MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel67MouseClicked
    
    public void setEditSiteMan(){
        try{
            Connection conn = SingletonConnection.getConnection();
            String req;
            PreparedStatement pstmt;
            ResultSet rs;
            req = "select * from guerite natural join compte where idSite = ?;";
            pstmt = conn.prepareStatement(req);
            pstmt.setInt(1, idSite);
            rs = pstmt.executeQuery();
            jcbSG1.removeAllItems();
            jcbSG1.addItem("--Select--");
            while (rs.next()){
                jcbSG1.addItem(rs.getString("id") + "- " + rs.getString("nom") + " " + rs.getString("prenom"));
            }
            req = "select * from entreprise where idSite = ?;";
            pstmt = conn.prepareStatement(req);
            pstmt.setInt(1, idSite);
            rs = pstmt.executeQuery();
            jcbSComp1.removeAllItems();
            jcbSComp1.addItem("--Select--");
            while (rs.next()){
                jcbSComp1.addItem(rs.getString("idEntreprise") + "- " + rs.getString("nom"));
            }
        }catch(SQLException e){
            
        }
    }
    
    private void jPanel67MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel67MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel67MouseEntered

    private void jPanel67MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel67MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel67MouseExited

    private void jPanel69MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel69MouseClicked
        // TODO add your handling code here:
        LinkedList<Demande> demandList;
        try {
            int n = Integer.parseInt(jcbNum2.getSelectedItem() + "");
            if (poste.equals("utilisateurentreprise"))demandList = getDemandList("select * from demande where id = ? and personne like '%" + jtfFAName1.getText() + "%';", "ue");
            else demandList = getDemandList("select * from demande natural join entreprise where idSite = ? and personne like '%" + jtfFAName1.getText() + "%';", "grs");
            displayDemands(demandList, 0, Integer.parseInt(jcbNum2.getSelectedItem() + ""));
            if (demandList.size() > 0) jlDisplayX2.setText("1");
            else jlDisplayX2.setText("0");
            if (demandList.size() > n) jlDisplayY2.setText(n + "");
            else jlDisplayY2.setText(demandList.size() + "");
            jlDisplayMax2.setText("from " + demandList.size());
        } catch (ParseException ex) {
            Logger.getLogger(JpDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card10");
        jLabel9.setText("Company user - Consult demands");
    }//GEN-LAST:event_jPanel69MouseClicked

    private void jPanel69MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel69MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel69MouseEntered

    private void jPanel69MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel69MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel69MouseExited

    private void jPanel70MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel70MouseClicked
        // TODO add your handling code here:
        setDashboard("responsablesite");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card16");
        jLabel9.setText("Site manager - Home");
    }//GEN-LAST:event_jPanel70MouseClicked

    private void jPanel70MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel70MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel70MouseEntered

    private void jPanel70MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel70MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel70MouseExited

    private void jPanel72MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel72MouseClicked
        // TODO add your handling code here:
        setEditSiteMan();
    }//GEN-LAST:event_jPanel72MouseClicked

    private void jcbSG1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSG1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbSG1ActionPerformed

    private void jlDelG2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDelG2MouseClicked
        // TODO add your handling code here:
        if (!jcbSG1.getSelectedItem().equals("--Select--"))jcbSG1.removeItemAt(jcbSG1.getSelectedIndex());
    }//GEN-LAST:event_jlDelG2MouseClicked

    private void jlAddG2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlAddG2MouseClicked
        // TODO add your handling code here:
        searchFilter("rsAddC");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Site manager - Select gatehouse");
        jLabel49.setText("Fist or Family name");
        jlSearchFilter.setText("Only valid gathouses are visible");
        viewListState = 8;
    }//GEN-LAST:event_jlAddG2MouseClicked

    private void jcbSComp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSComp1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbSComp1ActionPerformed

    private void jlDelC2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDelC2MouseClicked
        // TODO add your handling code here:
        if (!jcbSComp1.getSelectedItem().equals("--Select--"))jcbSComp1.removeItemAt(jcbSComp1.getSelectedIndex());
    }//GEN-LAST:event_jlDelC2MouseClicked

    private void jlAddC2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlAddC2MouseClicked
        // TODO add your handling code here:
        searchFilterEntreprise("companyAddRS");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Site manager - Select company");
        jLabel49.setText("Company name");
        jlSearchFilter.setText("Only valid companies are visible");
        viewListState = 9;
    }//GEN-LAST:event_jlAddC2MouseClicked

    private void jPanel73MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel73MouseClicked
        // TODO add your handling code here:
        int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
        if (res == 0){
            int r = 0;
        try{
            int id = idSite;
            Connection conn = SingletonConnection.getConnection();
            String req;
            PreparedStatement pstmt;
            req = "update guerite set idSite = null where idSite = ?;";
            pstmt = conn.prepareStatement(req);
            pstmt.setInt(1, id);
            r = pstmt.executeUpdate();
            for (int i = 1; i < jcbSG1.getItemCount() ; i++){
                req = "update guerite set idsite = (select idsite from site where idsite = ?) where id = ?;";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, id);
                pstmt.setInt(2, Integer.parseInt(jcbSG1.getItemAt(i).split("-", 2)[0]));
                r = pstmt.executeUpdate();
            }
            req = "update entreprise set idSite = null where idSite = ?;";
            pstmt = conn.prepareStatement(req);
            pstmt.setInt(1, id);
            r = pstmt.executeUpdate();
            for (int i = 1; i < jcbSComp1.getItemCount() ; i++){
                req = "update entreprise set idsite = (select idsite from site where idsite = ?) where identreprise = ?;";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, id);
                pstmt.setInt(2, Integer.parseInt(jcbSComp1.getItemAt(i).split("-", 2)[0]));
                r = pstmt.executeUpdate();
            }
        }catch(SQLException e){
                
        }
        }
        
    }//GEN-LAST:event_jPanel73MouseClicked

    private void jPanel75MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel75MouseClicked
        // TODO add your handling code here:
        setDashboard("admin");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card17");
        jLabel9.setText("Administation - Edit site");
    }//GEN-LAST:event_jPanel75MouseClicked

    private void jPanel75MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel75MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel75MouseEntered

    private void jPanel75MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel75MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel75MouseExited

    private void jPanel76MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel76MouseClicked
        // TODO add your handling code here:
        blockEditSiteAdmin();
        displaySitesAdmin();
        init_filter1SiteConsult(jcbCityEdit1);
        jTable9.getColumn("Edit").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\edit.png")));
        jTable9.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\edit.png")));
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card15");
        jLabel9.setText("Administation - Edit site");
    }//GEN-LAST:event_jPanel76MouseClicked
    
    public void setEditSiteAdmin(){
        int id = idEditSiteAdmin;
        try{
            Connection conn = SingletonConnection.getConnection();
            String req;
            PreparedStatement pstmt;
            ResultSet rs;
            req = "select * from responsablesite natural join compte where idsite = ?;";
            pstmt = conn.prepareStatement(req);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) jtfMSIDMan1.setText(rs.getInt("id") + "- " + rs.getString("nom") + " " + rs.getString("prenom"));
            else jtfMSIDMan1.setText("");
            req = "select * from guerite natural join compte where idSite = ?;";
            pstmt = conn.prepareStatement(req);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            jcbSEditC1.removeAllItems();
            jcbSEditC1.addItem("--Select--");
            while (rs.next()){
                jcbSEditC1.addItem(rs.getString("id") + "- " + rs.getString("nom") + " " + rs.getString("prenom"));
            }
            req = "select * from entreprise where idSite = ?;";
            pstmt = conn.prepareStatement(req);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            jcbSEditGH1.removeAllItems();
            jcbSEditGH1.addItem("--Select--");
            while (rs.next()){
                jcbSEditGH1.addItem(rs.getString("idEntreprise") + "- " + rs.getString("nom"));
            }
        }catch(SQLException e){
            
        }
    }
    
    public void allowEditSiteAdmin(){
        siteEditableAdmin = true;
        jlMSIDAdmin1.setForeground(colorOnSurface);
        jlMSIDGh1.setForeground(colorOnSurface);
        jlMSIDC1.setForeground(colorOnSurface);
        jtfMSIDMan1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlMSVL3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlMSVL3.setForeground(colorOnSurface);
        jlDelC4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlDelC4.setForeground(colorOnSurface);
        jlAddC4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlAddC4.setForeground(colorOnSurface);
        jlDelG4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlDelG4.setForeground(colorOnSurface);
        jlAddG4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlAddG4.setForeground(colorOnSurface);
        jlErrSComp4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jlErrSComp4.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\clearField.png"));
        jlAddG4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlDelC4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlDelG4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlAddC4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlMSVL3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        allowJCB(jlMSIDAdmin1, jcbSEditC1, jlMSIDAdmin1);
        allowJCB(jlMSIDAdmin1, jcbSEditGH1, jlMSIDAdmin1);
        jpMSDefault1.setBackground(colorSecondary);
        jpMSSave1.setBackground(colorSecondary);
        jpMSDefault1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jpMSSave1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlMSDefault1.setForeground(colorOnSecondary);
        jlMSSave1.setForeground(colorOnSecondary);
    }
    
    public void blockEditSiteAdmin(){
        siteEditableAdmin = false;
        jtfMSIDMan1.setText("");
        jlMSIDAdmin1.setForeground(colorOnSurface_80);
        jlMSIDGh1.setForeground(colorOnSurface_80);
        jlMSIDC1.setForeground(colorOnSurface_80);
        jtfMSIDMan1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlMSVL3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlMSVL3.setForeground(colorOnSurface_80);
        jlDelC4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlDelC4.setForeground(colorOnSurface_80);
        jlAddC4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlAddC4.setForeground(colorOnSurface_80);
        jlDelG4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlDelG4.setForeground(colorOnSurface_80);
        jlAddG4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlAddG4.setForeground(colorOnSurface_80);
        jlErrSComp4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jlErrSComp4.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\clearFieldFaded.png"));
        jlAddG4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlDelC4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlDelG4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlAddC4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlMSVL3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        blockJCB(jlMSIDAdmin1, jcbSEditC1, jlMSIDAdmin1);
        blockJCB(jlMSIDAdmin1, jcbSEditGH1, jlMSIDAdmin1);
        jpMSDefault1.setBackground(colorSecondary_80);
        jpMSSave1.setBackground(colorSecondary_80);
        jpMSDefault1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpMSSave1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlMSDefault1.setForeground(colorSecondary_80);
        jlMSSave1.setForeground(colorSecondary_80);
    }
    
    public void displaySitesAdmin(){
        try{
            Vector<Object> rows;
            Vector<Object> cols = new Vector<Object>();
            cols.add("ID");
            cols.add("Name");
            cols.add("City");
            cols.add("Edit");
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            Connection conn = SingletonConnection.getConnection();
            String req = "select * from site where idSite = ? and nomSite like '%" + jtfSFNameEdit1.getText() + "%';";
            PreparedStatement pstmt;
            ResultSet rs;
            for (String s : idsSite){
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, Integer.parseInt(s));
                rs = pstmt.executeQuery();
                if (rs.next()){
                    if ((jcbCityEdit1.getSelectedItem() + "").equals("--City--") || (!(jcbCityEdit1.getSelectedItem() + "").equals("--City--") && rs.getString("ville").equals(jcbCityEdit1.getSelectedItem() + ""))){
                        rows = new Vector<Object>();
                        rows.add(rs.getInt("idSite"));
                        rows.add(rs.getString("nomSite"));
                        rows.add(rs.getString("ville"));
                        rows.add("editSA");
                        data.add(rows);
                    }
                    
                }
                jTable9.setModel(new DefaultTableModel(data, cols));
                jtableDesign(jTable9, jScrollPane9);
                jTable9.getColumn("ID").setMaxWidth(50);
                jTable9.getColumn("ID").setMinWidth(50);
                jTable9.getColumn("Edit").setMaxWidth(50);
                jTable9.getColumn("Edit").setMinWidth(50);
                jTable9.getColumn("Edit").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\edit.png")));
                jTable9.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\edit.png")));
            }
        }catch(SQLException e){
            
        }
        
    }
    
    private void jPanel76MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel76MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel76MouseEntered

    private void jPanel76MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel76MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel76MouseExited

    private void jlMSVL3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlMSVL3MouseClicked
        // TODO add your handling code here:
        searchFilter("responsablesiteESA");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Administration - Select site manager");
        jlSearchFilter.setText("Only valid site managers are visible");
        jLabel49.setText("First of family name");
        viewListState = 10;
    }//GEN-LAST:event_jlMSVL3MouseClicked

    private void jlAddC4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlAddC4MouseClicked
        // TODO add your handling code here:
        searchFilter("gueriteAddESA");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Administration - Select gatehouse");
        jLabel49.setText("Fisrt or family name");
        jlSearchFilter.setText("Only valid gatehouses are visible");
        viewListState = 11;
    }//GEN-LAST:event_jlAddC4MouseClicked

    private void jlDelC4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDelC4MouseClicked
        // TODO add your handling code here:
        if (!jcbSEditC1.getSelectedItem().equals("--Select--"))jcbSEditC1.removeItemAt(jcbSEditC1.getSelectedIndex());
    }//GEN-LAST:event_jlDelC4MouseClicked

    private void jcbSEditC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSEditC1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbSEditC1ActionPerformed

    private void jlDelG4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDelG4MouseClicked
        // TODO add your handling code here:
        if (!jcbSEditGH1.getSelectedItem().equals("--Select--"))jcbSEditGH1.removeItemAt(jcbSEditGH1.getSelectedIndex());
    }//GEN-LAST:event_jlDelG4MouseClicked

    private void jlAddG4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlAddG4MouseClicked
        // TODO add your handling code here:
        searchFilterEntreprise("companyAddESA");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card5");
        jLabel9.setText("Administration - Select company");
        jLabel49.setText("Company name");
        jlSearchFilter.setText("Only valid companies are visible");
        viewListState = 12;
    }//GEN-LAST:event_jlAddG4MouseClicked

    private void jcbSEditGH1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSEditGH1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbSEditGH1ActionPerformed

    private void jpMSDefault1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpMSDefault1MouseClicked
        // TODO add your handling code here:
        if (siteEditableAdmin)setEditSiteAdmin();
    }//GEN-LAST:event_jpMSDefault1MouseClicked

    private void jpMSSave1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpMSSave1MouseClicked
        // TODO add your handling code here:
        if (siteEditableAdmin){
            int res = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
            if (res == 0){
                int r = 0;
            try{
                int id = idEditSiteAdmin;
                Connection conn = SingletonConnection.getConnection();
                String req;
                PreparedStatement pstmt;
                if (!jtfMSIDMan1.getText().equals("")){
                    req = "update responsablesite set idsite = null where id = (select id from responsablesite where idsite = ?);";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, id);
                    r = pstmt.executeUpdate();
                    req = "update responsablesite set idsite = (select idsite from site where idsite = ?) where id = ?;";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, id);
                    pstmt.setInt(2, Integer.parseInt(jtfMSIDMan1.getText().split("-", 2)[0]));
                    r = pstmt.executeUpdate();
                }else{
                    req = "update responsablesite set idsite = null where id = (select id from responsablesite where idsite = ?);";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, id);
                    r = pstmt.executeUpdate();
                }
                req = "update guerite set idSite = null where idSite = ?;";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, id);
                r = pstmt.executeUpdate();
                for (int i = 1; i < jcbSEditC1.getItemCount() ; i++){
                    req = "update guerite set idsite = (select idsite from site where idsite = ?) where id = ?;";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, id);
                    pstmt.setInt(2, Integer.parseInt(jcbSEditC1.getItemAt(i).split("-", 2)[0]));
                    r = pstmt.executeUpdate();
                }
                req = "update entreprise set idSite = null where idSite = ?;";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, id);
                r = pstmt.executeUpdate();
                for (int i = 1; i < jcbSEditGH1.getItemCount() ; i++){
                    req = "update entreprise set idsite = (select idsite from site where idsite = ?) where identreprise = ?;";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, id);
                    pstmt.setInt(2, Integer.parseInt(jcbSEditGH1.getItemAt(i).split("-", 2)[0]));
                    r = pstmt.executeUpdate();
                }
            }catch(SQLException e){
                
            }
            }
            
        }
    }//GEN-LAST:event_jpMSSave1MouseClicked

    private void jtfSFNameEdit1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfSFNameEdit1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfSFNameEdit1KeyTyped

    private void jtfSFNameEdit1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfSFNameEdit1KeyReleased
        // TODO add your handling code here:
        displaySitesAdmin();
    }//GEN-LAST:event_jtfSFNameEdit1KeyReleased

    private void jcbCityEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCityEdit1ActionPerformed
        // TODO add your handling code here:
        displaySitesAdmin();
    }//GEN-LAST:event_jcbCityEdit1ActionPerformed

    private void jTable9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable9MouseClicked

    private void jlErrSComp4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlErrSComp4MouseClicked
        // TODO add your handling code here:
        jtfMSIDMan1.setText("");
    }//GEN-LAST:event_jlErrSComp4MouseClicked

    private void jtfFAName7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfFAName7KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfFAName7KeyReleased

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
        // TODO add your handling code here:
        setDashboard("superadmin");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card17");
        jLabel9.setText("Administration - Home");
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jPanel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel13MouseEntered

    private void jPanel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel13MouseExited

    private void jPanel97MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel97MouseClicked
        // TODO add your handling code here:
        setDashboard("utilisateurentreprise");
        CardLayout c = (CardLayout) jPanel3.getLayout();
        c.show(jPanel3, "card16");
        jLabel9.setText("Company user - Create new demand");
    }//GEN-LAST:event_jPanel97MouseClicked

    private void jPanel97MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel97MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel97MouseEntered

    private void jPanel97MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel97MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel97MouseExited
    
    public void deleteDemand(){
        int r = 0;
        try{
            Connection conn = SingletonConnection.getConnection();
            String req = "delete from demande where numDemande = ?;";
            PreparedStatement pstmt = conn.prepareStatement(req);
            pstmt.setInt(1, idDemandEdit);
            r = pstmt.executeUpdate();
        }catch(SQLException e){
            
        }
    }
    
    public void blockEDJcb(JComboBox jcb){
        jcb.setSelectedIndex(0);
        jcb.setEnabled(false);
        jcomboboxDesign(jcb, colorOnSurface_80, color2_80, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDownFaded.png"));
    }
    
    public void allowEDJcb(JComboBox jcb){
        jcb.setEnabled(true);
        jcomboboxDesign(jcb, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDown.png"));
    }
    
    public void blockEDJcb2(JComboBox jcb){
        jcb.setSelectedIndex(0);
        jcb.setEnabled(false);
        jcomboboxDesign2(jcb, colorOnSurface_80, color2_80, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDownFaded.png"));
    }
    
    public void allowEDJcb2(JComboBox jcb){
        jcb.setEnabled(true);
        jcomboboxDesign2(jcb, colorOnSurface, color2_255, new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\dropDownFaded.png"));
    }
    
    public void allowEditDemandForm(){
        allowEDJcb(jcbDaySE);
        allowEDJcb(jcbMonthSE);
        allowEDJcb(jcbYearSE);
        allowEDJcb2(jcbHourSE);
        allowEDJcb2(jcbMinSE);
        
        allowEDJcb(jcbDayEE);
        allowEDJcb(jcbMonthEE);
        allowEDJcb(jcbYearEE);
        allowEDJcb2(jcbHourEE);
        allowEDJcb2(jcbMinEE);
        
        allowTextField(jLabel78, jtfNamePE, true, jlErrP1);
        allowTextField(jLabel80, jtfEmailPE, true, jlErrP2);
        allowTextField(jLabel81, jtfNumVE, true, jlErrP3);
        
        jPanel48.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        jPanel49.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface));
        
        jLabel79.setForeground(colorOnSurface);
        jLabel82.setForeground(colorOnSurface);
        
        jPanel50.setBackground(colorSecondary);
        jPanel50.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel51.setBackground(colorSecondary);
        jPanel51.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel83.setForeground(colorOnSurface);
        jLabel84.setForeground(colorOnSurface);
    }
    
    public void blockEditDemandForm(){
        blockEDJcb(jcbDaySE);
        blockEDJcb(jcbMonthSE);
        blockEDJcb(jcbYearSE);
        blockEDJcb2(jcbHourSE);
        blockEDJcb2(jcbMinSE);
        
        blockEDJcb(jcbDayEE);
        blockEDJcb(jcbMonthEE);
        blockEDJcb(jcbYearEE);
        blockEDJcb2(jcbHourEE);
        blockEDJcb2(jcbMinEE);
        
        blockTextField(jLabel78, jtfNamePE, jlErrP1);
        blockTextField(jLabel80, jtfEmailPE, jlErrP2);
        blockTextField(jLabel81, jtfNumVE, jlErrP3);
        
        jPanel48.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        jPanel49.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, colorOnSurface_80));
        
        jLabel79.setForeground(colorOnSurface_80);
        jLabel82.setForeground(colorOnSurface_80);
        
        jPanel50.setBackground(colorSecondary_80);
        jPanel50.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel51.setBackground(colorSecondary_80);
        jPanel51.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel83.setForeground(colorOnSurface_80);
        jLabel84.setForeground(colorOnSurface_80);
    }
    
    public void allowEditDemand(){
        int row = jTable6.getSelectedRow();
        if (row >= 0){
            idDemandEdit = Integer.parseInt(jTable6.getValueAt(row, 0) + "");
            allowEditDemandForm();
            jtfNamePE.setText(jTable6.getValueAt(row, 1) + "");
            jtfEmailPE.setText(jTable6.getValueAt(row, 2) + "");
            jtfNumVE.setText(jTable6.getValueAt(row, 5) + "");
            jcbDaySE.setSelectedItem((jTable6.getValueAt(row, 3) + "").split(" ", 6)[2]);
            jcbYearSE.setSelectedItem((jTable6.getValueAt(row, 3) + "").split(" ", 6)[5]);
            jcbHourSE.setSelectedItem((jTable6.getValueAt(row, 3) + "").split(" ", 6)[3].split(":", 3)[0]);
            jcbMinSE.setSelectedItem((jTable6.getValueAt(row, 3) + "").split(" ", 6)[3].split(":", 3)[1]);
            jcbDayEE.setSelectedItem((jTable6.getValueAt(row, 4) + "").split(" ", 6)[2]);
            jcbYearEE.setSelectedItem((jTable6.getValueAt(row, 4) + "").split(" ", 6)[5]);
            jcbHourEE.setSelectedItem((jTable6.getValueAt(row, 4) + "").split(" ", 6)[3].split(":", 3)[0]);
            jcbMinEE.setSelectedItem((jTable6.getValueAt(row, 4) + "").split(" ", 6)[3].split(":", 3)[1]);
            String month = (jTable6.getValueAt(row, 4) + "").split(" ", 6)[1];
            switch (month) {
                case "Jan":
                    month = "January";
                    break;
                case "Feb":
                    month = "February";
                    break;
                case "Mar":
                    month = "March";
                    break;
                case "Apr":
                    month = "April";
                    break;
                case "May":
                    month = "May";
                    break;
                case "Jun":
                    month = "June";
                    break;
                case "Jul":
                    month = "July";
                    break;
                case "Aug":
                    month = "August";
                    break;
                case "Sep":
                    month = "September";
                    break;
                case "Oct":
                    month = "October";
                    break;
                case "Nov":
                    month = "November";
                    break;
                default:
                    month = "December";
                    break;
            }
            jcbMonthEE.setSelectedItem(month);
            month = (jTable6.getValueAt(row, 3) + "").split(" ", 6)[1];
            switch (month) {
                case "Jan":
                    month = "January";
                    break;
                case "Feb":
                    month = "February";
                    break;
                case "Mar":
                    month = "March";
                    break;
                case "Apr":
                    month = "April";
                    break;
                case "May":
                    month = "May";
                    break;
                case "Jun":
                    month = "June";
                    break;
                case "Jul":
                    month = "July";
                    break;
                case "Aug":
                    month = "August";
                    break;
                case "Sep":
                    month = "September";
                    break;
                case "Oct":
                    month = "October";
                    break;
                case "Nov":
                    month = "November";
                    break;
                default:
                    month = "December";
                    break;
            }
            jcbMonthSE.setSelectedItem(month);
        }
    }
    
    public boolean checkDateForm(JComboBox jcb1, JComboBox jcb2, JComboBox jcb3){
        if ((jcb1.getSelectedItem() + "").equals("--Day--") || (jcb2.getSelectedItem() + "").equals("--Month--") || (jcb3.getSelectedItem() + "").equals("--Year--")) return false;
        return true;
    }
    
    public int getMonth(String month){
        switch (month) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            default:
                break;
        }
        return 12;
    }
    
    public int getDayCalendar(JComboBox jcb){
        String month = jcb.getSelectedItem() + "";
        if (!month.equals("--Month--")){
            if (month.equals("February")) return 28;
            if (month.equals("January") || month.equals("March") || month.equals("May") || month.equals("July") || month.equals("August") || month.equals("October") || month.equals("December")) return 31;
            return 30;
        }
        return 0;
    }
    
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String TEL_PATTERN = "\\d{10}";
    public boolean isTFValid(JTextField jtf, String type){
        if (type.equals("normal") && jtf.getText().equals(""))return false;
        if (type.equals("email") && !jtf.getText().matches(EMAIL_PATTERN)) return false;
        else{
            try{
                Connection conn = SingletonConnection.getConnection();
                String req = "select email from compte;";
                PreparedStatement pstmt = conn.prepareStatement(req);
                ResultSet rs = pstmt.executeQuery();
                while(rs.next()){
                    if (jtf.getText().equals(rs.getString("email"))){
                        JOptionPane.showMessageDialog(null, "alert", "email double", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }catch(SQLException e){
                
            }
        }
        if (type.equals("tel") && !jtf.getText().matches(TEL_PATTERN)) return false;
        return true;
    }
    
    public void removeErrIcon(String type){
        if (type.equals("signup")){
            jlErrName.setIcon(null);
            jlErrFName.setIcon(null);
            jlErrEmail.setIcon(null);
            jlErrTel.setIcon(null);
            jlErrB.setIcon(null);
            jlErrPos.setIcon(null);
            jlErrCIN.setIcon(null);
        }else if (type.equals("addsite")){
            jlErrSName.setIcon(null);
            jlErrSCity.setIcon(null);
        }else if (type.equals("createDemand")){
            jlErrNumV.setIcon(null);
            jlErrSD.setIcon(null);
            jlErrED.setIcon(null);
            jlErrP.setIcon(null);
            jlErrEmailP.setIcon(null);
            jlAlertED.setText("");
        }else if (type.equals("createDemandED")){
            jlErrP1.setIcon(null);
            jlErrP2.setIcon(null);
            jlErrP3.setIcon(null);
            jlErrSD1.setIcon(null);
            jlErrSD2.setIcon(null);
            jlAlertED2.setText("");
        }
        
    }
    
    public void resetFormTF(String type){
        if (type.equals("signup")){
            jtfName.setText("");
            jtfFName.setText("");
            jtfEmail.setText("");
            jtfTel.setText("");
            jtfB.setText("");
            jtfPos.setText("");
            jtfCIN.setText("");
            jrbCU.doClick();
        }else if (type.equals("addsite")){
            jtfSName.setText("");
            jtfSCity.setText("");
        }
    }
    
    public void siteManagerSelect(String type){
        Vector<Object> rows;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Object> cols = new Vector<Object>();
        cols.add("ID");
        cols.add("First name");
        cols.add("Family name");
        try{
            String req;
            Connection conn = SingletonConnection.getConnection();
            if (type.equals("admin")){
                req = "select * from compte natural join admin;";
            }else if (type.equals("responsablesite")){
                req = "select * from compte natural join responsablesite where idSite is null;";
            }else{
                req = "select * from compte natural join guerite where idSite is null;";
            }
            PreparedStatement pstmt = conn.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                rows = new Vector<Object>();
                rows.add(rs.getInt("id"));
                rows.add(rs.getString("nom"));
                rows.add(rs.getString("prenom"));
                data.add(rows);
            }
            jTable2.setModel(new DefaultTableModel(data, cols));
        }catch(SQLException e){
            System.err.println("ERR");
        }
    }
    
    public void searchFilterEntreprise(String type){
        boolean flag;
        Vector<Object> rows;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Object> cols = new Vector<>();
        cols.add("ID");
        cols.add("Name");
        cols.add("Site");
        try{
            Connection conn = SingletonConnection.getConnection();
            String req = "";
            if (type.equals("entreprise")){
                req = "select * from entreprise natural join site where nom like '%" + jtfSearchFilterRS.getText() + "%' or nomSite like '%" + jtfSearchFilterRS.getText() + "%' and idSite is not null;";
            }else if (type.equals("companyAdd") || type.equals("companyAddC")){
                req = "select * from entreprise join site where nom like '%" + jtfSearchFilterRS.getText() + "%' or nomSite like '%" + jtfSearchFilterRS.getText() + "%' group by idEntreprise;";
            }else if (type.equals("companyAddRS") || type.equals("companyAddESA")){
                req = "select idEntreprise, entreprise.idSite, nom, nomSite, ville from entreprise join site where ((entreprise.idSite = ? and site.idSite = entreprise.idSite) or entreprise.idsite is null) and (nom like '%" + jtfSearchFilterRS.getText() + "%' or nomSite like '%" + jtfSearchFilterRS.getText() + "%') group by entreprise.idEntreprise;";
            }
            PreparedStatement pstmt = conn.prepareStatement(req);
            System.out.println(idSite);
            if (type.equals("companyAddRS"))
                pstmt.setInt(1, idSite);
            if (type.equals("companyAddESA"))
                pstmt.setInt(1, idEditSiteAdmin);
            ResultSet rs = pstmt.executeQuery();
            if (type.equals("entreprise")){
                while (rs.next()){
                    rows = new Vector<Object>();
                    rows.add(rs.getInt("idEntreprise"));
                    rows.add(rs.getString("nom"));
                    rows.add(rs.getString("nomSite"));
                    data.add(rows);
                }
            }else if (type.equals("companyAdd")){
                flag = true;
                while (rs.next()){
                    for (int i = 1; i < jcbSEditC.getItemCount(); i++){
                        if (rs.getInt("identreprise") == Integer.parseInt(jcbSEditC.getItemAt(i).split("-", 2)[0])){
                            flag = false;
                            break;
                        }
                    }
                    if (flag){
                        rows = new Vector<Object>();
                        rows.add(rs.getInt("idEntreprise"));
                        rows.add(rs.getString("nom"));
                        rows.add(rs.getString("nomSite"));
                        data.add(rows);
                    }
                    flag = true;
                }
            }else if (type.equals("companyAddC")){
                flag = true;
                while (rs.next()){
                    for (int i = 1; i < jcbSComp.getItemCount(); i++){
                        if (rs.getInt("identreprise") == Integer.parseInt(jcbSComp.getItemAt(i).split("-", 2)[0])){
                            flag = false;
                            break;
                        }
                    }
                    if (flag){
                        rows = new Vector<Object>();
                        rows.add(rs.getInt("idEntreprise"));
                        rows.add(rs.getString("nom"));
                        rows.add(rs.getString("nomSite"));
                        data.add(rows);
                    }
                    flag = true;
                }
            }else if (type.equals("companyAddRS")){
                flag = true;
                while (rs.next()){
                    for (int i = 1; i < jcbSComp1.getItemCount(); i++){
                        if (rs.getInt("identreprise") == Integer.parseInt(jcbSComp1.getItemAt(i).split("-", 2)[0])){
                            flag = false;
                            break;
                        }
                    }
                    if (flag){
                        rows = new Vector<Object>();
                        rows.add(rs.getInt("idEntreprise"));
                        rows.add(rs.getString("nom"));
                        rows.add(rs.getString("nomSite"));
                        data.add(rows);
                    }
                    flag = true;
                }
            }else if (type.equals("companyAddESA")){
                flag = true;
                while (rs.next()){
                    for (int i = 1; i < jcbSEditGH1.getItemCount(); i++){
                        if (rs.getInt("identreprise") == Integer.parseInt(jcbSEditGH1.getItemAt(i).split("-", 2)[0])){
                            flag = false;
                            break;
                        }
                    }
                    if (flag){
                        rows = new Vector<Object>();
                        rows.add(rs.getInt("idEntreprise"));
                        rows.add(rs.getString("nom"));
                        rows.add(rs.getString("nomSite"));
                        data.add(rows);
                    }
                    flag = true;
                }
            }
            DefaultTableModel model = new DefaultTableModel(data, cols);
            jTable2.setModel(model);
            jtableDesign(jTable2, jScrollPane2);
        }catch(SQLException e){
            
        }
    }
    
    public void searchFilter(String type){
        Vector<Object> rows;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Object> cols = new Vector<Object>();
        cols.add("ID");
        cols.add("First name");
        cols.add("Family name");
        if (type.equals("all")){
            cols.add("Email");
        }
        
        try{
            String req = "";
            Connection conn = SingletonConnection.getConnection();
            if (type.equals("all")){
                req = "select * from compte where (nom like ? or prenom like ?);";
            }
            else if (type.equals("admin")){
                req = "select * from compte natural join admin where (nom like ? or prenom like ?);";
            }else if (type.equals("responsablesite")){
                req = "select * from compte natural join responsablesite where (nom like ? or prenom like ?);";
            }else if (type.equals("guerite")){
                req = "select * from compte natural join guerite where (nom like ? or prenom like ?);";
            }else if (type.equals("gueriteAdd") || type.equals("gueriteAddC")){
                req = "select * from compte natural join guerite where (nom like ? or prenom like ?);";
            }else if (type.equals("rsAddC")){
                req = "select * from compte natural join guerite where (nom like ? or prenom like ?) and (idSite is null or idSite = ?);";
            }else if (type.equals("responsablesiteESA")){
                req = "select * from compte natural join responsablesite where (nom like ? or prenom like ?) and (idSite is null or idSite = " + idEditSiteAdmin +");";
            }else if (type.equals("gueriteAddESA")){
                req = "select * from compte natural join guerite where (nom like ? or prenom like ?) and (idSite is null or idSite = " + idEditSiteAdmin +");";
            }
            PreparedStatement pstmt = conn.prepareStatement(req);
            pstmt.setString(1, "%" + jtfSearchFilterRS.getText() + "%");
            pstmt.setString(2, "%" + jtfSearchFilterRS.getText() + "%");
            if (type.equals("rsAddC")) pstmt.setInt(3, idSite);
            ResultSet rs = pstmt.executeQuery();
            if (type.equals("all")){
                while (rs.next()){
                    if (rs.getInt("statutC") == 1){
                        rows = new Vector<Object>();
                        rows.add(rs.getInt("id"));
                        rows.add(rs.getString("nom"));
                        rows.add(rs.getString("prenom"));
                        rows.add(rs.getString("email"));
                        data.add(rows);
                    }
                    
                }
            }else if (type.equals("gueriteAdd")){
                boolean flag = true;
                while (rs.next()){
                    for (int i = 1; i < jcbSEditGH.getItemCount(); i++){
                        if (rs.getInt("id") == Integer.parseInt(jcbSEditGH.getItemAt(i).split("-", 2)[0])){
                            flag = false;
                            break;
                        }
                    }
                    if (flag){
                        rows = new Vector<Object>();
                        rows.add(rs.getInt("id"));
                        rows.add(rs.getString("nom"));
                        rows.add(rs.getString("prenom"));
                        data.add(rows);
                    }
                    flag = true;
                }
            }else if (type.equals("gueriteAddC")){
                boolean flag = true;
                while (rs.next()){
                    for (int i = 1; i < jcbSG.getItemCount(); i++){
                        if (rs.getInt("id") == Integer.parseInt(jcbSG.getItemAt(i).split("-", 2)[0])){
                            flag = false;
                            break;
                        }
                    }
                    if (flag){
                        rows = new Vector<Object>();
                        rows.add(rs.getInt("id"));
                        rows.add(rs.getString("nom"));
                        rows.add(rs.getString("prenom"));
                        data.add(rows);
                    }
                    flag = true;
                }
            }else if (type.equals("rsAddC")){
                boolean flag = true;
                while (rs.next()){
                    for (int i = 1; i < jcbSG1.getItemCount(); i++){
                        if (rs.getInt("id") == Integer.parseInt(jcbSG1.getItemAt(i).split("-", 2)[0])){
                            flag = false;
                            break;
                        }
                    }
                    if (flag){
                        rows = new Vector<Object>();
                        rows.add(rs.getInt("id"));
                        rows.add(rs.getString("nom"));
                        rows.add(rs.getString("prenom"));
                        data.add(rows);
                    }
                    flag = true;
                }
            }else if (type.equals("gueriteAddESA")){
                boolean flag = true;
                while (rs.next()){
                    for (int i = 1; i < jcbSEditC1.getItemCount(); i++){
                        if (rs.getInt("id") == Integer.parseInt(jcbSEditC1.getItemAt(i).split("-", 2)[0])){
                            flag = false;
                            break;
                        }
                    }
                    if (flag){
                        rows = new Vector<Object>();
                        rows.add(rs.getInt("id"));
                        rows.add(rs.getString("nom"));
                        rows.add(rs.getString("prenom"));
                        data.add(rows);
                    }
                    flag = true;
                }
            }else{
                while (rs.next()){
                    rows = new Vector<Object>();
                    rows.add(rs.getInt("id"));
                    rows.add(rs.getString("nom"));
                    rows.add(rs.getString("prenom"));
                    data.add(rows);
                }
            }
            
            jTable2.setModel(new DefaultTableModel(data, cols));
            jtableDesign(jTable2, jScrollPane2);
        }catch(SQLException e){
            
        }
    }
    
    public void init_sitesConsult(String request){
        Vector<Object> rows;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Object> cols = new Vector<Object>();
        cols.add("ID");
        cols.add("Name");
        cols.add("City");
        cols.add("Admin's ID");
        cols.add("Site manager's ID");
        
        try{
            Connection conn = SingletonConnection.getConnection();
            String req = request;
            PreparedStatement pstmt = conn.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            ResultSet rs2;
            while (rs.next()){
                rows = new Vector<Object>();
                rows.add(rs.getInt("idSite"));
                rows.add(rs.getString("nomsite"));
                rows.add(rs.getString("ville"));
                if (rs.getInt("id") != 0)rows.add(rs.getInt("id"));
                else rows.add("None");
                req = "select id from responsablesite where idsite = ?;";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, rs.getInt("idSite"));
                rs2 = pstmt.executeQuery();
                if (rs2.next()) rows.add(rs2.getInt("id"));
                else rows.add("None");
                data.add(rows);
            }
            DefaultTableModel model = new DefaultTableModel(data, cols);
            jTable3.setModel(model);
        }catch(SQLException e){
            System.err.println("ERR");
        }
    }
    
    public LinkedList<Compte> init_ADUserAccounts(String request){
        LinkedList<Compte> listeC = new LinkedList<Compte>();
        try{
            Connection conn = SingletonConnection.getConnection();
            String req = request;
            PreparedStatement pstmt = conn.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                boolean statutC;
                if (rs.getInt("statutC") == 0) statutC = false;
                else statutC = true;
                listeC.add(new Compte(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("poste"), statutC));
            }
        }catch(SQLException e){
            System.err.println("ERR");
        }
        return listeC;
    }
    
    public void displaySites(LinkedList<Site> listeS, int begin, int num){
        Vector<Object> rows;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Object> cols = new Vector<Object>();
        cols.add("ID");
        cols.add("Name");
        cols.add("City");
        cols.add("Delete");
        cols.add("Edit");
        
        if (begin == 0){
            jlPreviousES.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            jlPreviousES.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\leftArrowFaded.png"));
        }else{
            jlPreviousES.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jlPreviousES.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\leftArrow.png"));
        }
        
        int end;
        if (begin + num < listeS.size()){
            end = begin + num;
            jlNextES.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jlNextES.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\rightArrow.png"));
        }
        else{
            end = listeS.size();
            jlNextES.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            jlNextES.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\rightArrowFaded.png"));
        }
        
        jlDisplayXES.setText((begin + 1) + "");
        jlDisplayYES.setText(end + "");
        jlDisplayMaxES.setText("from " + listeS.size() + "");
        
        
        for (int i = begin; i < end; i++){
            rows = new Vector<Object>();
            rows.add(listeS.get(i).idSite);
            rows.add(listeS.get(i).nomSite);
            rows.add(listeS.get(i).ville);
            rows.add("delete");
            rows.add("edit");
            data.add(rows);
        }
        
        DefaultTableModel model = new DefaultTableModel(data, cols);
        jTable4.setModel(model);
        jtableEditSitesRestyle();
    }
    
    public void displayADUserAccounts(LinkedList<Compte> listeC, int begin, int num){
        Vector<Object> rows;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Object> cols = new Vector<Object>();
        cols.add("ID");
        cols.add("Fisrt name");
        cols.add("Family name");
        cols.add("Position");
        cols.add("");
        cols.add(" ");
        
        if (begin == 0){
            jlPrevious1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            jlPrevious1.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\leftArrowFaded.png"));
        }else{
            jlPrevious1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jlPrevious1.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\leftArrow.png"));
        }
        
        int end;
        if (begin + num <listeC.size()){
            end = begin + num;
            jlNext1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jlNext1.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\rightArrow.png"));
        }
        else{
            end = listeC.size();
            jlNext1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            jlNext1.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\rightArrowFaded.png"));
        }
        
        jlDisplayX1.setText((begin + 1) + "");
        jlDisplayY1.setText(end + "");
        LinkedList<String> inactiveList = new LinkedList<String>();
        for (int i = begin; i < end; i++){
            rows = new Vector<Object>();
            rows.add(listeC.get(i).id);
            rows.add(listeC.get(i).nom);
            rows.add(listeC.get(i).prenom);
            rows.add(listeC.get(i).poste);
            rows.add("Active");
            rows.add("Inactive");
            if (!listeC.get(i).statutC) inactiveList.add(i + "");
            data.add(rows);
        }
        
        DefaultTableModel model = new DefaultTableModel(data, cols);
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                boolean flag = false;
                for (int i = 0; i < inactiveList.size(); i++){
                    if (Integer.parseInt(inactiveList.get(i)) == row){
                        flag = true;
                        break;
                    }
                }
                if (flag) c.setBackground(new Color(179, 0, 0, 30));
                else c.setBackground(table.getBackground());
                if (isSelected)c.setBackground(colorSecondary_80);
                return c;
            }
        });
        jTable1.setModel(model);
        jtableDesign(jTable1, jScrollPane1);
        jTable1.getColumn("ID").setMaxWidth(50);
        jTable1.getColumn("ID").setMinWidth(50);
        jTable1.getColumn("").setMaxWidth(35);
        jTable1.getColumn("").setMinWidth(35);
        jTable1.getColumn(" ").setMaxWidth(35);
        jTable1.getColumn(" ").setMinWidth(35);
    }
    
    public void activateAccount(){
        int id = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "");
        if (!(jTable1.getValueAt(jTable1.getSelectedRow(), 3) + "").equals("superadmin")){
            int r = 0;
            try{
                Connection conn = SingletonConnection.getConnection();
                String req = "update compte set statutC = 1 where id = ?;";
                PreparedStatement pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, id);
                r= pstmt.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        
    }
    
    public void deactivateAccount(){
        int id = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "");
        if (!(jTable1.getValueAt(jTable1.getSelectedRow(), 3) + "").equals("superadmin")){
            int r = 0;
            try{
                Connection conn = SingletonConnection.getConnection();
                String req = "update compte set statutC = 0 where id = ?;";
                PreparedStatement pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, id);
                r= pstmt.executeUpdate();
            }catch(SQLException e){

            }
        }
        
    }
    
    public void init_filter1SiteConsult(JComboBox jcb){
        jcb.removeAllItems();
        jcb.addItem("--City--");
        try{
            Connection conn = SingletonConnection.getConnection();
            String req = "select DISTINCT ville from site;";
            PreparedStatement pstmt = conn.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                jcb.addItem(rs.getString("ville"));
            }
        }catch (SQLException e){
            System.err.println("ERR");
        }
    }
    
    public void init_filter2SiteConsult(JComboBox jcb){
        jcb.removeAllItems();
        jcb.addItem("--Company--");
        try{
            Connection conn = SingletonConnection.getConnection();
            String req = "select * from entreprise;";
            PreparedStatement pstmt = conn.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                jcb.addItem(rs.getString("nom"));
            }
        }catch (SQLException e){
            System.err.println("ERR");
        }
    }
    
    public String searchFilterSite(JComboBox jcb1, JComboBox jcb2, JTextField jtf){
        String city = jcb1.getSelectedItem() + "";
        String company = jcb2.getSelectedItem() + "";
        String req;
        if (city.equals("--City--")){
            if (company.equals("--Company--")){
                return "select * from site where nomsite like '%" + jtf.getText() + "%';";
            }else{
                return "select * from site natural join entreprise where (entreprise.nom = '" + company + "' and nomsite like '%" + jtf.getText() + "%') group by site.nomSite;";
            }
        }else{
            if (company.equals("--Company--")){
                return "select * from site where ville = '" + city + "' and nomsite like '%" + jtf.getText() + "%';";
            }else{
                return "select * from site natural join entreprise where (entreprise.nom = '" + company + "' and ville = '" + city + "' and nomsite like '%" + jtf.getText() + "%') group by site.nomSite;";
            }
        }
    }
    
    public String searchFilterAccount(JComboBox jcb1, JComboBox jcb2, JTextField jtf){
        String position;
        if ((jcb1.getSelectedItem() + "").equals("Admin")) position = "admin";
        else if ((jcb1.getSelectedItem() + "").equals("Site manager")) position = "responsablesite";
        else if ((jcb1.getSelectedItem() + "").equals("Gatehouse")) position = "guerite";
        else if ((jcb1.getSelectedItem() + "").equals("Company user")) position = "utilisateurentreprise";
        else position = jcb1.getSelectedItem() + "";
        String state;
        if ((jcb2.getSelectedItem() + "").equals("Activated")) state = "1";
        else if ((jcb2.getSelectedItem() + "").equals("Deactivated")) state = "0";
        else state = jcb2.getSelectedItem() + "";
        
        if (position.equals("--Position--")){
            if (state.equals("--State--")){
                return "select * from compte where (nom like '%" + jtf.getText() + "%' or prenom like '%" + jtf.getText() + "%');";
            }else{
                return "select * from compte where statutC = " + state + " and (nom like '%" + jtf.getText() + "%' or prenom like '%" + jtf.getText() + "%');";
            }
        }else{
            if (state.equals("--State--")){
                return "select * from compte where poste = '" + position + "' and (nom like '%" + jtf.getText() + "%' or prenom like '%" + jtf.getText() + "%');";
            }else{
                return "select * from compte where statutC = " + state + " and poste = '" + position + "' and (nom like '%" + jtf.getText() + "%' or prenom like '%" + jtf.getText() + "%');";
            }
        }
    }
    
    public LinkedList<Site> getSiteList(String req){
        LinkedList<Site> siteList = new LinkedList<Site>();
        try{
            Connection conn = SingletonConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                siteList.add(new Site(rs.getInt("idSite"), rs.getString("nomSite"), rs.getString("ville"), rs.getInt("id")));
            }
        }catch(SQLException e){
            
        }
        return siteList;
    }
    
    public void consultEditDeleteSite(String req){
        Vector<Object> rows;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Object> cols = new Vector<Object>();
        cols.add("ID");
        cols.add("Name");
        cols.add("City");
        cols.add("Delete");
        cols.add("Edit");
        try{
            Connection conn = SingletonConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                rows = new Vector<Object>();
                rows.add(rs.getInt("idsite"));
                rows.add(rs.getString("nomsite"));
                rows.add(rs.getString("ville"));
                rows.add("delete");
                rows.add("edit");
                data.add(rows);
            }
            jTable4.setModel(new DefaultTableModel(data, cols));
        }catch(SQLException e){
            System.err.println("ERR");
        }
    }
    
    public void jtableEditSitesRestyle(){
        jtableDesign(jTable4, jScrollPane4);
        jTable4.getColumn("Delete").setMaxWidth(50);
        jTable4.getColumn("Delete").setMinWidth(50);
        jTable4.getColumn("ID").setMaxWidth(40);
        jTable4.getColumn("ID").setMinWidth(40);
        jTable4.getColumn("Edit").setMaxWidth(50);
        jTable4.getColumn("Edit").setMinWidth(50);
        jTable4.getColumn("Delete").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\delete.png")));
        jTable4.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\delete.png")));
        jTable4.getColumn("Edit").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\edit.png")));
        jTable4.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\edit.png")));
    }
    
    public void blockSitesEdit(){
        siteEditable = false;
        blockTextField(jlMSIDAdmin, jtfMSIDAdmin, jlMSVL);
        blockTextField(jlMSIDMan, jtfMSIDMan, jlMSVL1);
        jpMSSave.setBackground(color2_80);
        jlMSSave.setForeground(colorOnSurface_80);
        jpMSSave.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpMSDefault.setBackground(color2_80);
        jlMSDefault.setForeground(colorOnSurface_80);
        jpMSDefault.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        blockJCB(jlMSIDGh, jcbSEditGH, jlMSIDGh);
        blockLabel(jlAddG);
        blockLabel(jlDelG);
        blockJCB(jlMSIDC, jcbSEditC, jlMSIDC);
        blockLabel(jlAddC);
        blockLabel(jlDelC);
    }
    
    public static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static SecureRandom rnd = new SecureRandom();

    String randomString(int len){
       StringBuilder sb = new StringBuilder(len);
       for(int i = 0; i < len; i++)
          sb.append(AB.charAt(rnd.nextInt(AB.length())));
       return sb.toString();
    }
    
    public LinkedList<Demande> getDemandList(String req, String type) throws ParseException{
        LinkedList<Demande> demandList = new LinkedList<Demande>();
        
        try{
            Connection conn = SingletonConnection.getConnection();
            //String req = "select * from demande where id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(req);
            if (type.equals("ue")) pstmt.setInt(1, idConnected);
            else pstmt.setInt(1, idSite);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                demandList.add(new Demande(rs.getInt("numDemande"), rs.getInt("id"), rs.getString("nomC") + " " + rs.getString("prenomC"), rs.getString("nomC"), rs.getString("prenomC"), rs.getString("cnie"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("datevisite")), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("datefinvisite")), rs.getString("personne"), rs.getString("email"), rs.getInt("nbrPersonnes"), rs.getInt("statutD")));
            }
        }catch(SQLException e){
            
        }
        return demandList;
    }
    
    public void displayDemands(LinkedList<Demande> demandList, int begin, int num){
        try{
        Vector<Object> rows;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Object> cols = new Vector<Object>();
        cols.add("ID");
        cols.add("Person to see");
        cols.add("Email");
        cols.add("Start date");
        cols.add("End date");
        cols.add("Visitors");
        
        if (begin == 0){
            jlPrevious2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            jlPrevious2.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\leftArrowFaded.png"));
        }else{
            jlPrevious2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jlPrevious2.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\leftArrow.png"));
        }
        
        int end;
        if (begin + num <demandList.size()){
            end = begin + num;
            jlNext2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jlNext2.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\rightArrow.png"));
        }
        else{
            end = demandList.size();
            jlNext2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            jlNext2.setIcon(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\rightArrowFaded.png"));
        }
        
        jlDisplayX2.setText((begin + 1) + "");
        jlDisplayY2.setText(end + "");
        
        LinkedList<String> acceptedList = new LinkedList<String>();
        LinkedList<String> refusedList = new LinkedList<String>();
        
        for (int i = begin; i < end; i++){
            rows = new Vector<Object>();
            rows.add(demandList.get(i).numDemande);
            rows.add(demandList.get(i).personne);
            rows.add(demandList.get(i).email);
            rows.add(demandList.get(i).dateVisite);
            rows.add(demandList.get(i).dateFinVisite);
            rows.add(demandList.get(i).nbrPersonnes);
            data.add(rows);
            if (demandList.get(i).statutD == 0) refusedList.add(i + "");
            else if (demandList.get(i).statutD == 1 || demandList.get(i).statutD == 3) acceptedList.add(i + "");
        }
        
        DefaultTableModel model = new DefaultTableModel(data, cols);
        jTable5.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int flag = 2;
                for (int i = 0; i < refusedList.size(); i++){
                    if (Integer.parseInt(refusedList.get(i)) == row){
                        flag = 0;
                        break;
                    }
                }
                for (int i = 0; i < acceptedList.size(); i++){
                    if (Integer.parseInt(acceptedList.get(i)) == row){
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) c.setBackground(new Color(179, 0, 0, 40));
                else if (flag == 1) c.setBackground(new Color(0, 179, 0, 40));
                else c.setBackground(new Color(255, 143, 0, 40));
                //if (isSelected)c.setBackground(colorSecondary_80);
                return c;
            }
        });
        jTable5.setModel(model);
        jtableDesign(jTable5, jScrollPane5);
        jTable5.getColumn("ID").setMaxWidth(40);
        jTable5.getColumn("ID").setMinWidth(40);
        jTable5.getColumn("Visitors").setMaxWidth(60);
        jTable5.getColumn("Visitors").setMinWidth(60);
        }catch(Exception e){
            
        }
        
    }
    
    public void displayDemandsEdit(){
        try{
        LinkedList<Demande> demandList = getDemandList("select * from demande where id = ? and personne like '%" + jtfFAName2.getText()+  "%';", "ue");
        Vector<Object> rows;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Object> cols = new Vector<Object>();
        cols.add("ID");
        cols.add("Person to see");
        cols.add("Email");
        cols.add("Start date");
        cols.add("End date");
        cols.add("Visitors");
        cols.add("Edit");
        cols.add("Cancel");
         
        for (int i = 0; i < demandList.size(); i++){
            if (demandList.get(i).statutD == 2){
                rows = new Vector<Object>();
                rows.add(demandList.get(i).numDemande);
                rows.add(demandList.get(i).personne);
                rows.add(demandList.get(i).email);
                rows.add(demandList.get(i).dateVisite);
                rows.add(demandList.get(i).dateFinVisite);
                rows.add(demandList.get(i).nbrPersonnes);
                rows.add("editD");
                rows.add("cancelD");
                data.add(rows);
            }
            
        }
        
        DefaultTableModel model = new DefaultTableModel(data, cols);
        
        jTable6.setModel(model);
        jtableDesign(jTable6, jScrollPane6);
        jTable6.getColumn("ID").setMaxWidth(40);
        jTable6.getColumn("ID").setMinWidth(40);
        jTable6.getColumn("Visitors").setMaxWidth(60);
        jTable6.getColumn("Visitors").setMinWidth(60);
        jTable6.getColumn("Cancel").setMaxWidth(50);
        jTable6.getColumn("Cancel").setMinWidth(50);
        jTable6.getColumn("Edit").setMaxWidth(40);
        jTable6.getColumn("Edit").setMinWidth(40);
        jTable6.getColumn("Cancel").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\delete.png")));
        jTable6.getColumn("Cancel").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\delete.png")));
        jTable6.getColumn("Edit").setCellRenderer(new ButtonRenderer(new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\edit.png")));
        jTable6.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), new ImageIcon("C:\\Users\\PC GAMER\\Desktop\\icons\\edit.png")));
        }catch(Exception e){
        }
        
    }
    
    public void setDashboard(String type){
        try{
            Connection conn = SingletonConnection.getConnection();
            String  req;
            PreparedStatement pstmt;
            ResultSet rs;
            if (type.equals("utilisateurentreprise")){
                req = "select count(numDemande) as c from demande where id = ?;";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, idConnected);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel107.setText(rs.getInt("c") + "");
                req = "select count(numDemande) as c from demande where id = ? and (statutD = 1 or statutD = 3);";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, idConnected);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel129.setText(rs.getInt("c") + "");
                req = "select count(numDemande) as c from demande where id = ? and (statutD = 2);";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, idConnected);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel134.setText(rs.getInt("c") + "");
                req = "select count(numDemande) as c from demande where id = ? and (statutD = 0);";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, idConnected);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel110.setText(rs.getInt("c") + "");
            }if (type.equals("guerite") || type.equals("responsablesite")){
                req = "select count(numDemande) as c from demande natural join entreprise where idsite = ?;";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, idSite);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel107.setText(rs.getInt("c") + "");
                req = "select count(numDemande) as c from demande natural join entreprise where idsite = ? and (statutD = 1 or statutD = 3);";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, idSite);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel129.setText(rs.getInt("c") + "");
                req = "select count(numDemande) as c from demande natural join entreprise where idsite = ? and (statutD = 2);";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, idSite);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel134.setText(rs.getInt("c") + "");
                req = "select count(numDemande) as c from demande natural join entreprise where idsite = ? and (statutD = 0);";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, idSite);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel110.setText(rs.getInt("c") + "");
            }if (type.equals("admin")){
                req = "select count(idsite) as c from site where id = ?;";
                pstmt = conn.prepareStatement(req);
                pstmt.setInt(1, idConnected);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel139.setText(rs.getInt("c") + "");
                int count = 0;
                for (String s : idsSite){
                    req = "select count(id) as c from guerite where idsite = ?;";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, Integer.parseInt(s));
                    rs = pstmt.executeQuery();
                    rs.next();
                    count += rs.getInt("c");
                }
                jLabel146.setText(count + "");
                count = 0;
                for (String s : idsSite){
                    req = "select count(id) as c from responsablesite where idsite = ?;";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, Integer.parseInt(s));
                    rs = pstmt.executeQuery();
                    rs.next();
                    count += rs.getInt("c");
                }
                jLabel149.setText(count + "");
                count = 0;
                for (String s : idsSite){
                    req = "select count(numDemande) as c from demande natural join entreprise where idsite = ?;";
                    pstmt = conn.prepareStatement(req);
                    pstmt.setInt(1, Integer.parseInt(s));
                    rs = pstmt.executeQuery();
                    rs.next();
                    count += rs.getInt("c");
                }
                jLabel143.setText(count + "");
            }if (type.equals("superadmin")){
                req = "select count(idsite) as c from site;";
                pstmt = conn.prepareStatement(req);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel139.setText(rs.getInt("c") + "");
                req = "select count(id) as c from guerite;";
                pstmt = conn.prepareStatement(req);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel146.setText(rs.getInt("c") + "");
                req = "select count(id) as c from responsablesite;";
                pstmt = conn.prepareStatement(req);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel149.setText(rs.getInt("c") + "");
                req = "select count(numDemande) as c from demande;";
                pstmt = conn.prepareStatement(req);
                rs = pstmt.executeQuery();
                rs.next();
                jLabel143.setText(rs.getInt("c") + "");
            }
        }catch(SQLException e){
            
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel80;
    private javax.swing.JPanel jPanel81;
    private javax.swing.JPanel jPanel82;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel88;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JPanel jPanel93;
    private javax.swing.JPanel jPanel94;
    private javax.swing.JPanel jPanel95;
    private javax.swing.JPanel jPanel96;
    private javax.swing.JPanel jPanel97;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
    private javax.swing.JComboBox<String> jcbCity;
    private javax.swing.JComboBox<String> jcbCityEdit;
    private javax.swing.JComboBox<String> jcbCityEdit1;
    private javax.swing.JComboBox<String> jcbComp;
    private javax.swing.JComboBox<String> jcbCompEdit;
    private javax.swing.JComboBox<String> jcbDayE;
    private javax.swing.JComboBox<String> jcbDayEE;
    private javax.swing.JComboBox<String> jcbDayS;
    private javax.swing.JComboBox<String> jcbDaySE;
    private javax.swing.JComboBox<String> jcbFAPos;
    private javax.swing.JComboBox<String> jcbFAPos2;
    private javax.swing.JComboBox<String> jcbFAState;
    private javax.swing.JComboBox<String> jcbHourE;
    private javax.swing.JComboBox<String> jcbHourEE;
    private javax.swing.JComboBox<String> jcbHourS;
    private javax.swing.JComboBox<String> jcbHourSE;
    private javax.swing.JComboBox<String> jcbMinE;
    private javax.swing.JComboBox<String> jcbMinEE;
    private javax.swing.JComboBox<String> jcbMinS;
    private javax.swing.JComboBox<String> jcbMinSE;
    private javax.swing.JComboBox<String> jcbMonthE;
    private javax.swing.JComboBox<String> jcbMonthEE;
    private javax.swing.JComboBox<String> jcbMonthS;
    private javax.swing.JComboBox<String> jcbMonthSE;
    private javax.swing.JComboBox<String> jcbNum1;
    private javax.swing.JComboBox<String> jcbNum2;
    private javax.swing.JComboBox<String> jcbNumES;
    private javax.swing.JComboBox<String> jcbSComp;
    private javax.swing.JComboBox<String> jcbSComp1;
    private javax.swing.JComboBox<String> jcbSEditC;
    private javax.swing.JComboBox<String> jcbSEditC1;
    private javax.swing.JComboBox<String> jcbSEditGH;
    private javax.swing.JComboBox<String> jcbSEditGH1;
    private javax.swing.JComboBox<String> jcbSG;
    private javax.swing.JComboBox<String> jcbSG1;
    private javax.swing.JComboBox<String> jcbYearE;
    private javax.swing.JComboBox<String> jcbYearEE;
    private javax.swing.JComboBox<String> jcbYearS;
    private javax.swing.JComboBox<String> jcbYearSE;
    private javax.swing.JLabel jlAddC;
    private javax.swing.JLabel jlAddC1;
    private javax.swing.JLabel jlAddC2;
    private javax.swing.JLabel jlAddC4;
    private javax.swing.JLabel jlAddG;
    private javax.swing.JLabel jlAddG1;
    private javax.swing.JLabel jlAddG2;
    private javax.swing.JLabel jlAddG4;
    private javax.swing.JLabel jlAlertED;
    private javax.swing.JLabel jlAlertED1;
    private javax.swing.JLabel jlAlertED2;
    private javax.swing.JLabel jlDelC;
    private javax.swing.JLabel jlDelC1;
    private javax.swing.JLabel jlDelC2;
    private javax.swing.JLabel jlDelC4;
    private javax.swing.JLabel jlDelG;
    private javax.swing.JLabel jlDelG1;
    private javax.swing.JLabel jlDelG2;
    private javax.swing.JLabel jlDelG4;
    private javax.swing.JLabel jlDisplayMax1;
    private javax.swing.JLabel jlDisplayMax2;
    private javax.swing.JLabel jlDisplayMaxES;
    private javax.swing.JLabel jlDisplayX1;
    private javax.swing.JLabel jlDisplayX2;
    private javax.swing.JLabel jlDisplayXES;
    private javax.swing.JLabel jlDisplayY1;
    private javax.swing.JLabel jlDisplayY2;
    private javax.swing.JLabel jlDisplayYES;
    private javax.swing.JLabel jlErrB;
    private javax.swing.JLabel jlErrCIN;
    private javax.swing.JLabel jlErrED;
    private javax.swing.JLabel jlErrEmail;
    private javax.swing.JLabel jlErrEmailP;
    private javax.swing.JLabel jlErrEmailReset;
    private javax.swing.JLabel jlErrFName;
    private javax.swing.JLabel jlErrName;
    private javax.swing.JLabel jlErrNumV;
    private javax.swing.JLabel jlErrP;
    private javax.swing.JLabel jlErrP1;
    private javax.swing.JLabel jlErrP2;
    private javax.swing.JLabel jlErrP3;
    private javax.swing.JLabel jlErrPos;
    private javax.swing.JLabel jlErrSCity;
    private javax.swing.JLabel jlErrSComp1;
    private javax.swing.JLabel jlErrSComp3;
    private javax.swing.JLabel jlErrSComp4;
    private javax.swing.JLabel jlErrSD;
    private javax.swing.JLabel jlErrSD1;
    private javax.swing.JLabel jlErrSD2;
    private javax.swing.JLabel jlErrSName;
    private javax.swing.JLabel jlErrTel;
    private javax.swing.JLabel jlMSDefault;
    private javax.swing.JLabel jlMSDefault1;
    private javax.swing.JLabel jlMSIDAdmin;
    private javax.swing.JLabel jlMSIDAdmin1;
    private javax.swing.JLabel jlMSIDC;
    private javax.swing.JLabel jlMSIDC1;
    private javax.swing.JLabel jlMSIDGh;
    private javax.swing.JLabel jlMSIDGh1;
    private javax.swing.JLabel jlMSIDMan;
    private javax.swing.JLabel jlMSSave;
    private javax.swing.JLabel jlMSSave1;
    private javax.swing.JLabel jlMSVL;
    private javax.swing.JLabel jlMSVL1;
    private javax.swing.JLabel jlMSVL3;
    private javax.swing.JLabel jlNext1;
    private javax.swing.JLabel jlNext2;
    private javax.swing.JLabel jlNextES;
    private javax.swing.JLabel jlPrevious1;
    private javax.swing.JLabel jlPrevious2;
    private javax.swing.JLabel jlPreviousES;
    private javax.swing.JLabel jlSearchFilter;
    private javax.swing.JLabel jlabelCABS;
    private javax.swing.JPanel jpMSDefault;
    private javax.swing.JPanel jpMSDefault1;
    private javax.swing.JPanel jpMSSave;
    private javax.swing.JPanel jpMSSave1;
    private javax.swing.JPanel jpNext1;
    private javax.swing.JPanel jpNext2;
    private javax.swing.JPanel jpPrevious1;
    private javax.swing.JPanel jpPrevious2;
    private javax.swing.JRadioButton jrbA;
    private javax.swing.JRadioButton jrbCU;
    private javax.swing.JRadioButton jrbG;
    private javax.swing.JRadioButton jrbSM;
    private javax.swing.JTextField jtfB;
    private javax.swing.JTextField jtfCIN;
    private javax.swing.JTextField jtfEmail;
    private javax.swing.JTextField jtfEmailP;
    private javax.swing.JTextField jtfEmailPE;
    private javax.swing.JTextField jtfEmailReset;
    private javax.swing.JTextField jtfFAName;
    private javax.swing.JTextField jtfFAName1;
    private javax.swing.JTextField jtfFAName2;
    private javax.swing.JTextField jtfFAName5;
    private javax.swing.JTextField jtfFAName6;
    private javax.swing.JTextField jtfFAName7;
    private javax.swing.JTextField jtfFName;
    private javax.swing.JTextField jtfMSIDAdmin;
    private javax.swing.JTextField jtfMSIDMan;
    private javax.swing.JTextField jtfMSIDMan1;
    private javax.swing.JTextField jtfName;
    private javax.swing.JTextField jtfNameP;
    private javax.swing.JTextField jtfNamePE;
    private javax.swing.JTextField jtfNumV;
    private javax.swing.JTextField jtfNumVE;
    private javax.swing.JTextField jtfPos;
    private javax.swing.JTextField jtfSCity;
    private javax.swing.JTextField jtfSFName;
    private javax.swing.JTextField jtfSFNameEdit;
    private javax.swing.JTextField jtfSFNameEdit1;
    private javax.swing.JTextField jtfSIDA;
    private javax.swing.JTextField jtfSIDM;
    private javax.swing.JTextField jtfSName;
    private javax.swing.JTextField jtfSearchFilterRS;
    private javax.swing.JTextField jtfTel;
    // End of variables declaration//GEN-END:variables
}
