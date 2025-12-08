package com.github.jtesfaye.sosgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
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
import com.github.jtesfaye.sosgame.GameEventProcessor;
import com.github.jtesfaye.sosgame.GameObject.Piece;
import com.github.jtesfaye.sosgame.GameObject.Tile;
import com.github.jtesfaye.sosgame.GameIO.GdxInput;
import com.github.jtesfaye.sosgame.Main;
import com.github.jtesfaye.sosgame.Startup.GameConfigure;
import com.github.jtesfaye.sosgame.Startup.GameInitializer;
import com.github.jtesfaye.sosgame.util.*;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private final Main game;

    private PerspectiveCamera camera;

    private final BoardBuilder builder;
    private ModelBatch modelBatch;
    private Environment env;
    private sPieceModel sp;
    private oPieceModel op;

    private boolean gameOver;
    private String endGameMessage;

    private ArrayList<ArrayList<Tile>> tiles;

    private final Stage gameOverlay;
    private final Label currentTurnLabel;
    private final Label currentScoreLabel;
    private TextButton returnButton;
    private final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    private Cubemap skybox;

    private final ScreenConfig init;
    private final ArrayList<ModelInstance> modelsToRender;

    public GameScreen(ScreenConfig init, Main game) {

        this.game = game;
        this.init = init;

        gameOver = false;
        builder = init.getBuilder();
        sp = new sPieceModel(Color.BLACK);
        op = new oPieceModel(Color.BLACK);
        returnButton = null;

        currentScoreLabel = GameInitializer.initScoreTableLabel(init.getOpponentName(), skin);
        currentTurnLabel = GameInitializer.initPlayerLabel(skin);

        gameOverlay = GameInitializer.initTurnUi(
            currentTurnLabel,
            GameInitializer.initGameModeLabel(init.getGameMode(), skin),
            currentScoreLabel
        );

        modelsToRender = new ArrayList<>();

        registerGameScreen(game.getProcessor());

    }

    @Override
    public void show() {

        modelBatch = new ModelBatch();
        env = GameInitializer.initEnvironment();
        camera = GameInitializer.initCamera(init.getBoardWidth(), init.getBoardHeight());

        skybox = new Cubemap(
            Gdx.files.internal("skybox/right.png"),
            Gdx.files.internal("skybox/left.png"),
            Gdx.files.internal("skybox/up.png"),
            Gdx.files.internal("skybox/down.png"),
            Gdx.files.internal("skybox/back.png"),
            Gdx.files.internal("skybox/front.png")

        );

        env.set(new CubemapAttribute(CubemapAttribute.EnvironmentMap, skybox));

        tiles = builder.build(Color.WHITE, Color.GRAY);
        ArrayList <ArrayList<ModelInstance>> tileInstances = makeTileInstances();

        GdxInput inputProcessor = new GdxInput(camera, tileInstances, game.getProcessor());

        Gdx.input.setInputProcessor(inputProcessor);
        game.getProcessor().addEvent(new onNextTurnEvent()); //Tells eventProcessor to start the game

    }

    @Override
    public void render(float delta) {

        camera.update();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);

        Gdx.gl.glDepthFunc(GL20.GL_LESS);

        modelBatch.begin(camera);

        for (ArrayList<Tile> tile : tiles) {
            for (Tile value : tile) {
                ModelInstance inst = value.tileInstance;
                modelBatch.render(inst, env);
            }
        }

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

    public void addPieceToRender(PieceSetEvent e) {

        Runnable r = () -> {

            int row = e.row;
            int col = e.col;
            Piece p = e.piece;

            Vector3 vec = tiles.get(row).get(col).worldCenter;
            modelsToRender.add(createPieceInstance(p, vec));

        };

        Gdx.app.postRunnable(r);
    }

    public void addSOSSlashToRender(SOSMadeEvent e) {

        Runnable r = () -> {

            Pair<Integer, Integer> tile1 = e.tile1;
            Pair<Integer, Integer> tile2 = e.tile2;
            Pair<Integer, Integer> tile3 = e.tile3;

            Vector3 start = tiles.get(tile1.first()).get(tile1.second()).worldCenter;
            Vector3 middle = tiles.get(tile2.first()).get(tile2.second()).worldCenter;
            Vector3 end = tiles.get(tile3.first()).get(tile3.second()).worldCenter;

            modelsToRender.add(SOSSlashModel.SOSSlashInstance(start, middle, end, e.color));

        };

        Gdx.app.postRunnable(r);

    }

    public void setEndGame(EndGameEvent e) {

        Runnable r = () -> {

            endGameMessage = e.message;
            gameOver = true;

        };

        Gdx.app.postRunnable(r);
    }


    public void setCurrentPlayer(turnChangeEvent event) {

        Runnable r = () -> {

            currentTurnLabel.setText("Current turn: " + event.upcomingPlayer.toString());
        };

        Gdx.app.postRunnable(r);
    }

    public void updateScore(scoreChangeEvent event) {

        Runnable r = () -> {

            ArrayList<Pair<String, String>> scores = event.scores;

            StringBuilder score = new StringBuilder("Current Score:\n");
            for (Pair<String, String> item : scores) {

                score.append(item.first()).append(": ").append(item.second()).append("  ");

            }

            currentScoreLabel.setText(score);
        };

        Gdx.app.postRunnable(r);
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

    private ModelInstance createPieceInstance(Piece piece, Vector3 center) {

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

            returnButton = GameConfigure.getMainMenuButton(skin, game, this);
            returnButton.setColor(Color.CYAN);


            gameOverlay.addActor(returnButton);
        }

        returnButton.setPosition(
            Gdx.graphics.getWidth() / 2f - (returnButton.getWidth() / 2f),
            Gdx.graphics.getHeight() / 2f - 100
        );
    }

    private void registerGameScreen(GameEventProcessor processor) {

        processor.addSubscriber(PieceSetEvent.class, this::addPieceToRender);
        processor.addSubscriber(scoreChangeEvent.class, this::updateScore);
        processor.addSubscriber(turnChangeEvent.class, this::setCurrentPlayer);
        processor.addSubscriber(SOSMadeEvent.class, this::addSOSSlashToRender);
        processor.addSubscriber(EndGameEvent.class, this::setEndGame);

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {

        game.getProcessor().stopRunning();
        game.getEventProcessorThread().shutdown();

    }
}
