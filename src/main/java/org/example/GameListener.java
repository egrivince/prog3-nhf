package org.example;

public interface GameListener {
    public void boardChanged(BoardReadOnly board);
    public void addTileDragged(Pos pos);
    public void newDragSegment();
    public void startNewDrag();
    public void moveStarted(Pos pos);
    public void setActiveRow(Player player, int row);
    public void setTriangleColors(Player player);
    public void setLineColors(Player player);
    public void logMessage(String message);
}
