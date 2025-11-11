package com.github.jtesfaye.sosgame.GameIO;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.GameObject.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameInput extends InputAdapter {

    private final PerspectiveCamera camera;
    private final ArrayList<ArrayList<ModelInstance>> tiles;

    private Map<UUID, ClickInputHandler> handlers;
    private final int row;
    private final int height;
    private Player currentPlayer;
    private final GameLogic logic;

    public GameInput(
        PerspectiveCamera c,
        ArrayList<ArrayList<ModelInstance>> t,
        ArrayList<ClickInputHandler> handlers,
        GameLogic logic) {

        super();
        camera = c;
        tiles = t;
        this.logic = logic;
        row = t.size();
        height = t.get(0).size();
        this.handlers = new HashMap<>();

        for (ClickInputHandler h : handlers) {
            this.handlers.put(h.playerId, h);
        }

        currentPlayer = logic.getCurrentTurn();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Ray ray = camera.getPickRay(screenX, screenY);

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < height; c++) {

                ModelInstance tile = tiles.get(r).get(c);

                BoundingBox bounds = new BoundingBox();
                tile.calculateBoundingBox(bounds);
                bounds.mul(tile.transform);

                if (Intersector.intersectRayBoundsFast(ray, bounds)) {
                    handlers.get(currentPlayer.getPlayerId()).handleClick(r, c, button == Input.Buttons.LEFT);
                    currentPlayer = logic.getCurrentTurn();
                    return true;
                }
            }
        }

        return false;
    }
}
