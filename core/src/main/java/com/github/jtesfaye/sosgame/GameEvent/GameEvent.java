package com.github.jtesfaye.sosgame.GameEvent;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.Pair;
import lombok.Getter;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = InputEvent.class, name = "input"),
        @JsonSubTypes.Type(value = EndGameEvent.class, name = "end_game"),
        @JsonSubTypes.Type(value = onNextTurnEvent.class, name = "next_turn"),
        @JsonSubTypes.Type(value = PieceSetEvent.class, name = "piece_set"),
        @JsonSubTypes.Type(value = scoreChangeEvent.class, name = "score_change"),
        @JsonSubTypes.Type(value = SOSMadeEvent.class, name = "sos_made"),
        @JsonSubTypes.Type(value = turnChangeEvent.class, name = "turn_change"),
    }
)
public abstract class GameEvent {

    public String toJson(ObjectMapper mapper) {

        String toJson;

        try {
            toJson = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return toJson;
    };
}
