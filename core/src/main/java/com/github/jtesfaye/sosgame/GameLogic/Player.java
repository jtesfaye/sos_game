package com.github.jtesfaye.sosgame.GameLogic;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

import java.util.UUID;


public class Player {

    public enum Type {
      Human, Computer, LLM
    };

    private final UUID playerId;

    @Getter
    private Color playerColor;

    @Getter
    private final Type playerType;
    private final String description;

    public Player(Type type, Color color) {

        switch (type) {
            case Human:
                description = "Human";
                break;
            case Computer:
                description = "Computer";
                break;
            case LLM:
                description = "LLM";
                break;
            default:
                description = "";
        }

        playerColor = color;
        playerType = type;
        playerId = UUID.randomUUID();

    }

    @Override
    public String toString() {
        return description;
    }

    public String getPlayerId() {
        return playerId.toString();
    }
}
