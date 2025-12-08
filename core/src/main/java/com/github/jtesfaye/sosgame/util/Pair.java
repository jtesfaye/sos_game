package com.github.jtesfaye.sosgame.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public record Pair<T1, T2>(@Getter T1 first, @Getter T2 second) {

    @JsonCreator
    public Pair(@JsonProperty("first") T1 first, @JsonProperty("second") T2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", first, second);
    }
}
