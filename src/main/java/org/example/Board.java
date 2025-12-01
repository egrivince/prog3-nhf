package org.example;

import java.io.Serializable;
import java.util.*;

/**
 * Manages the board's data
 */
public class Board implements BoardReadOnly, Serializable{
    private List<List<Tile>> board;
    private Tile bottomGoalTile;
    private Tile topGoalTile;

    /**
     * Constructor, doesn't add the pieces, only creates the nested list and the goal tiles.
     */
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

    /**
     * Sets the start position.
     * Creates a random order of the pieces and puts them in the same order on both sides.
     */
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

    /**
     * Prints the board to the console for debugging.
     */
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

    /**
     * Moves the piece on the board.
     * Doesn't check anything.
     * @param piece the piece that is being moved
     * @param toTile the tile the piece is being moved to
     */
    public void movePiece(Piece piece, Tile toTile) {
        piece.getTile().setPiece(null);
        piece.setTile(toTile);
        toTile.setPiece(piece);
        
    }

    /**
     * Calculates the active row of a player
     * Goes up or down on the board depending on the player
     * and finds the first non empty row and returns it. 
     * @param player this players active row will be returned
     */
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

    /**
     * Returns true if the given piece is on the active row of the player
     * @param piece the piece we are checking
     * @param palyer the player whose active row we are checking for
     */
    public boolean isPieceActive(Piece piece, Player player) {
        int activeRow = activeRow(player);
        return piece.getTile().getRow() == activeRow;
    }

    /**
     * Returns the whole board.
     */
    @Override
    public List<List<Tile>> getWholeBoard() {
        return board;
    }
    
    /**
     * Returns the tile on the given position.
     * Just to make getting to a tile shorter.
     * @param pos the tile at this position will be returned
     */
    @Override
    public Tile getTile(Pos pos) {
        if(pos.col == Pos.COL_SPEC) {
            if(pos.row == Pos.BOTTOMGOAL) return bottomGoalTile;
            else return topGoalTile;
        }
        return board.get(pos.row).get(pos.col);
    }
    
    /**
     * Returns the top goal tile.
     */
    @Override
    public Tile getTopGoalTile() {
        return topGoalTile;
    }
    
    /**
     * Returns the bottom goal tile.
     */
    @Override
    public Tile getBottomGoalTile() {
        return bottomGoalTile;
    }

    /**
     * Returns a list of tiles that are adjacent to the given tile.
     * Any tile on the top row is adjacent to the top goal tile and so on.
     * @param tile the center tile
     */
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

    /**
     * Returns a list of the pieces that are on the active row
     * @param player the player whose active row to check
     */
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
