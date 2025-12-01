package org.example;

import java.util.List;

/**
 * An interface that doesn't allow to change the board's data.
 * Used to pass the board to functions that only need to read it, like the gui.
 */
public interface BoardReadOnly {
    public Tile getTile(Pos pos);
    public List<List<Tile>> getWholeBoard();
    public Tile getTopGoalTile();
    public Tile getBottomGoalTile();
    public List<Piece> activeRowPieces(Player player);
    public int activeRow(Player player);
    public List<Tile> neighborTilesList(Tile tile);
}
