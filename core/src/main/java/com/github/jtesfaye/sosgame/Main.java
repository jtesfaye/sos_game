package com.github.jtesfaye.sosgame;

import com.badlogic.gdx.Game;
import com.github.jtesfaye.sosgame.GameEvent.*;
import com.github.jtesfaye.sosgame.Screens.MainMenuScreen;
import com.github.jtesfaye.sosgame.util.GameEventProcessor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Game {

    @Getter
    @Setter
    private ConcurrentLinkedQueue<GameEvent> queue;

    @Getter
    @Setter
    private GameEventProcessor processor;

    @Getter
    @Setter
    private ExecutorService eventProcessorThread;

    @Override
    public void create() {

        setScreen(new MainMenuScreen(this));

    }
}
