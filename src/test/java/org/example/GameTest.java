package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//tested functions:
//board.startposition()
//board.getTile()
//tile.getPiece()
//board.movePiece();
//board.activeRow()
//board.isPieceActive()
//board.areAdjacentTiles()
//ThinkingMachine.winMove()
//move.isValidFinal()
//move.addTiletoLast()
//neighborTilesList()
//Pos.up, down, right, left
//GameManager.executeMove()
//board.activeRowPieces()
//+getters, setters

public class GameTest {

    /*
    . . . . . .
    . . . . . .
    . . . 2 . .
    . . 3 . . .
    . . . . . .
    . . . . . .
    */
    public Board testBoard() {
        Board board = new Board();
        Tile tile1 = board.getTile(Pos.at(3,2));
        tile1.setPiece(new Piece(3, tile1));
        Tile tile2 = board.getTile(Pos.at(2,3));
        tile2.setPiece(new Piece(2, tile2));

        return board;
    }

    public Move testBoardMove() {
        Board board = testBoard();
        Pos startPos = Pos.at(2,3);
        Tile startTile = board.getTile(startPos);
        Piece startPiece = startTile.getPiece();

        Pos middlePos = Pos.at(3,2);
        Tile middleTile = board.getTile(middlePos);

        Move move = new Move(Player.BOTTOM, startPiece);
        MoveSegment moveSegment1 = new MoveSegment();
        moveSegment1.setPiece(startPiece);
        moveSegment1.addTile(startTile);
        moveSegment1.addTile(board.getTile(Pos.down(startPos)));
        moveSegment1.addTile(board.getTile(Pos.left(Pos.down(startPos))));

        MoveSegment moveSegment2 = new MoveSegment();
        moveSegment2.setPiece(startPiece);
        moveSegment2.addTile(middleTile);
        moveSegment2.addTile(board.getTile(Pos.up(middlePos)));
        moveSegment2.addTile(board.getTile(Pos.up(Pos.up(middlePos))));

        move.addMovesegment(moveSegment1);
        move.addMovesegment(moveSegment2);

        return move;
    }
    
    //board.startposition()
    //board.getTile()
    //tile.getPiece()
    @Test 
    public void boardStartPieces() {
        Board board = new Board();
        board.boardStartPosition();
        for(int col=0; col<6; col++) {
            Tile tile = board.getTile(Pos.at(0,col));
            Piece piece = tile.getPiece();
            assertNotNull(piece);
        }
        for(int row=1; row<5; row++) {
            for(int col=0; col<6; col++) {
                Tile tile = board.getTile(Pos.at(row,col));
                Piece piece = tile.getPiece();
                assertNull(piece);
            }
        }
        for(int col=0; col<6; col++) {
            Tile tile = board.getTile(Pos.at(5,col));
            Piece piece = tile.getPiece();
            assertNotNull(piece);
        }
    }

    //board.movePiece();
    @Test
    public void boardMovePiece() {
        Board board = new Board();
        board.boardStartPosition();
        Tile from = board.getTile(Pos.at(0,0));
        Tile to = board.getTile(Pos.at(3,3));
        Piece piece = from.getPiece();
        board.movePiece(piece, to);
        assertNull(from.getPiece());
        assertNotNull(to.getPiece());
    }

    //board.activeRow()
    //board.isPieceActive()
    @Test
    public void boardActiveRow() {
        Board board = new Board();
        board.boardStartPosition();
        int activeRow = board.activeRow(Player.BOTTOM);
        assertEquals(5, activeRow);
        Piece piece = board.getTile(Pos.at(0,4)).getPiece();
        boolean isActive = board.isPieceActive(piece, Player.TOP);
        assertTrue(isActive);
    }

    //board.areAdjacentTiles()
    @Test
    public void boardAdjacentTiles() {
        Board board = new Board();
        board.boardStartPosition();
        Tile tile1 = board.getTile(Pos.at(4,3));
        Tile tile2 = board.getTile(Pos.at(4,2));
        boolean areAdjacent = Tile.areAdjacentTiles(tile1, tile2);
        assertTrue(areAdjacent);
    }

    //Pos.up, down....
    @Test
    public void directions() {
        Pos pos = new Pos(5,5);
        Pos up = Pos.up(pos);
        Pos upexp = new Pos(4,5);
        assertEquals(up.col, upexp.col);
        assertEquals(up.row, upexp.row);

        Pos down = Pos.down(pos);
        Pos downexp = new Pos(Player.BOTTOM);
        assertEquals(down.col, downexp.col);
        assertEquals(down.row, downexp.row);

        Pos right = Pos.right(pos);
        //Pos rightexp = null;
        assertNull(right);

        Pos left = Pos.left(pos);
        Pos leftexp = new Pos(5,4);
        assertEquals(left.col, leftexp.col);
        assertEquals(left.row, leftexp.row);
    }

    //ThinkingMachine.winMove()
    @Test
    public void winMove() {
        Board board = testBoard();
        ThinkingMachine tm = new ThinkingMachine(board, Player.TOP);
        Move winMove = tm.winMoveFinal();
        assertNotNull(winMove);
    }

    //move.isValidFinal()
    @Test
    public void moveValid() {
        Board board = testBoard();
        Move move = testBoardMove();
        assertFalse(move.isValidFinal());

        move.addTileToLast(board.getTile(Pos.at(0,2)));
        assertTrue(move.isValidFinal());
    }

    //get best move, execute it
    //GameManager.executeMove()
    @Test
    public void executeMove() {
        GameManager gm = new GameManager();
        GuiManager gui = new GuiManager(gm);
        gm.setgameListener(gui);
        gm.newGame();
        Board board = gm.getBoard();
        ThinkingMachine tm = new ThinkingMachine(board, Player.BOTTOM); 
        Move bestMove = tm.getBestMove();
        assertTrue(bestMove.isValidFinal());
        gm.setMove(bestMove);
        gm.executeMove();
    }

    //neighborTilesList
    @Test
    public void neighborTiles() {
        Board board = new Board();
        Tile tile = board.getTile(Pos.at(0,3));
        List<Tile> neighbors = board.neighborTilesList(tile);
        List<Tile> expected = new ArrayList<>();
        expected.add(board.getTile(Pos.at(Player.TOP)));
        expected.add(board.getTile(Pos.at(0,2)));
        expected.add(board.getTile(Pos.at(1,3)));
        expected.add(board.getTile(Pos.at(0,4)));
        
        assertEquals(4, neighbors.size());
        assertTrue(neighbors.containsAll(expected));
    }

    //board.activeRowPieces()
    @Test
    public void activeRowPiecesTest() {
        Board board = new Board();
        board.boardStartPosition();
        List<Piece> activePieces = board.activeRowPieces(Player.TOP);
        for(Piece p : activePieces) {
            Tile tile = p.getTile();
            int row = tile.getRow();
            assertEquals(0, row);
        }
    }

}
