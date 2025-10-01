package com.github.jtesfaye.sosgame.Components;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;


public class oPieceModel {

    private final Model oPieceModel;

    public oPieceModel() {

        ObjLoader loader = new ObjLoader();
        oPieceModel = loader.loadModel(Gdx.files.internal("Grenade.obj"));

    }

    public Model getPieceModel() {
        return oPieceModel;
    }

}
