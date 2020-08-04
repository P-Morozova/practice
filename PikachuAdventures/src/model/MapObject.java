package model;
import view.Animation;
import view.TileMap;

import java.awt.*;

public abstract class MapObject {
    protected TileMap tileMap;
    protected int tileSize;
    protected double xmap;
    protected double ymap;

    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    protected int width;
    protected int height;

    protected int cwidth;
    protected int cheight;

    protected int currRow;
    protected int currCol;
    protected double xdest;
    protected double ydest;
    protected double xtemp;
    protected double ytemp;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    protected Animation animation;
    protected int currentAction;
    protected boolean facingRight;

    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean jumping;
    protected boolean falling;

    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;

    protected double startX;
    protected double startY;

    public MapObject(TileMap tm) {
        tileMap = tm;
        tileSize = tm.getTileSize();
    }

    public boolean intersects(MapObject o) {
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
    }

    public Rectangle getRectangle() {
        return new Rectangle((int) (x + (width - cwidth) / 2 + xmap), (int) (y + (height - cheight) / 2 + ymap), cwidth, cheight);
    }


    public int getx() {
        return (int)x;
    }

    public int gety() {
        return (int)y;
    }

    public int getxmap() { return (int) xmap; }

    public int getymap() { return (int) ymap; }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCWidth() {
        return cwidth;
    }

    public int getCHeight() {
        return cheight;
    }

    public boolean isFacingRight() { return facingRight; }

    public Animation getAnimation() { return animation; }

    public void setPosition (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setStartPosition(double x, double y) {
        startX = x;
        startY = y;
        this.x = x;
        this.y = y;
    }

    public void setMapPosition() {
        xmap = tileMap.getx();
        ymap = tileMap.gety();
    }

    public void setLeft(boolean b) { left = b; }
    public void setRight(boolean b) { right = b; }
    public void setUp(boolean b) { up = b; }
    public void setJumping(boolean b) { jumping = b; }

}