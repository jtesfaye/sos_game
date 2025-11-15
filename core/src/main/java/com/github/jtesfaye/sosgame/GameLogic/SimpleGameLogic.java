package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.GameEvent.GameEvent;
import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.stream.IntStream;

class SimpleGameLogic extends GameLogic {

    SimpleGameLogic(int r, int h, Player[] players, Queue<GameEvent> q) {
        super(r, h, players, q);
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

            scoreArr[currentTurn] += 1;
            SOSMadeEvent event = new SOSMadeEvent(tiles, getCurrentTurn().getPlayerColor());
            this.eventQueue.add(event);
            return false;
        }

        if ((tiles = checker.checkHorizontal(r, c)) != null) {

            scoreArr[currentTurn] += 1;
            SOSMadeEvent event = new SOSMadeEvent(tiles, getCurrentTurn().getPlayerColor());
            this.eventQueue.add(event);
            return false;
        }

        if ((tiles = checker.checkVertical(r, c)) != null) {

            scoreArr[currentTurn] += 1;
            SOSMadeEvent event = new SOSMadeEvent(tiles, getCurrentTurn().getPlayerColor());
            this.eventQueue.add(event);
            return false;
        }

        return false;
    }
}
