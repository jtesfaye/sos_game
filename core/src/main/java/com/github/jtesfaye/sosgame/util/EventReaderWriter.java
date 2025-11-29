package com.github.jtesfaye.sosgame.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jtesfaye.sosgame.GameEvent.GameEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EventRecorder {

    private final ObjectMapper mapper = new ObjectMapper();
    private ArrayList<GameEvent> events = new ArrayList<>();
    private File eventFile;
    public EventRecorder(String fileSaveName) {
        eventFile = new File(fileSaveName);
    }

    public void addEvent(GameEvent e) {
        events.add(e);
    }

    public void writeEvents() throws IOException {
        mapper.writeValue(eventFile, events);
    }
}
