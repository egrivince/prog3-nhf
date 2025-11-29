package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        GameManager gm = new GameManager();
        GuiManager gui = new GuiManager(gm);

        //gui.setGuiListener(gm);
        gm.setgameListener(gui);

        gm.newGame();

    }
}

//todo, problems 
// new movesegment back to start tile illegal
// cehck retracking
// check active row
// add finish tiles and logic
// A player with no valid moves must pass
// at move start move piece to temptile, and then the check become simpler becuase start tile is empty
// .
