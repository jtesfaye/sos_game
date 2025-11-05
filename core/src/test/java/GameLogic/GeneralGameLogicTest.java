package GameLogic;

import com.github.jtesfaye.sosgame.GameEvent.GameEvent;
import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;
import com.github.jtesfaye.sosgame.GameEvent.WinnerEvent;
import com.github.jtesfaye.sosgame.GameLogic.GameLogicFactory;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;

import com.github.jtesfaye.sosgame.GameLogic.Piece;
import com.github.jtesfaye.sosgame.GameLogic.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class GeneralGameLogicTest {

    public GameLogic logic = GameLogicFactory.createGameLogic(3,3, "General", "Human");

    @Test
    public void Player1WinnerTest() {

        GameEvent e = null;

        //Simulate game
        logic.setPiece(0,0 , Piece.sPiece); //Player 1
        e = logic.getChanges();

        logic.setPiece(1,0 , Piece.oPiece); //Player 2
        e = logic.getChanges();
        logic.setPiece(2,0 , Piece.sPiece); //PLayer 1 should score with this move
        e = logic.getChanges();
        e = logic.getChanges();

        //Place a piece on every other tile to signify end of game
        logic.setPiece(0,1 , Piece.sPiece);
        e = logic.getChanges();
        logic.setPiece(0,2 , Piece.sPiece);
        e = logic.getChanges();
        logic.setPiece(1,1 , Piece.sPiece);
        e = logic.getChanges();
        logic.setPiece(1,2 , Piece.sPiece);
        e = logic.getChanges();
        logic.setPiece(2,1 , Piece.sPiece);
        e = logic.getChanges();
        logic.setPiece(2,2 , Piece.sPiece);
        e = logic.getChanges();

        e = logic.getChanges();

        assertInstanceOf(WinnerEvent.class, e);

        WinnerEvent w = (WinnerEvent) e;
        assertEquals("Player 1", w.player.toString());

    }

    @Test
    public void Player2WinnerTest() {
        GameEvent e = null;

        //Simulate game
        logic.setPiece(0,0 , Piece.sPiece); //Player 1
        e = logic.getChanges();

        logic.setPiece(1,0 , Piece.oPiece); //Player 2
        e = logic.getChanges();

        logic.setPiece(0,1 , Piece.sPiece); //PLayer 1
        e = logic.getChanges();

        logic.setPiece(2,0 , Piece.sPiece); //PLayer 2 should score with this move
        e = logic.getChanges();
        e = logic.getChanges();

        logic.setPiece(1,1 , Piece.sPiece); //PLayer 1
        e = logic.getChanges();

        logic.setPiece(0,2 , Piece.sPiece); //Player 2
        e = logic.getChanges();

        logic.setPiece(1,2 , Piece.sPiece);
        e = logic.getChanges();

        logic.setPiece(2,1 , Piece.sPiece);
        e = logic.getChanges();

        logic.setPiece(2,2 , Piece.sPiece);
        e = logic.getChanges();

        e = logic.getChanges();

        assertInstanceOf(WinnerEvent.class, e);
        WinnerEvent w = (WinnerEvent) e;

        assertEquals("Player 2", w.player.toString());

    }

    @Test
    public void ScoreDoesntTransitionTurnTest() {

        String currentTurn = logic.getCurrentTurn().toString();

        logic.setPiece(0,0 , Piece.sPiece); //Player 1
        logic.setPiece(1,0 , Piece.oPiece); //Player 2
        logic.setPiece(2,0 , Piece.sPiece); //PLayer 1 should score with this move

        String turnAfterScore = logic.getCurrentTurn().toString();

        assertEquals(currentTurn, turnAfterScore);

    }
}
