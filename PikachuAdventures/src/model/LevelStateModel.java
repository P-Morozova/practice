package model;
import gameState.*;
import main.GamePanel;
import view.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import static model.PlayerModel.levelStart;

public class LevelStateModel {
    public static TileMap tileMap;
    private PlayerView player;
    private static ArrayList<PokeballView> poke;
    private static final int maxQuantity = 50;
    private static final int maxAtt = 50;
    private static ArrayList<EnemyView> enemies;
    private static Flag flag;
    private Font font;
    public static boolean flagIsGet;

    public LevelStateModel() throws IOException {
        init();
    }

    public void init() {
        tileMap = new TileMap(30);
        poke = new ArrayList<PokeballView>();
        enemies = new ArrayList<EnemyView>();
        flag = new Flag(tileMap);
        flag.isSet(false);
        tileMap.loadTiles("D:\\PikachuAdventures\\Resources\\Tiles.png");
        tileMap.setPosition(0, 0);
        player = new PlayerView(tileMap);
        player.setStartPosition(100, 100);
        font = new Font ("Arial", Font.BOLD, 30);

    }

    public static void setBonus(int row, int col) {
        poke.add(new PokeballView(tileMap));
        poke.get(poke.size() - 1).setStartPosition(col * 30, row * 30);
    }

    public static void setEnemy(int row, int col, int type) {
        enemies.add(new EnemyView(tileMap, type));
        enemies.get(enemies.size() - 1).setStartPosition(col * 30, row * 30);

    }

    public static void setFlag(int row, int col) {
        flag.setPosition(col * 30, row * 30);
        flag.isSet(true);
    }

    public void update() {
        if(levelStart) {

            if(!player.isDead())
                player.update();
            tileMap.setPosition(((GamePanel.WIDTH / 2) - player.getx()), (GamePanel.HEIGHT / 2) - player.gety());

            for (int i = 0; i < poke.size(); i++) {
                poke.get(i).update();
            }
            for (int i = 0; i < enemies.size(); i++) {
                EnemyView e = enemies.get(i);
                e.update();
            }
        }
        if(player.getPm().intersects(flag) && !flagIsGet && levelStart) {
            levelStart = false;
            flagIsGet = true;
        }

    }


    public boolean levelEnd() {
        return flagIsGet;
    }

    public void setFlagGet(boolean b)  {
        flagIsGet= b;
    }

    public void reset() {
        flagIsGet = false;
        player.reset();
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).reset();
        }
        for(int i = 0; i < poke.size(); i++) {
            poke.get(i).reset();
        }
    }

    public static PokeballView getPokeball(int i) { return poke.get(i); }

    public static int getPokeballsLen() {return poke.size();}

    public static EnemyView getEnemy(int i) { return enemies.get(i); }

    public static int getEnemiesLen() {return enemies.size();}

    public static int getMaxAtt() { return maxAtt; }

    public void keyPressed(int k) throws IOException {
        if(!player.isDead()) {
            if (k == KeyEvent.VK_A) player.setLeft(true);
            if (k == KeyEvent.VK_D) player.setRight(true);
            if (k == KeyEvent.VK_SPACE || k == KeyEvent.VK_W) player.setJumping(true);
        }
        if(k == KeyEvent.VK_R) {
            reset();
        }
        if(k == KeyEvent.VK_F)  player.setFiring(true);
    }

    public void keyReleased(int k) {
        if(k == KeyEvent.VK_A) player.setLeft(false);
        if(k == KeyEvent.VK_D) player.setRight(false);
        if(k == KeyEvent.VK_SPACE || k == KeyEvent.VK_W) player.setJumping(false);
        if(k == KeyEvent.VK_F)  player.setFiring(false);

    }

    public void keyTyped(char k) {
    }

    public PlayerView getPlayer() {
        return player;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public Flag getFlag() {
        return flag;
    }

    public ArrayList<EnemyView> getEnemies() { return enemies; }

    public ArrayList<PokeballView> getPokeballs() { return poke; }

    public int getMaxPokeballQuantity() {
        return maxQuantity;
    }
}
