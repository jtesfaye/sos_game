package com.github.jtesfaye.sosgame.GameIO;

import com.github.jtesfaye.sosgame.GameEvent.InputEvent;
import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;

import java.util.UUID;

public class ClickInputHandler extends InputHandler {

    public ClickInputHandler(UUID playerId) {
        super(playerId, InputType.PassiveIO);
    }

    public void handleClick(int row, int col, Piece piece) {

        consumer.accept(new InputEvent(new Move(row, col, piece), playerId));

    }

    @Override
    public void getInput(Piece[][] b) {}
}
