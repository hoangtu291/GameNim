package ht291.nim.model;

import java.util.Random;
import java.util.Scanner;

public class Nim {

    //*** Chế độ chơi với máy, sử dụng phương pháp đổi bit trong hàng xác định tại vị trí bit Nimsum bằng 1 ***//
    private int mapGame[][]; // ma tran luu trang thai game
    private int binGame[][]; //ma tran lưu số lượng của mỗi hàng bằng nhị phân, dòng cuối lưu Nim Sum Binary;
    private int nrow;
    private int ncol;

    Scanner sc = new Scanner(System.in);

    public Nim(int nrow, int ncol) {
        this.nrow = nrow;
        this.ncol = ncol;
        mapGame = new int[nrow][ncol];
        binGame = new int[nrow + 1][8]; //dòng cuối lưu nimsum
    }

    public void initGame() {//khởi tạo, gán tất cả các ô bằng 0;
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                if (j <= i) {
                    mapGame[i][j] = 1;
                } else {
                    mapGame[i][j] = 0;
                }
            }
        }
        calcBinNim();
    }

    public void initRandom() {
        for (int i = 0; i < nrow; i++) {
            Random rand = new Random();
            int ranNum = rand.nextInt(ncol) + 1;
            for (int j = 0; j < ranNum; j++) {
                mapGame[i][j] = 1;
            }
        }
        calcBinNim();
    }

    public void initBinGame() {//khởi tạo, gán tất cả các ô bằng -1;
        for (int i = 0; i < nrow + 1; i++) {
            for (int j = 0; j < 8; j++) {
                binGame[i][j] = -1;
            }
        }
    }

    public void printGame() {//in ma trận game
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                System.out.printf("%5d  ", mapGame[i][j]);
            }
            System.out.println();
        }
    }

    public void printBinGame() {
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.printf("%d  ", binGame[i][j]);
            }
            System.out.println();
        }

        System.out.println("KQ: ");
        for (int i = 0; i < 8; i++) {
            System.out.printf("%d  ", binGame[nrow][i]);
        }
        System.out.println();
    }

    public int toDecimal(int row) {// chuyển dòng nhị phân sang thập phân
        int result = 0;
        for (int i = 0; i < 8; i++) {
            result += binGame[row][i] * Math.pow(2, 7 - i);
        }
        return result;
    }

    public int lenBit(int row) {//tính chiều dài bit của dòng nhị phân
        for (int i = 0; i < 8; i++) {
            if (binGame[row][i] == 1) {
                return (8 - i);
            }
        }
        return 0;
    }

    public int findRowSameNimSum() {//tìm hàng thích hợp khi độ dài NimSum != (nhỏ hơn) bất kì dòng nào.
        for (int i = 0; i < nrow; i++) {
            if (lenBit(nrow) > 0 && binGame[i][8 - lenBit(nrow)] == 1) {//kiem tra neu hang co cot tai bit lon nhat cua NimSum = 1 thi doi bit;
                return i;
            }
        }
        return 0;
    }

    public boolean checkNumRow() {// Nếu còn hàng nào có số lượng lớn hơn 1 thì true
        for (int i = 0; i < nrow; i++) {
            if (toDecimal(i) > 1) {
                return true;
            }
        }
        return false;
    }

    public int selectedRow() {//tìm hàng thích hợp cho BOTHard chơi
        int bitNimSum = lenBit(nrow);// chiều dài của NimsumBinary
        for (int i = 0; i < nrow; i++) {
            if (lenBit(i) == bitNimSum) {
                return i;
            }
        }
        return findRowSameNimSum();
    }

    public int selectedRowNormal() {//tìm hàng thích hợp cho BOTNormal chơi
        int nimSum = toDecimal(nrow);
        int rd;
        if (nimSum % 2 == 1) {
            do {
                Random rand = new Random();
                rd = (Math.abs(rand.nextInt()) % nrow);
                if (toDecimal(rd) > 0) {
                    return rd;
                }
            } while (toDecimal(rd) <= 0);
        } else {
            do {
                Random rand = new Random();
                rd = (Math.abs(rand.nextInt()) % nrow);
                if (toDecimal(rd) > 0) {
                    return rd;
                }
            } while (toDecimal(rd) <= 1);
        }
        return rd;
    }

    public void changeBitRow(int row) {// đổi bit tại những vị trí bit Nimsum == 1
        for (int i = 7; i >= (8 - lenBit(nrow)); i--) {
            if (binGame[nrow][i] == 1) {
                binGame[row][i] = Math.abs(binGame[row][i] - 1);
            }
        }
    }

    public void appendBit(int row, int x) {// chèn bit vào hàng, chuyển thập phân sang nhị phân
        boolean flag = false;
        for (int i = 7; i >= 0 && !flag; i--) {
            if (binGame[row][i] == -1) {
                binGame[row][i] = x;
                flag = true;
            }
        }
    }

    public void toBinnary(int row, int number) {// chuyển thập phân sang nhị phân
        appendBit(row, number % 2);
        if (number / 2 != 0) {
            toBinnary(row, number / 2);
        }
    }

    public void allNimSum() {// tính NimSum dạng nhị phân bằng phương pháp XOR
        int count;
        for (int i = 7; i >= 0; i--) {
            count = 0;
            for (int j = 0; j < nrow; j++) {
                if (binGame[j][i] == 1) {
                    count++;
                }
            }
            appendBit(nrow, count % 2);
        }
    }

    public void formatBin() {// định dạng nhị phân -1 thành 0
        for (int i = 0; i < nrow + 1; i++) {
            for (int j = 0; j < 8; j++) {
                if (binGame[i][j] == -1) {
                    binGame[i][j] = 0;
                }
            }
        }
    }

    public void calcBinNim() {// tính số ô trên mỗi hàng và gọi hàm chuyển thành nhị phân
        initBinGame();
        int rowSum;
        for (int i = 0; i < nrow; i++) {
            rowSum = 0;
            for (int j = 0; j < ncol; j++) {
                rowSum += mapGame[i][j];
            }
            toBinnary(i, rowSum);
        }
        allNimSum();
        formatBin();
    }

    public void update() {
        System.out.flush();
        printGame();
    }

    public void playNim(int row, int num) {// chuyển các ô được chọn thành 0, row được truyền theo 1->n
        System.out.println("Chọn: " + row + " " + num);
        int count = 0, i = ncol - 1;
        while (count != num && i >= 0) {
            if (mapGame[row - 1][i] != 0) {
                mapGame[row - 1][i] = 0;
                count++;
            }
            i--;
        }
        calcBinNim();
        System.out.println("NIMSUM: " + toDecimal(nrow));
//        update();
    }

    public int nimLeft() {// số ô còn lại trong game, hàm bổ trợ
        int num = 0;
        for (int i = 0; i < nrow; i++) {
            num += toDecimal(i);
        }
        return num;
    }

    public boolean isNimEnd() {// kiểm tra Kết thúc game
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                if (mapGame[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public String chooseRandom(int selcrow, int numRow) {
        Random rand = new Random();
        System.out.println("Bot nimSum = 0");
        boolean check = false;

        while (!check) {
            int rd = (Math.abs(rand.nextInt()) % nrow);
            if (toDecimal(rd) > 0) {
                selcrow = rd;
                numRow = toDecimal(selcrow);
                check = true;
                //change bit from bit length of row
                for (int x = 7; x >= (7 - lenBit(rd)); x--) {
                    binGame[rd][x] = Math.abs(binGame[rd][x] - 1);
                }
            }
        }
        return (selcrow + " " + numRow);
    }

    public String BOTPlayer(int level) {
        String result;
        switch (level) {
            case 0:
                result = BOTEasy();
                break;
            case 1:
                result = BOTNormal();
                break;
            default:
                result = BOTHard();
                break;
        }
        return result;
    }

    public String BOTEasy() {
        int selcrow = selectedRow();//chon hang
        int numRow = toDecimal(selcrow); // so o trong hang
        System.out.println("So o con lai: " + nimLeft());
        System.out.println("BOT dang choi...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
//        Pair<Integer, Integer> pair = chooseRandom(selcrow, numRow);
//        selcrow = pair.getKey();
//        numRow = pair.getValue();
        String s = chooseRandom(selcrow, numRow);
        String index[] = s.split("\\s");
        selcrow = Integer.parseInt(index[0]);
        numRow = Integer.parseInt(index[1]);
        System.out.println("BOT chon hang " + (selcrow + 1) + ", " + (numRow - toDecimal(selcrow)) + " o !!");
        return (selcrow + 1) + " " + (numRow - toDecimal(selcrow));
    }

    public String BOTNormal() {
        int selcrow = selectedRowNormal();//chon hang
        int numRow = toDecimal(selcrow); // so o trong hang
        int rd;// so o chon

        System.out.println("So o con lai: " + nimLeft());
        System.out.println("BOT dang choi...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
//        if (selcrow != 0) {
        Random rand = new Random();
        rd = (Math.abs(rand.nextInt()) % numRow);
        rd += (rd == 0 ? 1 : 0);
        if (numRow > 1) {
            if (toDecimal(nrow) % 2 == 1) {
                rd += (rd % 2 == 0 ? 1 : 0);
            } else {
                rd += (rd % 2 == 1 ? 1 : 0);
            }
        } else {
            rd = 1;
        }
        System.out.println("BOT chon hang " + (selcrow + 1) + ", " + rd + " o !!");
        return (selcrow + 1) + " " + rd;
    }

    public String BOTHard() {
        int selcrow = selectedRow();//chon hang
        int numRow = toDecimal(selcrow); // so o trong hang
        System.out.println("So o con lai: " + nimLeft());
        System.out.println("BOT dang choi...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        if (toDecimal(nrow) > 0) {
            changeBitRow(selcrow);
        } else {
            String s = chooseRandom(selcrow, numRow);
            String index[] = s.split("\\s");
            selcrow = Integer.parseInt(index[0]);
            numRow = Integer.parseInt(index[1]);
        }
        System.out.println("BOT chon hang " + (selcrow + 1) + ", " + (numRow - toDecimal(selcrow)) + " o !!");
        return (selcrow + 1) + " " + (numRow - toDecimal(selcrow));
    }

    //getter, setter ---------------------------------
    public int[][] getMapGame() {
        return mapGame;
    }

    public void setMapGame(int[][] mapGame) {
        this.mapGame = mapGame;
        calcBinNim();
    }

    public int[][] getBinGame() {
        return binGame;
    }

    public void setBinGame(int[][] binGame) {
        this.binGame = binGame;
    }

    public int getNrow() {
        return nrow;
    }

    public void setNrow(int nrow) {
        this.nrow = nrow;
    }

    public int getNcol() {
        return ncol;
    }

    public void setNcol(int ncol) {
        this.ncol = ncol;
    }

    public static void main(String[] args) {
        Nim nim = new Nim(5, 5);

        nim.initRandom();
        nim.printGame();
        int i = 0;
//        System.out.println();
//
//        while (!nim.isNimEnd()) {
////            nim.printGame();
//            nim.calcBinNim();
////            nim.printBinGame();
//            System.out.println("Dong nen chon: " + (nim.selectedRow() + 1));
//            System.out.println("Nim Sum = " + nim.toDecimal(nim.nrow));
//            nim.player((i % 2) + 1);
//            i++;
//        }
//        System.out.println("Nguoi choi " + (((i - 1) % 2) + 1) + " WIN!!!!!!!!");
//    }
    }

}
