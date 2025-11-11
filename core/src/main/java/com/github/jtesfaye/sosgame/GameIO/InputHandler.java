package com.github.jtesfaye.sosgame.GameIO;

import lombok.Getter;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class InputHandler {

    @Getter
    protected final UUID playerId;

    @Getter
    protected Consumer<InputEvent> consumer;

    @Getter
    protected final InputType inputType;

    public enum InputType {
        PassiveIO, //Waiting for user to click
        NetworkIO, //Sending a message over network and wait for a response
        Procedure //Calling an algorithm that makes a move
    }

    public InputHandler(UUID playerId, InputType type) {

        this.playerId = playerId;
        this.inputType = type;

    }

    abstract public void getInput();

    protected void addConsumer(Consumer<InputEvent> e) {
        this.consumer = e;
    }
}
