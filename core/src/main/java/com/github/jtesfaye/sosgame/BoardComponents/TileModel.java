package com.github.jtesfaye.sosgame.BoardComponents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class TileModel {


    private final Model tileModel;
    private static final int width = 3;
    private static final int height = 3;

    public TileModel(Color primColor) {

        ModelBuilder builder = new ModelBuilder();

        tileModel = builder.createBox(
            width,
            0.1f,
            height,
            new Material(ColorAttribute.createDiffuse(primColor)),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
                | VertexAttributes.Usage.TextureCoordinates
        );
    }

    public Model getTileModel() {
        return tileModel;
    }

    public static float getWidth() {return width;}

    public static float getHeight() {return height;}

}
