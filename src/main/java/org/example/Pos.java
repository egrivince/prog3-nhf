package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pos implements Serializable{
    public static final int TOPGOAL = -1;
    public static final int BOTTOMGOAL = 6;
    public static final int COL_SPEC = -99; //it cant be -1 bc that messes up the left direction method

    public final int row;
    public final int col;

    public Pos(int row, int col) {
        this.row = row;
        this.col = col;
    }
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

    public static Pos at(int row, int col) {
        return new Pos(row, col);
    }
    public static Pos at(Player player) {
        return new Pos(player);
    }
    

    public boolean outOfBoard() {
        if(col == COL_SPEC) return false;
        return (col < 0 || 5 < col || row < 0 || 5 < row);
    }
    public Pos goalTileCheck() {
        if(row == TOPGOAL) return new Pos(Player.TOP);
        else if(row == BOTTOMGOAL) return new Pos(Player.BOTTOM);
        return this;
    }

    public static Pos up(Pos pos) {
        Pos p = new Pos(pos.row-1, pos.col);
        p = p.goalTileCheck();
        if(p.outOfBoard()) return null;
        return p;
    }
    public static Pos down(Pos pos) {
        Pos p = new Pos(pos.row+1, pos.col);
        p = p.goalTileCheck();
        if(p.outOfBoard()) return null;
        return p;
    }
    public static Pos right(Pos pos) {
        Pos p = new Pos(pos.row, pos.col+1);
        p = p.goalTileCheck();
        if(p.outOfBoard()) return null;
        return p;
    }
    public static Pos left(Pos pos) {
        Pos p = new Pos(pos.row, pos.col-1);
        p = p.goalTileCheck();
        if(p.outOfBoard()) return null;
        return p;
    }


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
