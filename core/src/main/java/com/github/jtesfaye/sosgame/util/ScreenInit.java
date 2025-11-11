package com.github.jtesfaye.sosgame.util;

import com.github.jtesfaye.sosgame.BoardComponents.BoardBuilder;
import com.github.jtesfaye.sosgame.GameIO.ClickInputHandler;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import lombok.Value;

import java.util.ArrayList;

@Value
public class ScreenInit {
    int boardWidth;
    int boardHeight;
    BoardBuilder builder;
    String gameMode;
    String opponentType;
    GameLogic logic;
    ArrayList<ClickInputHandler> handlers;
}
