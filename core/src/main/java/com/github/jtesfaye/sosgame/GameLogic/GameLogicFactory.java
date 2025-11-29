package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.GameEvent.GameEvent;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.GameEventProcessor;

import java.util.Queue;

public class GameLogicFactory {


    public static GameLogic createGameLogic(int row, int height, Player[] players, String mode, GameEventProcessor p) {

        if (mode.equals("Simple")) {

            return new SimpleGameLogic(row, height, players, p);

        } else if (mode.equals("General")) {

            return new GeneralGameLogic(row, height, players, p);

        } else {

            throw new RuntimeException("Invalid game mode as argument");
        }
    }
}
