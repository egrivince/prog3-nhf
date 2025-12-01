package org.example;

import java.io.Serializable;

/**
 * Contains the information about a tile on the board.
 */
public class Tile implements Serializable{
    /**The position of the tile on the board. */
    private Pos pos;
    /**The piece that might be on the tile, null if there is no piece. */
    private Piece piece;

    /**Constructor, sets the position of the tile.
     * @param pos the position of the tile.
     */
    public Tile(Pos pos) {
        this.pos = pos;
    }

    /**
     * Static fucntion that returns true if the 2 given tiles are adjacent.
     * @param tile1 one tile
     * @param tile2 the other tile
     */
    public static boolean areAdjacentTiles(Tile tile1, Tile tile2) {
        int dCol = Math.abs(tile1.getCol() - tile2.getCol());
        int dRow = Math.abs(tile1.getRow() - tile2.getRow());
        if(tile1.getCol() == Pos.COL_SPEC || tile2.getCol() == Pos.COL_SPEC) { //one of the tiles is a goal tile, just have to check dRow
            return dRow == 1;
        } 
        return (dRow==0 && dCol==1) || (dRow==1 && dCol==0); //check if they are adjacent tiles

    }

    /**Returns a String for debugging. */
    @Override
    public String toString() {
        if(piece == null) return ("("+getRow()+","+getCol()+"_"+"."+")");
        return ("("+getRow()+","+getCol()+"_"+piece+")");
    }
    /**Returns a String for debugging on the console. */
    public String toStringConsole() {
        if(piece == null) return ".";
        else return piece.toString();
    }
    /**Setter for piece. */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    /**Getter for piece. */
    public Piece getPiece() {
        return this.piece;
    }
    /**Getter for the position's row. */
    public int getRow() {
        return pos.row;
    }
    /**Getter for the position's column. */
    public int getCol() {
        return pos.col;
    }
    /**Getter for the position of the tile. */
    public Pos getPos() {
        return pos;
    }
}
