package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


/**
 * Contains the attributes of a move.
 */
public class Move implements Serializable{

    /**a list of the movesegments that make up the move */
    private List<MoveSegment> moveSegmentList;
    /**a list of pairs of tiles that the piece went through during the move */
    private List<Set<Tile>> tilePairsList;
    /**the piece that is being moved */
    private Piece piece;
    /**the tile that the last piece is being knocked to if the move ends with a knock, otherwise null */
    private Tile knockTile;
    /**the player that makes the move */
    private Player player;

    /**Constructor
     * @param player the player that makes the move
     * @param piece the piece that is being moved
     */
    public Move(Player player, Piece piece) {
        this.player = player;
        this.piece = piece;

        this.moveSegmentList = new ArrayList<>();
        this.tilePairsList = new ArrayList<>();
    }

    /**
     * Copy Constructor.
     */
    public Move(Move other) {
        this.player = other.player;
        this.piece = other.piece;

        this.moveSegmentList = new ArrayList<>(other.moveSegmentList);
        this.tilePairsList = new ArrayList<>(other.tilePairsList);
    }

    /**
     * Checks if the final move is valid.
     */
    public boolean isValidFinal() {
        if(!isValid()) return false;
        //check if the move ends on an empty tile or there was a knocked tile
        if(knockTile == null && getLastMoveSegment().getLastTile().getPiece() != null && piece.getTile() != getLastMoveSegment().getLastTile()) {
            System.out.println("you cant end the move on a tile, you have to move again");
            return false;
        }
        return true;
    }

    /**
     * Checks if the move is valid.
     */
    public boolean isValid() {
        if(piece == null) return false;
        for(MoveSegment moveSegment : moveSegmentList) {
            int size = moveSegment.getTiles().size();
            if(size != moveSegment.getMoveLength()) return false;
            for(int i=0; i<size-1; i++) {
                Tile first = moveSegment.getTiles().get(i);
                Tile second = moveSegment.getTiles().get(i+1);
                if(!Tile.areAdjacentTiles(first, second)) return false;
            }
            
            for(int i=1; i<size-1; i++) { //check if you are jumping over pieces mid move, but if the move you are jumping over is the startpiece it doesnt count as invalid
                Tile middleTile = moveSegment.getTiles().get(i);
                if(middleTile.getPiece() != null && middleTile.getPiece().getTile() != piece.getTile()) {
                    return false;
                }
            }
        }
        //check if the player is moving a piece on their active row is in gamemanager, not here
        

        //check if you go through 2 tiles  multiple times in a move
        Set<Set<Tile>> tilePairSet = new HashSet<>(tilePairsList);
        int setSize = tilePairSet.size();
        int listSize = tilePairsList.size();
        if(setSize != listSize) {
            System.out.println("you tried to go through 2 tiles  multiple times in a move");
            return false;
        }

        //check if the endpoints of the movesegments match
        for(int i=0; i<moveSegmentList.size()-1; i++) {
            Tile firstEndTile = moveSegmentList.get(i).getLastTile();
            Tile secondStartTile = moveSegmentList.get(i+1).getTiles().getFirst();
            if(firstEndTile != secondStartTile) {
                System.out.println("check if the endpoints of the movesegments match failed");
                return false;
            }
        }

        //goal tile checks
        for(MoveSegment moveSegment : moveSegmentList) {
            for(Tile tile : moveSegment.getTiles()) {
                if(tile.getCol() == Pos.COL_SPEC && tile != getLastMoveSegment().getLastTile()) {
                    System.out.println("You can only arrive on the goal tile as the last tile in a move!");
                    return false;
                }
            }
        }
        if(getLastMoveSegment().getLastTile().getCol() == Pos.COL_SPEC) {
            if(player == Player.BOTTOM && getLastMoveSegment().getLastTile().getRow() == Pos.BOTTOMGOAL) { //bottom player cant go into their own goal tile
                System.out.println("you cant go into your own goal tile! (bottom)");
                return false;
            }
            else if(player == Player.TOP && getLastMoveSegment().getLastTile().getRow() == Pos.TOPGOAL) {
                System.out.println("you cant go into your own goal tile! (top)");
                return false;
            }
        } 

        return true;
    }
    /**Returns the piece */
    public Piece getPiece() {
        return piece;
    }
    
