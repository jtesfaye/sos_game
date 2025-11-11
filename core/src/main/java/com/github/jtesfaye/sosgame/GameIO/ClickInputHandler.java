package com.github.jtesfaye.sosgame.GameIO;

import com.github.jtesfaye.sosgame.GameObject.Piece;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class ClickInputHandler extends InputHandler {

    public ClickInputHandler(UUID playerId) {
        super(playerId, InputType.PassiveIO);
    }

    public void handleClick(int row, int col, boolean isLeft) {

        Piece piece = isLeft ? Piece.sPiece : Piece.oPiece;
        consumer.accept(new InputEvent(row, col, piece, Optional.ofNullable(playerId)));

    }

    @Override
    public void getInput() {}
}
