package com.github.jtesfaye.sosgame.GameEvent;

import com.github.jtesfaye.sosgame.util.Pair;

import java.util.ArrayList;

public class scoreChangeEvent extends GameEvent{

    public final ArrayList<Pair<String, String>> scores;

    public scoreChangeEvent(ArrayList<Pair<String, String>> scores) {
        this.scores = scores;
    }
}
