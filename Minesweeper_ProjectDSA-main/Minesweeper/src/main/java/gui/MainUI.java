package gui;

import icons.Icons;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;


public class MainUI extends JFrame implements GameListener {

    MenuGame menuGame;
    Board board;

    JLabel lbCountFlag;
    JLabel lbTimePlay;

    public MainUI(MenuGame menuGame, Board board) throws HeadlessException {
        super();
        this.setTitle("Minesweeper");
        this.menuGame = menuGame;
        this.board = board;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new CardLayout(10, 10));

        this.add(menuGame, "MENUGAME");

        JPanel panelWrapBoard = new JPanel(new BorderLayout());
        panelWrapBoard.add(board, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, 10, 0, 0));
        panelWrapBoard.add(panel, BorderLayout.EAST);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel.add(panel2, BorderLayout.SOUTH);

        lbCountFlag = new JLabel();
        lbCountFlag.setIcon(new ImageIcon(Icons.getFlagIcon().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        lbCountFlag.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        lbCountFlag.setText("0");
        panel2.add(lbCountFlag);

        lbTimePlay = new JLabel();
        lbTimePlay.setIcon(new ImageIcon(Icons.getTimerIcon().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        lbTimePlay.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        lbTimePlay.setText("00:00");
        panel2.add(lbTimePlay);

        this.add(panelWrapBoard, "BOARD");

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);
    }

    public void showMenuGame() {
        CardLayout card = (CardLayout) getContentPane().getLayout();
        card.show(getContentPane(), "MENUGAME");
        board.setPreferredSize(new Dimension(200, 150));
        this.pack();
    }

    private void showBoardGame() {
        CardLayout card = (CardLayout) getContentPane().getLayout();
        card.show(getContentPane(), "BOARD");
    }

    public void setCountFlag(int countFlag) {
        lbCountFlag.setText(countFlag + "");
    }

    public void setTimePlay(int time) {
        int minute = time / 60;
        int second = time % 60;
        lbTimePlay.setText(String.format("%02d:%02d", minute, second));
    }

    private int showGameOverLose() {
        return JOptionPane.showOptionDialog(this,
                "Bạn đã thua!!!",
                "Thông báo",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Restart", "Trở về menu"}, // this is the array
                "default");
    }

    private int showGameOverWin(int time) {
        return JOptionPane.showOptionDialog(this,
                "Bạn đã thắng!!!\nThời gian: " + String.format("%02d:%02d", time/60, time%60),
                "Thông báo",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Restart", "Trở về menu"}, // this is the array
                "default");
    }

    @Override
    public void onRestartGame() {
        setTimePlay(0);
        showBoardGame();
        pack();
    }

    @Override
    public int onGameOver(boolean playerWin, int xLast, int yLast, int time) {
        // Draw over game
        // Show dialog
        if(playerWin) {
            return showGameOverWin(time);
        }
        else {
            return showGameOverLose();
        }
    }
}
