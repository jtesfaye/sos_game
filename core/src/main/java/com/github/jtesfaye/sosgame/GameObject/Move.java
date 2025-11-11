package com.github.jtesfaye.sosgame.GameObject;

import lombok.Getter;

public class Move {
    @Getter
    private final int row;

    @Getter
    private final int col;

    @Getter
    private final Piece piece;

    public Move(int r, int c, Piece p) {
        row = r;
        col = c;
        piece = p;
    }
}
