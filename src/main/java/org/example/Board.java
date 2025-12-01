package org.example;

import java.io.Serializable;
import java.util.*;

public class Board implements BoardReadOnly, Serializable{
    private List<List<Tile>> board;
    private Tile bottomGoalTile;
    private Tile topGoalTile;

    //ctor
    public Board() {
        board = new ArrayList<>();
        for(int row=0; row<6; row++) {
            List<Tile> tileRow = new ArrayList<>();
            for(int col=0; col<6; col++) {
                tileRow.add(new Tile(Pos.at(row, col)));
            }
            board.add(tileRow);
        }
        bottomGoalTile = new Tile(Pos.at(Player.BOTTOM)); //bottom players closest tile
        topGoalTile = new Tile(Pos.at(Player.TOP)); //top players closest tile
    }

    //sets start position with random piece order on both sides
    public void boardStartPosition() {
        List<Integer> pieceTypes = new ArrayList<>(Arrays.asList(1,1,2,2,3,3));
        Collections.shuffle(pieceTypes);
        for(int i=0; i<6; i++) {
            Tile tile = getTile(Pos.at(0,i));
            tile.setPiece(new Piece(pieceTypes.get(i),tile));
        }
        for(int i=0; i<6; i++) {
            Tile tile = getTile(Pos.at(5,i));
            tile.setPiece(new Piece(pieceTypes.get(i),tile));
        }
    }
    //prints the boards contents to terminal for debugging
    public void print() {
        System.out.println("-------------------------------");
        System.out.println(topGoalTile.toStringConsole());
        for(List<Tile> row : board) {
            for(Tile tile : row) {
                System.out.print(tile.toStringConsole()+" ");
            }
            System.out.println();
        }
        System.out.println(bottomGoalTile.toStringConsole());
        System.out.println("-------------------------------");

        
    }

    public void movePiece(Piece piece, Tile toTile) {
        piece.getTile().setPiece(null);
        piece.setTile(toTile);
        toTile.setPiece(piece);
        
    }

    @Override
    public int activeRow(Player player) {
        if(player == Player.TOP) { //top player
            for(int row=0; row<6; row++) {
                for(int col=0; col<6; col++) {
                    if(getTile(Pos.at(row, col)).getPiece() != null) return row;
                }
            }
        }
        else { //bottom player
            for(int row=5; row>0; row--) {
                for(int col=0; col<6; col++) {
                    if(getTile(Pos.at(row, col)).getPiece() != null) return row;
                }
            }
        }
        return -1; //cant reach this part
    }

    public boolean isPieceActive(Piece piece, Player player) {
        int activeRow = activeRow(player);
        return piece.getTile().getRow() == activeRow;
    }

    @Override
    public List<List<Tile>> getWholeBoard() {
        return board;
    }
    @Override
    public Tile getTile(Pos pos) {
        if(pos.col == Pos.COL_SPEC) {
            if(pos.row == Pos.BOTTOMGOAL) return bottomGoalTile;
            else return topGoalTile;
        }
        return board.get(pos.row).get(pos.col);
    }
    @Override
    public Tile getTopGoalTile() {
        return topGoalTile;
    }
    @Override
    public Tile getBottomGoalTile() {
        return bottomGoalTile;
    }

    @Override
    public List<Tile> neighborTilesList(Tile tile) {
        if(tile.getRow() == Pos.COL_SPEC) return null; //no need for this, you can only move in, not out of the goal tile
        List<Tile> neighborTilesList = new ArrayList<>();
        List<Pos> neighborPosList = Pos.neighborPosList(tile.getPos());
        for(Pos pos : neighborPosList) {
            neighborTilesList.add(getTile(pos));
        }
        return neighborTilesList;
    }

    @Override
    public List<Piece> activeRowPieces(Player player) {
        List<Piece> activeRowPieces = new ArrayList<>();
        for(int col=0; col<6; col++) {
            Tile tile = getTile(Pos.at(activeRow(player), col));
            if(tile.getPiece() != null) {
                activeRowPieces.add(tile.getPiece());
            }
        }
        return activeRowPieces;
    }


}
