package com.github.jtesfaye.sosgame.GameEvent;

import com.github.jtesfaye.sosgame.GameObject.Player;

public class EndGameEvent extends GameEvent {

    public final Player player;
    public String message;

    public EndGameEvent(Player player) {

        this.player = player;

        if (player != null) {
            message = "Winner: " + player.toString();
        } else {
            message = "Tie";
        }
    }
}
