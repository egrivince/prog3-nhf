package org.example;

import java.awt.*;
import javax.swing.*;


public class PieceLabel extends JLabel{
    private int type;


    public PieceLabel(int type) {
        this.type = type;

        this.setPreferredSize(new Dimension(50,50));
        this.setFont(new Font("Arial", Font.BOLD, 30));
        this.setText(Integer.toString(type));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setBackground(Params.PIECE_COLOR);
        this.setOpaque(true);
        this.setFocusable(false);
        this.setEnabled(false);
    }
}
