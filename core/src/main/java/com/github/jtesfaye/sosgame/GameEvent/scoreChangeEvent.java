package com.github.jtesfaye.sosgame.GameEvent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.jtesfaye.sosgame.util.Pair;
import lombok.Getter;

import java.util.ArrayList;

@JsonTypeName("score_change")
public class scoreChangeEvent extends GameEvent{

    @Getter
    public final ArrayList<Pair<String, String>> scores;

    @JsonCreator
    public scoreChangeEvent(@JsonProperty("scores") ArrayList<Pair<String, String>> scores) {
        this.scores = scores;
    }
}
