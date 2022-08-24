package ht291.nim.model;

public class Status {

    private int mode;
    private int level;
    private int turn;

    private int player;
    private int mapGame[][];
    private int ncol;
    private int nrow;

    public Status(int mode, int level, int turn, int player, int[][] mapGame, int ncol, int nrow) {
        this.mode = mode;
        this.level = level;
        this.turn = turn;
        this.player = player;
        this.mapGame = mapGame;
        this.ncol = ncol;
        this.nrow = nrow;
    }

    @Override
    public String toString() {
        String s = getMode() + " " + getLevel() + " " + getTurn() + "\n";
        s += player + "\n";
        s += nrow+" "+ ncol;
        for (int i = 0; i < getNrow(); i++) {
            s += "\n";
            for (int j = 0; j < getNcol(); j++) {
                s += getMapGame()[i][j] + (j == ncol - 1 ? "" : " ");
            }
        }
        return s;
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

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int[][] getMapGame() {
        return mapGame;
    }

    public void setMapGame(int[][] mapGame) {
        this.mapGame = mapGame;
    }

    public int getNcol() {
        return ncol;
    }

    public void setNcol(int ncol) {
        this.ncol = ncol;
    }

    public int getNrow() {
        return nrow;
    }

    public void setNrow(int nrow) {
        this.nrow = nrow;
    }

}
