package com.github.jtesfaye.sosgame.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.PerspectiveCamera;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.github.jtesfaye.sosgame.BoardComponents.*;
import com.github.jtesfaye.sosgame.GameEvent.*;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.GameObject.Tile;
import com.github.jtesfaye.sosgame.GameIO.GdxInput;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.util.GameInitializer;
import com.github.jtesfaye.sosgame.util.MenuInitializer;
import com.github.jtesfaye.sosgame.util.Pair;
import com.github.jtesfaye.sosgame.util.ScreenInit;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private final Game game;

    private PerspectiveCamera camera;

    private final BoardBuilder builder;
    private ModelBatch modelBatch;
    private Environment env;
    private sPieceModel sp;
    private oPieceModel op;

    private final GameLogic logic;

    private boolean gameOver;
    private String endGameMessage;

    private ArrayList<ArrayList<Tile>> tiles;

    private final Stage gameOverlay;
    private final Label currentTurnLabel;
    private final Label currentScoreLabel;
    private TextButton returnButton;
    private final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    private final ScreenInit init;
    private final ArrayList<ModelInstance> modelsToRender;

    public GameScreen(ScreenInit init, Game game) {

        this.game = game;
        this.init = init;
        gameOver = false;
        logic = init.getLogic();
        builder = init.getBuilder();
        sp = new sPieceModel(Color.GREEN);
        op = new oPieceModel(Color.GREEN);
        returnButton = null;

        currentScoreLabel = GameInitializer.initScoreTableLabel(logic.getOpponentName(), skin);
        currentTurnLabel = GameInitializer.initPlayerLabel(skin);

        gameOverlay = GameInitializer.initTurnUi(
            currentTurnLabel,
            GameInitializer.initGameModeLabel(init.getGameMode(), skin),
            currentScoreLabel
        );

        modelsToRender = new ArrayList<>();

    }

    @Override
    public void show() {

        modelBatch = new ModelBatch();
        env = GameInitializer.initEnvironment();
        camera = GameInitializer.initCamera(logic.boardRow, logic.boardCol);

        tiles = builder.build(Color.WHITE, Color.GRAY);
        ArrayList <ArrayList<ModelInstance>> tileInstances = makeTileInstances();

        GdxInput inputProcessor = new GdxInput(camera, tileInstances, init.getHandlers(), init.getLogic());
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

        for (ModelInstance model : modelsToRender) {
            modelBatch.render(model, env);
        }

        modelBatch.end();

        if (gameOver) {
            Gdx.input.setInputProcessor(gameOverlay);
            renderEndGame(endGameMessage);
        }

        gameOverlay.act(delta);
        gameOverlay.draw();

    }

    @Override
    public void resize(int width, int height) {

        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        gameOverlay.getViewport().update(width, height, true);

        if (returnButton != null) {

            returnButton.setPosition(
                Gdx.graphics.getWidth() / 2f - (returnButton.getWidth() / 2f),
                Gdx.graphics.getHeight() / 2f - 100
            );
        }
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

    private void renderEndGame(String winnerMessage) {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        SpriteBatch batch = new SpriteBatch();

        BitmapFont font = new BitmapFont();
        font.getData().setScale(2f);
        font.setColor(Color.GOLD);

        batch.begin();
        font.draw(batch, winnerMessage,
            Gdx.graphics.getWidth() / 2f - (winnerMessage.length() / 2f) * 11f ,
            Gdx.graphics.getHeight() / 2f + 20);
        batch.end();

        if (returnButton == null) {

            returnButton = MenuInitializer.getMainMenuButton(skin, game, this);
            returnButton.setColor(Color.CYAN);


            gameOverlay.addActor(returnButton);
        }

        returnButton.setPosition(
            Gdx.graphics.getWidth() / 2f - (returnButton.getWidth() / 2f),
            Gdx.graphics.getHeight() / 2f - 100
        );
    }

    private void setCurrentPlayer() {

        currentTurnLabel.setText("Current turn: " + logic.getCurrentTurn().toString());
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

        GameEvent event = logic.getChanges();

        if (event == null) {
            return;
        }

        if (event instanceof PieceSetEvent) {

            PieceSetEvent e = (PieceSetEvent) event;

            int row = e.row;
            int col = e.col;
            Piece p = e.piece;

            Vector3 center = tiles.get(row).get(col).worldCenter;

            modelsToRender.add(renderPiece(p, center));
            updateScore();

            setCurrentPlayer();

        }

        if (event instanceof SOSMadeEvent) {

            SOSMadeEvent e = (SOSMadeEvent) event;

            Vector3 start = tiles.get(e.row1).get(e.col1).worldCenter;
            Vector3 middle = tiles.get(e.row2).get(e.col2).worldCenter;
            Vector3 end = tiles.get(e.row3).get(e.col3).worldCenter;

            modelsToRender.add(SOSSlashModel.SOSSlashInstance(start, middle, end, e.color));

        }

        if (event instanceof WinnerEvent) {

            WinnerEvent e = (WinnerEvent) event;
            gameOver = true;
            endGameMessage = "Winner: " + e.player.toString();
        }

        if (event instanceof TieEvent) {

            TieEvent e = (TieEvent) event;
            gameOver = true;
            endGameMessage = e.message;
        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
