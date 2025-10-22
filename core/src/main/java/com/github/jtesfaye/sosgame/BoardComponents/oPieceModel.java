package com.github.jtesfaye.sosgame.BoardComponents;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;


public class oPieceModel {

    private final Model oPieceModel;

    public oPieceModel() {

        ObjLoader loader = new ObjLoader();
        oPieceModel = loader.loadModel(Gdx.files.internal("oPiece.obj"));

    }

    public Model getPieceModel() {
        return oPieceModel;
    }

}
