package org.example;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Used to make buttons on the side.
 * Currently used for the newgame buttons and saving and loading.
 */
public class GameButton extends JButton {

    /**
     * Constructor, sets the look of the button.
     */
    public GameButton(String text) {
        super(text);

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
        });

        


    }
}
