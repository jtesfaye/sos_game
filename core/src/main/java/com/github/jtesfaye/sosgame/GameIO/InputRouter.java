package com.github.jtesfaye.sosgame.GameIO;

import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.GameObject.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class InputRouter {

    private final GameLogic logic;

    private final Map<UUID, InputHandler> handlers;

    public InputRouter(GameLogic logic) {
        this.logic = logic;
        handlers = new HashMap<>();
    }

    public void registerHandler(InputHandler handler) {

        handler.addConsumer(this::onInputEvent);
        handlers.put(handler.playerId, handler);

    }

    private void onNextTurn() {

        Player currentPlayer = logic.getCurrentTurn();
        InputHandler handler = handlers.get(currentPlayer.getPlayerId());

        if (handler.inputType == InputHandler.InputType.PassiveIO) {
            return;
        }

        handler.getInput();

    }

    private void onInputEvent(InputEvent e) {

        Player currentPlayer = logic.getCurrentTurn();

        if (currentPlayer.getPlayerType().equals(Player.Type.Human) && (!e.getPlayerId().isPresent())) {
            throw new RuntimeException("Current turn is human player, but player id is not present");
        }

        //This is so click inputs from a human is only processed on their turn.
        /*
            If inputEvent is from a human player, and its their turn, apply the move
            otherwise, if the inputEvent is from a human, but its not their turn, exit
         */
        if (currentPlayer.getPlayerType().equals(Player.Type.Human)
            && currentPlayer.getPlayerId().equals(e.getPlayerId().get())) {

            logic.applyMove(e.getRow(), e.getCol(), e.getPiece());

        } else if (!(currentPlayer.getPlayerType().equals(Player.Type.Human)
            && currentPlayer.getPlayerId().equals(e.getPlayerId().get()))) {

            return;
        }


        logic.applyMove(e.getRow(), e.getCol(), e.getPiece());

        onNextTurn();
    }
}
