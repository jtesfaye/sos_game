package com.github.jtesfaye.sosgame.GameIO;

import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

public class InputEvent {

    public InputEvent(Move move, UUID playerId) {

        this.move = move;
        this.playerId = playerId;
    }

    @Getter
    private final Move move;

    @Getter
    private final UUID playerId;

}
