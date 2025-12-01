package org.example;

/**
 * The listener class of the gui.
 * These methods are called and passed to the gamemanager if there is a gui event, like mouse events or a button being pressed.
 */
public interface GuiListener {
    public void clickedOnTile(Pos pos);
    public void tileDrag(Pos pos);
    public void mouseDragReleased();
    public void failedMouseDragRelease();
    public void newGamePvPPressed();
    public void newGamePvAiPressed();
    public void newGameAivAiPressed();

    public GameManager getGameManager();
    public void loadGame(GameManager gm);
}
