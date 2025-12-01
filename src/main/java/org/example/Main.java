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

//todo, problems 
// A player with no valid moves must pass
// at move start move piece to temptile, and then the check become simpler becuase start tile is empty
// AI
// thinkingmachine doesnt care about the start piece having moved
// keep the moved piece a different color until the next move
// keep the knocked piece a different color until the next move
// saving to a file
// loading from a file
// dragging on a piece in the same tile throws error
// movesegments piece vs moves piece confusion
// ai can backtrack on the line, not legal so no move gets played.
