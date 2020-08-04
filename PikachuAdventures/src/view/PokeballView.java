package view;

import model.PokeballModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PokeballView {
    PokeballModel pm;
    Animation animation;
    private BufferedImage[] bonus;

    public PokeballView(TileMap tm) {
        pm = new PokeballModel(tm);
        BufferedImage b = null;
        try {
            b = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\poke.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bonus = new BufferedImage[4];
        for(int i = 0; i < 4; i++) {
            bonus[i] = b.getSubimage(i * 30, 0, 30, 30);
        }
        animation = new Animation();
        animation.setFrames(bonus);
        animation.setDelay(400);
    }

    public boolean isGet() { return pm.isGet(); }

    public void setGet() { pm.setGet(); }

    public void update() {
        pm.update();
        animation.update();
    }

    public void setStartPosition(double x, double y) {
        pm.setStartPosition(x, y);
    }

    public PokeballModel getPm() { return pm; }

    public void reset() { pm.reset(); }

    public void draw(Graphics2D g) {
        pm.setMapPosition();
        int x = pm.getx();
        int xmap = pm.getxmap();
        int y = pm.gety();
        int ymap = pm.getymap();
        if(!pm.isGet()) {
            g.drawImage (animation.getImage(), (x + xmap), (y + ymap), null);
        }

    }
}
