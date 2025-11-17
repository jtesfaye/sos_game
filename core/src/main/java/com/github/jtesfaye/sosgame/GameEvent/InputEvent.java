package com.github.jtesfaye.sosgame.GameEvent;

import com.github.jtesfaye.sosgame.GameObject.Move;
import lombok.Getter;

import java.util.UUID;

public class InputEvent extends GameEvent {

    public InputEvent(Move move, UUID playerId) {

        this.move = move;
        this.playerId = playerId;
    }

    public InputEvent(Move move) {
        this.move = move;
        this.playerId = null;
    }

    @Getter
    private final Move move;

    @Getter
    private final UUID playerId;

}
