package util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.jtesfaye.sosgame.util.GameInit;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.github.jtesfaye.sosgame.util.GameInitializer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameInitializerTest {

    @Test
    @DisplayName("initGame() should extract correct width and height from board size and the game mode")
    void testGettingBoardSizeAndMode() {
        // "boardSize" would normally be a string like "3x3"
        GameInit gameInit = GameInitializer.initGame("3x3", "Simple");

        assertEquals(3, gameInit.getBoardWidth(), "Width should be 3");
        assertEquals(3, gameInit.getBoardHeight(), "Height should be 3");
        assertEquals("Simple", gameInit.getGameMode(), "Game mode should be Simple");
        assertNotNull(gameInit.getLogic(), "GameLogic should be initialized");
        assertNotNull(gameInit.getBuilder(), "BoardBuilder should be initialized");
    }

}
