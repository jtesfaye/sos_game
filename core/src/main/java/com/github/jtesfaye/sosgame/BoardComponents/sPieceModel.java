package com.github.jtesfaye.sosgame.BoardComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;

public class sPieceModel {

    private final Model sPieceModel;

    public sPieceModel(Color c) {

        ObjLoader loader = new ObjLoader();
        sPieceModel = loader.loadModel(Gdx.files.internal("sPiece.obj"));
        for (Material m : sPieceModel.materials) {
            m.set(ColorAttribute.createDiffuse(c));
        }

    }

    public Model getPieceModel() {

        return sPieceModel;
    }

}
