package com.github.jtesfaye.sosgame.GameLogic;

public enum Player {

    Player1("Player 1"), Player2("Player 2");

    final String description;

    Player(String desc) {
        description = desc;
    }

    @Override
    public String toString() {
        return description;
    }
}
