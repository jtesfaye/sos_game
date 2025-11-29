package com.github.jtesfaye.sosgame.GameObject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class Move {

    @Getter
    private int row;

    @Getter
    private int col;

    @Getter
    private Piece piece;

    @JsonCreator
    public Move(@JsonProperty("row") int r,
                @JsonProperty("col") int c,
                @JsonProperty("piece") Piece p) {
        row = r;
        col = c;
        piece = p;
    }
}
