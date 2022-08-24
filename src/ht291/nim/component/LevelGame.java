/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht291.nim.component;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class LevelGame extends javax.swing.JPanel {

    public LevelGame() {
        initComponents();
        mode = 0;
        
        setUpButton(btnBOTFirst, "btnBOTFirst");
        setUpButton(btnPlayerFirst, "btnPlayerFirst");
        setUpButton(btnEasy, "btnEasy");
        setUpButton(btnNormal, "btnNormal");
        setUpButton(btnHard, "btnHard");
        setUpButton(btnContinue, "btnContinue");

        ImgComponent imgTxtTurn = new ImgComponent("txtTurn_131_19");
        this.add(imgTxtTurn);
        imgTxtTurn.setBounds(10, 19, imgTxtTurn.getPanelWidth(), imgTxtTurn.getPanelHeight());

        ImgComponent imgTxtDifficulty = new ImgComponent("txtDifficulty_414_19");
        this.add(imgTxtDifficulty);
        imgTxtDifficulty.setBounds(10, 90, imgTxtDifficulty.getPanelWidth(), imgTxtDifficulty.getPanelHeight());
    }

    public void setUpButton(JButton btn, String icon) {
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMouseListener(btn, icon);
    }

    public void setMouseListener(JButton btn, String icon) {
        btn.setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/" + icon + "Black.png")));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/" + icon + "Red.png")));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/" + icon + "Black.png")));
            }
        });
    }

    public void setSelectedButton(JButton btn, String icon) {
        btn.setSelected(true);
        btn.setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/" + icon + "Selected.png")));
        for (int i = 1; i < btn.getMouseListeners().length; i++) {
            btn.removeMouseListener(btn.getMouseListeners()[i]);
        }
    }
    
    public void setChooseButton(JButton btn, String icon){
        setSelectedButton(btn, icon);
        setSelectedButton(btn, icon);
        
        switch(icon){
            case "btnBOTFirst": {
                setMouseListener(btnPlayerFirst, "btnPlayerFirst");
                btnPlayerFirst.setSelected(false);
                turn = 0;
                break;
            }
            
            case "btnPlayerFirst": {
                setMouseListener(btnBOTFirst, "btnBOTFirst");
                btnBOTFirst.setSelected(false);
                turn = 1;
                break;
            }
            case "btnEasy": {
                setMouseListener(btnNormal, "btnNormal");
                btnNormal.setSelected(false);
                setMouseListener(btnHard, "btnHard");
                btnHard.setSelected(false);
                level = 0;
                break;
            }
            
            case "btnNormal": {
                setMouseListener(btnEasy, "btnEasy");
                btnEasy.setSelected(false);
                setMouseListener(btnHard, "btnHard");
                btnHard.setSelected(false);
                level = 1; 
                break;
            }
            
            case "btnHard": {
                setMouseListener(btnEasy, "btnEasy");
                btnEasy.setSelected(false);
                setMouseListener(btnNormal, "btnNormal");
                btnNormal.setSelected(false);
                level = 2;
                break;
            }
        }
        
        if(btnBOTFirst.isSelected() || btnPlayerFirst.isSelected()){
            if(btnEasy.isSelected() || btnNormal.isSelected()|| btnHard.isSelected()){
                btnContinue.setEnabled(true);
            }
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnHard = new javax.swing.JButton();
        btnPlayerFirst = new javax.swing.JButton();
        btnNormal = new javax.swing.JButton();
        btnContinue = new javax.swing.JButton();
        btnBOTFirst = new javax.swing.JButton();
        btnEasy = new javax.swing.JButton();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(710, 269));

        btnHard.setPreferredSize(new java.awt.Dimension(188, 57));
        btnHard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHardActionPerformed(evt);
            }
        });

        btnPlayerFirst.setPreferredSize(new java.awt.Dimension(188, 57));
        btnPlayerFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayerFirstActionPerformed(evt);
            }
        });

        btnNormal.setPreferredSize(new java.awt.Dimension(188, 57));
        btnNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNormalActionPerformed(evt);
            }
        });

        btnContinue.setEnabled(false);
        btnContinue.setPreferredSize(new java.awt.Dimension(271, 37));
        btnContinue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinueActionPerformed(evt);
            }
        });

        btnBOTFirst.setPreferredSize(new java.awt.Dimension(188, 57));
        btnBOTFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBOTFirstActionPerformed(evt);
            }
        });

        btnEasy.setPreferredSize(new java.awt.Dimension(188, 57));
        btnEasy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEasyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBOTFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEasy, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addComponent(btnNormal, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPlayerFirst, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(219, 219, 219)
                .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBOTFirst, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPlayerFirst, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNormal, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHard, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEasy, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNormalActionPerformed
        setChooseButton(btnNormal, "btnNormal");
    }//GEN-LAST:event_btnNormalActionPerformed

    private void btnPlayerFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayerFirstActionPerformed
        setChooseButton(btnPlayerFirst, "btnPlayerFirst");
    }//GEN-LAST:event_btnPlayerFirstActionPerformed

    private void btnContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnContinueActionPerformed

    private void btnBOTFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBOTFirstActionPerformed
        setChooseButton(btnBOTFirst, "btnBOTFirst");
    }//GEN-LAST:event_btnBOTFirstActionPerformed

    private void btnEasyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEasyActionPerformed
        setChooseButton(btnEasy, "btnEasy");
    }//GEN-LAST:event_btnEasyActionPerformed

    private void btnHardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHardActionPerformed
        setChooseButton(btnHard, "btnHard");
    }//GEN-LAST:event_btnHardActionPerformed

    public JButton getBtnBOTFirst() {
        return btnBOTFirst;
    }

    public JButton getBtnContinue() {
        return btnContinue;
    }

    public JButton getBtnEasy() {
        return btnEasy;
    }

    public JButton getBtnHard() {
        return btnHard;
    }

    public JButton getBtnNormal() {
        return btnNormal;
    }

    public JButton getBtnPlayerFirst() {
        return btnPlayerFirst;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBOTFirst;
    private javax.swing.JButton btnContinue;
    private javax.swing.JButton btnEasy;
    private javax.swing.JButton btnHard;
    private javax.swing.JButton btnNormal;
    private javax.swing.JButton btnPlayerFirst;
    // End of variables declaration//GEN-END:variables
    private int turn, level, mode;

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
