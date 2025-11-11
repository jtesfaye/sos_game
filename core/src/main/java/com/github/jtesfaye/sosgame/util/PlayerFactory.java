package com.github.jtesfaye.sosgame.util;

import com.badlogic.gdx.graphics.Color;
import com.github.jtesfaye.sosgame.GameObject.Player;

public class PlayerFactory {

    public static Player createPlayer(String type, Color color, String displayName) {

        switch (type) {
            case "Human":
                return new Player(Player.Type.Human, color, displayName);
            case "Computer":
                return new Player(Player.Type.Computer, color, displayName);
            case "LLM":
                return new Player(Player.Type.LLM, color, displayName);
            default:
                throw new RuntimeException("Invalid player type given");
        }
    }

}