    /**Returns the list of movesegments */
    public List<MoveSegment> getMoveSegmentList() {
        return moveSegmentList;
    }
    
    /**Returns the last movesegment */
    public MoveSegment getLastMoveSegment() {
        if(moveSegmentList.isEmpty()) return null;
        return moveSegmentList.getLast();
    }
    
    /**Adds a tile to the last movesegment.
     * @param tile the tile to add
     */
    public void addTileToLast(Tile tile) {
        //add tilepair
        
        if(!this.getLastMoveSegment().getTiles().isEmpty()) { //only add to the array if its not the first tile added to the movesegment 
            Set<Tile> newTilePair = new HashSet<>();
            newTilePair.add(this.getLastMoveSegment().getLastTile());
            newTilePair.add(tile);
            tilePairsList.add(newTilePair);
        }
        
        this.getLastMoveSegment().addTile(tile);
        
            
        
    }
    
    /**
     * Starts a new movesegment.
     */
    public void startNewSegment() {
        MoveSegment lastSegment = getLastMoveSegment(); //null if its the first segment!

        MoveSegment moveSegment = new MoveSegment();
        moveSegment.setPiece(piece);
        moveSegmentList.add(moveSegment);

        if(moveSegmentList.size() == 1) { //first movesegment is determined by the moved piece
            moveSegment.setMoveLength(piece.getType()+1);
        }
        else { // on movesegments after jumping, the length is determined by the piece jumped off from
            Piece fromPiece = lastSegment.getLastTile().getPiece();
            moveSegment.setMoveLength(fromPiece.getType()+1);
        }

    }

    /**
     * Adds all of the movesegments given.
     * Used by the ai.
     * @param moveSegmentsList the list of movesegments to add
     */
    public void addAllMovesegments(List<MoveSegment> moveSegmentsList) {
        for(MoveSegment moveSegment : moveSegmentsList) {
            addMovesegment(moveSegment);
        }
    }

    /**
     * Adds a single movesegment to the move.
     */
    public void addMovesegment(MoveSegment moveSegment) {
        startNewSegment();
        for(Tile tile : moveSegment.getTiles()) {
            addTileToLast(tile);
        }
    }
    
    /**Setter for the piece.*/
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    /**Adds a knock tile.
     * @param tile the knock tile to add
     */
    public void addKnockTile(Tile tile) {
        this.knockTile = tile;
    }
    /**Getter for the knock tile. */
    public Tile getKnockTile() {
        return this.knockTile;
    }
    /**Setter for the player. */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /*public void debuglists() {
        for(Set<Tile> tilePair : tilePairsList) {
            for(Tile tile : tilePair) {
                System.out.print("{"+tile+"}");
            }
            System.out.println("");
        }
        for(MoveSegment ms : moveSegmentList) {
            for(Tile t : ms.getTiles()) {
                System.out.print(t+"-");
            }
            System.out.println("");
        }
    }*/
    
    /**Prints the move's data to the console for debugging. */
    public void printMove() {
        for(MoveSegment ms : moveSegmentList) {
            ms.printMovesegment();
            System.out.println("");
        }
    }

    /**
     * Returns a set of pairs of tiles that the piece moved through during the move.
     */
    public Set<Set<Tile>> tilePairs() {
        Set<Set<Tile>> tilePairs = new HashSet<>();
        for(MoveSegment moveSegment : moveSegmentList) {
            tilePairs.addAll(moveSegment.tilePairs());
        }
        return tilePairs;
    }

    /**Returns the position of the end of the move. */
    public Pos to() {
        return getLastMoveSegment().getLastTile().getPos();
    }

    /**Returns the start position of the move. */
    public Pos from() {
        return moveSegmentList.getFirst().getTiles().getFirst().getPos();
    }
}
