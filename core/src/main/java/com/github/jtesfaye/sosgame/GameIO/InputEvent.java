package com.github.jtesfaye.sosgame.GameIO;

import com.github.jtesfaye.sosgame.GameObject.Piece;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

public class InputEvent {

    public InputEvent(int row, int col, Piece p, Optional<UUID> playerId) {

        this.row = row;
        this.col = col;
        this.piece = p;
        this.playerId = playerId;
    }

    public InputEvent(int row, int col, Piece p) {

        this.row = row;
        this.col = col;
        this.piece = p;
        this.playerId = Optional.empty();
    }

    @Getter
    private final int row;

    @Getter
    private final int col;

    @Getter
    private final Piece piece;

    @Getter
    private final Optional<UUID> playerId;

}
