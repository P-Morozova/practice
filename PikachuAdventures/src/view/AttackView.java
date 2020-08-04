package view;

import model.AttackModel;
import model.MapObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AttackView {

    private AttackModel am;
    private Animation animation;
    private BufferedImage[] attack;
    private int width;
    private int height;

    public AttackView(TileMap tm) {
        am = new AttackModel(tm);
        width = am.getWidth();
        height = am.getHeight();
        int numSprites = 0;
        BufferedImage b1 = null;
        try {
            b1 = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\molniaSprites.png"));
            numSprites = b1.getWidth() / width;
        } catch (IOException e) {
            e.printStackTrace();
        }
        attack = new BufferedImage[numSprites];
        for (int i = 0; i < attack.length; i++) {
            attack[i] = b1.getSubimage(i * width, 0, width, height);
        }
        animation = new Animation();
        animation.setFrames(attack);
    }

    public int getx() { return am.getx();}

     public void setPosition(double x, double y, boolean mirror) { am.setPosition(x, y, mirror);}

    public void update() {
        am.update();
        if (am.getDraw()) {
            animation.setFrame(animation.getCurrentFrame() + 1);
            animation.update();
        }
    }

    public void isDraw(boolean b) { am.isDraw(b);}

    public boolean intersects(MapObject ob) { return am.intersects(ob); }

    public void draw(Graphics2D g) {
        am.setMapPosition();
        int x = am.getx();
        int xmap = am.getxmap();
        int y = am.gety();
        int ymap = am.getymap();
        if(am.getDraw()) {
            if(!am.isMirror())
                g.drawImage(animation.getImage(), (x + xmap - width / 2), (y + ymap - height / 2), null);
            else
                g.drawImage(animation.getImage(), (x + xmap - width / 2 + width), (y + ymap - height / 2), -width, height, null);
        }
    }

    public boolean getDraw() {
        return am.getDraw();
    }
}
