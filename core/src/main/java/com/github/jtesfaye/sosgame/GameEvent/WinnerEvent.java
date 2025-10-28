package com.github.jtesfaye.sosgame.GameEvent;

import com.github.jtesfaye.sosgame.GameLogic.Player;

public class WinnerEvent extends GameEvent {

    public final Player player;

    public WinnerEvent(Player player) {
        super(EventType.DeclareWinner);

        this.player = player;
    }
}
