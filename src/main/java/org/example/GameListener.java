package org.example;

import java.util.List;

/**
 * The listener interface of the gamemanager.
 * Gamemanager overrides and calls these methods when a game event occures.
 */
public interface GameListener{
    public void boardChanged(BoardReadOnly board);
    public void boardChanged(BoardReadOnly board, List<Pos> changed);
    public void addTileDragged(Pos pos);
    public void newDragSegment();
    public void startNewDrag();
    public void moveStarted(Pos pos);
    public void setActiveRow(Player player, int row);
    public void setTriangleColors(Player player);
    public void setLineColors(Player player);
    public void logMessage(String message);
}
