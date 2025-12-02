package com.github.jtesfaye.sosgame.Startup;

import com.github.jtesfaye.sosgame.GameEventProcessor;
import com.github.jtesfaye.sosgame.Main;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.jtesfaye.sosgame.Screens.MainMenuScreen;
import com.github.jtesfaye.sosgame.Screens.NewGameConfig;
import lombok.Getter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

public class GameConfigure {

    private final String[] colors = {"RED", "BLUE", "PURPLE", "GREEN", "GOLD"};
    private final String[] boardSizes = {"3x3", "4x4", "5x5","6x6", "7x7", "8x8","9x9"};
    private final String[] modes = {"General", "Simple"};
    private final String[] playerTypes = {"Human", "Computer", "LLM"};
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

    private Label difficultyLabel;

    public Table gameConfig(Skin skin, Main game) {

        Table table = new Table();
        table.setFillParent(true);

        table.add(title(skin)).colspan(2).padBottom(20);
        table.row();

        table.add(boardSizeLabel(skin)).padRight(10);
        boardSizeChoice = new SelectBox<>(skin);
        boardSizeChoice.setItems(boardSizes);
        table.add(boardSizeChoice).padBottom(15);
        table.row();

        table.add(gameModeLabel(skin)).padRight(10);
        modeChoice = new SelectBox<>(skin);
        modeChoice.setItems(modes);
        table.add(modeChoice).padBottom(15);
        table.row();

        table.add(playerSelectLabel(skin, 1)).padRight(10);
        p1Choice = new SelectBox<>(skin);
        p1Choice.setItems(playerTypes);
        table.add(p1Choice).padBottom(15);
        table.row();

        table.add(colorSelectLabel(skin, 1)).padRight(10);
        player1ColorChoice = new SelectBox<>(skin);
        player1ColorChoice.setItems(colors);
        table.add(player1ColorChoice).padBottom(15);
        table.row();

        table.add(playerSelectLabel(skin, 2)).padRight(10);
        p2Choice = new SelectBox<>(skin);
        p2Choice.setItems(playerTypes);
        table.add(p2Choice).padBottom(15);
        table.row();

        table.add(colorSelectLabel(skin, 1)).padRight(10);
        player2ColorChoice = new SelectBox<>(skin);
        player2ColorChoice.setItems(colors);
        table.add(player2ColorChoice).padBottom(15);
        table.row();

        table.row();

        p1Choice.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (p1Choice.getSelected().equals("Computer")  && difficultyChoice == null) {

                    difficultyLabel = difficultyChoiceLabel(skin);
                    table.add(difficultyLabel).padRight(10);
                    difficultyChoice = new SelectBox<>(skin);
                    difficultyChoice.setItems(difficulties);
                    table.add(difficultyChoice).padBottom(15);
                    table.row();
                } else if (!(p1Choice.getSelected().equals("Computer") || difficultyChoice == null)) {
                    difficultyChoice.remove();
                    difficultyLabel.remove();
                    difficultyChoice = null;
                    difficultyLabel = null;

                }
            }
        });

        p2Choice.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (p2Choice.getSelected().equals("Computer") && difficultyChoice == null) {

                    table.add(difficultyChoiceLabel(skin)).padRight(10);
                    difficultyChoice = new SelectBox<>(skin);
                    difficultyChoice.setItems(difficulties);
                    table.add(difficultyChoice).padBottom(15);
                    table.row();
                } else if (!(p2Choice.getSelected().equals("Computer") || difficultyChoice == null)) {
                    difficultyChoice.remove();
                    difficultyLabel.remove();
                    difficultyChoice = null;
                    difficultyLabel = null;

                }
            }
        });

        startButton = new TextButton("Start game", skin);

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                startGame(game);
            }
        });

        table.add(startButton).colspan(2).padTop(20);

        return table;
    }


    private void startGame(Main g) {

        String sizeSelected = boardSizeChoice.getSelected();
        String modeSelected = modeChoice.getSelected();
        String p1Type = p1Choice.getSelected();
        String p2Type = p2Choice.getSelected();
        String player1Color = player1ColorChoice.getSelected();
        String player2Color = player2ColorChoice.getSelected();
        String difficulty = "";

        if (difficultyChoice != null) {
            difficulty = difficultyChoice.getSelected();
        }
        g.setQueue(new ConcurrentLinkedQueue<>());
        g.setProcessor(new GameEventProcessor(g.getQueue(), false));

        NewGameConfig init = GameInitializer.initGame(
            sizeSelected,
            modeSelected,
            p1Type,
            p2Type,
            player1Color,
            player2Color,
            difficulty,
            g);

        g.setEventProcessorThread(Executors.newSingleThreadExecutor());
        g.getEventProcessorThread().execute(() -> {

            try {

                g.getProcessor().run();

            } catch (InterruptedException e) {
                return;
            }
        });

        g.setScreen(init.screen);
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

    private Label title(Skin skin) {

        return new Label("SOS", skin);
    }

    private Label boardSizeLabel(Skin skin) {

        return new Label("Board Size:", skin);
    }

    private Label gameModeLabel(Skin skin) {
        return new Label("Game Mode:", skin);
    }

    private Label playerSelectLabel(Skin skin, int num) {

        return new Label("Select Player " + num + ": ", skin);
    }

    private Label colorSelectLabel(Skin skin, int playerNum) {
        return new Label("Player " + playerNum + " select a color:", skin);
    }

    private Label difficultyChoiceLabel(Skin skin) {
        return new Label("Select difficulty: ", skin);
    }
}
