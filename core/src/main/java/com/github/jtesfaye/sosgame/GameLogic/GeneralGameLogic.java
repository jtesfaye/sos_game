package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.GameEvent.GameEvent;
import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.GameEventProcessor;
import com.github.jtesfaye.sosgame.util.Pair;

import java.util.ArrayList;
import java.util.Queue;

class GeneralGameLogic extends GameLogic{

    GeneralGameLogic(int r, int h, Player[] players, GameEventProcessor p) {
        super(r, h, players, p);
    }

    @Override
    public boolean isEndGame() {

        return this.capacity == 0;
    }

    @Override
    protected boolean checkSOS(int r, int c) {

        //General game
        ArrayList<Pair<Integer, Integer>> sosTiles;
        int prevScore = scoreArr[currentPlayerIndex];

        if ((sosTiles = checker.checkDiagonal(r, c)) != null) {
            onSOSMade(sosTiles);
        }

        if ((sosTiles = checker.checkHorizontal(r, c)) != null) {
            onSOSMade(sosTiles);
        }

        if ((sosTiles = checker.checkVertical(r, c)) != null) {
            onSOSMade(sosTiles);
        }

        return scoreArr[currentPlayerIndex] > prevScore;

    }
}
