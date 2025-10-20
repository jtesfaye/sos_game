package com.github.jtesfaye.sosgame.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.jtesfaye.sosgame.BoardComponents.BoardBuilder;
import com.github.jtesfaye.sosgame.BoardComponents.Tile;
import com.github.jtesfaye.sosgame.BoardComponents.TileModel;
import com.github.jtesfaye.sosgame.GameLogic.GameLogicFactory;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;

import lombok.Value;

import java.util.ArrayList;


public class GameInitializer {

    private static int width;
    private static int height;

    public static GameInit initGame(String boardSize, String gameMode) {

        Pair<Integer, Integer> dimensions = utilFunctions.getBoardDimensions(boardSize);

        width = dimensions.first;
        height = dimensions.second;

        GameLogic logic = GameLogicFactory.createGameLogic(width, height, gameMode);

        BoardBuilder builder = new BoardBuilder(width, height, Color.CHARTREUSE, Color.RED);

        return new GameInit(width, height, logic, builder);

    }

    public static Stage initTurnUi(Label turn) {

        SpriteBatch batch = new SpriteBatch();
        Stage stage = new Stage(new ScreenViewport(), batch);

        Table table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.add(turn);

        stage.addActor(table);;

        return stage;
    }

    public static Label initLabel() {

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Label currentTurn = new Label("Current Turn: Player 1", skin);
        currentTurn.setPosition(20, Gdx.graphics.getHeight() - 40);

        return  currentTurn;

    }

    public static Environment initEnvironment() {

        Environment e = new Environment();
        e.set(new ColorAttribute(ColorAttribute.AmbientLight,.8f,.8f,.8f, 1f));
        e.add(new DirectionalLight().set(1f, 1f, 1f, -1f, -0.8f, -0.2f));

        return e;
    }

    public static PerspectiveCamera initCamera(int width, int height) {

        PerspectiveCamera camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float centerx = (width * TileModel.getWidth()) / 2f - (TileModel.getWidth() / 2f);
        float centerz = (height * TileModel.getHeight()) / 2f - (TileModel.getWidth() / 2f);

        camera.position.set(centerx ,10f,-centerz);
        camera.lookAt(centerx,0,centerz);
        camera.up.set(0,0,1);
        camera.near = .1f;
        camera.far = 50f;
        camera.update();

        return camera;

    }

}
