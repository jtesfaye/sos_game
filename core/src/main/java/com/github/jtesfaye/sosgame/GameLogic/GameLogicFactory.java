package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.GameObject.Player;

public class GameLogicFactory {


    public static GameLogic createGameLogic(int row, int height, Player[] players, String mode) {

        if (mode.equals("Simple")) {

            return createSimpleGameLogic(row, height, players);

        } else if (mode.equals("General")) {

            return createGeneralGameLogic(row, height, players);

        } else {
            throw new RuntimeException("Invalid game mode as argument");
        }
    }

    private static SimpleGameLogic createSimpleGameLogic(int r, int h, Player[] players) {

        return new SimpleGameLogic(r, h, players);
    }

    private static GeneralGameLogic createGeneralGameLogic(int r, int h, Player[] players) {

        return new GeneralGameLogic(r, h, players);
    }
}
