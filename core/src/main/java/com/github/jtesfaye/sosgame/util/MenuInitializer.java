package com.github.jtesfaye.sosgame.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.jtesfaye.sosgame.Screens.MainMenuScreen;
import lombok.Getter;

import java.util.ArrayList;

public class MenuInitializer {

    private final String[] colors = {"RED", "BLUE", "PURPLE", "GREEN", "GOLD"};

    @Getter
    private TextButton startButton;

    @Getter
    private SelectBox<String> boardSizeChoice;

    @Getter
    private SelectBox<String> modeChoice;

    @Getter
    private SelectBox<String> opponentChoice;

    @Getter
    private SelectBox<String> player1ColorChoice;

    @Getter
    private SelectBox<String> player2ColorChoice;

    private Label p2ColorLabel;

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

        table.add(initGameModeLabel(skin)).padRight(10);
        modeChoice = new SelectBox<>(skin);
        modeChoice.setItems("General", "Simple");
        table.add(modeChoice).padBottom(15);
        table.row();

        table.add(initOpponentTypeLabel(skin)).padRight(10);
        opponentChoice = new SelectBox<>(skin);
        opponentChoice.setItems("Computer", "Human", "LLM");
        table.add(opponentChoice).padBottom(15);
        table.row();

        table.add(initColorSelectLabel(skin, 1)).padRight(10);
        player1ColorChoice = new SelectBox<>(skin);
        player1ColorChoice.setItems(colors);
        table.add(player1ColorChoice).padBottom(15);
        table.row();


        opponentChoice.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if ("Human".equals(opponentChoice.getSelected())) {

                    if (player2ColorChoice != null) {
                        return;
                    }

                    if (startButton.hasParent()) startButton.remove();

                    table.row();

                    p2ColorLabel = initColorSelectLabel(skin, 2);
                    table.add(p2ColorLabel).padRight(10);
                    player2ColorChoice = new SelectBox<>(skin);
                    setPlayer2ColorChoice();
                    table.add(player2ColorChoice).padBottom(15);
                    table.row();

                    table.add(startButton).colspan(2).padTop(20);

                } else {

                    if (player2ColorChoice == null) return;
                    if (player2ColorChoice.hasParent()) {
                        player2ColorChoice.remove();
                        p2ColorLabel.remove();
                        player2ColorChoice = null;
                        p2ColorLabel = null;
                    }
                }
            }
        });

        player1ColorChoice.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (player2ColorChoice == null) {
                    return;
                }
                setPlayer2ColorChoice();
            }
        });

        startButton = new TextButton("Start game", skin);

        table.add(startButton).colspan(2).padTop(20);

        return stage;
    }

    public static TextButton getMainMenuButton(Skin skin, Game game, Screen s) {

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

    private Label initOpponentTypeLabel(Skin skin) {
        return new Label("Select opponent: ", skin);
    }

    private Label initColorSelectLabel(Skin skin, int playerNum) {
        return new Label("Player " + playerNum + " select a color:", skin);
    }

    private void setPlayer2ColorChoice() {

        ArrayList<String> p2Colors = new ArrayList<>();

        for (int i = 0; i < colors.length; i++) {

            if (player1ColorChoice.getSelected().equals(colors[i])) {
                continue;
            }

            p2Colors.add(colors[i]);

        }

        player2ColorChoice.setItems(p2Colors.toArray(new String[0]));
    }
}
