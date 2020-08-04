import model.LevelStateModel;
import model.PlayerModel;
import view.TileMap;

import java.io.IOException;

import static org.junit.Assert.*;

public class PlayerModelTest {

    private LevelStateModel lsm = new LevelStateModel();
    private PlayerModel pm = new PlayerModel(lsm.getTileMap());

    public PlayerModelTest() throws IOException {
        lsm.getTileMap().loadMap("D:\\PikachuAdventures\\Resources\\mapTest.txt");
    }

    @org.junit.Test
    public void checkIntersectEnemy() {
        double x = lsm.getEnemies().get(0).getEm().getx();
        double y = lsm.getEnemies().get(0).getEm().gety();
        pm.setPosition(x, y);
        pm.checkIntersectEnemy();
        assertEquals(2, pm.getHealth());
    }

    @org.junit.Test
    public void checkGetPokeball() {
        double x = lsm.getPokeballs().get(0).getPm().getx();
        double y = lsm.getPokeballs().get(0).getPm().gety();
        pm.setPosition(x, y);
        pm.checkGetPokeball();
        assertEquals(1, pm.getPokeCount());
    }

    @org.junit.Test
    public void checkEnemyDied() {
        pm.getLightning().isDraw(true);
        double x = lsm.getEnemies().get(0).getEm().getx();
        double y = lsm.getEnemies().get(0).getEm().gety();
        pm.setPosition(x - 30, y);
        pm.getLightning().setPosition(x, y, false);
        pm.checkEnemyDie();
        assertTrue(lsm.getEnemies().get(0).isDead());
    }


}