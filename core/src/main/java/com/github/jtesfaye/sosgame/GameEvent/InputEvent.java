package com.github.jtesfaye.sosgame.GameEvent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jtesfaye.sosgame.GameObject.Move;
import lombok.Getter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;

import java.util.UUID;

@JsonTypeName("input")
public class InputEvent extends GameEvent {

    @JsonCreator
    public InputEvent(
        @JsonProperty("move") Move move,
        @JsonProperty("playerId") UUID playerId) {
        this.move = move;
        this.playerId = playerId;
    }

    public InputEvent(Move move) {
        this.move = move;
        this.playerId = null;
    }

    @Getter
    private final Move move;

    @Getter
    private final UUID playerId;

}
