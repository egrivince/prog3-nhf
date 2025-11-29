package org.example;

import java.util.List;

public interface BoardReadOnly {
    public Tile getTile(Pos pos);
    public List<List<Tile>> getWholeBoard();
    public Tile getTopGoalTile();
    public Tile getBottomGoalTile();
}
