package view;

import model.EnemyModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static model.EnemyModel.JUMP_TYPE;
import static model.EnemyModel.WALK_TYPE;

public class EnemyView {

    private EnemyModel em;
    Animation animation;
    private BufferedImage[] sprites;

    public EnemyView(TileMap tm, int type) {
        em = new EnemyModel(tm, type);
        animation = new Animation();
        try {
            if(type == WALK_TYPE) {
                BufferedImage bi = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\enemy.png"));
                sprites = new BufferedImage[9];
                for (int i = 0; i < sprites.length; i++) {
                    sprites[i] = bi.getSubimage(i * 30, 0, 30, 30);
                }
                animation.setDelay(300);
            }
            else if(type == JUMP_TYPE) {
                BufferedImage bi = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\enemy2.png"));
                sprites = new BufferedImage[2];
                for (int i = 0; i < sprites.length; i++) {
                    sprites[i] = bi.getSubimage(i * 30, 0, 30, 30);
                }
                animation.setDelay(500);
                em.setFacingRight();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        animation.setFrames(sprites);
    }

    public double getMoveSpeed() { return em.getMoveSpeed(); }

    public EnemyModel getEm() { return em; }

    public void dead() { em.dead(); }

    public void setMapPosition() { em.setMapPosition(); }
    public Rectangle getRectangle() { return em.getRectangle(); }


    public void update() {
        em.update();
        if(em.getType() == JUMP_TYPE) {
            if (em.jumping()) animation.setFrame(1);
            else animation.setFrame(0);
        }
        animation.update();
    }

    public void setRight(boolean b) { em.setRight(b);}
    public void setLeft(boolean b) { em.setLeft(b);}

    public void setStartPosition(double x, double y) { em.setStartPosition(x, y); }
    public void setPosition(double x, double y) { em.setPosition(x, y); }
    public void reset() { em.reset(); }

    public void draw(Graphics2D g) {
        setMapPosition();
        if(!em.isDead()) {
            BufferedImage cur = animation.getImage();
            if (em.isFacingRight()) {
                g.drawImage(cur, (int) (em.getx() + em.getxmap()), (int) (em.gety() + em.getymap()), null);

            } else {
                g.drawImage(cur, em.getx() + em.getxmap() + em.getWidth(),
                        em.gety()  + em.getymap(), -em.getWidth(), em.getHeight(), null);
            }

        }
    }

    public double getXtemp() {return em.getXtemp();}
    public double getYtemp() {return em.getYtemp();}

    public boolean isDead() {
        return em.isDead();
    }
}
