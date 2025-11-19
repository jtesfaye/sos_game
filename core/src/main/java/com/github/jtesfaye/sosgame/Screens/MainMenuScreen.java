package com.github.jtesfaye.sosgame.Screens;

import com.badlogic.gdx.Game;
import com.github.jtesfaye.sosgame.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.jtesfaye.sosgame.util.*;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

public class MainMenuScreen implements Screen {

    private final Stage stage;
    private final Main game;

    public MainMenuScreen(Main game) {

        this.game = game;
        MenuInitializer menu = new MenuInitializer();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        stage = menu.createStage(skin);

        menu.getStartButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                String sizeSelected = menu.getBoardSizeChoice().getSelected();
                String modeSelected = menu.getModeChoice().getSelected();
                String p1Choice = menu.getP1Choice().getSelected();
                String p2Choice = menu.getP2Choice().getSelected();
                String player1Color = menu.getPlayer1ColorChoice().getSelected();
                String player2Color = menu.getPlayer2ColorChoice().getSelected();
                String difficulty = "";

                if (menu.getDifficultyChoice() != null) {
                    difficulty = menu.getDifficultyChoice().getSelected();
                }
                game.setQueue(new ConcurrentLinkedQueue<>());
                game.setProcessor(new GameEventProcessor(game.getQueue()));

                NewGameInit init = GameInitializer.initGame(
                    sizeSelected,
                    modeSelected,
                    p1Choice,
                    p2Choice,
                    player1Color,
                    player2Color,
                    difficulty,
                    game);

                game.setEventProcessorThread(Executors.newSingleThreadExecutor());
                game.getEventProcessorThread().execute(() -> {

                    try {

                        game.getProcessor().run();

                    } catch (InterruptedException e) {
                        return;
                    }
                });
                game.setScreen(init.screen);
            }
        });

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

}
