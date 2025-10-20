package com.github.jtesfaye.sosgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.PerspectiveCamera;

import com.github.jtesfaye.sosgame.util.GameInit;
import com.github.jtesfaye.sosgame.GameIO.GameInput;
import com.github.jtesfaye.sosgame.GameIO.InputHandler;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.BoardComponents.Tile;
import com.github.jtesfaye.sosgame.util.GameInitializer;
import com.github.jtesfaye.sosgame.BoardComponents.oPieceModel;
import com.github.jtesfaye.sosgame.BoardComponents.sPieceModel;
import com.github.jtesfaye.sosgame.BoardComponents.BoardBuilder;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private PerspectiveCamera camera;

    private final BoardBuilder builder;
    private ModelBatch modelBatch;
    private Environment env;
    private sPieceModel sp;
    private oPieceModel op;

    private final GameLogic logic;

    private ArrayList<ArrayList<Tile>> tiles;

    private final Stage stage;
    private final Label currentTurn;

    public GameScreen(GameInit init) {

        logic = init.getLogic();
        builder = init.getBuilder();
        sp = new sPieceModel();
        op = new oPieceModel();
        currentTurn = GameInitializer.initLabel();
        stage = GameInitializer.initTurnUi(currentTurn);

    }

    @Override
    public void show() {

        modelBatch = new ModelBatch();
        env = GameInitializer.initEnvironment();
        camera = GameInitializer.initCamera(logic.boardRow, logic.boardCol);

        tiles = builder.build();
        ArrayList <ArrayList<ModelInstance>> tileInstances = makeTileInstances();

        GameInput inputProcessor = new GameInput(camera, tileInstances, new InputHandler(logic));
        Gdx.input.setInputProcessor(inputProcessor);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();

        modelBatch.begin(camera);

        updateBoard();
        modelBatch.end();
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void dispose() {

        modelBatch.dispose();
        stage.dispose();

    }

    private ArrayList<ArrayList<ModelInstance>> makeTileInstances() {

        ArrayList <ArrayList<ModelInstance>> inst = new ArrayList<>();

        for (ArrayList<Tile> tiles: tiles) {

            ArrayList<ModelInstance> tile_row = new ArrayList<>();

            for (Tile t : tiles) {
                tile_row.add(t.tileInstance);
            }

            inst.add(tile_row);
        }

        return inst;
    }

    private ModelInstance renderPiece(Model m, Vector3 center) {

        ModelInstance inst = new ModelInstance(m);
        inst.transform.setToTranslation(center).translate(0,0f,0f).scale(3f,3f,3f);

        return inst;

    }

    private void setCurrentPlayer() {
        currentTurn.setText("Current turn: " + logic.getCurrentTurn().toString());
    }

    private void updateBoard() {

        for (int row = 0; row < tiles.size(); row++) {
            for (int col = 0; col < tiles.get(row).size(); col++) {

                ModelInstance inst = tiles.get(row).get(col).tileInstance;
                Vector3 center = tiles.get(row).get(col).worldCenter;
                modelBatch.render(inst, env);

                switch(logic.getPiece(row, col)) {

                    case sPiece:
                        modelBatch.render(renderPiece(sp.getPieceModel(), center));
                        setCurrentPlayer();
                        break;
                    case oPiece:
                        modelBatch.render(renderPiece(op.getPieceModel(), center));
                        setCurrentPlayer();
                        break;
                    default:
                        break;

                }
            }
        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
