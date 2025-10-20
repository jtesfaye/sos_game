package com.github.jtesfaye.sosgame.BoardComponents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

import java.util.ArrayList;


public class BoardBuilder {

    private final Model primaryTile;
    private final Model secondaryTile;

    private final BoardLayout layout;

    private final float width;
    private final float height;

    ArrayList<Vector3> centers;

    public BoardBuilder(float w, float h, Color p, Color s) {

        width = w;
        height = h;
        primaryTile = new TileModel(p).getTileModel();
        secondaryTile = new TileModel(s).getTileModel();
        layout = new BoardLayout(w, h, TileModel.getWidth(), TileModel.getHeight());

    }

    public ArrayList<ArrayList<Tile>> build() {

        ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
        centers = layout.getCenters();

        int index = 0;
        for (float x = 0; x < width; x++) {

            ArrayList<Tile> row = new ArrayList<>();

            for (float z = 0; z < height; z++) {

                ModelInstance tileInstance;

                if ((x + z) % 2 == 0) {

                    tileInstance = new ModelInstance(primaryTile);

                } else {

                    tileInstance = new ModelInstance(secondaryTile);
                }

                tileInstance.transform.setToTranslation(centers.get(index));

                row.add(new Tile(tileInstance, centers.get(index)));

                index += 1;

            }

            tiles.add(row);
        }
        return tiles;
    }

    public ArrayList<Vector3> getCenters() {

        return centers;
    }
}
