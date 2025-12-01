package org.example;


public class Main {
    public static void main(String[] args) {

        GameManager gm = new GameManager();
        GuiManager gui = new GuiManager(gm);

        //gui.setGuiListener(gm);
        gm.setgameListener(gui);

        gm.newGame();

    }
}

//todo, possible problems 
// A player with no valid moves must pass
// at move start move piece to temptile, and then the check become simpler becuase start tile is empty
// keep the moved piece a different color until the next move
// keep the knocked piece a different color until the next move
