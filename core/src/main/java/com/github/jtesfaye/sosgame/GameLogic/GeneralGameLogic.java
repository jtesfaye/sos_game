package com.github.jtesfaye.sosgame.GameLogic;

import com.github.jtesfaye.sosgame.GameEvent.SOSMadeEvent;
import com.github.jtesfaye.sosgame.GameObject.Player;

class GeneralGameLogic extends GameLogic{

    GeneralGameLogic(int r, int h, Player[] players) {
        super(r, h, players);
    }

    @Override
    public boolean isEndGame() {

        return this.capacity == 0;
    }

    @Override
    protected boolean checkSOS(int r, int c) {

        //General game
        SOSMadeEvent event;
        int count = 0;

        if ((event = checker.checkDiagonal(r, c, getCurrentTurn())) != null) {
            count += 1;
            this.eventQueue.add(event);
        }

        if ((event = checker.checkHorizontal(r, c, getCurrentTurn())) != null) {
            count += 1;
            this.eventQueue.add(event);
        }

        if ((event = checker.checkVertical(r, c, getCurrentTurn())) != null) {
            count += 1;
            this.eventQueue.add(event);
        }

        scoreArr[currentTurn] += count;

        return count >  0;

    }
}
