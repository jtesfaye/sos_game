package com.github.jtesfaye.sosgame.GameLogic;

import java.util.Arrays;
import java.util.stream.IntStream;

class SimpleGameLogic extends GameLogic {

    SimpleGameLogic(int r, int h) {
        super(r, h);
    }

    @Override
    public boolean isWinner() {

        return Arrays.stream(scoreArr).sum() > 0;
    }

    @Override
    protected void checkSOS(int r, int c) {

        if (checker.isDiagonal(r, c) || checker.isHorizontal(r,c) || checker.isVertical(r, c)) {

            scoreArr[currentTurn.ordinal()] += 1;

        }
    }
}
