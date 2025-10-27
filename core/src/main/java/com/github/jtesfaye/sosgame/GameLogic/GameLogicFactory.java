package com.github.jtesfaye.sosgame.GameLogic;

public class GameLogicFactory {


    public static GameLogic createGameLogic(int row, int height, String mode, String oppType) {

        if (mode.equals("Simple")) {

            return createSimpleGameLogic(row, height, oppType);

        } else if (mode.equals("General")) {

            return createGeneralGameLogic(row, height, oppType);

        } else {
            throw new RuntimeException("Invalid game mode as argument");
        }
    }

    private static SimpleGameLogic createSimpleGameLogic(int r, int h, String oppType) {

        return new SimpleGameLogic(r, h, oppType);
    }

    private static GeneralGameLogic createGeneralGameLogic(int r, int h, String oppType) {

        return new GeneralGameLogic(r, h, oppType);
    }
}
