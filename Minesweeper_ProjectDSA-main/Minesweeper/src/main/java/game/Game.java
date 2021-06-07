package game;

import gui.*;
import icons.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Random;


public class Game implements ChooseBoardListener, ActionBoardListener {

    // Trang thai ban choi
    Cell stateBoard[][] = null;
    int row, column, totalMine;

    MainUI mainUI;
    MenuGame menuGame;
    Board board;

    int countMark = 0;
    int countCellOpen = 0;
    int timePlay = 0;

    Timer timePlayGame = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            timePlay += 1;
            mainUI.setTimePlay(timePlay);
        }
    });

    public Game() {
        menuGame = new MenuGame();
        menuGame.setChooseBoardListener(this);
        board = new Board();
        board.setActionBoardListener(this);

        mainUI = new MainUI(menuGame, board);

        mainUI.setVisible(true);
    }

    @Override
    public void chooseBoardPerformed(int row, int column, int mines) {
        this.row = row;
        this.column = column;
        this.totalMine = mines;
        restartGame();
    }

    private void createBoard(int row, int column, int mines) {
        stateBoard = new Cell[row][column];
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < column; j++) {
                stateBoard[i][j] = new Cell();
            }
        }

        // Random mìn
        int countMineRand = 0;
        Random random = new Random();
        while(countMineRand < mines) {
            int y = random.nextInt(row);
            int x = random.nextInt(column);
            if(!(stateBoard[y][x].isBom())) {
                stateBoard[y][x].setBom(true);
                countMineRand++;
            }
        }

        // Tạo ô số
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < column; j++) {
                // Khong phai min
                if(!(stateBoard[i][j].isBom())) {
                    int countMineAround = 0;
                    if(i - 1 >= 0 && stateBoard[i - 1][j].isBom()) {
                        countMineAround++;
                    }
                    if(i + 1 < row && stateBoard[i + 1][j].isBom()) {
                        countMineAround++;
                    }
                    if(j - 1 >= 0 && stateBoard[i][j - 1].isBom()) {
                        countMineAround++;
                    }
                    if(j + 1 < column && stateBoard[i][j + 1].isBom()) {
                        countMineAround++;
                    }
                    if(i - 1 >= 0 && j - 1 >= 0 && stateBoard[i - 1][j - 1].isBom()) {
                        countMineAround++;
                    }
                    if(i - 1 >= 0 && j + 1 < column  && stateBoard[i - 1][j + 1].isBom()) {
                        countMineAround++;
                    }
                    if(i + 1 < row && j + 1 < column && stateBoard[i + 1][j + 1].isBom()) {
                        countMineAround++;
                    }
                    if(i + 1 < row && j - 1 >= 0 && stateBoard[i + 1][j - 1].isBom()) {
                        countMineAround++;
                    }
                    stateBoard[i][j].setValue(countMineAround);
                }
            }
        }
    }

    @Override
    public void onOpenCell(int x, int y) {
        if(stateBoard[y][x].isMark() || stateBoard[y][x].isOpen()){
            return;
        }
        // first game
        if(timePlay == 0) {
            timePlayGame.restart();
        }
        if(stateBoard[y][x].isBom()) {
            // Game over
            timePlayGame.stop();
            board.onGameOver(false, x, y, timePlay);
            if(mainUI.onGameOver(false, x, y, timePlay) == JOptionPane.OK_OPTION) {
                // Restart game
                restartGame();
            }
            else {
                // Tro ve menu game
                mainUI.showMenuGame();
            }
        }
        else {
            eatFreeCell(x, y);
            if(countCellOpen == column * row - totalMine){
                timePlayGame.stop();
                board.onGameOver(true, x, y, timePlay);
                if(mainUI.onGameOver(true, x, y, timePlay) == JOptionPane.OK_OPTION) {
                    // Restart game
                    restartGame();
                }
                else {
                    // Tro ve menu game
                    mainUI.showMenuGame();
                }
            }
            board.repaint();
        }
    }

    private void restartGame() {
        createBoard(row, column, totalMine);
        countMark = totalMine;
        countCellOpen = 0;
        timePlay = 0;
        board.setStateBoard(stateBoard);
        board.onRestartGame();
        mainUI.onRestartGame();
        mainUI.setCountFlag(countMark);
    }

    @Override
    public void onMarkCell(int x, int y) {
        if(stateBoard[y][x].isOpen()) {
            return;
        }
        if(stateBoard[y][x].isMark()) {
            stateBoard[y][x].setMark(false);
            countMark++;
        }
        else if(countMark != 0) {
            stateBoard[y][x].setMark(true);
            countMark--;
            if(countMark < 0) {
                countMark = 0;
            }
        }
        mainUI.setCountFlag(countMark);
        board.repaint();
    }

    private void eatFreeCell(int x, int y) {
        if(x < 0 || x >= column || y < 0 || y >= row) {
            return;
        }
        stateBoard[y][x].setOpen(true);
        countCellOpen++;
        if(y - 1 >= 0 && stateBoard[y - 1][x].isBom()) {
            return;
        }
        if(y + 1 < row && stateBoard[y + 1][x].isBom()) {
            return;
        }
        if(x - 1 >= 0 && stateBoard[y][x - 1].isBom()) {
            return;
        }
        if(x + 1 < column && stateBoard[y][x + 1].isBom()) {
            return;
        }
        if(y - 1 >= 0 && x - 1 >= 0 && stateBoard[y - 1][x - 1].isBom()) {
            return;
        }
        if(y - 1 >= 0 && x + 1 < column && stateBoard[y - 1][x + 1].isBom()) {
            return;
        }
        if(y + 1 < row && x + 1 < column && stateBoard[y + 1][x + 1].isBom()) {
            return;
        }
        if(y + 1 < row && x - 1 >= 0 && stateBoard[y + 1][x - 1].isBom()) {
            return;
        }

        if(y - 1 >= 0  && !stateBoard[y - 1][x].isOpen()) {
            eatFreeCell(x, y - 1);
        }
        if(y + 1 < row && !stateBoard[y + 1][x].isOpen()) {
            eatFreeCell(x, y + 1);
        }
        if(x - 1 >= 0 && !stateBoard[y][x - 1].isOpen()) {
            eatFreeCell(x - 1, y);
        }
        if(x + 1 < column && !stateBoard[y][x + 1].isOpen()) {
            eatFreeCell(x + 1, y);
        }
        if(x - 1 >= 0 && y - 1 >= 0 && !stateBoard[y - 1][x - 1].isOpen()) {
            eatFreeCell(x - 1, y - 1);
        }
        if(x + 1 < column && y - 1 >= 0 && !stateBoard[y - 1][x + 1].isOpen()) {
            eatFreeCell(x + 1, y - 1);
        }
        if(x + 1 < column && y + 1 < row && !stateBoard[y + 1][x + 1].isOpen()) {
            eatFreeCell(x + 1, y + 1);
        }
        if(x - 1 >= 0 && y + 1 < row && !stateBoard[y + 1][x - 1].isOpen()) {
            eatFreeCell(x - 1, y + 1);
        }
    }

    public static void main(String args[]) throws IOException, URISyntaxException {
        Icons.loadIcon();
        Game game = new Game();
    }
}




















