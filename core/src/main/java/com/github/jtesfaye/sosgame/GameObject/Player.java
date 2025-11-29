package com.github.jtesfaye.sosgame.GameObject;

import com.badlogic.gdx.graphics.Color;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Getter
    private final String description;

    public Player(
        Type type,
        Color color,
        String displayName) {

        description = displayName;
        playerColor = color;
        playerType = type;
        playerId = UUID.randomUUID();
    }

    @JsonCreator
    public Player(
        @JsonProperty("playerType") Type type,
        @JsonProperty("playerColor") Color color,
        @JsonProperty("description") String displayName,
        @JsonProperty("playerId") UUID id) {

        description = displayName;
        playerColor = color;
        playerType = type;
        playerId = id;
    }

    @Override
    public String toString() {
        return description;
    }

}
