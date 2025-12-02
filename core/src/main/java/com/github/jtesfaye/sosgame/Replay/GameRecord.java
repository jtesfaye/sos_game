package com.github.jtesfaye.sosgame.Replay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.GameEvent.GameEvent;
import com.github.jtesfaye.sosgame.util.Pair;

import java.util.List;


public record GameRecord(
    @JsonProperty("boardDim") Pair<Integer, Integer> boardDim,
    @JsonProperty("gameMode") String gameMode,
    @JsonProperty("players") List<Player> players,
    @JsonProperty("events") List<GameEvent> events) {}
