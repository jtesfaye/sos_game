package com.github.jtesfaye.sosgame.util;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;

public class MenuInitializer {

    @Getter
    private TextButton startButton;

    @Getter
    private SelectBox<String> boardSizeChoice;

    @Getter
    private SelectBox<String> modeChoice;

    public Stage createStage(Skin skin) {

        Stage stage = new Stage(new ScreenViewport());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(initTitle(skin)).colspan(2).padBottom(20);
        table.row();

        table.add(initBoardSizeLabel(skin)).padRight(10);
        boardSizeChoice = new SelectBox<>(skin);
        boardSizeChoice.setItems("3x3", "4x4", "5x5","6x6", "7x7", "8x8","9x9");
        table.add(boardSizeChoice).padBottom(15);
        table.row();

        table.add(initBoardSizeLabel(skin)).padRight(10);
        modeChoice = new SelectBox<>(skin);
        modeChoice.setItems("General", "Simple");
        table.add(modeChoice).padBottom(15);
        table.row();

        startButton = new TextButton("Start game", skin);

        table.add(startButton).colspan(2).padTop(20);

        return stage;

    }

    private Label initTitle(Skin skin) {

        return new Label("SOS", skin);

    }

    private Label initBoardSizeLabel(Skin skin) {

        return new Label("Board Size:", skin);
    }

    private Label initGameModeLabel(Skin skin) {

        return new Label("Game Mode:", skin);

    }



}
