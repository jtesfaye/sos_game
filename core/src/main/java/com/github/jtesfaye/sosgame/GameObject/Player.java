package com.github.jtesfaye.sosgame.GameObject;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

import java.util.UUID;


public class Player {

    public enum Type {
      Human, Computer, LLM
    };

    @Getter
    private final UUID playerId;

    @Getter
    private Color playerColor;

    @Getter
    private final Type playerType;
    private final String description;

    public Player(Type type, Color color, String displayName) {

        description = displayName;
        playerColor = color;
        playerType = type;
        playerId = UUID.randomUUID();

    }

    @Override
    public String toString() {
        return description;
    }

}
