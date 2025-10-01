package com.github.jtesfaye.sosgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.github.jtesfaye.sosgame.BoardBuilder;
import com.github.jtesfaye.sosgame.Screens.GameScreen;

public class Main extends Game {

    private BoardBuilder builder;

    @Override
    public void create() {

        setScreen(new GameScreen(3,3));

    }
}
