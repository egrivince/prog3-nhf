package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveSegment implements Serializable{
    private List<Tile> tiles;
    private Piece piece;
    private int moveLength;

    public MoveSegment(/*Piece piece*/) {
        tiles = new ArrayList<>();
    }

    //copy ctor, needed for the recursive algo that gets all possible movesegments
    public MoveSegment(MoveSegment other) {
        this.tiles = new ArrayList<>(other.tiles);
        //this.piece = new Piece(other.piece.getType(), new Tile(other.piece.getTile().getPos()));
        this.piece = other.piece; //not okay, not deep copy
        this.moveLength = other.moveLength;
    }

    /*public boolean isValid() { //check if tiles are adjacent and if it has the righth amount of moves(the piece's type)
        int size = tiles.size();
        if(piece == null) return false;
        if(size != moveLength) return false;
        for(int i=0; i<size-1; i++) {
            Tile first = tiles.get(i);
            Tile second = tiles.get(i+1);
           if(!Tile.areAdjacentTiles(first, second));
        }
        
        for(int i=1; i<size-1; i++) { //check if you are jumping over pieces mid move, but if the move you are jumping over is the startpiece it doesnt count as invalid
            Tile middleTile = tiles.get(i);
            if(middleTile.getPiece() != null && middleTile.getPiece().getTile() != piece.getTile()) {
                return false;
            }
        }

        return true;
    }*/
    public void printMovesegment() {
        for(Tile t : tiles) {
            System.out.print(t+"_");
        }
    }


    public Set<Set<Tile>> tilePairs() {
        Set<Set<Tile>> tilePairsList = new HashSet<>();
        int size = tiles.size();
        if(size <= 1) return tilePairsList;
        for(int i=0; i<size-1; i++) {
            Tile first = tiles.get(i);
            Tile second = tiles.get(i+1);
            tilePairsList.add(new HashSet<>(Set.of(first, second)));
        }
        return tilePairsList;

    }

    public boolean endWin() {
        return getLastTile().getCol() == Pos.COL_SPEC;
    }
    public boolean jumpAgain() {
        return getLastTile().getPiece() != null && getLastTile() != piece.getTile();
    }


    public boolean isTilesEmpty() {
        return tiles.isEmpty();
    }
    public void addTile(Tile tile) {
        tiles.add(tile);
    }
    public List<Tile> getTiles() {
        return tiles;
    }
    public Tile getLastTile() {
        if(tiles.isEmpty()) {
            System.out.println("no last tile!");
            return null;
        }
        return tiles.getLast();
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public Piece getPiece() {
        return piece;
    }
    public void setMoveLength(int moveLength) {
        this.moveLength = moveLength;
    }
    public int getMoveLength() {
        return moveLength;
    }
}
