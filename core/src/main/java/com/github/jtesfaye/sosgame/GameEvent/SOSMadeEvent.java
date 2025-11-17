package com.github.jtesfaye.sosgame.GameEvent;

import com.badlogic.gdx.graphics.Color;
import com.github.jtesfaye.sosgame.util.Pair;

import java.util.ArrayList;

public class SOSMadeEvent extends GameEvent {

    public final Color color;

    public final Pair<Integer, Integer> tile1;
    public final Pair<Integer, Integer> tile2;
    public final Pair<Integer,Integer> tile3;

    public SOSMadeEvent(ArrayList<Pair<Integer, Integer>> tiles, Color color) {

        tile1 = tiles.get(0);
        tile2 = tiles.get(1);
        tile3 = tiles.get(2);
        this.color = color;
    }
}
