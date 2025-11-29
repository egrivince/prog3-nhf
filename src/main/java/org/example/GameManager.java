package org.example;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements GuiListener {
    private GameListener gameListener;
    public void setgameListener(GameListener l) {
        gameListener = l;
    }
    
    private Board board;
    private Move currentMove;
    private Player currentPlayer;
    private boolean aiMode = false;
    private boolean gameOver;
    
    public GameManager() {

    }

    public void newGame() {
        System.out.println("new game started");
        board = new Board();
        board.boardStartPosition();
        board.print();
        currentPlayer = Player.BOTTOM;
        gameOver = false;
        gameListener.setActiveRow(Player.BOTTOM, 5); //bottom player;
        gameListener.setActiveRow(Player.TOP, 0); //top player;
        gameListener.setTriangleColors(currentPlayer);
        gameListener.startNewDrag();
        gameListener.boardChanged(board);


        ThinkingMachine tm = new ThinkingMachine(board);
        List<MoveSegment> l = tm.legalMoveSegments(board.getTile(Pos.at(0,3)).getPiece());
        for(MoveSegment ms : l) {
            for(Tile t : ms.getTiles()) {
                System.out.print(t+"_");
            }
            System.out.println("new");
        }
        
    }

    public Player checkWin() {
        if(board.getBottomGoalTile().getPiece() != null) { //if the bottom goal tile is occupied by a piece, top wins
            return Player.TOP;
        }
        else if(board.getTopGoalTile().getPiece() != null) { //if the top goal tile is occupied by a piece, bottom wins
            return Player.BOTTOM;
        }
        return null;
    }
    public Board getBoard() {
        return board;
    }
    public Player nextPlayer() {
        if(currentPlayer == Player.BOTTOM) return Player.TOP;
        else return Player.BOTTOM;
    }

    public void nextMove() { //if its player vs player just 
        if(!aiMode) {
            currentMove = null;
            currentPlayer = nextPlayer(); //change the currentplayer to the other
        }
        else {
            ThinkingMachine thinkingMachine = new ThinkingMachine(board);
            currentMove = thinkingMachine.getBestMove();
            executeMove();
            currentMove = null;
            currentPlayer = nextPlayer(); //change the currentplayer to the other
        }
    }
    

    public void executeMove() {
        if(!currentMove.isValid()) { //this valid check doesnt execute at all i think, executeMove only gets called when the move is already checked
            System.out.println("NOT VALID MOVE!");
            currentMove = null;
            gameListener.startNewDrag();
            //gameListener.boardChanged(board);
            return;
        }
        //check if the player is moving a piece on their active row, not checked in move.isvalid, only here
        int activeRow = board.activeRow(currentPlayer);
        if(!board.isPieceActive(currentMove.getPiece(), currentPlayer)) {
            System.out.println("player"+currentPlayer+" actriveRow="+activeRow);
            System.out.println("not on the active row, but on row "+currentMove.getPiece().getTile().getRow()+", illegal move for player"+currentPlayer);
            currentMove = null;
            gameListener.startNewDrag();
            gameListener.boardChanged(board); //to update the tiles being transparent even if the move was legal but by the wrong piece
            return;
        }

        //the move is valid from here
        Tile endTile = currentMove.getLastMoveSegment().getTiles().getLast();
        Tile tempTile = new Tile(Pos.at(-2, -2)); //has nothing to do with the board, just to not mess up piece swaps
        if(currentMove.getKnockTile() == null) { //its just a normal move with jumping
            board.movePiece(currentMove.getPiece(), endTile);
        }
        else { //its a move with jumping and knocking
            board.movePiece(endTile.getPiece(), tempTile);
            board.movePiece(currentMove.getPiece(), endTile);
            board.movePiece(tempTile.getPiece(), currentMove.getKnockTile());
        }
        board.print();
        //currentMove.debuglists();
        gameListener.boardChanged(board);
        gameListener.setActiveRow(nextPlayer(), board.activeRow(nextPlayer()));
        gameListener.setActiveRow(currentPlayer, board.activeRow(currentPlayer));
        gameListener.setTriangleColors(nextPlayer());

        Player winnerPlayer = checkWin();
        if(winnerPlayer != null) {
            if(winnerPlayer == Player.BOTTOM) {
                System.out.println("BOTTOM WINS!");
            }
            else if(winnerPlayer == Player.TOP) {
                System.out.println("TOP WINS!");
            }
            gameOver = true;
        }

        nextMove(); // set currentmove to null, change players, make the ai move
    }
    
    //
    //
    //
    //
    //
    //mouse listener functions
    @Override
    public void clickedOnTile(Pos pos) {
        if(gameOver) return;

        System.out.println("("+pos.row+","+pos.col+") clicked using listener interface");

        if(currentMove == null) { //clicking only matters if there is already a piece jumping over another piece waiting for the move to be continued or the piece knocked out
            return;
        }
        if(board.getTile(pos).getPiece() != null && board.getTile(pos) != currentMove.getPiece().getTile()) { //if there isnt a piece on the tile you are trying to knock the other to or its the starting piece
            return;
        }
        //you cant knock a piece behind the enemys active row
        //System.out.println("row:"+row+"activerow top:");
        if (currentPlayer == Player.BOTTOM && pos.row < board.activeRow(Player.TOP)) { //bottom player
            System.out.println("cant knock a piece to behind the enemys active row");
            gameListener.logMessage("cant knock a piece to behind the enemys active row");
            return;
        }
        else if (currentPlayer == Player.TOP && board.activeRow(Player.BOTTOM) < pos.row) { //top player
            System.out.println("cant knock a piece to behind the enemys active row");
            gameListener.logMessage("cant knock a piece to behind the enemys active row");
            return;
        }
        currentMove.addKnockTile(board.getTile(pos));
        currentMove.getMoveSegmentList().removeLast(); //have to remove it bc when you released the mouse it added an empty new segment that waits to be filled but messes up the valid check
        executeMove();
    }

    @Override
    public void tileDrag(Pos pos) {
        if(gameOver) return;
        //System.out.println("drag"+row+"_"+col);
        Tile draggedTile = board.getTile(pos);

        if(pos.col == Pos.COL_SPEC) { //special tiles
            goalTileMouseDrag(pos);
            return;
        }

        //start of the new move
        if(currentMove == null) { 
            if(draggedTile.getPiece() == null) {
                //System.out.println("move from an empty tile!");
                return;
            }
            //board.getTile(row, col).setPiece(null); //set the tile's piece to null so it counts as empty, but the piece's tile stays so i can read that
            if(!board.isPieceActive(draggedTile.getPiece(), currentPlayer)) {
                return;
            }
            currentMove = new Move(currentPlayer, draggedTile.getPiece());
            currentMove.startNewSegment();
            gameListener.startNewDrag();
            gameListener.setLineColors(currentPlayer);
            gameListener.moveStarted(pos);
        }
        
        if(currentMove.getLastMoveSegment().isTilesEmpty()) { //if its a new movesegment, start a new line on the screen so the line doesnt jump (only for looks, the move would be illegal anyway)
            gameListener.newDragSegment();
        }
        //start of the new moveSegment or entered a new tile
        if(currentMove.getLastMoveSegment().isTilesEmpty() || currentMove.getLastMoveSegment().getTiles().getLast() != draggedTile) { 
            currentMove.addTileToLast(draggedTile);
            System.out.println("----------------------------------------added tile to drag:"+pos.row+"_"+pos.col);
            gameListener.addTileDragged(pos);
        }
    }

    public void goalTileMouseDrag(Pos pos) {
        //System.out.println("goal tile drag:"+pos.row);
        if(currentMove == null) { //you cant start a move from here
            return;
        }
        if(currentMove.getLastMoveSegment().getTiles().getLast() != board.getTile(pos)) {
            if(pos.row == Pos.BOTTOMGOAL) { //bottom player
                System.out.println("added bottom tile to drag");
                currentMove.addTileToLast(board.getBottomGoalTile());
            }
            else { //top player
                System.out.println("added top tile to drag");
                currentMove.addTileToLast(board.getTopGoalTile());
            }
            gameListener.addTileDragged(pos);
        }
        
    }

    
    @Override
    public void mouseDragReleased() { //row, col doesnt matter, cant detect it, because drag locks the tile
        if(gameOver) return;
        //System.out.println("mouse drag released");
        if(currentMove == null) { //
            System.out.println("empty move");
            return;
        }
        if(/*!currentMove.getLastMoveSegment().isValid()*/!currentMove.isValid()) {
            System.out.println("abort move not executed, new move started");
            gameListener.logMessage("move not executed, new move started");
            failedMouseDragRelease();
            return;
        }
        
        if(currentMove.getLastMoveSegment().getLastTile().getPiece() == null) { //if the last tile was empty
            executeMove();
            
        }
        else { //if there was already a piece on the last tile
            if(currentMove.getPiece().getTile() == currentMove.getLastMoveSegment().getLastTile()) { //if the piece looped back to its original place, it doesnt count as a tile with a piece on it
                executeMove();
                System.out.println("looped back");
                gameListener.logMessage("looped back");
            }
            else {
                currentMove.startNewSegment();
            }
            
        }
    }

    @Override
    public void failedMouseDragRelease() {
        if(gameOver) return;
        System.out.println("failed mouse drag release");
        currentMove = null;
        gameListener.startNewDrag();
        gameListener.boardChanged(board);
    }

    @Override
    public void newGamePressed() {
        newGame();
    }
    


}
