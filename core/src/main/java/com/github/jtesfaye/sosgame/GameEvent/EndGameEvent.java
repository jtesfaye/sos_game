package com.github.jtesfaye.sosgame.GameEvent;

import com.github.jtesfaye.sosgame.GameObject.Player;

public class WinnerEvent extends GameEvent {


    public String message = "Winner: ";

    public WinnerEvent(Player player) {
        super(EventType.DeclareWinner);
        
        message += player.toString();
    }
}
