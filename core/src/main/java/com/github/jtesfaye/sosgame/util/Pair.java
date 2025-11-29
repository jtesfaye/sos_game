package com.github.jtesfaye.sosgame.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class Pair<T1, T2> {

    @Getter
    public final T1 first;

    @Getter
    public final T2 second;

    @JsonCreator
    public Pair(@JsonProperty("first") T1 first, @JsonProperty("second") T2 second) {
        this.first = first;
        this.second = second;
    }
}
