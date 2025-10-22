package GameIO;

import com.github.jtesfaye.sosgame.GameIO.InputHandler;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.GameLogic.GameLogicFactory;
import com.github.jtesfaye.sosgame.GameLogic.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputHandlerTest {

    @Test
    public void testHandleClick() {

        GameLogic logic = GameLogicFactory.createGameLogic(3,3, "Simple");
        InputHandler handler = new InputHandler(logic);

        handler.handleClick(0,0, false);
        handler.handleClick(1,1, true);
        handler.handleClick(2,2, false);

        assertEquals(Piece.oPiece, logic.getPiece(0,0));
        assertEquals(Piece.sPiece, logic.getPiece(1,1));
        assertEquals(Piece.oPiece, logic.getPiece(2,2));

    }

    @Test
    public void testHandleClickOnFullBoardDoesNothing() {

        GameLogic logic = GameLogicFactory.createGameLogic(3, 3, "Simple");
        InputHandler handler = new InputHandler(logic);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                logic.setPiece(row, col, Piece.sPiece);
            }
        }

        handler.handleClick(0, 0, false);
        handler.handleClick(1, 1, false);
        handler.handleClick(2, 2, false);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                assertEquals(Piece.sPiece, logic.getPiece(row, col),
                    "Expected board position (" + row + "," + col + ") to remain sPiece");
            }
        }
    }
}
