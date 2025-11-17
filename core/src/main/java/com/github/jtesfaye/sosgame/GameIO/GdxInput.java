package com.github.jtesfaye.sosgame.GameIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.github.jtesfaye.sosgame.GameEvent.InputEvent;
import com.github.jtesfaye.sosgame.GameEvent.turnChangeEvent;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.GameObject.Move;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.util.GameEventProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class GdxInput extends InputAdapter {

    private final PerspectiveCamera camera;
    private final ArrayList<ArrayList<ModelInstance>> tiles;

    private final int row;
    private final int height;
    private Player currentPlayer;

    private final GameEventProcessor p;

    public GdxInput(
        PerspectiveCamera c,
        ArrayList<ArrayList<ModelInstance>> t,
        GameEventProcessor p) {

        super();
        this.p = p;
        camera = c;
        tiles = t;
        row = t.size();
        height = t.get(0).size();

        p.addSubscriber(turnChangeEvent.class, this::onTurnChange);
    }

    public void onTurnChange(turnChangeEvent e) {

        Runnable r = () -> {
            this.currentPlayer = e.upcomingPlayer;
        };

        Gdx.app.postRunnable(r);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (currentPlayer != null && currentPlayer.getPlayerType() != Player.Type.Human) {
            return false;
        }

        Ray ray = camera.getPickRay(screenX, screenY);

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < height; c++) {

                ModelInstance tile = tiles.get(r).get(c);

                BoundingBox bounds = new BoundingBox();
                tile.calculateBoundingBox(bounds);
                bounds.mul(tile.transform);

                if (Intersector.intersectRayBoundsFast(ray, bounds)) {

                    Piece piece = button == Input.Buttons.LEFT ? Piece.sPiece : Piece.oPiece;

                    if (currentPlayer == null) {
                        p.addEvent(new InputEvent(new Move(r, c, piece)));
                        return true;
                    }

                    p.addEvent(new InputEvent(new Move(r, c, piece), currentPlayer.getPlayerId()));
                    return true;
                }
            }
        }

        return false;
    }
}
