package io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class PlayerReader {

    public PlayerReader() {}

    public ArrayList spriteRead(String[] paths) {
        ArrayList sprites = new ArrayList<BufferedImage[]>();
        try {
            BufferedImage bi = ImageIO.read(new File(paths[0]));
            BufferedImage[] temp2 = new BufferedImage[17];
            for (int i = 0; i < 17; i++) {
                temp2[i] = bi.getSubimage(i * 30, 0, 30, 30);
            }
            sprites.add(temp2);

            bi = ImageIO.read(new File(paths[1]));
            BufferedImage[] temp = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                temp[i] = bi.getSubimage(i * 30, 0, 30, 30);
            }
            sprites.add(temp);

            bi = ImageIO.read(new File(paths[2]));
            BufferedImage[] temp3 = new BufferedImage[1];
            temp3[0] = bi.getSubimage(0, 0, 30, 30);
            sprites.add(temp3);

            bi = ImageIO.read(new File(paths[3]));
            BufferedImage[] temp4 = new BufferedImage[40];
            for (int i = 0; i < 40; i++) {
                temp4[i] = bi.getSubimage(i * 30, 0, 30, 30);
            }
            sprites.add(temp4);

            bi = ImageIO.read(new File(paths[4]));
            BufferedImage[] temp5 = new BufferedImage[1];
            temp5[0] = bi.getSubimage(0, 0, 30, 30);
            sprites.add(temp5);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return sprites;
    }
}
