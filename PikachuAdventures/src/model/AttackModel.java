package model;

import view.TileMap;

import java.awt.*;

public class AttackModel extends MapObject {
    private boolean draw;
    private boolean mirror;

    public AttackModel(TileMap tm) {
        super(tm);
        width = 30;
        height = 30;
        cwidth = 27;
        cheight = 29;
    }

    public Rectangle getRectangle() {
        return new Rectangle((int)(x - cwidth / 2 + xmap),(int)(y - cheight / 2 + ymap), cwidth, cheight);
    }

    public void setPosition(double x, double y, boolean mirror) {
        this.x = x;
        this.y = y;
        this.mirror = mirror;
    }

    public void update() {}

    public void isDraw(boolean b) {
        draw = b;
    }

    public boolean getDraw() {
        return draw;
    }

    public boolean isMirror() { return mirror; }

}
