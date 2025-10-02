package com.github.jtesfaye.sosgame.Screens;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.math.Vector3;
import com.github.jtesfaye.sosgame.BoardBuilder;
import com.github.jtesfaye.sosgame.BoardState;
import com.github.jtesfaye.sosgame.Components.TileModel;
import com.github.jtesfaye.sosgame.Components.oPieceModel;
import com.github.jtesfaye.sosgame.Components.sPieceModel;
import com.github.jtesfaye.sosgame.GameInput;
import com.github.jtesfaye.sosgame.Tile;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private PerspectiveCamera camera;

    private final int boardWidth;
    private final int boardHeight;
    private final BoardBuilder builder;
    private ModelBatch modelBatch;
    private Environment env;
    private sPieceModel sp;
    private oPieceModel op;

    private ArrayList<ArrayList<Tile>> tiles;
    private final BoardState board;

    public GameScreen(int width, int height) {

        builder = new BoardBuilder(width, height);
        boardWidth = width;
        boardHeight = height;
        board = new BoardState(width, height);
        sp = new sPieceModel();
        op = new oPieceModel();

    }

    public ModelInstance renderPiece(Model m, Vector3 center) {

        ModelInstance inst = new ModelInstance(m);
        inst.transform.setToTranslation(center).translate(0,0f,0f).scale(3f,3f,3f);

        return inst;

    }

    @Override
    public void show() {

        modelBatch = new ModelBatch();

        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight,.8f,.8f,.8f, 1f));
        env.add(new DirectionalLight().set(1f, 1f, 1f, -1f, -0.8f, -0.2f));

        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float centerx = (boardWidth * TileModel.getWidth()) / 2f - (TileModel.getWidth() / 2f);
        float centerz = (boardHeight * TileModel.getHeight()) / 2f - (TileModel.getWidth() / 2f);

        camera.position.set(centerx ,10f,-centerz);
        camera.lookAt(centerx,0,centerz);
        camera.up.set(0,0,1);
        camera.near = .1f;
        camera.far = 50f;
        camera.update();

        tiles = builder.build();
        System.out.println(tiles.size());
        System.out.println(tiles.get(0).size());

        ArrayList <ArrayList<ModelInstance>> tileInstances = new ArrayList<>();

        for (ArrayList<Tile> tiles: tiles) {

            ArrayList<ModelInstance> tile_row = new ArrayList<>();

            for (Tile t : tiles) {
                tile_row.add(t.tileInstance);
            }

            tileInstances.add(tile_row);
        }

        GameInput inputProcessor = new GameInput(camera, tileInstances, board);
        Gdx.input.setInputProcessor(inputProcessor);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();

        modelBatch.begin(camera);

        for (int row = 0; row < tiles.size(); row++) {
            for (int col = 0; col < tiles.get(row).size(); col++) {

                ModelInstance inst = tiles.get(row).get(col).tileInstance;
                Vector3 center = tiles.get(row).get(col).worldCenter;
                modelBatch.render(inst, env);

                switch(board.getPiece(row, col)) {

                    case sPiece:
                        modelBatch.render(renderPiece(sp.getPieceModel(), center));
                        break;
                    case oPiece:
                        modelBatch.render(renderPiece(op.getPieceModel(), center));
                        break;
                    default:
                        break;

                }
            }
        }

        modelBatch.end();

    }

    @Override
    public void resize(int width, int height) {

        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {

        modelBatch.dispose();

    }
}
