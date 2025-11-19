package GameLogic;

import com.badlogic.gdx.graphics.Color;
import com.github.jtesfaye.sosgame.GameEvent.GameEvent;
import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;
import com.github.jtesfaye.sosgame.GameEvent.EndGameEvent;
import com.github.jtesfaye.sosgame.GameLogic.GameLogicFactory;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;

import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.GameObject.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.util.GameEventProcessor;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class GeneralGameLogicTest {

    public static Player[] players = {
        new Player(Player.Type.Human, Color.RED, "P1"),
        new Player(Player.Type.Human, Color.RED, "P2")
    };


    @Test
    public void Player1WinnerTest() {
        GameEventProcessor p = new GameEventProcessor(new ConcurrentLinkedQueue<>());
        GameLogic logic = GameLogicFactory.createGameLogic(3, 3, players, "General", p);

        final boolean[] flag = {false};

        Consumer<EndGameEvent> consumer = (e) -> {
            if (e.player.toString().equals("Player 1")) {
                flag[0] = true;
            }
        };

        p.addSubscriber(EndGameEvent.class, consumer);

        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            try {
                p.run();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Simulate game
        logic.applyMove(new Move(0, 0, Piece.sPiece));
        logic.applyMove(new Move(1, 0, Piece.oPiece));
        logic.applyMove(new Move(2, 0, Piece.sPiece));

        logic.applyMove(new Move(0, 1, Piece.sPiece));
        logic.applyMove(new Move(0, 2, Piece.sPiece));
        logic.applyMove(new Move(1, 1, Piece.sPiece));
        logic.applyMove(new Move(1, 2, Piece.sPiece));
        logic.applyMove(new Move(2, 1, Piece.sPiece));
        logic.applyMove(new Move(2, 2, Piece.sPiece));

        p.stopRunning();
        thread.shutdown();
        try {
            if (thread.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                thread.shutdownNow();
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        assertTrue(flag[0]);
    }

    @Test
    public void Player2WinnerTest() {
        GameEventProcessor p = new GameEventProcessor(new ConcurrentLinkedQueue<>());
        GameLogic logic = GameLogicFactory.createGameLogic(3, 3, players, "General", p);

        final boolean[] flag = {false};

        Consumer<EndGameEvent> consumer = (e) -> {
            if (e.player.toString().equals("Player 2")) {
                flag[0] = true;
            }
        };

        p.addSubscriber(EndGameEvent.class, consumer);

        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            try {
                p.run();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Simulate game
        logic.applyMove(new Move(0, 0, Piece.sPiece));
        logic.applyMove(new Move(1, 0, Piece.oPiece));
        logic.applyMove(new Move(0, 1, Piece.sPiece));
        logic.applyMove(new Move(2, 0, Piece.sPiece));
        logic.applyMove(new Move(1, 1, Piece.sPiece));
        logic.applyMove(new Move(0, 2, Piece.sPiece));
        logic.applyMove(new Move(1, 2, Piece.sPiece));
        logic.applyMove(new Move(2, 1, Piece.sPiece));
        logic.applyMove(new Move(2, 2, Piece.sPiece));

        p.stopRunning();
        thread.shutdown();
        try {
            if (thread.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                thread.shutdownNow();
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        assertTrue(flag[0]);
    }

    @Test
    public void ScoreDoesntTransitionTurnTest() {
        GameEventProcessor p = new GameEventProcessor(new ConcurrentLinkedQueue<>());
        GameLogic logic = GameLogicFactory.createGameLogic(3, 3, players, "General", p);

        final String startingTurn = logic.getCurrentTurn().toString();

        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            try {
                p.run();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Simulate scoring sequence
        logic.applyMove(new Move(0, 0, Piece.sPiece));
        logic.applyMove(new Move(1, 0, Piece.oPiece));
        logic.applyMove(new Move(2, 0, Piece.sPiece));

        final String endingTurn = logic.getCurrentTurn().toString();

        p.stopRunning();
        thread.shutdown();
        try {
            if (thread.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                thread.shutdownNow();
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        assertEquals(startingTurn, endingTurn);
    }
}
