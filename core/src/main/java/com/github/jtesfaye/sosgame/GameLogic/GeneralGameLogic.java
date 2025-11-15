package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.GameEvent.GameEvent;
import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.Pair;

import java.util.ArrayList;
import java.util.Queue;

class GeneralGameLogic extends GameLogic{

    GeneralGameLogic(int r, int h, Player[] players, Queue<GameEvent> q) {
        super(r, h, players, q);
    }

    @Override
    public boolean isEndGame() {

        return this.capacity == 0;
    }

    @Override
    protected boolean checkSOS(int r, int c) {

        //General game
        ArrayList<Pair<Integer, Integer>> sosTiles;
        int count = 0;

        if ((sosTiles = checker.checkDiagonal(r, c)) != null) {
            count += 1;
            SOSMadeEvent event = new SOSMadeEvent(sosTiles, getCurrentTurn().getPlayerColor());
            this.eventQueue.add(event);
        }

        if ((sosTiles = checker.checkHorizontal(r, c)) != null) {
            count += 1;
            SOSMadeEvent event = new SOSMadeEvent(sosTiles, getCurrentTurn().getPlayerColor());
            this.eventQueue.add(event);
        }

        if ((sosTiles = checker.checkVertical(r, c)) != null) {
            count += 1;
            SOSMadeEvent event = new SOSMadeEvent(sosTiles, getCurrentTurn().getPlayerColor());
            this.eventQueue.add(event);
        }

        scoreArr[currentTurn] += count;

        return count >  0;

    }
}
