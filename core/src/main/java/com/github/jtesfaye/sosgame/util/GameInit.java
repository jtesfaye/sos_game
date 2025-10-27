package com.github.jtesfaye.sosgame.util;

import com.github.jtesfaye.sosgame.BoardComponents.BoardBuilder;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import lombok.Value;

@Value
public class GameInit {
    int boardWidth;
    int boardHeight;
    GameLogic logic;
    BoardBuilder builder;
    String gameMode;
    String opponentType;
}
