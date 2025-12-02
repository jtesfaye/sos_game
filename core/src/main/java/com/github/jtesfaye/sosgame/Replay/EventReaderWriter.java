package com.github.jtesfaye.sosgame.Replay;
import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.jtesfaye.sosgame.GameEvent.GameEvent;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventReaderWriter {

    static private final ObjectMapper mapper = new ObjectMapper();

    private final ArrayList<GameEvent> events = new ArrayList<>();
    private final List<Player> players;
    private final Pair<Integer, Integer> boardDim;
    private final String gameMode;

    public EventReaderWriter(int row, int col, String gameMode, List<Player> players) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.players = players;
        boardDim = new Pair<>(row, col);
        this.gameMode = gameMode;
    }

    public void addEvent(GameEvent e) {
        events.add(e);
    }

    public void writeEvents(String fileName) throws IOException {
        File eventFile = Gdx.files.local("replays/" + fileName).file();
        eventFile.getParentFile().mkdirs();
        if (eventFile.createNewFile()) {
            mapper.writeValue(eventFile, new GameRecord(boardDim, gameMode, players, events));
        }
    }

    static public GameRecord readFromJson(String fileName) throws IOException {
        File eventFile = new File(fileName);
        return mapper.readValue(eventFile, GameRecord.class);
    }
}
