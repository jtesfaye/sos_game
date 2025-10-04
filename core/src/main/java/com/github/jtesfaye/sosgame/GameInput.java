package com.github.jtesfaye.sosgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;

import java.util.ArrayList;

public class GameInput extends InputAdapter {

    private final PerspectiveCamera camera;
    private final ArrayList<ArrayList<ModelInstance>> tiles;
    private final GameLogic logic;

    private final int row;
    private final int height;


    public GameInput(PerspectiveCamera c, ArrayList<ArrayList<ModelInstance>> t, GameLogic l) {
        super();

        camera = c;
        tiles = t;
        logic = l;

        row = t.size();
        height = t.get(0).size();
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

                    if (button == Input.Buttons.LEFT) {
                        return logic.setPiece(r, c, GameLogic.State.sPiece);

                    } else if (button == Input.Buttons.RIGHT) {
                        return logic.setPiece(r, c, GameLogic.State.oPiece);

                    }
                }
            }
        }

        return false;
    }
}
