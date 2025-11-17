package com.github.jtesfaye.sosgame.GameEvent;

import com.github.jtesfaye.sosgame.GameObject.Player;

public class turnChangeEvent extends GameEvent {

    public Player upcomingPlayer;

    public turnChangeEvent(Player upcomingPlayer) {
        this.upcomingPlayer = upcomingPlayer;
    }

}
