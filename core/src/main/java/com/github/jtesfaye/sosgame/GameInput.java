package com.github.jtesfaye.sosgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;

import java.util.ArrayList;

public class GameInput extends InputAdapter {

    private final PerspectiveCamera camera;
    private final ArrayList<ArrayList<ModelInstance>> tiles;
    private final BoardState board;

    public GameInput(PerspectiveCamera c, ArrayList<ArrayList<ModelInstance>> t, BoardState s) {
        super();

        camera = c;
        tiles = t;
        board = s;

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Ray ray = camera.getPickRay(screenX, screenY);

        for (int r = 0; r < board.row; r++) {
            for (int c = 0; c < board.col; c++) {

                ModelInstance tile = tiles.get(r).get(c);

                BoundingBox bounds = new BoundingBox();
                tile.calculateBoundingBox(bounds);
                bounds.mul(tile.transform);

                if (Intersector.intersectRayBoundsFast(ray, bounds)) {

                    if (button == Input.Buttons.LEFT) {
                        return board.setPiece(r, c, BoardState.State.sPiece);

                    } else if (button == Input.Buttons.RIGHT) {
                        return board.setPiece(r, c, BoardState.State.oPiece);

                    }
                }
            }
        }

        return false;
    }
}
