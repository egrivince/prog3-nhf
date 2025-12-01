package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**Stores the information about a position on the board. */
public class Pos implements Serializable{
    /**The constant that means the row of the special top goal tile. */
    public static final int TOPGOAL = -1;
    /**The constant that means the row of the special bottom goal tile. */
    public static final int BOTTOMGOAL = 6;
    /**The constant that means the column of any special goal tile.
     * Cant be -1 because that messes up the left direction method.
     */
    public static final int COL_SPEC = -99; //it cant be -1 bc that messes up the left direction method

    public final int row;
    public final int col;

    /**Constructor for two integers.
     * @param row the row of the position
     * @param col the column of the position
     */
    public Pos(int row, int col) {
        this.row = row;
        this.col = col;
    }
    /**Constructor for the special goal tile positions.
     * @param player the player who is closer to the special goal tile 
     */
    public Pos(Player player) {
        if(player == Player.BOTTOM) { //BOTTOM
            this.row = Pos.BOTTOMGOAL;
            this.col = Pos.COL_SPEC;
        }
        else { //TOP
            this.row = Pos.TOPGOAL;
            this.col = Pos.COL_SPEC;
        }
    }

    /**Returns a new Pos object at the given row and column.
     * @param row the row of the position
     * @param col the column of the position
     */
    public static Pos at(int row, int col) {
        return new Pos(row, col);
    }
    /**Returns a new Pos object at the given player's goal tile.
     * @param player the player who is closer to the special goal tile 
    */
    public static Pos at(Player player) {
        return new Pos(player);
    }
    
    /**Returns true if the position is out of bounds for the board. */
    public boolean outOfBoard() {
        if(col == COL_SPEC) return false;
        return (col < 0 || 5 < col || row < 0 || 5 < row);
    }
    /**Returns the special tiles if the row matches a special tile's row, otherwise returns the original Pos object. */
    public Pos goalTileCheck() {
        if(row == TOPGOAL) return new Pos(Player.TOP);
        else if(row == BOTTOMGOAL) return new Pos(Player.BOTTOM);
        return this;
    }

    /**Returns the position one tile up. */
    public static Pos up(Pos pos) {
        Pos p = new Pos(pos.row-1, pos.col);
        p = p.goalTileCheck();
        if(p.outOfBoard()) return null;
        return p;
    }
    /**Returns the position one tile down. */
    public static Pos down(Pos pos) {
        Pos p = new Pos(pos.row+1, pos.col);
        p = p.goalTileCheck();
        if(p.outOfBoard()) return null;
        return p;
    }
    /**Returns the position one tile rigth. */
    public static Pos right(Pos pos) {
        Pos p = new Pos(pos.row, pos.col+1);
        p = p.goalTileCheck();
        if(p.outOfBoard()) return null;
        return p;
    }
    /**Returns the position one tile left. */
    public static Pos left(Pos pos) {
        Pos p = new Pos(pos.row, pos.col-1);
        p = p.goalTileCheck();
        if(p.outOfBoard()) return null;
        return p;
    }

    /**Returns a list of positions that are adjacent to the given position
     * @param pos the center position.
     */
    public static List<Pos> neighborPosList(Pos pos) {
        Pos up = Pos.up(pos);
        Pos down = Pos.down(pos);
        Pos right = Pos.right(pos);
        Pos left = Pos.left(pos);
        List<Pos> directionsList = new ArrayList<>(Arrays.asList(up, down, right, left));
        List<Pos> neighborPosList = new ArrayList<>();
        for(Pos p : directionsList) {
            if(p != null) {
                neighborPosList.add(p);
            }
        }
        return neighborPosList;
    }
}
