package GameLogic;

import com.badlogic.gdx.graphics.Color;
import com.github.jtesfaye.sosgame.GameEvent.GameEvent;
import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;
import com.github.jtesfaye.sosgame.GameEvent.EndGameEvent;
import com.github.jtesfaye.sosgame.GameLogic.GameLogicFactory;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;

import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.GameEventProcessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleGameLogicTest {


    public static Player[] players = {
        new Player(Player.Type.Human, Color.RED, "P1"),
        new Player(Player.Type.Human, Color.RED, "P2")
    };


    @Test
    public void Player1WinnerTest() {

        final boolean[] flag = {false};

        ExecutorService thread = Executors.newSingleThreadExecutor();

        GameEventProcessor p = new GameEventProcessor(new ConcurrentLinkedQueue<>());

        GameLogic logic = GameLogicFactory.createGameLogic(3, 3, players,"Simple", p);

        Consumer<EndGameEvent> consumer = (e) -> {
            if (e.player.equals(players[0])) {
                flag[0] = true;
            }
        };

        p.addSubscriber(EndGameEvent.class, consumer);

        thread.execute(() -> {
            try {
                p.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        //Simulate game
        logic.applyMove(new Move(0,0 , Piece.sPiece)); //Player 1
        logic.applyMove(new Move(1,0 , Piece.oPiece)); //Player 2
        logic.applyMove(new Move(2,0 , Piece.sPiece)); //PLayer 1 should score with this move

        p.stopRunning();
        thread.shutdown();
        try {
            if (thread.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                thread.shutdownNow();
            };
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(flag[0]);

    }

    @Test
    public void Player2WinnerTest() {

        final boolean[] flag = {false};

        ExecutorService thread = Executors.newSingleThreadExecutor();

        GameEventProcessor p = new GameEventProcessor(new ConcurrentLinkedQueue<>());

        GameLogic logic = GameLogicFactory.createGameLogic(3, 3, players,"Simple", p);

        Consumer<EndGameEvent> consumer = (e) -> {
            if (e.player.equals(players[0])) {
                flag[0] = true;
            }
        };

        p.addSubscriber(EndGameEvent.class, consumer);

        thread.execute(() -> {
            try {
                p.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        //Simulate game
        logic.applyMove(new Move(0, 0, Piece.sPiece)); //Player 1
        logic.applyMove(new Move(1, 0, Piece.oPiece)); //Player 2
        logic.applyMove(new Move(2, 2, Piece.sPiece)); //PLayer 1
        logic.applyMove(new Move(2, 0, Piece.sPiece)); //PLayer 2 should score with this move

        p.stopRunning();
        thread.shutdown();
        try {
            if (thread.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                thread.shutdownNow();
            };
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(flag[0]);


    }

    @Test
    public void TieGameTest() {

        final boolean[] flag = {false};

        ExecutorService thread = Executors.newSingleThreadExecutor();

        GameEventProcessor p = new GameEventProcessor(new ConcurrentLinkedQueue<>());

        GameLogic logic = GameLogicFactory.createGameLogic(3, 3, players,"Simple", p);

        Consumer<EndGameEvent> consumer = (e) -> {
            if (e.player.equals(players[0])) {
                flag[0] = true;
            }
        };

        p.addSubscriber(EndGameEvent.class, consumer);

        thread.execute(() -> {
            try {
                p.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        logic.applyMove(new Move(0, 0, Piece.sPiece));
        logic.applyMove(new Move(1, 0, Piece.sPiece));
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
            };
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        assertTrue(flag[0]);

    }
}
