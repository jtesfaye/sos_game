package com.github.jtesfaye.sosgame.GameObject;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Piece {

    OPEN("_"),
    sPiece("S"),
    oPiece("O");

    private final String description;

    Piece(String desc) {
        description = desc;
    }

    @Override
    public String toString() {

        return description;
    }
}
