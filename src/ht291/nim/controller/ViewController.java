package ht291.nim.controller;

import ht291.nim.component.WelcomePanel;
import ht291.nim.component.GamePanel;
import ht291.nim.component.ImgComponent;
import ht291.nim.component.LevelGame;
import ht291.nim.component.ModeGameBtn;
import ht291.nim.component.StartGame;
import ht291.nim.model.Status;
import ht291.nim.view.MainFrame;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class ViewController {

    private MainFrame frame;
    private ModeGameBtn modeGame;
    private GamePanel gamePanel;
    private LevelGame levelGame;
    private WelcomePanel welcomePanel;
    private StartGame startGame;

    private final int frWIDTH;
    private final int frHEIGHT;

    public ViewController(MainFrame frame) {
        this.frame = frame;
        frWIDTH = (int) frame.getPreferredSize().getWidth();
        frHEIGHT = (int) frame.getPreferredSize().getHeight();
    }

    public ViewController() {
        frame = new MainFrame();
        frWIDTH = (int) frame.getPreferredSize().getWidth();
        frHEIGHT = (int) frame.getPreferredSize().getHeight();
    }

    public void showWelcomePanel() {
        frame.getContentPane().removeAll();

        welcomePanel = new WelcomePanel();

        frame.add(welcomePanel);

        int panelWidth = (int) welcomePanel.getPreferredSize().getWidth();
        int panelHeight = (int) welcomePanel.getPreferredSize().getHeight();
        welcomePanel.setBounds(0, 0, panelWidth, panelHeight);
        frame.revalidate();
        frame.repaint();
    }

    public void showStartGame() {
        frame.getContentPane().removeAll();

        ImgComponent imgLogo = new ImgComponent("logo_251_109");
        startGame = new StartGame();

        frame.add(startGame);
        frame.add(imgLogo);

        int panelWidth = (int) welcomePanel.getPreferredSize().getWidth();
        int panelHeight = (int) welcomePanel.getPreferredSize().getHeight();

        startGame.setBounds(246, 254, panelWidth, panelHeight);
        imgLogo.setBounds((frWIDTH / 2) - (imgLogo.getPanelWidth() / 2), 24, imgLogo.getPanelWidth(), imgLogo.getPanelHeight());

        startGame.getBtnPlayGame().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Status status = startGame.readFileStatus();
                if (status == null) {
                    showModeGame();
                } else {
                    int result;
                    result = JOptionPane.showConfirmDialog(gamePanel,
                            "Do you want to play the previous game again?",
                            "NIM",
                            JOptionPane.YES_NO_OPTION);

                    if (result == 0) {
                        showGamePanelExist(status);
                    } else {
                        showModeGame();
                    }
                }

            }
        });

        frame.revalidate();
        frame.repaint();
    }

    public void showGamePanelExist(Status status) {
        System.out.println("Mode: " + status.getMode() + " Level: " + status.getLevel() + " Turn: " + status.getTurn());
        frame.getContentPane().removeAll();

        JButton btnHome = new JButton();
        btnHome.setBounds(30, 28, 119, 35);
        btnHome.setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/btnHome.png")));
        btnHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(btnHome);

        gamePanel = new GamePanel(this, status);
        ImgComponent imgLogo = new ImgComponent("logo_136_59");
        frame.add(imgLogo);

        frame.add(gamePanel);
        int panelWidth = (int) gamePanel.getPreferredSize().getWidth();
        int panelHeight = (int) gamePanel.getPreferredSize().getHeight();

        gamePanel.setBounds(10, 85, panelWidth, panelHeight);
        imgLogo.setBounds((frWIDTH / 2) - (imgLogo.getPanelWidth() / 2), 24, imgLogo.getPanelWidth(), imgLogo.getPanelHeight());

        //Click event
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result;
                result = JOptionPane.showConfirmDialog(gamePanel,
                        "Do you want to save the game?",
                        "NIM",
                        JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    gamePanel.resetTimer();
                    JOptionPane.showMessageDialog(gamePanel, "Save successfully!!!", "NIM", JOptionPane.PLAIN_MESSAGE);
                    gamePanel.getGameController().saveFileStatus(result);
                    showStartGame();
                    frame.removeWindowListener(frame.getWindowListeners()[0]);
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    gamePanel.getGameController().setIsVisible(false);

                } else if (result == 1) {
                    gamePanel.resetTimer();
                    gamePanel.getGameController().saveFileStatus(result);
                    showStartGame();
                    frame.removeWindowListener(frame.getWindowListeners()[0]);
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    gamePanel.getGameController().setIsVisible(false);

                }

            }
        });

        //Close by [X]
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                int result;
                result = JOptionPane.showConfirmDialog(gamePanel,
                        "Do you want to save the game?",
                        "NIM",
                        JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    JOptionPane.showMessageDialog(gamePanel, "Save successfully!!!", "NIM", JOptionPane.PLAIN_MESSAGE);
                    gamePanel.getGameController().saveFileStatus(result);
                } else if (result == 1) {
                    gamePanel.getGameController().saveFileStatus(result);
                } else {
                    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    public void showModeGame() {
        frame.getContentPane().removeAll();

        JButton btnBack;
        btnBack = new JButton();
        btnBack.setBounds(30, 28, 95, 35);
        btnBack.setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/btn_back.png")));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(btnBack);

        ImgComponent imgLogo = new ImgComponent("logo_179_78");
        ImgComponent imgPlayWith = new ImgComponent("txtPlayWith_355_20");
        modeGame = new ModeGameBtn();

        frame.add(modeGame);
        frame.add(imgLogo);
        frame.add(imgPlayWith);

        int panelWidth = (int) modeGame.getPreferredSize().getWidth();
        int panelHeight = (int) modeGame.getPreferredSize().getHeight();

        modeGame.setBounds(27, 239, panelWidth, panelHeight);
        imgLogo.setBounds((frWIDTH / 2) - (imgLogo.getPanelWidth() / 2), 24, imgLogo.getPanelWidth(), imgLogo.getPanelHeight());
        imgPlayWith.setBounds((frWIDTH / 2) - (imgPlayWith.getPanelWidth() / 2), 170, imgPlayWith.getPanelWidth(), imgPlayWith.getPanelHeight());

        //Click Event
        modeGame.getBtnWithBOT().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLevelGame();
            }
        });

        modeGame.getBtnWithPlayer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGamePanel(1, 0, 1);
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStartGame();
            }
        });

        frame.revalidate();
        frame.repaint();

    }

    public void showGamePanel(int mode, int level, int turn) {
        System.out.println("Mode: " + mode + " Level: " + level + " Turn: " + turn);
        frame.getContentPane().removeAll();

        JButton btnHome = new JButton();
        btnHome.setBounds(30, 28, 119, 35);
        btnHome.setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/btnHome.png")));
        btnHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(btnHome);

        gamePanel = new GamePanel(this, mode, level, turn);
        ImgComponent imgLogo = new ImgComponent("logo_136_59");
        frame.add(imgLogo);

        frame.add(gamePanel);
        int panelWidth = (int) gamePanel.getPreferredSize().getWidth();
        int panelHeight = (int) gamePanel.getPreferredSize().getHeight();

        gamePanel.setBounds(10, 85, panelWidth, panelHeight);
        imgLogo.setBounds((frWIDTH / 2) - (imgLogo.getPanelWidth() / 2), 24, imgLogo.getPanelWidth(), imgLogo.getPanelHeight());

        //Click event
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result;
                result = JOptionPane.showConfirmDialog(gamePanel,
                        "Do you want to save the game?",
                        "NIM",
                        JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    JOptionPane.showMessageDialog(gamePanel, "Save successfully!!!", "NIM", JOptionPane.PLAIN_MESSAGE);
                    gamePanel.resetTimer();
                    gamePanel.getGameController().saveFileStatus(result);
                    showStartGame();
                    frame.removeWindowListener(frame.getWindowListeners()[0]);
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    gamePanel.getGameController().setIsVisible(false);

                } else if (result == 1) {
                    gamePanel.resetTimer();
                    gamePanel.getGameController().saveFileStatus(result);
                    showStartGame();
                    frame.removeWindowListener(frame.getWindowListeners()[0]);
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    gamePanel.getGameController().setIsVisible(false);
                }
            }
        });

        //Close by [X]
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                int result;
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                result = JOptionPane.showConfirmDialog(gamePanel,
                        "Do you want to save the game?",
                        "NIM",
                        JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    JOptionPane.showMessageDialog(gamePanel, "Save successfully!!!", "NIM", JOptionPane.PLAIN_MESSAGE);
                    gamePanel.getGameController().saveFileStatus(result);
                } else if (result == 1) {
                    gamePanel.getGameController().saveFileStatus(result);
                } else {
                    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    public void showLevelGame() {
        frame.getContentPane().removeAll();

        JButton btnBack;
        btnBack = new JButton();
        btnBack.setBounds(30, 28, 95, 35);
        btnBack.setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/btn_back.png")));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(btnBack);

        levelGame = new LevelGame();
        modeGame = new ModeGameBtn();
        ImgComponent imgLogo = new ImgComponent("logo_136_59");

        frame.add(levelGame);
        frame.add(imgLogo);

        int panelWidth = (int) levelGame.getPreferredSize().getWidth();
        int panelHeight = (int) levelGame.getPreferredSize().getHeight();

        levelGame.setBounds((frWIDTH / 2) - (panelWidth / 2) - 10, 111, panelWidth, panelHeight);
        imgLogo.setBounds((frWIDTH / 2) - (imgLogo.getPanelWidth() / 2), 24, imgLogo.getPanelWidth(), imgLogo.getPanelHeight());

        //Click Event
        levelGame.getBtnContinue().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGamePanel(levelGame.getMode(), levelGame.getLevel(), levelGame.getTurn());
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showModeGame();
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        ViewController view = new ViewController();
        view.frame.setVisible(true);
        view.showWelcomePanel();

        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    view.getWelcomePanel().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                view.showStartGame();
            }
        };
        th.start();
    }

    public MainFrame getFrame() {
        return frame;
    }

    public ModeGameBtn getModeGame() {
        return modeGame;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public LevelGame getLevelGame() {
        return levelGame;
    }

    public WelcomePanel getWelcomePanel() {
        return welcomePanel;
    }

    public int getFrWIDTH() {
        return frWIDTH;
    }

    public int getFrHEIGHT() {
        return frHEIGHT;
    }

}
