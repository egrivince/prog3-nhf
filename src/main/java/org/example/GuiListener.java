package org.example;

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
