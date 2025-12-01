package org.example;

import java.awt.Color;
import java.awt.Font;

/**
 * Manages and contains the parameters neccessary for setting up the gui.
 * Contains static final variables that describe sizes, colors, fonts.
 */
public class Params {
    
    //you cant instantiate this class
    private Params() {}

    public static final int TILESIZE = 80;
    public static final int GAPSIZE = 5;
    public static final int BORDERSIZE = 50;
    public static final int HEIGHT = TILESIZE*6 + GAPSIZE*5 + 2*TILESIZE + 2*GAPSIZE;
    public static final int WIDTH = TILESIZE*6 + GAPSIZE*5;
    public static final int SIDEPANEL_WIDTH = 400;
 
    public static final Color BOARDCOLOR = new Color(196, 164, 132);
    public static final Color TILECOLOR = new Color(245, 222, 179);
    public static final Color ACTIVE_RED = new Color(235, 19, 19,255); //red
    public static final Color PASSIVE_RED = new Color(235, 19, 19,70); //transparent red
    public static final Color ACTIVE_BLUE = new Color(30, 84, 232,255); //blue
    public static final Color PASSIVE_BLUE = new Color(30, 84, 232,70); //transparent blue
    public static final Color LINE_RED = new Color(235, 19, 19,150);
    public static final Color LINE_BLUE = new Color(30, 84, 232,150);
    public static final Color BUTTON_NORMAL = TILECOLOR;
    public static final Color BUTTON_ENTERED = new Color(225, 208, 179);
    public static final Color PIECE_COLOR = new Color(0, 0, 0, 255);
    public static final Color ACTIVE_PIECE_COLOR = new Color(0, 0, 0, 120);

    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 20);
    public static final Font LOG_FONT = new Font("Monospaced", Font.PLAIN, 16);

}
