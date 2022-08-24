/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht291.nim.component;

import ht291.nim.controller.GameController;
import ht291.nim.controller.ViewController;
import ht291.nim.model.Status;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author ASUS
 */
public class GamePanel extends javax.swing.JPanel {

    private Timer time;
    private int second;

    /**
     * Creates new form GamePanel
     *
     * @param viewController
     * @param mode
     * @param level
     * @param turn
     */
    public GamePanel(ViewController viewController, int mode, int level, int turn) {
        initComponents();

        time = new Timer();
        resetTimer();

        gameController = new GameController(this, viewController);
        gameController.initGame(mode, level, turn);
        this.btnNewGame.setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/btnNewGame_black.png")));
        this.btnNewGame.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
                startTimer(turn);
                boardGame.removeAll();
                gameController.initGame(mode, level, turn);
                boardGame.revalidate();
                boardGame.repaint();
            }
        });

        txtTime.setFont(new Font("Porter Sans Block", Font.PLAIN, 20));
        startTimer(turn);
    }

    public GamePanel(ViewController viewController, Status status) {
        initComponents();
        time = new Timer();
        resetTimer();

        gameController = new GameController(this, viewController);
        gameController.initGame(status);
        this.btnNewGame.setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/btnNewGame_black.png")));
        this.btnNewGame.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
                startTimer(status.getTurn());
                boardGame.removeAll();
                gameController.initGame(status.getMode(), status.getLevel(), status.getTurn());
                boardGame.revalidate();
                boardGame.repaint();
            }
        });

        txtTime.setFont(new Font(createFont().getFontName(), createFont().PLAIN, 20));
        startTimer(status.getPlayer());
    }

    public void resetTimer() {
        time.cancel();
        time = new Timer();
        second = 30;
        txtTime.setText("TIME: 0:" + second);
    }

    public void startTimer(int player) {
//        if(player == 2){
//            System.out.println("ht291.nim.component.GamePanel.startTimer()");
//        }
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (second > 0) {
                    second--;
                    if (second > 9) {
                        txtTime.setForeground(Color.BLACK);
                        txtTime.setText("TIME: 0:" + second);
                    } else {
                        txtTime.setForeground(new Color(208, 11, 0));
                        txtTime.setText("TIME: 0:0" + second);
                        try {
                            sleep(650);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        txtTime.setText("");
                    }
                } else {
                    time.cancel();
                    gameController.setWinGame(player);
                }
            }
        }, 1000, 1000);
    }
    
    public Font createFont(){
        InputStream file = null;
        Font font = null;
        try {
            file = new FileInputStream("porter-sans-inline-block.ttf");
        } catch (FileNotFoundException e) {
            System.out.println("Error createFont");
        }
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, file);
        } catch (FontFormatException | IOException  e) {
            System.out.println("Error createFont");
        }
        return font;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boardGame = new ht291.nim.component.BoardGame();
        playerPanel = new ht291.nim.component.PlayerPanel();
        btnNewGame = new javax.swing.JButton();
        txtTime = new javax.swing.JLabel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(770, 300));

        boardGame.setToolTipText("");

        btnNewGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ht291/nim/data/btnNewGame_black.png"))); // NOI18N
        btnNewGame.setMaximumSize(new java.awt.Dimension(226, 42));
        btnNewGame.setMinimumSize(new java.awt.Dimension(226, 42));
        btnNewGame.setPreferredSize(new java.awt.Dimension(226, 42));

        txtTime.setText("TIME: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boardGame, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtTime)
                        .addGap(26, 26, 26)
                        .addComponent(playerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNewGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(boardGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(playerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(txtTime)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ht291.nim.component.BoardGame boardGame;
    private javax.swing.JButton btnNewGame;
    private ht291.nim.component.PlayerPanel playerPanel;
    private javax.swing.JLabel txtTime;
    // End of variables declaration//GEN-END:variables
    private GameController gameController;

    public GameController getGameController() {
        return gameController;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

}
