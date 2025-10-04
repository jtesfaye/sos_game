package com.github.jtesfaye.sosgame.GameLogic;

public class SimpleGameLogic extends GameLogic {

    SimpleGameLogic(int r, int h) {
        super(r, h);
    }

    @Override
    public boolean isWinner() {

        return Math.max(this.player1SOSCount, this.computerSOSCount) > 0;

    }

    @Override
    public Player getWinner() {

        if (this.player1SOSCount == 1) {

            return Player.Player1;

        } else {

            return Player.Computer;

        }
    }
}
