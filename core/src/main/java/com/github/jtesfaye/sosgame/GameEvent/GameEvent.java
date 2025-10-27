package com.github.jtesfaye.sosgame.GameEvent;

import lombok.Getter;

public abstract class GameEvent {

    public enum EventType {
        PieceSet, //Tell screen to render a S or O
        SOSMade, //Tell screen to render a line over a newly made SOS
        DeclareWinner, //End game declaring who won and who lost
    };

    @Getter
    private final EventType event;

    protected GameEvent(EventType event) {
        this.event = event;
    }

}
