package com.github.jtesfaye.sosgame.BoardComponents;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;


public class oPieceModel {

    private final Model oPieceModel;

    public oPieceModel(Color c) {

        ObjLoader loader = new ObjLoader();
        oPieceModel = loader.loadModel(Gdx.files.internal("oPiece.obj"));
        for (Material m : oPieceModel.materials) {
            m.set(ColorAttribute.createDiffuse(c));
        }

    }

    public Model getPieceModel() {
        return oPieceModel;
    }

}
