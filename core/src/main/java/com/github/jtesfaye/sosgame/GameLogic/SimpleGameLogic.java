package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;

import java.util.Arrays;
import java.util.stream.IntStream;

class SimpleGameLogic extends GameLogic {

    SimpleGameLogic(int r, int h, String opponent) {
        super(r, h, opponent);
    }

    @Override
    public boolean isWinner() {

        return Arrays.stream(scoreArr).sum() > 0 || capacity == 0;
    }

    @Override
    protected boolean checkSOS(int r, int c) {


        SOSMadeEvent event;

        if ((event = checker.checkDiagonal(r, c, getCurrentTurn())) != null) {
            scoreArr[currentTurn] += 1;
            this.eventQueue.add(event);
            return true;
        }

        if ((event = checker.checkHorizontal(r, c, getCurrentTurn())) != null) {
            scoreArr[currentTurn] += 1;
            this.eventQueue.add(event);
            return true;
        }

        if ((event = checker.checkVertical(r, c, getCurrentTurn())) != null) {
            scoreArr[currentTurn] += 1;
            this.eventQueue.add(event);
            return true;
        }

        return false;
    }
}
