package org.example;

import java.awt.dnd.DragGestureEvent;

public class Tile {
    private Pos pos;
    private Piece piece;

    public Tile(Pos pos) {
        this.pos = pos;
    }

    public static boolean areAdjacentTiles(Tile tile1, Tile tile2) {
        int dCol = Math.abs(tile1.getCol() - tile2.getCol());
        int dRow = Math.abs(tile1.getRow() - tile2.getRow());
        if(tile1.getCol() == Pos.COL_SPEC || tile2.getCol() == Pos.COL_SPEC) { //one of the tiles is a goal tile, just have to check dRow
            return dRow == 1;
        } 
        return (dRow==0 && dCol==1) || (dRow==1 && dCol==0); //check if they are adjacent tiles

    }

    @Override
    public String toString() {
        if(piece == null) return ("("+getRow()+","+getCol()+"_"+"."+")");
        return ("("+getRow()+","+getCol()+"_"+piece+")");
    }
    public String toStringConsole() {
        if(piece == null) return ".";
        else return piece.toString();
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public Piece getPiece() {
        return this.piece;
    }
    public int getRow() {
        return pos.row;
    }
    public int getCol() {
        return pos.col;
    }
    public Pos getPos() {
        return pos;
    }
}
