package com.github.jtesfaye.sosgame.GameIO;

import com.github.jtesfaye.sosgame.GameEvent.InputEvent;
import com.github.jtesfaye.sosgame.GameEvent.turnChangeEvent;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.GameEventProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class InputRouter {

    private final GameLogic logic;

    private final Map<UUID, InputHandler> handlers;
    private final GameEventProcessor p;

    public InputRouter(GameLogic logic, GameEventProcessor p) {
        this.logic = logic;
        handlers = new HashMap<>();
        this.p = p;

        p.addSubscriber(InputEvent.class, this::onInputEvent);

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

        //if null it means it was the starting move of the game
        if (e.getPlayerId() == null) {
            p.addEvent(new turnChangeEvent(currentPlayer));
        } else if (!e.getPlayerId().equals(currentPlayer.getPlayerId())) {
            //If its not their turn, ignore players input
            return;
        }

        logic.applyMove(e.getMove());

        onNextTurn();
    }
}
