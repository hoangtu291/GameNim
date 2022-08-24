/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht291.nim.component;

import ht291.nim.model.Nim;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardGame extends javax.swing.JPanel {

    public BoardGame() {
        initComponents();
    }

//    public void unEnableBtn(){
//        for (int i = 0; i < mapNim.getNrow(); i++) {
//            for (int j = 0; j < mapNim.getNcol(); j++) {
//                matButton[i][j].getButtonNim().setEnabled(false);
//            }
//        }
//    }
    
    public void chooseNim(Nim mapNim, ButtonNim[][] matButton) {

        MouseListener ml = new MouseAdapter() {
            
            @Override
            public void mouseEntered(MouseEvent e) {
                Component c = getComponentAt(e.getPoint());
                if (c.isVisible()) {
                    for (int i = 0; i < mapNim.getNrow(); i++) {
                        for (int j = 0; j < mapNim.getNcol(); j++) {
                            matButton[i][j].getButtonNim().setBackground(Color.BLACK);
                        }
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Component c = getComponentAt(e.getPoint());
                if (c != null) {
                    String s = ((ButtonNim) c).getButtonNim().getName();
                    String index[] = s.split("\\s");
                    int x = Integer.parseInt(index[0]);
                    int y = Integer.parseInt(index[1]);
                    for (int i = y; i < mapNim.toDecimal(x); i++) {
                        matButton[x][i].getButtonNim().setBackground(new Color(208, 11, 0));
                    }
                }
            }

        };
        this.addMouseListener(ml);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    
    
}
