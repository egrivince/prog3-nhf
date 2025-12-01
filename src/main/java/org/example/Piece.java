package org.example;

import java.io.Serializable;

/**Contains the data of a piece. */
public class Piece implements Serializable{
    /**The type of the piece that describes how far it can move.
     * Can be 1, 2, 3.
     */
    private int type; //1,2,3
    /**The tile the piece is currently on. */
    private Tile tile;

    /**Constructor for piece. */
    public Piece(int type, Tile tile) {
        this.type = type;
        this.tile = tile;
    }
    /**Getter for type. */
    public int getType() {
        return type;
    }
    /**Getter for tile. */
    public Tile getTile() {
        return tile;
    }
    /**Setter for tile. */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**Returns the type as a String for debugging. */
    @Override
    public String toString() {
        return String.valueOf(type);
    }
}
