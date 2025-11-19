package com.github.jtesfaye.sosgame.gameStrategy;

import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;

import java.util.Timer;
import java.util.TimerTask;

public interface GameStrategy {

    Move makeMove(Piece[][] board);

}
