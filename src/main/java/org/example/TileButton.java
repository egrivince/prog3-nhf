package org.example;

import java.awt.Color;

import javax.swing.JButton;

public class TileButton extends JButton {
    private int row;
    private int col;


    public TileButton(int r, int c) {
        this.row = r;
        this.col = c;
        String buttonstr = "("+row+","+col+")";
        this.setText(buttonstr);
        
        this.setBackground(Color.ORANGE);
        this.setOpaque(true);
        this.setFocusable(false);
        this.setBorderPainted(false); 
        this.setFocusPainted(false);
    }

    
}
