package com.github.jtesfaye.sosgame.util;

import com.github.jtesfaye.sosgame.GameIO.InputRouter;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.Screens.GameScreen;

public class NewGameConfig {

    public final GameLogic logic;
    public final InputRouter router;
    public final GameScreen screen;

    public NewGameConfig(GameLogic l, InputRouter r, GameScreen s) {
        this.logic = l;
        this.router = r;
        this.screen = s;
    }
}
