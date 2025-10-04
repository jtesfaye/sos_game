package com.github.jtesfaye.sosgame.GameLogic;

public class GameLogicFactory {


    public static GameLogic createGameLogic(int row, int height, String mode) {

        if (mode.equals("Simple")) {

            return createSimpleGameLogic(row, height);

        } else if (mode.equals("General")) {

            return createGeneralGameLogic(row, height);

        }

        return null;
    }

    private static SimpleGameLogic createSimpleGameLogic(int r, int h) {

        return new SimpleGameLogic(r, h);
    }

    private static GeneralGameLogic createGeneralGameLogic(int r, int h) {

        return new GeneralGameLogic(r, h);
    }
}
