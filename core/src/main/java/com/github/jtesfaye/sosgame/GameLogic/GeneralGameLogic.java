package com.github.jtesfaye.sosgame.GameLogic;

public class GeneralGameLogic extends GameLogic{

    GeneralGameLogic(int r, int h) {
        super(r, h);
    }

    @Override
    public boolean isWinner() {

        //General games continue until the board is full, therefore no winner if capacity > 0
        if (this.capacity > 0 ) {
            return false;
        }

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
