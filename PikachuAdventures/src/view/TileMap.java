package view;

import io.MapReader;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileMap {
    MapReader mr;
    private double x;
    private double y;

    private int xmin;
    private int xmax;
    private int ymin;
    private int ymax;

    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numColumns;
    private int width;
    private int height;
    private int columnOffset;
    private int rowOffset;

    private int numRowsToDraw;
    private int numColsToDraw;

    private Tile[][] tiles;

    public static class Tile {
        private BufferedImage image;
        private int type;

        public static final int NORMAL = 0;
        public static final int BLOCKED = 1;

        public Tile(BufferedImage image, int type) {
            this.image = image;
            this.type = type;
        }

        public BufferedImage getImage() {
            return image;
        }

        public int getType()  {
            return type;
        }
    }

    private BufferedImage pokeballs;
    private BufferedImage heart;
    private BufferedImage attacks;

    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        mr = new MapReader();
        try {
            pokeballs = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\curPoke.png"));
            heart = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\hearts.png"));
            attacks = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\attack.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
    }

    public void loadTiles(String s) {
        tiles = mr.readTiles(s, tileSize);
    }

    public void loadMap(String s) {
        map = mr.readMap(s);
        numRows = map.length;
        numColumns = map[0].length;
        width = numColumns * tileSize;
        height = numRows * tileSize;
        xmin = GamePanel.WIDTH - width;
        xmax = 0;
        ymin = GamePanel.HEIGHT - height;
        ymax = 0;

    }


    public int getTileSize() {
        return tileSize;
    }

    public int getx() {
        return (int)x;
    }

    public int gety() {
        return (int) y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getType(int row, int col) {
        int rc = map[row][col];
        if(rc == 0) return Tile.NORMAL;
        return tiles[0][rc - 1].getType();
    }

    public void setPosition(double x, double y) {
        this.x = x - 100;
        this.y = y - 100;
        fixBounds();
        columnOffset = (int)-this.x / tileSize;
        rowOffset= (int)-this.y / tileSize;
    }

    private void fixBounds() {
        if(x < xmin) x = xmin;
        if(y < ymin) y = ymin;
        if(x > xmax) x = xmax;
        if(y > ymax) y = ymax;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numColumns;
    }

    public void draw(Graphics2D g) {
        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
            if (row >= numRows) break;
            for (int col = columnOffset; col < columnOffset + numColsToDraw; col++) {
                if (col >= numColumns) break;
                if (map[row][col] == 0) continue;
                int rc = map[row][col];
                g.drawImage(tiles[0][rc - 1].getImage(), (int) x + col * tileSize, (int) y + row * tileSize, tileSize, tileSize, null);
            }
        }
        g.drawImage(pokeballs, 0, 0, null);
        g.drawImage(heart, 7, pokeballs.getHeight() - 3, heart.getWidth() / 53, heart.getHeight() / 53, null);
        g.drawImage(attacks, 0, 40, (int) (attacks.getWidth() / 1.5), (int) (attacks.getHeight() / 1.5), null);
    }

}
