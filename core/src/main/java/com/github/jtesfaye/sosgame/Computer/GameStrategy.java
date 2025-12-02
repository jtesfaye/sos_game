package com.github.jtesfaye.sosgame.Computer;

import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;

public interface GameStrategy {

    Move makeMove(Piece[][] board);

}
