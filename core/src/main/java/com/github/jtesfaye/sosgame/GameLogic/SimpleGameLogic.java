package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.GameEvent.GameEvent;

import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;
import com.github.jtesfaye.sosgame.GameEvent.scoreChangeEvent;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.GameEventProcessor;
import com.github.jtesfaye.sosgame.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.stream.IntStream;

class SimpleGameLogic extends GameLogic {

    SimpleGameLogic(int r, int h, Player[] players, GameEventProcessor p) {
        super(r, h, players, p);
    }

    @Override
    public boolean isEndGame() {

        return Arrays.stream(scoreArr).sum() > 0 || capacity == 0;
    }

    @Override
    protected boolean checkSOS(int r, int c) {

        //Simple game
        ArrayList<Pair<Integer, Integer>> tiles;

        if ((tiles = checker.checkDiagonal(r, c)) != null) {
            onSOSMade(tiles);
            return false;
        }

        if ((tiles = checker.checkHorizontal(r, c)) != null) {
            onSOSMade(tiles);
            return false;
        }

        if ((tiles = checker.checkVertical(r, c)) != null) {
            onSOSMade(tiles);
            return false;
        }

        return false;
    }
}
