package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class ThinkingMachine {
    private BoardReadOnly board;
    private int activeRow;
    private Player player;
    private List<Move> okayMoves;
    private List<Move> allMoves;


    public ThinkingMachine(BoardReadOnly board, Player player) {
        this.board = board;
        this.player = player;
        activeRow = board.activeRow(player); //the ai is always the top player in P_vs_Ai mode 
        okayMoves = new ArrayList<>();
        allMoves = new ArrayList<>();
    }

    public Move getBestMove() {
        Move possibleWinMove = winMoveFinal(); //this also fills up okaymoves
        if(possibleWinMove != null) return possibleWinMove; //if its null just go ahead with a random move from okaymoves

        if(!okayMoves.isEmpty()) {
            int randomIdx = new Random().nextInt(okayMoves.size());
            Move randomOkayMove = okayMoves.get(randomIdx);
            return randomOkayMove;
        }
        else {
            int randomIdx = new Random().nextInt(allMoves.size());
            Move randomMove = allMoves.get(randomIdx);
            return randomMove;
        }
    }

    

    /*public Piece getRandomPiece() {
        int randomIdx = new Random().nextInt(board.activeRowPieces(player).size());
        Piece randomPiece = board.activeRowPieces(player).get(randomIdx);
        return randomPiece;
    }*/

    public List<MoveSegment> legalMoveSegments(Piece piece, Tile startTile, Set<Set<Tile>> tilePairs) {
        List<MoveSegment> start = new ArrayList<>();
        MoveSegment moveSegment = new MoveSegment();
        moveSegment.setPiece(piece); //this is not neccesarily on the startTile, but at the moves beginning
        moveSegment.addTile(startTile);
        start.add(moveSegment);
        return legalMoveSegmentsRecursive(piece, start, tilePairs);
    }

    public List<MoveSegment> legalMoveSegmentsRecursive(Piece piece, List<MoveSegment> current, Set<Set<Tile>> moveTilePairs) {
        if(current.isEmpty()) { //if current is empty there is a dead end, no legal moves
            return new ArrayList<>();
        }
        Piece segmentPiece = current.getFirst().getPiece(); //this is the piece at the beginning of the segment
        int maxSize = segmentPiece.getType()+1; //the first tile counts too so 1 more
        int currentSize = current.getFirst().getTiles().size(); //all movesegments are the same size
        if(currentSize >= maxSize) {
            return current; //reached the required size
        } 

        List<MoveSegment> newMoveSegmentList = new ArrayList<>();

        for(MoveSegment moveSegment : current) { //grow each movesegment by 1 if possible
            Tile last = moveSegment.getLastTile();
            List<Tile> neighborTilesList = board.neighborTilesList(last);
            for(Tile neighborTile : neighborTilesList) {
                if(neighborTile.getPiece() != null && neighborTile != piece.getTile() && currentSize != maxSize-1) { //only the last tile can only be occupied, (the start tile counts as occupied here, not fixed yet)
                    continue;
                }
                if(neighborTile.getRow() == Pos.at(player).row) { //you cant move into your own end tile
                    continue;
                }
                if(neighborTile.getRow() == Pos.at(player.other()).row && currentSize != maxSize-1) { //only the last tile can be the end tile //only maybe works for top ai player for now
                    continue;
                }
                Set<Tile> tilePair = new HashSet<>(Set.of(last, neighborTile));
                Set<Set<Tile>> moveSegmentTilePairs = moveSegment.tilePairs(); //this will contain the moves previous tile pairs, the current movesegments too
                moveSegmentTilePairs.addAll(moveTilePairs);
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

    public Move winMoveFinal() {
        List<Piece> activeRowPieces = board.activeRowPieces(player);
        for(Piece piece : activeRowPieces) {
            Move move = winMoveRecursive(new Move(player, piece));
            if(move != null) {
                System.out.println("Win move found! winmovefinal");
                move.printMove();
                return move;
            }
        }
        return null;
    }

    public Move winMoveRecursive(Move currentMove) {
        //calculate start tile
        Tile startTile;
        if(currentMove.getMoveSegmentList().isEmpty()) {
            startTile = currentMove.getPiece().getTile();
        }
        else {
            startTile = currentMove.getLastMoveSegment().getLastTile();
        }

        List<MoveSegment> moveSegmentsList = legalMoveSegments(startTile.getPiece(), startTile, currentMove.tilePairs()); // ! these movesegments are just from the middle piece !
        List<MoveSegment> jumpableSegments = new ArrayList<>();
        int okayMoveRow;
        if(player == Player.TOP) {
            okayMoveRow = activeRow+1;
        }
        else {
            okayMoveRow = activeRow-1;
        }
        System.out.println("move: ");
        currentMove.printMove();
        System.out.println("jumpables:");
        //if the last tile is empty but its 1 row ahead the active row, its an okay move
        //when choosing the best move, if there is no winning move, a move like this is chosen if possible
        for(MoveSegment moveSegment : moveSegmentsList) {
            moveSegment.setPiece(currentMove.getPiece());
            if(moveSegment.endWin()) {
                Move winMove = new Move(currentMove); //copy the current move and add the current movesegment to it
                winMove.addMovesegment(moveSegment);
                return winMove;
            }
            if(moveSegment.jumpAgain()) {
                jumpableSegments.add(moveSegment);
                moveSegment.printMovesegment();
                System.out.println("--piece"+moveSegment.getPiece().getTile());
            }
            else {
                Move move = new Move(currentMove); //copy the current move and add the current movesegment to it
                move.addMovesegment(moveSegment);
                allMoves.add(move);
                if(moveSegment.getLastTile().getRow() == okayMoveRow) { 
                    okayMoves.add(move);
                }
            }
        }//could remove the not jumpable segments here to get jumpablesegments
        

        //List<MoveSegment> jumpableSegments = moveSegmentsList.stream().filter(ms -> ms.jumpAgain()).collect(Collectors.toList());
        if(jumpableSegments.isEmpty()) return null; //if no good movesegments were found, return null we cant continue this path

        for(MoveSegment goodMoveSegment : jumpableSegments) {
            Move newMove = new Move(currentMove); //copy the current move and add the current movesegment to it
            newMove.addMovesegment(goodMoveSegment);
            Move result = winMoveRecursive(newMove); //recursion
            if(result != null) return result;
        }
        return null;
    }


}
