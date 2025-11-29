package org.example;

import java.util.ArrayList;
import java.util.List;

public class ThinkingMachine {
    private Board board;
    private int activeRow;

    public ThinkingMachine(Board board) {
        this.board = board;
        activeRow = board.activeRow(Player.TOP); //the ai is always the top player
    }

    public Move getBestMove() {
        return null;
    }

    public List<MoveSegment> legalMoveSegments(Piece piece) {
        return legalMoveSegmentsRecursive(piece, new ArrayList<>());
    }

    public List<MoveSegment> legalMoveSegmentsRecursive(Piece piece, List<MoveSegment> current) {
        if(current.isEmpty()) { //first call
            MoveSegment moveSegment = new MoveSegment();
            moveSegment.addTile(piece.getTile());
            current.add(moveSegment);
            return legalMoveSegmentsRecursive(piece, current);
        }
        int max = piece.getType()+1; //the first tile counts too so 1 more
        if(current.getFirst().getTiles().size() >= max) return current; //reached the required size
        List<MoveSegment> newList = new ArrayList<>();
        for(MoveSegment moveSegment : current) { //grow each movesegments by 1 if possible
            Tile last = moveSegment.getLastTile();
            List<Tile> neighborTilesList = board.neighborTilesList(last);
            for(Tile neighborTile : neighborTilesList) {
                MoveSegment newMoveSegment = new MoveSegment(moveSegment);
                newMoveSegment.addTile(neighborTile);
                newList.add(newMoveSegment);
            }
        }
        return legalMoveSegmentsRecursive(piece, newList);
    }


}
