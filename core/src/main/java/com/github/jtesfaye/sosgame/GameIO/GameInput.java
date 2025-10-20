package com.github.jtesfaye.sosgame.GameIO;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.GameLogic.Piece;

import java.util.ArrayList;

public class GameInput extends InputAdapter {

    private final PerspectiveCamera camera;
    private final ArrayList<ArrayList<ModelInstance>> tiles;

    private final int row;
    private final int height;

    private final InputHandler handler;


    public GameInput(PerspectiveCamera c, ArrayList<ArrayList<ModelInstance>> t, InputHandler handler) {
        super();

        camera = c;
        tiles = t;

        row = t.size();
        height = t.get(0).size();

        this.handler = handler;
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
                    return handler.handleClick(r, c, button == Input.Buttons.LEFT);
                }
            }
        }

        return false;
    }
}
