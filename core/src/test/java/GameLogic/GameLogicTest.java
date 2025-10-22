package GameLogic;

import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.GameLogic.Piece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


import com.github.jtesfaye.sosgame.GameLogic.GameLogicFactory;

public class GameLogicTest {

    /*
        Does setPiece work as it should
     */
    @Test
    public void CorrectSetPieceTest() {

        //Create a GameLogic class for every possible board size
        for (int size = 3; size < 10; size++) {

            GameLogic logic = GameLogicFactory.createGameLogic(size, size, "Simple");

            //Iterate through every position and set a piece
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    logic.setPiece(i, j, Piece.sPiece);

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

            GameLogic logic = GameLogicFactory.createGameLogic(size, size, "Simple");

            //Iterate through every position and set a piece
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    logic.setPiece(i,j, Piece.sPiece);

                    //Try to set an opposite piece after a piece has been set
                    logic.setPiece(i,j, Piece.oPiece);
                    assertEquals(Piece.sPiece, logic.getPiece(i,j));

                }
            }
        }
    }
}
