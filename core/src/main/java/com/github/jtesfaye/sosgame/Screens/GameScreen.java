package com.github.jtesfaye.sosgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.PerspectiveCamera;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.jtesfaye.sosgame.GameLogic.Piece;
import com.github.jtesfaye.sosgame.util.GameInit;
import com.github.jtesfaye.sosgame.GameIO.GameInput;
import com.github.jtesfaye.sosgame.GameIO.InputHandler;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.BoardComponents.Tile;
import com.github.jtesfaye.sosgame.util.GameInitializer;
import com.github.jtesfaye.sosgame.BoardComponents.oPieceModel;
import com.github.jtesfaye.sosgame.BoardComponents.sPieceModel;
import com.github.jtesfaye.sosgame.BoardComponents.BoardBuilder;
import com.github.jtesfaye.sosgame.util.Pair;

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

    private final Stage gameOverlay;
    private final Label currentTurnLabel;
    private final Label currentScoreLabel;

    private final ArrayList<Pair<Piece, Vector3>> piecesToRender;

    public GameScreen(GameInit init) {

        logic = init.getLogic();
        builder = init.getBuilder();
        sp = new sPieceModel(Color.GREEN);
        op = new oPieceModel(Color.GREEN);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        currentScoreLabel = GameInitializer.initScoreTableLabel(init.getOpponentType(), skin);
        currentTurnLabel = GameInitializer.initPlayerLabel(skin);
        gameOverlay = GameInitializer.initTurnUi(
            currentTurnLabel,
            GameInitializer.initGameModeLabel(
                init.getGameMode(),
                skin),
            currentScoreLabel
        );
        piecesToRender = new ArrayList<>();

    }

    @Override
    public void show() {

        modelBatch = new ModelBatch();
        env = GameInitializer.initEnvironment();
        camera = GameInitializer.initCamera(logic.boardRow, logic.boardCol);

        tiles = builder.build(Color.CHARTREUSE, Color.RED);
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

        for (ArrayList<Tile> tile : tiles) {
            for (Tile value : tile) {
                ModelInstance inst = value.tileInstance;
                modelBatch.render(inst, env);
            }
        }

        updateState();

        for (Pair<Piece, Vector3> piece : piecesToRender) {
            modelBatch.render(renderPiece(piece.first, piece.second), env);
        }

        modelBatch.end();

        gameOverlay.act(delta);
        gameOverlay.draw();

    }

    @Override
    public void resize(int width, int height) {

        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        gameOverlay.getViewport().update(width, height, true);

    }

    @Override
    public void dispose() {

        modelBatch.dispose();
        gameOverlay.dispose();

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

    private ModelInstance renderPiece(Piece piece, Vector3 center) {

        Model m = piece.equals(Piece.sPiece) ? sp.getPieceModel() : op.getPieceModel();
        ModelInstance inst = new ModelInstance(m);
        inst.transform.setToTranslation(center).translate(0,0f,0f).scale(3f,3f,3f);

        return inst;

    }

    private void setCurrentPlayer() {
        currentTurnLabel.setText("Current turn: " + logic.getCurrentTurn().toString() );
    }

    private void updateScore() {

        StringBuilder score = new StringBuilder("Current Score:\n");
        ArrayList<Pair<String, String>> scores = logic.getScores();
        for (Pair<String, String> item : scores) {

            score.append(item.first).append(": ").append(item.second).append("  ");

        }

        currentScoreLabel.setText(score);

    }

    private void updateState() {

        Pair<Pair<Integer, Integer>, Piece> placement = logic.getChanges();

        if (placement != null) {

            int row = placement.first.first;
            int col = placement.first.second;

            Pair<Piece, Vector3> rend = new Pair<>(
                placement.second,
                tiles.get(row).get(col).worldCenter
            );

            piecesToRender.add(rend);
            updateScore();

            setCurrentPlayer();
        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
