package com.github.jtesfaye.sosgame.GameEvent;

import com.badlogic.gdx.graphics.Color;
import com.github.jtesfaye.sosgame.util.Pair;

public class SOSMadeEvent extends GameEvent {

    public final int row1, row2 ,row3;
    public final int col1, col2, col3;
    public final Color color;

    public SOSMadeEvent(int r1, int c1, int r2, int c2, int r3, int c3, Color color) {
        super(EventType.SOSMade);
        this.row1 = r1;
        this.row2 = r2;
        this.row3 = r3;
        this.col1 = c1;
        this.col2 = c2;
        this.col3 = c3;
        this.color = color;
    }
}
