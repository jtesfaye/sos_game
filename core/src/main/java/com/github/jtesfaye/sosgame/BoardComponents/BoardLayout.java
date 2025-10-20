package com.github.jtesfaye.sosgame.BoardComponents;

import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;

public class BoardLayout {

    private final float width;
    private final float height;
    private final float tileW;
    private final float tileH;

    public BoardLayout(float width, float height, float tileW, float tileH) {
        this.width = width;
        this.height = height;
        this.tileW = tileW;
        this.tileH = tileH;
    }

    public ArrayList<Vector3> getCenters() {

        ArrayList<Vector3> centers = new ArrayList<>();

        for (float x = 0; x < width; x += 1f) {
            for (float z = 0; z < height; z += 1f) {
                centers.add(new Vector3(x * ((tileW + tileW) / 2f), 0, z * ((tileH + tileH) / 2f)));
            }
        }

        return centers;
    }
}
