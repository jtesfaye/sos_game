package com.github.jtesfaye.sosgame.computer;

import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;

public interface GameStrategy {

    Move makeMove(Piece[][] board);

}
