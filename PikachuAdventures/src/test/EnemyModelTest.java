import model.EnemyModel;
import model.LevelStateModel;
import model.PlayerModel;
import view.TileMap;

import java.io.IOException;

import static org.junit.Assert.*;

public class EnemyModelTest {
    private LevelStateModel lsm = new LevelStateModel();

    public EnemyModelTest() throws IOException {
        lsm.getTileMap().loadMap("D:\\PikachuAdventures\\Resources\\mapTest.txt");
    }

    @org.junit.Test
    public void setPosition() {
        int x = 30;
        int y = 30;
        lsm.getEnemies().get(0).setPosition(x + 100, y + 150);
        assertEquals(130, lsm.getEnemies().get(0).getEm().getx());
        assertEquals(180, lsm.getEnemies().get(0).getEm().gety());
    }
}