package com.github.jtesfaye.sosgame.GameLogic;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

public class Player {

    final String description;

    @Getter
    Color playerColor;

    public Player(String desc, Color color) {

        description = desc;
        playerColor = color;
    }

    @Override
    public String toString() {
        return description;
    }
}
