package ht291.nim.controller;

import ht291.nim.component.ButtonNim;
import ht291.nim.component.GamePanel;
import ht291.nim.model.Nim;
import ht291.nim.model.Status;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GameController {

    private GamePanel gamePanel;
    private ViewController viewController;
    private ButtonNim[][] matButton;
    private Nim mapNim;

    private int mode;
    private int level;
    private int turn;
    private int player;

    private boolean isVisible;

    public GameController(GamePanel gamePanel, ViewController viewController) {
        this.gamePanel = gamePanel;
        this.viewController = viewController;
        isVisible = true;
    }

    public void initGame(Status status) {
        this.mode = status.getMode();
        this.level = status.getLevel();
        this.turn = status.getTurn();
        setMapNim(new Nim(status.getNrow(), status.getNcol()));
        getMapNim().setMapGame(status.getMapGame());
        setMatButton(new ButtonNim[getMapNim().getNrow()][getMapNim().getNcol()]);
        player = status.getPlayer();
        initBoardGame();
        gamePanel.getBoardGame().chooseNim(mapNim, matButton);
    }

    public void initGame(int mode, int level, int turn) {
        this.mode = mode;
        this.level = level;
        this.turn = turn;

        setMapNim(new Nim(5, 5));
        if (level == 2 || level == 1) {
            getMapNim().initRandom();
        } else {
            getMapNim().initGame();
        }
        setMatButton(new ButtonNim[getMapNim().getNrow()][getMapNim().getNcol()]);
        player = turn;
        initBoardGame();//turn=> 0=>BOT first, 1=> Player First
        gamePanel.getBoardGame().chooseNim(mapNim, matButton);
    }

    public void initBoardGame() {
        System.out.println("player: " + player);
        if (player == 1) {
            gamePanel.getPlayerPanel().getPlayerTop().setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/player1_red.png")));
            if (mode == 0) {
                switch (level) {
                    case 0:
                        gamePanel.getPlayerPanel().getPlayerBottom().setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/BOT_easy_black.png")));
                        break;
                    case 1:
                        gamePanel.getPlayerPanel().getPlayerBottom().setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/BOT_normal_black.png")));
                        break;
                    default:
                        gamePanel.getPlayerPanel().getPlayerBottom().setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/BOT_hard_black.png")));
                        break;
                }
            } else {
                gamePanel.getPlayerPanel().getPlayerBottom().setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/player2_black.png")));
            }

        } else {
            gamePanel.getPlayerPanel().getPlayerTop().setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/player1_black.png")));
            if (mode == 0) {
                switch (level) {
                    case 0:
                        gamePanel.getPlayerPanel().getPlayerBottom().setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/BOT_easy_red.png")));
                        break;
                    case 1:
                        gamePanel.getPlayerPanel().getPlayerBottom().setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/BOT_normal_red.png")));
                        break;
                    default:
                        gamePanel.getPlayerPanel().getPlayerBottom().setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/BOT_hard_red.png")));
                        break;
                }
            } else {
                gamePanel.getPlayerPanel().getPlayerBottom().setIcon(new ImageIcon(getClass().getResource("/ht291/nim/data/player2_red.png")));
            }
        }
        gamePanel.getBoardGame().setLayout(new GridLayout(getMapNim().getNrow(), getMapNim().getNcol(), 0, 0));
        for (int i = 0; i < getMapNim().getNrow(); i++) {
            for (int j = 0; j < getMapNim().getNcol(); j++) {
                getMatButton()[i][j] = new ButtonNim();
                getMatButton()[i][j].getButtonNim().setName(i + " " + j);
                if (player != 0) {
                    chooseNimClick(player, i, j);
                }
                if (getMapNim().getMapGame()[i][j] == 0) {
                    getMatButton()[i][j].setVisible(false);
                }
                gamePanel.getBoardGame().add(getMatButton()[i][j]);
            }
        }
        Thread th = new Thread() {// luồng xử lý BOT chơi mà không ảnh hưởng đến việc updateBoard
            @Override
            public void run() {
                if (player == 0 && mode == 0) {
                    BOTPlay();
                }
            }
        };
        th.start();
    }

    public void playGame(int player, int row, int num) {
        getMapNim().playNim(row, num);
        if (mode == 0) {
            updateBoard(Math.abs(player - 1));
        } else {
            updateBoard((player % 2) + 1);
        }
    }

    public void BOTPlay() {
        String s = getMapNim().BOTPlayer(level);
        String index[] = s.split("\\s");
        int x = Integer.parseInt(index[0]);
        int y = Integer.parseInt(index[1]);
        System.out.println(x + " " + y);

        if (level == 1) {
            for (int i = getMapNim().toDecimal(x - 1) - 1; i > (getMapNim().toDecimal(x - 1) - y - 1); i--) {
                getMatButton()[x - 1][i].getButtonNim().setBackground(Color.ORANGE);
            }
        } else {
            for (int i = (getMapNim().toDecimal(x - 1) + y) - 1; i > getMapNim().toDecimal(x - 1) - 1; i--) {
                getMatButton()[x - 1][i].getButtonNim().setBackground(Color.ORANGE);
            }
        }

        try {
            
            sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        playGame(0, x, y);
        if (!getMapNim().isNimEnd() && isVisible) {
            gamePanel.resetTimer();
            gamePanel.startTimer(1);
        }
    }

    public void chooseNimClick(int player, int i, int j) {
        getMatButton()[i][j].getButtonNim().addActionListener((ActionEvent e) -> {
            playGame(player, i + 1, getMapNim().toDecimal(i) - j);
            gamePanel.resetTimer();
            gamePanel.startTimer((player%2)+1);
        });
    }

    public void updateBoard(int player) {
        this.player = player;
        if (!getMapNim().isNimEnd()) {
            gamePanel.getBoardGame().removeAll();
            initBoardGame();
            gamePanel.getBoardGame().revalidate();
            gamePanel.getBoardGame().repaint();
        } else {
            gamePanel.resetTimer();
            setWinGame(player);
        }
    }

    public void setWinGame(int player) {//tham số là player thua
        if (mode == 0) {
            player = Math.abs(player - 1);
        } else {
            player = (player % 2) + 1;
        }

        int result;
        result = JOptionPane.showConfirmDialog(gamePanel,
                (player == 0 ? "BOT" : "Player " + player) + " WIN"
                + "\nDo you want to continue playing?", "NIM",
                JOptionPane.YES_NO_OPTION);
        if (result == 0) {
            gamePanel.resetTimer();
            gamePanel.startTimer(turn);
            gamePanel.getBoardGame().removeAll();
            initGame(mode, level, turn);
            gamePanel.getBoardGame().revalidate();
            gamePanel.getBoardGame().repaint();
        } else {
            gamePanel.resetTimer();
            viewController.getFrame().removeWindowListener(viewController.getFrame().getWindowListeners()[0]);
            viewController.showModeGame();
        }

    }

    public void saveFileStatus(int choose) {
        File file = null;
        FileOutputStream fos = null;

        Status status = new Status(mode, level, turn, player,
                getMapNim().getMapGame(),
                getMapNim().getNrow(),
                getMapNim().getNcol());

//        try {
////            file = new File(getClass().getResource("status.txt").toURI());
//        } catch (URISyntaxException ex) {
//            System.out.println("ht291.nim.controller.GameController.saveFileStatus()");
//        }
        try {
//            fos = new FileOutputStream("..\\status.txt");
            fos = new FileOutputStream("status.txt");
            if (choose == 0) {
                fos.write(status.toString().getBytes());
            } else {
                fos.write(("").getBytes());
            }

            fos.flush();
            fos.close();
            System.out.println("Ghi file thành công");
        } catch (IOException ex) {
            System.out.println("ht291.nim.controller.GameController.saveFileStatus() NULL FILE");
        }
    }

    public ButtonNim[][] getMatButton() {
        return matButton;
    }

    public void setMatButton(ButtonNim[][] matButton) {
        this.matButton = matButton;
    }

    public Nim getMapNim() {
        return mapNim;
    }

    public void setMapNim(Nim mapNim) {
        this.mapNim = mapNim;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

}
