package com.github.jtesfaye.sosgame.GameIO;

import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.GameLogic.Piece;

public class InputHandler {

    private final GameLogic logic;

    public InputHandler(GameLogic logic) {

        this.logic = logic;

    }

    public boolean handleClick(int row, int col, boolean isLeft) {

        Piece piece = isLeft ? Piece.oPiece : Piece.sPiece;
        return logic.setPiece(row, col, piece);

    }
}
