package com.github.jtesfaye.sosgame;

import com.badlogic.gdx.Game;
import com.github.jtesfaye.sosgame.Screens.GameScreen;
import com.github.jtesfaye.sosgame.Screens.MainMenuScreen;

public class Main extends Game {

    private BoardBuilder builder;

    @Override
    public void create() {

        setScreen(new MainMenuScreen(this));

    }
}
