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

public class MainMenuScreen implements Screen {

    private Stage stage;
    private Skin skin;

    public MainMenuScreen(Game game) {

        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("SOS", skin);
        table.add(title).colspan(2).padBottom(20);
        table.row();

        Label boardLabel = new Label("Board Size:", skin);
        SelectBox<String> boardSizeSelect = new SelectBox<>(skin);
        boardSizeSelect.setItems("3x3", "4x4", "5x5","6x6", "7x7", "8x8","9x9");

        table.add(boardLabel).padRight(10);
        table.add(boardSizeSelect).padBottom(15);
        table.row();

        Label modeLabel = new Label("Game Mode:", skin);
        SelectBox<String> modeSelect = new SelectBox<>(skin);
        modeSelect.setItems("General", "Simple");

        table.add(modeLabel).padRight(10);
        table.add(modeSelect).padBottom(15);
        table.row();

        TextButton startButton = new TextButton("Start game", skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String sizeSelected = boardSizeSelect.getSelected();
                String modeSelected = modeSelect.getSelected();

                game.setScreen(new GameScreen(sizeSelected, modeSelected));


            }
        });

        table.add(startButton).colspan(2).padTop(20);

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
