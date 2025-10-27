package com.github.jtesfaye.sosgame.GameEvent;

import com.github.jtesfaye.sosgame.GameLogic.Piece;

public class PieceSetEvent extends GameEvent {

    public final int row;
    public final int col;
    public final Piece piece;

    public PieceSetEvent(int row, int col, Piece piece) {
        super(EventType.PieceSet);
        this.row = row;
        this.col = col;
        this.piece = piece;
    }
}
