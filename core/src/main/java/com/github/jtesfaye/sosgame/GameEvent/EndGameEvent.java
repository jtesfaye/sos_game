package com.github.jtesfaye.sosgame.GameEvent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.jtesfaye.sosgame.GameObject.Player;
import lombok.Getter;

@JsonTypeName("end_game")
public class EndGameEvent extends GameEvent {

    @Getter
    public final Player player;

    @Getter
    public String message;

    @JsonCreator
    public EndGameEvent(@JsonProperty("player") Player player) {

        this.player = player;

        if (player != null) {
            message = "Winner: " + player.toString();
        } else {
            message = "Tie";
        }
    }
}
