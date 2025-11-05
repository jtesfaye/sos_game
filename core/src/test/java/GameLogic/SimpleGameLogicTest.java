package GameLogic;

import com.github.jtesfaye.sosgame.GameEvent.GameEvent;
import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;
import com.github.jtesfaye.sosgame.GameEvent.TieEvent;
import com.github.jtesfaye.sosgame.GameEvent.WinnerEvent;
import com.github.jtesfaye.sosgame.GameLogic.GameLogicFactory;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;

import com.github.jtesfaye.sosgame.GameLogic.Piece;
import com.github.jtesfaye.sosgame.GameLogic.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class SimpleGameLogicTest {

    public GameLogic logic = GameLogicFactory.createGameLogic(3, 3, "Simple", "Human");

    @Test
    public void Player1WinnerTest() {

        GameEvent e = null;

        //Simulate game
        logic.setPiece(0,0 , Piece.sPiece); //Player 1
        e = logic.getChanges(); //PieceSetEvent

        logic.setPiece(1,0 , Piece.oPiece); //Player 2
        e = logic.getChanges(); //PieceSetEvent

        logic.setPiece(2,0 , Piece.sPiece); //PLayer 1 should score with this move
        e = logic.getChanges(); //PieceSetEvent
        e = logic.getChanges(); //SOSMadeEvent

        e = logic.getChanges(); //Should be a WinnerEvent

        assertInstanceOf(WinnerEvent.class, e);

        WinnerEvent w = (WinnerEvent) e;
        assertEquals("Player 1", w.player.toString());

    }

    @Test
    public void Player2WinnerTest() {

        GameEvent e = null;

        //Simulate game
        logic.setPiece(0,0 , Piece.sPiece); //Player 1
        e = logic.getChanges(); //PieceSetEvent

        logic.setPiece(1,0 , Piece.oPiece); //Player 2
        e = logic.getChanges(); //PieceSetEvent

        logic.setPiece(2,2, Piece.sPiece); //PLayer 1
        e = logic.getChanges();

        logic.setPiece(2,0 , Piece.sPiece); //PLayer 2 should score with this move
        e = logic.getChanges(); //PieceSetEvent
        e = logic.getChanges(); //SOSMadeEvent

        e = logic.getChanges(); //Should be a WinnerEvent

        assertInstanceOf(WinnerEvent.class, e);

        WinnerEvent w = (WinnerEvent) e;
        assertEquals("Player 2", w.player.toString());

    }

    @Test
    public void TieGameTest() {

        GameEvent e = null;

        //Place the same piece on every tile
        logic.setPiece(0,0 , Piece.sPiece);
        e = logic.getChanges();
        logic.setPiece(1,0 , Piece.sPiece);
        e = logic.getChanges();
        logic.setPiece(2,0 , Piece.sPiece);
        e = logic.getChanges();
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

        e = logic.getChanges(); //Should be a TieEvent

        assertInstanceOf(TieEvent.class, e);

    }
}
