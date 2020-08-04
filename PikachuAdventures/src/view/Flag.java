package view;

import model.MapObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Flag extends MapObject {
    private BufferedImage[] flag;
    private boolean isSet;

    public Flag(TileMap tm) {
        super(tm);
        flag = new BufferedImage[1];
        width = 30;
        height = 30;
        cwidth = 30;
        cheight = 30;
        try {
            flag[0] = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\flag.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        animation = new Animation();
        animation.setFrames(flag);
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        if(isSet) {
            g.drawImage(animation.getImage(), (int) (x + xmap), (int) (y + ymap), null);
        }

    }

    public void isSet(boolean b) {
        isSet = b;
    }
}
