package com.github.jtesfaye.sosgame.gameStrategy;

import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;

public class HardGameStrategy implements GameStrategy {
    @Override
    public Move makeMove(Piece[][] board) {

        return new Move(0,0, Piece.oPiece);
    }
}
