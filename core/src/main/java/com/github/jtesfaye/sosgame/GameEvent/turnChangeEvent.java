package com.github.jtesfaye.sosgame.GameEvent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.jtesfaye.sosgame.GameObject.Player;
import lombok.Getter;

@JsonTypeName("turn_change")
public class turnChangeEvent extends GameEvent {

    @Getter
    public Player upcomingPlayer;

    @JsonCreator
    public turnChangeEvent(@JsonProperty("upcomingPlayer") Player upcomingPlayer) {
        this.upcomingPlayer = upcomingPlayer;
    }
}
