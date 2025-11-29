package org.example;

public interface GuiListener {
    public void clickedOnTile(Pos pos);
    public void tileDrag(Pos pos);
    public void mouseDragReleased();
    public void failedMouseDragRelease();
    public void newGamePressed();
}
