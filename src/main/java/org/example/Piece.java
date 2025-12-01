package org.example;

import java.io.Serializable;

public class Piece implements Serializable{
    private int type; //1,2,3
    private Tile tile;

    public Piece(int type, Tile tile) {
        this.type = type;
        this.tile = tile;
    }
    public int getType() {
        return type;
    }
    public Tile getTile() {
        return tile;
    }
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    @Override
    public String toString() {
        return String.valueOf(type);
    }
}
