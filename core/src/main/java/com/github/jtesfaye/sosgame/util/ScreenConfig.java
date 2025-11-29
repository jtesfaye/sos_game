package com.github.jtesfaye.sosgame.util;

import com.github.jtesfaye.sosgame.BoardComponents.BoardBuilder;
import com.github.jtesfaye.sosgame.GameEvent.GameEvent;
import com.github.jtesfaye.sosgame.GameIO.ClickInputHandler;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import lombok.Value;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

@Value
public class ScreenInit {

    int boardWidth;
    int boardHeight;
    BoardBuilder builder;
    String gameMode;
    String opponentName;
}
