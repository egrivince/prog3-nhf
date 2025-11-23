import java.util.*;

public class Board {
    private List<List<Tile>> board;

    public Board() {
        board = new ArrayList<>();
        for(int i=0; i<6; i++) {
            List<Tile> row = new ArrayList<>();
            for(int j=0; j<6; j++) {
                row.add(new Tile(i,j));
            }
            board.add(row);
        }
    }
}
