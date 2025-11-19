package GameLogic;

import com.badlogic.gdx.graphics.Color;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.GameEventProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


import com.github.jtesfaye.sosgame.GameLogic.GameLogicFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GameLogicTest {


    GameEventProcessor p = new GameEventProcessor(new ConcurrentLinkedQueue<>());
    public static Player[] players = {
        new Player(Player.Type.Human, Color.RED, "P1"),
        new Player(Player.Type.Human, Color.RED, "P2")
    };

    @Test
    public void CorrectSetPieceTest() {

        //Create a GameLogic class for every possible board size
        for (int size = 3; size < 10; size++) {

            GameLogic logic = GameLogicFactory.createGameLogic(size, size, players, "Simple", p);

            //Iterate through every position and set a piece
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    logic.applyMove(new Move(i, j, Piece.sPiece));

                    assertEquals(Piece.sPiece, logic.getPiece(i,j));
                }
            }
        }
    }

    /*
        Confirm that setPiece will not place a piece in an occupied space
     */
    @Test
    public void OccupiedSetPieceTest() {

        for (int size = 3; size < 10; size++) {

            GameLogic logic = GameLogicFactory.createGameLogic(size, size, players, "Simple", p);

            //Iterate through every position and set a piece
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    logic.applyMove(new Move(i, j, Piece.sPiece));

                    //Try to set an opposite piece after a piece has been set
                    logic.applyMove(new Move(i,j, Piece.oPiece));
                    assertEquals(Piece.sPiece, logic.getPiece(i,j));

                }
            }
        }
    }
}
