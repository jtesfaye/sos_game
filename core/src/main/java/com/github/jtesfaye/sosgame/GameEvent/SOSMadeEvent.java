package com.github.jtesfaye.sosgame.GameEvent;

import com.badlogic.gdx.graphics.Color;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.jtesfaye.sosgame.util.Pair;
import lombok.Getter;

import java.util.ArrayList;

@JsonTypeName("sos_made")
public class SOSMadeEvent extends GameEvent {

    @Getter
    public final Color color;

    @Getter
    public final Pair<Integer, Integer> tile1;

    @Getter
    public final Pair<Integer, Integer> tile2;

    @Getter
    public final Pair<Integer,Integer> tile3;

    public SOSMadeEvent(ArrayList<Pair<Integer, Integer>> tiles, Color color) {

        tile1 = tiles.get(0);
        tile2 = tiles.get(1);
        tile3 = tiles.get(2);
        this.color = color;
    }

    @JsonCreator
    public SOSMadeEvent(
        @JsonProperty("tile1") Pair<Integer, Integer> tile1,
        @JsonProperty("tile2") Pair<Integer, Integer> tile2,
        @JsonProperty("tile3") Pair<Integer, Integer> tile3,
        @JsonProperty("color") Color color) {

        this.tile1 = tile1;
        this.tile2 = tile2;
        this.tile3 = tile3;
        this.color = color;

    }
}
