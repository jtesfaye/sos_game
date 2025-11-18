package com.github.jtesfaye.sosgame.util;

import com.github.jtesfaye.sosgame.Main;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.jtesfaye.sosgame.Main;
import com.github.jtesfaye.sosgame.Screens.MainMenuScreen;
import lombok.Getter;

import java.util.ArrayList;

public class MenuInitializer {

    private final String[] colors = {"RED", "BLUE", "PURPLE", "GREEN", "GOLD"};
    private final String[] boardSizes = {"3x3", "4x4", "5x5","6x6", "7x7", "8x8","9x9"};
    private final String[] modes = {"General", "Simple"};
    private final String[] playerTypes = {"Computer", "Human", "LLM"};
    private final String[] difficulties = {"Easy", "Medium", "Hard"};

    @Getter
    private TextButton startButton;

    @Getter
    private SelectBox<String> boardSizeChoice;

    @Getter
    private SelectBox<String> modeChoice;

    @Getter
    private SelectBox<String> p1Choice;

    @Getter
    private SelectBox<String> p2Choice;

    @Getter
    private SelectBox<String> player1ColorChoice;

    @Getter
    private SelectBox<String> player2ColorChoice;

    @Getter
    private SelectBox<String> difficultyChoice;

    public Stage createStage(Skin skin) {

        Stage stage = new Stage(new ScreenViewport());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(initTitle(skin)).colspan(2).padBottom(20);
        table.row();

        setChoice(table, initBoardSizeLabel(skin), skin, boardSizes, boardSizeChoice);
        setChoice(table, initGameModeLabel(skin), skin, modes, modeChoice);
        setChoice(table, playerSelectLabel(skin, 1), skin, playerTypes, p1Choice);
        setChoice(table, initColorSelectLabel(skin, 1), skin, colors, player1ColorChoice);
        setChoice(table, initColorSelectLabel(skin, 2), skin, colors, player2ColorChoice);
        setChoice(table, playerSelectLabel(skin, 2), skin, playerTypes, p2Choice);
        setChoice(table, getDifficultyChoiceLabel(skin), skin, difficulties, difficultyChoice);
        table.row();

        p1Choice.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (p1Choice.getSelected() == "Computer") {

                }
            }
        });

        startButton = new TextButton("Start game", skin);

        table.add(startButton).colspan(2).padTop(20);

        return stage;
    }

    public static TextButton getMainMenuButton(Skin skin, Main game, Screen s) {

        TextButton menuButton = new TextButton("Return to main menu", skin);

        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
                s.dispose();
            }
        });

        return menuButton;
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

    private Label playerSelectLabel(Skin skin, int num) {
        return new Label("Select Player " + num + ": ", skin);
    }

    private Label initColorSelectLabel(Skin skin, int playerNum) {
        return new Label("Player " + playerNum + " select a color:", skin);
    }

    private Label getDifficultyChoiceLabel(Skin skin) {
        return new Label("Select difficulty: ", skin);
    }

    private void setChoice(Table table, Label label, Skin skin, String[] choices, SelectBox<String> option) {
        table.add(label).padRight(10);
        option = new SelectBox<>(skin);
        option.setItems(choices);
        table.add(option).padBottom(15);
        table.row();
    }

}
