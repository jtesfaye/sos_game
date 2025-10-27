package com.github.jtesfaye.sosgame.GameLogic;

class GeneralGameLogic extends GameLogic{

    GeneralGameLogic(int r, int h, String opponent) {
        super(r, h, opponent);
    }

    @Override
    public boolean isWinner() {

        return this.capacity <= 0;
    }

    @Override
    protected void checkSOS(int r, int c) {

        int count = 0;

        if (checker.isDiagonal(r,c))
            count += 1;

        if (checker.isHorizontal(r,c))
            count += 1;

        if (checker.isVertical(r,c))
            count += 1;

        scoreArr[currentTurn] += count;
    }
}
