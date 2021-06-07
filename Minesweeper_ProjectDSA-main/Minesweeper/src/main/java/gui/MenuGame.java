package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuGame extends JPanel implements ActionListener {

    ChooseBoardListener chooseBoardListener;

    public MenuGame() {

        this.setLayout(new GridLayout(3, 1, 0, 10));
        this.setPreferredSize(new Dimension(200, 150));
        initComponents();
    }

    private void initComponents() {
        JButton btn8x8 = new JButton("8x8");
        btn8x8.setActionCommand("PLAY_8x8");
        btn8x8.addActionListener(this);
        this.add(btn8x8);

        JButton btn16x16 = new JButton("16x16");
        btn16x16.setActionCommand("PLAY_16x16");
        btn16x16.addActionListener(this);
        this.add(btn16x16);

        JButton btn30x16 = new JButton("30x16");
        btn30x16.setActionCommand("PLAY_30x16");
        btn30x16.addActionListener(this);
        this.add(btn30x16);
    }

    public void setChooseBoardListener(ChooseBoardListener chooseBoardListener) {
        this.chooseBoardListener = chooseBoardListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "PLAY_8x8":
                chooseBoardListener.chooseBoardPerformed(8, 8, 10);
                break;
            case "PLAY_16x16":
                chooseBoardListener.chooseBoardPerformed(16, 16, 40);
                break;
            case "PLAY_30x16":
                chooseBoardListener.chooseBoardPerformed(16, 30, 99);
                break;
        }
    }
}
