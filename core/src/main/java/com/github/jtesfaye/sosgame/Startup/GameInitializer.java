package com.github.jtesfaye.sosgame.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.jtesfaye.sosgame.BoardComponents.BoardBuilder;
import com.github.jtesfaye.sosgame.BoardComponents.TileModel;
import com.github.jtesfaye.sosgame.GameIO.*;
import com.github.jtesfaye.sosgame.GameLogic.GameLogicFactory;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.github.jtesfaye.sosgame.GameObject.Player;
import com.github.jtesfaye.sosgame.GameObject.PlayerFactory;
import com.github.jtesfaye.sosgame.Main;
import com.github.jtesfaye.sosgame.Screens.GameScreen;
import com.github.jtesfaye.sosgame.computer.EasyGameStrategy;
import com.github.jtesfaye.sosgame.computer.HardGameStrategy;
import com.github.jtesfaye.sosgame.computer.MediumGameStrategy;

import java.util.Arrays;

public class GameInitializer {

    private static int width;
    private static int height;

    public static NewGameConfig initGame(
        String boardSize,
        String gameMode,
        String p1,
        String p2,
        String p1Color,
        String p2Color,
        String difficulty,
        Main mGame) {

        Pair<Integer, Integer> dimensions = utilFunctions.getBoardDimensions(boardSize);

        width = dimensions.first;
        height = dimensions.second;

        Player[] players = createPlayers(p1, p2, p1Color, p2Color);

        GameLogic logic = GameLogicFactory.createGameLogic(width, height, players, gameMode, mGame.getProcessor());

        InputRouter router = initializeInputRouter(logic, players, difficulty,mGame.getProcessor());

        BoardBuilder builder = new BoardBuilder(width, height);

        ScreenConfig s =  new ScreenConfig(width, height, builder, gameMode, logic.getOpponentName());
        GameScreen screen = new GameScreen(s, mGame);

        EventReaderWriter rec = new EventReaderWriter(width, height, gameMode, Arrays.stream(players).toList());
        mGame.getProcessor().setRecorder(rec);

        return new NewGameConfig(logic, router, screen);
    }

    public static GameScreen setupReplay(GameRecord record, Main mGame) {

        int row = record.boardDim().first;
        int col = record.boardDim().second;
        Player[] players = record.players().toArray(new Player[0]);
        BoardBuilder builder = new BoardBuilder(row, col);
        ScreenConfig s = new ScreenConfig(row, col, builder, record.gameMode(), players[1].getDescription());

        return new GameScreen(s, mGame);
    }

    private static Player[] createPlayers(String p1Type, String p2Type, String p1Color, String p2Color) {

        return new Player[] {
            PlayerFactory.createPlayer(p1Type, getColor(p1Color), "Player 1"),
            PlayerFactory.createPlayer(p2Type, getColor(p2Color), "Player 2")
        };
    }

    private static InputRouter initializeInputRouter(GameLogic logic, Player[] players, String diff ,GameEventProcessor p) {

        InputHandler p1handler, p2handler;

        switch (players[0].getPlayerType()) {
            case Human:
                p1handler = new ClickInputHandler(players[0].getPlayerId());
                break;
            case Computer:
                p1handler = setComputerHandler(players[0], diff);
                if (p1handler == null) {
                    throw new RuntimeException("ComputerInputHandler is null");
                }
                break;
            case LLM:
                p1handler = new LLMInputHandler(players[0].getPlayerId());
                break;
            default:
                throw new RuntimeException("Unrecognized player type");
        }

        switch (players[1].getPlayerType()) {
            case Human:
                p2handler = new ClickInputHandler(players[1].getPlayerId());
                break;
            case Computer:
                p2handler = setComputerHandler(players[1], diff);
                if (p2handler == null) {
                    throw new RuntimeException("ComputerInputHandler is null");
                }
                break;
            case LLM:
                p2handler = new LLMInputHandler(players[1].getPlayerId());
                break;
            default:
                throw new RuntimeException("Unrecognized player type");
        }

        InputRouter router = new InputRouter(logic, p);

        router.registerHandler(p1handler);
        router.registerHandler(p2handler);

        return router;
    }

    public static Stage initTurnUi(Label turn, Label gameMode, Label currentScore) {

        SpriteBatch batch = new SpriteBatch();
        Stage stage = new Stage(new ScreenViewport(), batch);

        Table table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.add(gameMode);
        table.row();
        table.add(currentScore);
        table.row();

        table.add(turn);

        stage.addActor(table);

        return stage;
    }

    public static Label initPlayerLabel(Skin skin) {

        Label currentTurn = new Label("Current Turn: Player 1", skin);
        currentTurn.setPosition(20, Gdx.graphics.getHeight() - 40);

        return  currentTurn;

    }

    public static Label initGameModeLabel(String gameMode, Skin skin) {

        Label gameModeLabel = new Label("Game Mode: " + gameMode + "      ", skin);
        gameModeLabel.setPosition(20, Gdx.graphics.getHeight() - 40);

        return gameModeLabel;
    }

    public static Label initScoreTableLabel(String opponent, Skin skin) {

        String o = opponent + ": 0";
        Label scoreTable = new Label("Current score: \nPlayer 1: 0  " + o, skin);
        scoreTable.setPosition(20, Gdx.graphics.getHeight() - 40);

        return scoreTable;
    }

    public static Environment initEnvironment() {

        Environment e = new Environment();
        e.set(new ColorAttribute(ColorAttribute.AmbientLight,.8f,.8f,.8f, 1f));
        e.add(new DirectionalLight().set(1f, 1f, 1f, -1f, -0.8f, -0.2f));

        return e;
    }

    public static PerspectiveCamera initCamera(int width, int height) {

        PerspectiveCamera camera = new PerspectiveCamera(67 + (width - 3) * 1.5f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float centerx = (width * TileModel.getWidth()) / 2f - (TileModel.getWidth() / 2f);
        float centerz = (height * TileModel.getHeight()) / 2f - (TileModel.getWidth() / 2f);

        camera.position.set(centerx ,9f + (width - 3),(-centerz - 1) + (width - 3) * 1.5f);
        camera.lookAt(centerx,0,centerz);
        camera.up.set(0,0,1);
        camera.near = .1f;
        camera.far = 50f;
        camera.update();

        return camera;

    }

    static private ComputerInputHandler setComputerHandler(Player p, String diff) {

        switch (diff) {
            case "Easy":

                return new ComputerInputHandler(p.getPlayerId(), new EasyGameStrategy());

            case "Medium":

                return new ComputerInputHandler(p.getPlayerId(), new MediumGameStrategy());

            case "Hard":

                return new ComputerInputHandler(p.getPlayerId(), new HardGameStrategy());

            default:
                return null;
        }
    }

    static private Color getColor(String color) {

        switch (color) {

            case "BLUE":
                return Color.BLUE;

            case "RED":
                return Color.RED;

            case "GOLD":
                return Color.GOLD;

            case "PURPLE":
                return Color.PURPLE;

            case "GREEN":
                return Color.GREEN;

            default:
                return Color.SKY;
        }
    }
}
