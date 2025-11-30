package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ThinkingMachine {
    private Board board;
    private int activeRow;
    Player player;

    public ThinkingMachine(Board board, Player player) {
        this.board = board;
        this.player = player;
        activeRow = board.activeRow(player); //the ai is always the top player in P_vs_Ai mode 
    }

    public Move getBestMove() {
        Piece piece = getRandomPiece();
        List<MoveSegment> moveSegmentsList = legalMoveSegments(piece, new HashSet<>());
        int randomIdx;
        MoveSegment randomMoveSegment;
        Move randomMove;
        
        do { 
            randomMove = new Move(Player.TOP, piece);
            randomIdx = new Random().nextInt(moveSegmentsList.size());
            randomMoveSegment = moveSegmentsList.get(randomIdx);
            randomMove.addAllMovesegments(new ArrayList<>(List.of(randomMoveSegment)));
        } while (!randomMove.isValidFinal());

        return randomMove;
    }

    public List<Piece> activeRowPieces() {
        List<Piece> activeRowPieces = new ArrayList<>();
        for(int col=0; col<6; col++) {
            Tile tile = board.getTile(Pos.at(activeRow, col));
            if(tile.getPiece() != null) {
                activeRowPieces.add(tile.getPiece());
            }
        }
        return activeRowPieces;
    }

    public Piece getRandomPiece() {
        int randomIdx = new Random().nextInt(activeRowPieces().size());
        Piece randomPiece = activeRowPieces().get(randomIdx);
        return randomPiece;
    }

    public List<MoveSegment> legalMoveSegments(Piece piece, Set<Set<Tile>> tilePairsList) {
        return legalMoveSegmentsRecursive(piece, new ArrayList<>(), tilePairsList);
    }

    public List<MoveSegment> legalMoveSegmentsRecursive(Piece piece, List<MoveSegment> current, Set<Set<Tile>> moveTilePairs) {
        if(current.isEmpty()) { //first call
            MoveSegment moveSegment = new MoveSegment();
            moveSegment.addTile(piece.getTile());
            current.add(moveSegment);
            return legalMoveSegmentsRecursive(piece, current, moveTilePairs);
        }
        int maxSize = piece.getType()+1; //the first tile counts too so 1 more
        int currentSize = current.getFirst().getTiles().size(); //all movesegments are the same size
        if(currentSize >= maxSize) {
            return current; //reached the required size
        } 

        List<MoveSegment> newMoveSegmentList = new ArrayList<>();

        for(MoveSegment moveSegment : current) { //grow each movesegments by 1 if possible
            Tile last = moveSegment.getLastTile();
            List<Tile> neighborTilesList = board.neighborTilesList(last);
            for(Tile neighborTile : neighborTilesList) {
                if(neighborTile.getPiece() != null && currentSize != maxSize-1) { //only the last tile can only be occupied, (the start tile counts as occupied here, not fixed yet)
                    continue;
                }
                Set<Tile> tilePair = new HashSet<>(Set.of(last, neighborTile));
                Set<Set<Tile>> moveSegmentTilePairs = moveSegment.tilePairs(); //this will contain the moves previous tile pairs, the current movesegments and the current tile too
                moveSegmentTilePairs.addAll(moveTilePairs);
                //moveSegmentTilePairs.add(tilePair);
                if(moveSegmentTilePairs.contains(tilePair)) { //checks repeating moves between 2 tiles
                    continue;
                }
                MoveSegment newMoveSegment = new MoveSegment(moveSegment);
                newMoveSegment.addTile(neighborTile);
                newMoveSegmentList.add(newMoveSegment);
            }
        }
        return legalMoveSegmentsRecursive(piece, newMoveSegmentList, moveTilePairs);
    }


}
