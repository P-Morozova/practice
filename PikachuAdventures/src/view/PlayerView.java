package view;

import io.PlayerReader;
import model.PlayerModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static model.PlayerModel.*;

public class PlayerView {

    private PlayerModel pm;
    private PlayerReader pr;
    private String[] paths = { "D:\\PikachuAdventures\\Resources\\standing.png", "D:\\PikachuAdventures\\Resources\\running.png",
            "D:\\PikachuAdventures\\Resources\\jumping.png", "D:\\PikachuAdventures\\Resources\\attacking.png",
            "D:\\PikachuAdventures\\Resources\\dieded.png"};

    private ArrayList<BufferedImage[]> sprites;
    private Animation animation;

    public PlayerView(TileMap tm) {

        pm = new PlayerModel(tm);
        pr = new PlayerReader();
        sprites = pr.spriteRead(paths);
        animation = new Animation();
        animation.setFrames(sprites.get(pm.getCurrentAction()));
        animation.setDelay(400);
    }

    public void update() {
        pm.update();
        if(pm.isChangeAnimations()) {
            animation.setFrames(sprites.get(pm.getCurrentAction()));
            animation.setDelay(pm.getDelay());
        }
        animation.update();
    }

    public void reset() { pm.reset(); }

    public boolean isDead() { return pm.isDead(); }

    public boolean isLevelStart() { return levelStart; }

    public static void levelStart(boolean b) {
        levelStart = b;
    }

    public void setStartPosition(double x, double y) {
        pm.setStartPosition(x, y);
    }

    public int getx() { return pm.getx(); }

    public int gety() { return pm.gety(); }

    public int getxmap() { return pm.getxmap(); }

    public int getymap() { return pm.getymap(); }

    public int getWidth() { return pm.getWidth(); }

    public int getHeight() { return pm.getHeight(); }

    public int getHealth() { return pm.getHealth(); }

    public int getMaxHealth() { return pm.getMaxHealth(); }

    public int getAtt() { return pm.getAtt(); }

    public PlayerModel getPm() { return pm; }

    public void setLeft(boolean b) { pm.setLeft(b); }

    public void setRight(boolean b) { pm.setRight(b); }

    public void setJumping(boolean b) { pm.setJumping(b); }

    public void setFiring(boolean b) { pm.setFiring(b);}

    public void draw(Graphics2D g) {
        pm.setMapPosition();
        if(levelStart) {

            if(pm.isFlinching() && !pm.isDead()) {
                long elapsed = System.currentTimeMillis() - pm.getFlinchTimeStart();
                if(elapsed / 100 % 2 == 0)
                    return;
            }

            BufferedImage bi = animation.getImage();
            int x = pm.getx();
            int xmap = pm.getxmap();
            int width = pm.getWidth();
            int y = pm.gety();
            int ymap = pm.getymap();
            int height = pm.getHeight();
            int cwidth = pm.getCWidth();

            if (pm.isFacingRight()) {
                g.drawImage(bi, (x + xmap - width / 2), (y + ymap - height / 2), null);
                pm.getLightning().setPosition(x + cwidth, y, false);

            } else {
                g.drawImage(bi, (x + xmap - width / 2 + width), (y + ymap - height / 2), -width, height, null);
                pm.getLightning().setPosition(x - cwidth, y, true);
            }
            pm.getLightning().draw(g);
        }
    }

    public int getPokeCount() {
        return pm.getPokeCount();
    }
}
