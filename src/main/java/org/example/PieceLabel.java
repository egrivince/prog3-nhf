package org.example;

import java.awt.*;
import javax.swing.*;

/**The class for the JLabels that represent the pieces on the board. */
public class PieceLabel extends JLabel{
    private int type;

    /**Constructor, sets the look and text of the label. */
    public PieceLabel(int type) {
        this.type = type;

        this.setPreferredSize(new Dimension(50,50));
        this.setFont(new Font("Arial", Font.BOLD, 30));
        this.setText(Integer.toString(this.type));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setBackground(Params.PIECE_COLOR);
        this.setOpaque(true);
        this.setFocusable(false);
        this.setEnabled(false);
    }
    /**Setter for type. */
    public void setType(int type) {
        this.type = type;
    }
}
