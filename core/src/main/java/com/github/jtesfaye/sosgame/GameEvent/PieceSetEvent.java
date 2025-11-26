package com.github.jtesfaye.sosgame.GameEvent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import lombok.Getter;

@JsonTypeName("piece_set")
public class PieceSetEvent extends GameEvent {

    @Getter
    public final int row;

    @Getter
    public final int col;

    @Getter
    public final Piece piece;

    @JsonCreator
    public PieceSetEvent(
        @JsonProperty("row") int row,
        @JsonProperty("col") int col,
        @JsonProperty("piece") Piece piece) {

        this.row = row;
        this.col = col;
        this.piece = piece;
    }
}
