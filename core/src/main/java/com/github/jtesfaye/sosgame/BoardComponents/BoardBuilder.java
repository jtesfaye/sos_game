package com.github.jtesfaye.sosgame.BoardComponents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import lombok.Getter;

import java.util.ArrayList;


public class BoardBuilder {

    private Model primaryTile;
    private Model secondaryTile;

    private final BoardLayout layout;

    private final float width;
    private final float height;

    @Getter
    ArrayList<Vector3> centers;

    public BoardBuilder(float w, float h) {

        width = w;
        height = h;
        primaryTile = null;
        secondaryTile = null;
        layout = new BoardLayout(w, h, TileModel.getWidth(), TileModel.getHeight());

    }

    public ArrayList<ArrayList<Tile>> build(Color p, Color s) {

        primaryTile = new TileModel(p).getTileModel();
        secondaryTile = new TileModel(s).getTileModel();

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

}
