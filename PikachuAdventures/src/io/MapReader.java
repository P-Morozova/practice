package io;

import model.LevelStateModel;
import view.TileMap.Tile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class MapReader {

    public Tile[][] readTiles(String path, int tileSize) {
        BufferedImage tileset = null;
        try {
            tileset = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int numTilesAcross = tileset.getWidth() / tileSize;
        Tile[][] tiles = new Tile[1][numTilesAcross];
        BufferedImage subimage;
        for(int col = 0; col < numTilesAcross; col++) {
            subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
            if(col > 8) tiles[0][col] = new Tile(subimage, Tile.NORMAL);
            else tiles[0][col] = new Tile(subimage, Tile.BLOCKED);
        }
        return tiles;
    }

    public int[][] readMap(String path) {

        InputStream in = null;
        try {
            in = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(in);
        int numColumns = sc.nextInt();
        int numRows = sc.nextInt();
        int[][] map = new int[numRows][numColumns];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                map[row][col] = sc.nextInt();
                if (map[row][col] == 10) {
                    map[row][col] = 0;
                    LevelStateModel.setBonus(row, col);
                } else if (map[row][col] == 11 || map[row][col] == 12) {
                    LevelStateModel.setEnemy(row, col, map[row][col]);
                    map[row][col] = 0;
                } else if (map[row][col] == 13) {
                    map[row][col] = 0;
                    LevelStateModel.setFlag(row, col);
                }
            }

        }
        return map;
    }



}
