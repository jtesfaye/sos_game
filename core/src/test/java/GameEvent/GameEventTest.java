package GameEvent;
import com.badlogic.gdx.graphics.Color;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jtesfaye.sosgame.GameEvent.*;
import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.Pair;
import com.github.jtesfaye.sosgame.util.PlayerFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class GameEventTest {

    @Test
    public void serializeDeserializeInputEventTest() {

        Move move = new Move(0, 0, Piece.sPiece);
        GameEvent e = new InputEvent(move, UUID.randomUUID());

        ObjectMapper mapper = new ObjectMapper();
        String str = e.toJson(mapper);
        GameEvent test;

        try {

            test = mapper.readValue(str, GameEvent.class);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(this);
        assertInstanceOf(InputEvent.class, test);
    }

    @Test
    public void serializeDeserializeEndEventTest() {

        GameEvent e = new EndGameEvent(null);

        ObjectMapper mapper = new ObjectMapper();
        String str = e.toJson(mapper);
        GameEvent test;

        try {

            test = mapper.readValue(str, GameEvent.class);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(this);
        assertInstanceOf(EndGameEvent.class, test);
    }

    @Test
    public void serializeDeserializeonNextTurnTest() {

        GameEvent e = new onNextTurnEvent();

        ObjectMapper mapper = new ObjectMapper();
        String str = e.toJson(mapper);
        GameEvent test;

        try {

            test = mapper.readValue(str, GameEvent.class);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(this);
        assertInstanceOf(onNextTurnEvent.class, test);
    }

    @Test
    public void serializeDeserializePieceSetEventTest() {

        GameEvent e = new PieceSetEvent(0, 0, Piece.sPiece);

        ObjectMapper mapper = new ObjectMapper();
        String str = e.toJson(mapper);
        GameEvent test;

        try {

            test = mapper.readValue(str, GameEvent.class);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(this);
        assertInstanceOf(PieceSetEvent.class, test);
    }

    @Test
    public void serializeDeserializeScoreChangeTest() {

        ArrayList<Pair<String, String>> arr = new ArrayList<>();
        Pair<String, String> pair = new Pair<>("Foo","Bar");
        arr.add(pair);
        GameEvent e = new scoreChangeEvent(arr);

        ObjectMapper mapper = new ObjectMapper();
        String str = e.toJson(mapper);
        GameEvent test;

        try {

            test = mapper.readValue(str, GameEvent.class);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(this);
        assertInstanceOf(scoreChangeEvent.class, test);
    }

    @Test
    public void serializeDeserializeSOSMadeEventTest() {

        Pair<Integer, Integer> tile1 = new Pair<>(0,0);
        Pair<Integer, Integer> tile2 = new Pair<>(0,0);
        Pair<Integer, Integer> tile3 = new Pair<>(0,0);
        GameEvent e = new SOSMadeEvent(tile1, tile2, tile3, Color.RED);

        ObjectMapper mapper = new ObjectMapper();
        String str = e.toJson(mapper);
        GameEvent test;

        try {

            test = mapper.readValue(str, GameEvent.class);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(this);
        assertInstanceOf(SOSMadeEvent.class, test);
    }

    @Test
    public void serializeDeserializeTurnChangeEventTest() {

        Player p = PlayerFactory.createPlayer("Human", Color.BLUE, "Foobario barfoo");
        GameEvent e = new turnChangeEvent(p);

        ObjectMapper mapper = new ObjectMapper();
        String str = e.toJson(mapper);
        GameEvent test;

        try {

            test = mapper.readValue(str, GameEvent.class);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(this);
        assertInstanceOf(turnChangeEvent.class, test);
    }


}
