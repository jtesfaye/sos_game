package com.github.jtesfaye.sosgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.BoardComponents.Tile;
import com.github.jtesfaye.sosgame.GameIO.GameInput;
import com.github.jtesfaye.sosgame.BoardComponents.BoardBuilder;
import com.github.jtesfaye.sosgame.util.Pair;
import com.github.jtesfaye.sosgame.util.utilFunctions;
import com.github.jtesfaye.sosgame.BoardComponents.TileModel;
import com.github.jtesfaye.sosgame.BoardComponents.oPieceModel;
import com.github.jtesfaye.sosgame.BoardComponents.sPieceModel;
import com.github.jtesfaye.sosgame.GameLogic.GameLogicFactory;

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

    private final GameLogic logic;

    private ArrayList<ArrayList<Tile>> tiles;

    private final Stage stage;
    private final Label currentTurn;

    public GameScreen(String boardSize, String gameMode) {

        Pair<Integer, Integer> dimensions = utilFunctions.getBoardDimensions(boardSize);

        boardWidth = dimensions.first;
        boardHeight = dimensions.second;

        logic = GameLogicFactory.createGameLogic(boardWidth, boardHeight, gameMode);

        builder = new BoardBuilder(boardWidth, boardHeight, Color.CHARTREUSE, Color.RED);

        sp = new sPieceModel();
        op = new oPieceModel();

        SpriteBatch batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), batch);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        currentTurn = new Label("Current Turn: Player 1", skin);
        currentTurn.setPosition(20, Gdx.graphics.getHeight() - 40);

        Table table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.add(currentTurn);

        stage.addActor(table);

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

        ArrayList <ArrayList<ModelInstance>> tileInstances = new ArrayList<>();

        for (ArrayList<Tile> tiles: tiles) {

            ArrayList<ModelInstance> tile_row = new ArrayList<>();

            for (Tile t : tiles) {
                tile_row.add(t.tileInstance);
            }

            tileInstances.add(tile_row);
        }

        GameInput inputProcessor = new GameInput(camera, tileInstances, logic);
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
