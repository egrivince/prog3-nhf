package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**Contains one segment of a move */
public class MoveSegment implements Serializable{
    /**A list of tiles in the segment, containing the start and end tiles. */
    private List<Tile> tiles;
    /**The piece making the move. 
     * Not neccesarily at the start of the movesegment
     */
    private Piece piece;
    /**The length the segment can be. */
    private int moveLength;

    public MoveSegment(/*Piece piece*/) {
        tiles = new ArrayList<>();
    }

    /**Copy constructor.
     * Nneeded for the recursive algorithm that gets all possible movesegments.
     */
    public MoveSegment(MoveSegment other) {
        this.tiles = new ArrayList<>(other.tiles);
        //this.piece = new Piece(other.piece.getType(), new Tile(other.piece.getTile().getPos()));
        this.piece = other.piece; //not okay, not deep copy
        this.moveLength = other.moveLength;
    }

    /**Prints the movesegments data to the console for debugging. */
    public void printMovesegment() {
        for(Tile t : tiles) {
            System.out.print(t+"_");
        }
    }

    /**Calculates and returns a set of pairs of tiles that the piece passed through during the movesegment. */
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

    /**Return true if the piece reached the goal tile at the end. */
    public boolean endWin() {
        return getLastTile().getCol() == Pos.COL_SPEC;
    }
    /**Returns true if the movesegment ends on a tile that is occupied.
     * Does not count the move's start as an occupied tile.
     */
    public boolean jumpAgain() {
        return getLastTile().getPiece() != null && getLastTile() != piece.getTile();
    }

    /**Returns true if the movesegment contains no tiles currently */
    public boolean isTilesEmpty() {
        return tiles.isEmpty();
    }
    /**Adds a tile to the movesegment. */
    public void addTile(Tile tile) {
        tiles.add(tile);
    }
    /**Getter for the tiles. */
    public List<Tile> getTiles() {
        return tiles;
    }
    /**Returns the last tile of the segment. */
    public Tile getLastTile() {
        if(tiles.isEmpty()) {
            System.out.println("no last tile!");
            return null;
        }
        return tiles.getLast();
    }
    /**Setter for piece. */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    /**Getter for piece. */
    public Piece getPiece() {
        return piece;
    }
    /**Setter for movelength. */
    public void setMoveLength(int moveLength) {
        this.moveLength = moveLength;
    }
    /**Getter for movelength. */
    public int getMoveLength() {
        return moveLength;
    }
}
