package GameStrategy;

import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.GameLogic.GameLogicFactory;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.gameStrategy.MediumGameStrategy;
import com.github.jtesfaye.sosgame.util.Pair;
import com.github.jtesfaye.sosgame.util.utilFunctions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MediumGameStrategyTest {

    @Test
    public void SelectValidTileTest() {

        Piece[][] board = {
            {Piece.sPiece, Piece.oPiece, Piece.oPiece},
            {Piece.sPiece, Piece.oPiece, Piece.sPiece},
            {Piece.sPiece, Piece.sPiece, Piece.OPEN}
        };

        ArrayList<Pair<Integer, Integer>> arr = utilFunctions.getOpenPositions(board);
        assert(arr.size() == 1);
        MediumGameStrategy strat = new MediumGameStrategy();

        strat.makeMove(board);

        assertNotEquals(Piece.OPEN, board[2][2]);

    }

    @Test
    public void createSOSTest() {
        Piece[][] board = {
            {Piece.sPiece, Piece.oPiece, Piece.oPiece},
            {Piece.OPEN, Piece.oPiece, Piece.oPiece},
            {Piece.sPiece, Piece.oPiece, Piece.OPEN}
        };

        MediumGameStrategy strat = new MediumGameStrategy();

        strat.makeMove(board);

        assertEquals(Piece.oPiece, board[1][0]);


    }
}
