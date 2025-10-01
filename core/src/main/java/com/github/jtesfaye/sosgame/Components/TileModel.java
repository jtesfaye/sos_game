package com.github.jtesfaye.sosgame.Components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class TileModel {


    private final Model redTile;
    private final Model greenTile;
    private static final int width = 3;
    private static final int height = 3;

    public TileModel() {

        ModelBuilder builder = new ModelBuilder();

        redTile = builder.createBox(
            width,
            0.1f,
            height,
            new Material(ColorAttribute.createDiffuse(Color.RED)),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
                | VertexAttributes.Usage.TextureCoordinates
        );

        greenTile = builder.createBox(
            width,
            0.1f,
            height,
            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
                | VertexAttributes.Usage.TextureCoordinates
        );
    }

    public Model getRedTileModel() {
        return redTile;
    }

    public Model getGreenTileModel() {return greenTile;}

    public static float getWidth() {return width;}

    public static float getHeight() {return height;}

}
