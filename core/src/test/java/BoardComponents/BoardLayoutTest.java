package BoardComponents;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;
import com.github.jtesfaye.sosgame.BoardComponents.BoardLayout;
import com.github.jtesfaye.sosgame.BoardComponents.TileModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardLayoutTest {

    @Test
    public void testTileCentersAreEquallySpaced() {

        for (int size = 3; size <= 9; size++) {
            BoardLayout layout = new BoardLayout(size, size, TileModel.getWidth(), TileModel.getHeight());
            ArrayList<Vector3> centers = layout.getCenters();

            float requiredDistance = centers.get(0).dst(centers.get(1));

            boolean flag = true;

            for (int i = 2; i < centers.size(); i++) {

                float dist;

                if (i % size == 0) {
                    System.out.println(centers.get(i));
                    System.out.println(centers.get(i - size));
                    dist = centers.get(i).dst(centers.get(i - size));

                } else {
                    dist = centers.get(i).dst(centers.get(i - 1));

                }

                if(Math.abs(requiredDistance - dist) > .00001f) {
                    flag = false;
                    break;
                }
            }
            assertTrue(flag, "The distance between the tile centers should be equal.");
        }
    }
}
