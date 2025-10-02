package com.github.jtesfaye.sosgame;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.github.jtesfaye.sosgame.Components.TileModel;

import java.util.ArrayList;


public class BoardBuilder {

    private TileModel tileModel;

    private ArrayList<ArrayList<Tile>> tiles;

    private final Model redTile;
    private final Model greenTile;

    private final float width;
    private final float height;

    public BoardBuilder(float w, float h) {

        width = w;
        height = h;
        tileModel = new TileModel();
        tiles = new ArrayList<>();
        redTile = tileModel.getRedTileModel();
        greenTile = tileModel.getGreenTileModel();

    }

    public ArrayList<ArrayList<Tile>> build() {

        if (!tiles.isEmpty()) {
            return tiles;
        }

        float tileH = TileModel.getHeight();
        float tileW = TileModel.getWidth();

        BoundingBox bounds = new BoundingBox();


        for (float x = 0; x < width; x +=1f) {

            ArrayList<Tile> row = new ArrayList<>();

            for (float z = 0; z < height; z += 1f) {
                ModelInstance tileInstance;

                if ((x + z) % 2 == 0) {
                    tileInstance = new ModelInstance(redTile);

                } else {
                    tileInstance = new ModelInstance(greenTile);

                }

                tileInstance.transform.setToTranslation(x * tileW , 0 ,z * tileH);

                tileInstance.calculateBoundingBox(bounds);

                Vector3 center = new Vector3();
                bounds.getCenter(center);
                center.mul(tileInstance.transform);

                row.add(new Tile(tileInstance, center));
            }

            tiles.add(row);
        }

        return tiles;
    }
}
