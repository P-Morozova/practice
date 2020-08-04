package gameState;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class GameState {

    protected GameStateManager gsm;
    protected Background bg;

    public abstract void init() throws IOException;
    public abstract void update();
    public abstract void draw(Graphics2D g);
    public abstract void keyPressed(int k) throws IOException;
    public abstract void keyReleased(int k);
    public abstract void keyTyped(char k);
    protected abstract void select() throws IOException;

    public static class Background {
        private BufferedImage image;
        private double x;
        private double y;

        public Background(String fileName) throws IOException {
            image = ImageIO.read(new File(fileName));
        }

        public void setPosition(double x, double y) {
            this.x = x % GamePanel.WIDTH;
            this.y = y % GamePanel.HEIGHT;
        }

        public void draw(Graphics2D g) {
            g.drawImage(image, (int) x, (int) y,null);
        }
    }

}
