package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class GameButton extends JButton {
    /*private GuiListener guiListener;
    public void setGuiListener(GuiListener l) {
        this.guiListener = l;
    }*/

    public GameButton(String text) {
        super(text);
        //setGuiListener(l);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setFocusable(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setBackground(Params.BUTTON_NORMAL);
        setFont(Params.BUTTON_FONT);
        setAlignmentX(0.5f);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Params.BUTTON_ENTERED);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Params.BUTTON_NORMAL);
            }
            /*@Override
            public void mousePressed(MouseEvent e) {
                setBackground(Color.BLACK);
            }*/
        });

        


    }
}
