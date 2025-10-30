package com.github.jtesfaye.sosgame.GameEvent;

import com.github.jtesfaye.sosgame.GameLogic.Player;

public class TieEvent extends GameEvent{

    public final String message = "Tie";

    public TieEvent() {
        super(EventType.DeclareWinner);
    }
}
