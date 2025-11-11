package com.github.jtesfaye.sosgame.gameStrategy;

import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;

public interface GameStrategy {

    Move makeMove(Piece[][] board);

}
