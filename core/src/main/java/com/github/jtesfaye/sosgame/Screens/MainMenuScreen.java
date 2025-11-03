package com.github.jtesfaye.sosgame.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.jtesfaye.sosgame.util.GameInitializer;
import com.github.jtesfaye.sosgame.util.MenuInitializer;

public class MainMenuScreen implements Screen {

    private final Stage stage;

    public MainMenuScreen(Game game) {

        MenuInitializer menu = new MenuInitializer();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        stage = menu.createStage(skin);

        menu.getStartButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                String sizeSelected = menu.getBoardSizeChoice().getSelected();
                String modeSelected = menu.getModeChoice().getSelected();
                String opponentSelected = menu.getOpponentChoice().getSelected();

                game.setScreen(new GameScreen(
                        GameInitializer.initGame(sizeSelected, modeSelected, opponentSelected),
                        game
                ));
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
