package com.github.jtesfaye.sosgame.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;

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
