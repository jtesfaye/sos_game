package com.github.jtesfaye.sosgame.BoardComponents;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class Tile {

    public ModelInstance tileInstance;
    public Vector3 worldCenter;

    public Tile(ModelInstance m, Vector3 v) {
        tileInstance = m;
        worldCenter = v;
    }
}
