package com.github.jtesfaye.sosgame.GameEvent;

public class TieEvent extends GameEvent{

    public final String message = "Tie";

    public TieEvent() {
        super(EventType.Tie);
    }
}
