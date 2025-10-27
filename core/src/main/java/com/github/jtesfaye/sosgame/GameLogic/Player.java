package com.github.jtesfaye.sosgame.GameLogic;

public class Player {

    final String description;

    public Player(String desc) {

        description = desc;
    }

    @Override
    public String toString() {
        return description;
    }
}
