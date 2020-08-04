package model;

import view.TileMap;

import java.awt.image.BufferedImage;

public class PokeballModel extends MapObject {
    private BufferedImage[] bonus;
    protected boolean isGet;
    private static int pokeCount;

    public PokeballModel(TileMap tm) {
        super(tm);
        pokeCount = 0;
        width = 30;
        height = 30;
        isGet = false;
        cwidth = 12;
        cheight = 12;
    }

    public void update() {}

    public void setGet() {
        isGet = true;
        addPokeball();
    }

    public boolean isGet() { return isGet; }

    public void reset() {
        setPosition(startX, startY);
        isGet = false;
        pokeCount = 0;
    }

    public void addPokeball() {
        pokeCount++;
    }
}
