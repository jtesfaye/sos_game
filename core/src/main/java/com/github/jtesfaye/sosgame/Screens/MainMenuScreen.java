package com.github.jtesfaye.sosgame.Screens;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.jtesfaye.sosgame.GameEvent.StartReplayEvent;
import com.github.jtesfaye.sosgame.GameEventProcessor;
import com.github.jtesfaye.sosgame.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.jtesfaye.sosgame.Replay.EventReaderWriter;
import com.github.jtesfaye.sosgame.Replay.GameRecord;
import com.github.jtesfaye.sosgame.Startup.GameConfigure;
import com.github.jtesfaye.sosgame.Startup.GameInitializer;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

public class MainMenuScreen implements Screen {

    private final Stage stage;
    private final Main game;
    private final Table homeScreen;
    private final Table gameConfig;
    public MainMenuScreen(Main game) {

        this.game = game;
        GameConfigure config = new GameConfigure();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        stage = new Stage(new ScreenViewport());

        homeScreen = homeScreenTable(skin);
        gameConfig = config.gameConfig(skin, game);

        stage.addActor(homeScreen);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {

        stage.dispose();
    }



    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    private Table homeScreenTable(Skin skin) {

        Table table = new Table(skin);
        table.setFillParent(true);

        TextButton newGameButton = new TextButton("New Game", skin);
        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                stage.clear();
                stage.addActor(gameConfig);
            }
        });

        table.add(newGameButton).colspan(2).padTop(20);
        table.row();

        TextButton replay = new TextButton("Replay game", skin);

        replay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {

                FileHandle startingDir = Gdx.files.local("replays/");
                FileHandle[] files = startingDir.list();

                Window window = new Window("Choose a file", skin);
                window.setModal(true);
                window.setMovable(false);
                window.setFillParent(true);

                Table fileTable = new Table(skin);
                fileTable.top();
                fileTable.defaults().fillX().pad(5);

                for (FileHandle f : files) {
                    if (!f.isDirectory() && f.extension().equals("json")) {
                        TextButton fileButton = new TextButton(f.name(), skin);

                        fileButton.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent changeEvent, Actor actor) {
                                window.remove();
                                startReplay(f);
                            }
                        });

                        fileTable.row();
                        fileTable.add(fileButton);
                    }
                }

                ScrollPane scroll = new ScrollPane(fileTable, skin);
                scroll.setFadeScrollBars(false);
                window.add(scroll).grow();
                stage.addActor(window);
            }
        });

        table.add(replay).colspan(2).padTop(20);
        table.row();

        return table;
    }

    private void startReplay(FileHandle f) {

        GameRecord record;
        try {
            record = EventReaderWriter.readFromJson(f.path());
        } catch (IOException e) {
            System.out.println("Here");
            throw new RuntimeException(e.getMessage());
        }

        game.setQueue(new ConcurrentLinkedQueue<>());
        game.setProcessor(new GameEventProcessor(game.getQueue(), true));

        GameScreen screen = GameInitializer.setupReplay(record, game);

        game.setEventProcessorThread(Executors.newSingleThreadExecutor());
        game.getEventProcessorThread().execute(() -> {

            try {

                game.getProcessor().run();

            } catch (InterruptedException e) {
                return;
            }
        });

        game.setScreen(screen);
        game.getProcessor().addReplayEvents(record.events());
        game.getProcessor().addEvent(new StartReplayEvent());
    }
}
