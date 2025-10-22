package com.github.jtesfaye.sosgame.BoardComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;

public class sPieceModel {

    private final Model sPieceModel;

    public sPieceModel() {

        ObjLoader loader = new ObjLoader();
        sPieceModel = loader.loadModel(Gdx.files.internal("sPiece.obj"));

    }

    public Model getPieceModel() {

        return sPieceModel;
    }

}
