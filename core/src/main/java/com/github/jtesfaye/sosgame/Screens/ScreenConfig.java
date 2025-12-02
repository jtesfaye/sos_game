package com.github.jtesfaye.sosgame.Screens;

import com.github.jtesfaye.sosgame.BoardComponents.BoardBuilder;
import lombok.Value;

@Value
public class ScreenConfig {

    int boardWidth;
    int boardHeight;
    BoardBuilder builder;
    String gameMode;
    String opponentName;
}
