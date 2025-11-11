package com.github.jtesfaye.sosgame.GameIO;

import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.gameStrategy.GameStrategy;

import java.util.UUID;

public class ComputerInputHandler extends InputHandler {

    GameStrategy strategy;

    public ComputerInputHandler(UUID playerId, GameStrategy strat) {

        super(playerId, InputType.Procedure);
        strategy = strat;
    }

    @Override
    public void getInput(Piece[][] board) {

        Move move = strategy.makeMove(board);
        consumer.accept(new InputEvent(move, playerId));
    }
}
