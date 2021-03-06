package gui;

import game.Cell;
import icons.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Board extends JPanel implements MouseMotionListener, MouseListener, GameListener {

    private static final int PADDING = 2;
    private static final int WIDTH_CELL = 30;
    private static final Color COLOR_CELL_NOT_OPEN = new Color(147, 151, 158);
    private static final Color COLOR_CELL_OPEN = new Color(225, 229, 237);
    private static final Color COLOR_MOVE_HINT = new Color(225, 229, 237, 100);

    private Cell stateBoard[][] = null;

    private int xMove = -1;
    private int yMove = -1;
    private int xLast = -1;
    private int yLast = -1;

    private ActionBoardListener actionBoardListener;

    private Image[] imageNums = null;

    private boolean isDrawEndGame = false;

    public Board() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        imageNums = Icons.getArrayNum();
    }

    public void setStateBoard(Cell[][] stateBoard) {
        this.stateBoard = stateBoard;

        int heightBoard = stateBoard.length*(WIDTH_CELL + PADDING);
        int widthBoard = stateBoard[0].length*(WIDTH_CELL + PADDING);

        this.setPreferredSize(new Dimension(widthBoard, heightBoard));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Draw cell
        for (int i = 0; i < stateBoard.length; i++) {
            for (int j = 0; j < stateBoard[i].length; j++) {
                if (stateBoard[i][j].isMark()) {
                    // Draw flag
                    g2.drawImage(Icons.getFlagIcon(), (PADDING + WIDTH_CELL) * j, (PADDING + WIDTH_CELL) * i,
                            WIDTH_CELL, WIDTH_CELL, null);
                } else if (stateBoard[i][j].isOpen() && !stateBoard[i][j].isBom()) {
                    // Draw normal cell
                    g2.setColor(COLOR_CELL_OPEN);
                    g2.fillRect((PADDING + WIDTH_CELL) * j, (PADDING + WIDTH_CELL) * i,
                            WIDTH_CELL, WIDTH_CELL);
                    if (stateBoard[i][j].getValue() > 0) {
                        g2.drawImage(imageNums[stateBoard[i][j].getValue() - 1], (PADDING + WIDTH_CELL) * j, (PADDING + WIDTH_CELL) * i,
                                WIDTH_CELL, WIDTH_CELL, null);
                    }
                }
                else {
                    g2.setColor(COLOR_CELL_NOT_OPEN);
                    g2.fillRect((PADDING + WIDTH_CELL) * j, (PADDING + WIDTH_CELL) * i,
                            WIDTH_CELL, WIDTH_CELL);
                }
            }
        }

        //Draw hint move
        if (xMove != -1 && yMove != -1) {
            g2.setColor(COLOR_MOVE_HINT);
            g2.fillRect((PADDING + WIDTH_CELL) * xMove, (PADDING + WIDTH_CELL) * yMove,
                    WIDTH_CELL, WIDTH_CELL);
        }

        //Draw end game
        if(isDrawEndGame) {
            for(int i = 0; i < stateBoard.length; i++) {
                for(int j = 0; j < stateBoard[0].length; j++) {
                    if(stateBoard[i][j].isBom()){
                        //Draw bom
                        g2.drawImage(Icons.getMineIcon(), (PADDING + WIDTH_CELL) * j, (PADDING + WIDTH_CELL) * i,
                                WIDTH_CELL, WIDTH_CELL, null);
                    }
                }
            }
            // Chi draw last move neu o mo cuoi cung la bom
            if(stateBoard[yLast][xLast].isBom()) {
                g2.drawImage(Icons.getMineRedIcon(), (PADDING + WIDTH_CELL) * xLast, (PADDING + WIDTH_CELL) * yLast,
                        WIDTH_CELL, WIDTH_CELL, null);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int xCell = e.getX() / (WIDTH_CELL + PADDING);
        int yCell = e.getY() / (WIDTH_CELL + PADDING);

        xMove = xCell;
        yMove = yCell;
        if(!stateBoard[yMove][xMove].isOpen() &&
                !stateBoard[yMove][xMove].isMark()) {
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int xCell = e.getX() / (WIDTH_CELL + PADDING);
        int yCell = e.getY() / (WIDTH_CELL + PADDING);

        if(SwingUtilities.isRightMouseButton(e)) {
            actionBoardListener.onMarkCell(xCell, yCell);
            return;
        }

        actionBoardListener.onOpenCell(xCell, yCell);

    }


    public void setActionBoardListener(ActionBoardListener actionBoardListener) {
        this.actionBoardListener = actionBoardListener;
    }

    @Override
    public int onGameOver(boolean playerWin, int xLast, int yLast, int time) {
        isDrawEndGame = true;
        this.xLast = xLast;
        this.yLast = yLast;
        repaint();
        return 0;
    }

    @Override
    public void onRestartGame() {
        isDrawEndGame = false;
    }

    // Not use
    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
