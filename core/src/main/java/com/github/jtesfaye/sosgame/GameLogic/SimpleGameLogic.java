package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;
import com.github.jtesfaye.sosgame.GameObject.Player;

import java.util.Arrays;
import java.util.stream.IntStream;

class SimpleGameLogic extends GameLogic {

    SimpleGameLogic(int r, int h, Player[] players) {
        super(r, h, players);
    }

    @Override
    public boolean isEndGame() {

        return Arrays.stream(scoreArr).sum() > 0 || capacity == 0;
    }

    @Override
    protected boolean checkSOS(int r, int c) {

        //Simple game
        SOSMadeEvent event;

        if ((event = checker.checkDiagonal(r, c, getCurrentTurn())) != null) {
            scoreArr[currentTurn] += 1;
            this.eventQueue.add(event);
            return false;
        }

        if ((event = checker.checkHorizontal(r, c, getCurrentTurn())) != null) {
            scoreArr[currentTurn] += 1;
            this.eventQueue.add(event);
            return false;
        }

        if ((event = checker.checkVertical(r, c, getCurrentTurn())) != null) {
            scoreArr[currentTurn] += 1;
            this.eventQueue.add(event);
            return false;
        }

        return false;
    }
}
