package com.github.jtesfaye.sosgame.GameIO;

import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.GameObject.Move;
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

        handler.getInput(logic.getBoard());

    }

    private void onInputEvent(InputEvent e) {

        Player currentPlayer = logic.getCurrentTurn();

        //If its not their turn, ignore players input
        if (!e.getPlayerId().equals(currentPlayer.getPlayerId())) {
            return;
        }

        logic.applyMove(e.getMove());

        onNextTurn();
    }
}
