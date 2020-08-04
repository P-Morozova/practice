package model;
import view.TileMap;

public class EnemyModel extends MapObject {

    public static final int WALK_TYPE = 11;
    public static final int JUMP_TYPE = 12;
    private int type;
    private long startJumping;
    private long endJumping;
    protected boolean dead;
    private boolean jumpAllow;

    public EnemyModel(TileMap tm, int type) {
        super(tm);
        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;
        moveSpeed = 0.5;
        fallSpeed = 2;
        right = true;
        this.type = type;
    }

    public double getXtemp() {return xtemp;}
    public double getYtemp() {return ytemp;}

    public void setFacingRight() {
        facingRight = true;
    }

    public boolean jumping() { return jumping; }

    public double getMoveSpeed() {
        return moveSpeed;
    }

    public void update() {

        if(type == WALK_TYPE) {
            getNextPosition();
            checkTileMapCollision();
            setPosition(xtemp, ytemp);
        }

        else if(type == JUMP_TYPE) {
            checkTileMapCollision();
            setPosition(x, ytemp);
            if(jumpAllow) {
                long elapsed = System.currentTimeMillis();
                if (!jumping) {
                    if (elapsed - endJumping > 500) {
                        startJumping = System.currentTimeMillis();
                        setPosition(x, y - 30);
                        jumping = true;
                    }
                } else {
                    endJumping = System.currentTimeMillis();
                    if (elapsed - startJumping > 500) {
                        setPosition(x, y + 30);
                        jumping = false;
                    }
                }

            }
        }

    }

    private void getNextPosition() {
        if(left) {
            dx = -moveSpeed;
        }
        else if(right) {
            dx = moveSpeed;
        }
    }

    private void checkTileMapCollision() {
        currCol = (int) x / tileSize;
        xdest = x + dx;
        xtemp = x;
        ytemp = y;

        if(y + height == tileSize * tileMap.getNumRows() && falling) {
            dead = true;
            return;
        }

        else if (y < tileSize * (tileMap.getNumRows() - 1)) {
            int type = tileMap.getType((int) (y + height) / tileSize, (int) x / tileSize);
            if (type == 0 && !jumpAllow) {
                ytemp += tileSize;
                falling = true;
            } else {
                falling = false;
            }
        }

        if(this.type == WALK_TYPE) {
            if (!falling) {
                if (dx < 0) {
                    int sideBottomType = tileMap.getType((int) (y + height) / tileSize, (int) (xdest) / tileSize);
                    int sideType = tileMap.getType((int) y / tileSize,(int) (xdest) / tileSize);
                    if (sideType == 1 || sideBottomType == 0) {
                        right = true;
                        left = false;
                        facingRight = false;
                        dx = -dx;
                        return;
                    } else {
                        right = false;
                        left = true;
                        facingRight = true;
                        xtemp += dx;
                    }
                }
                if (dx > 0) {
                    int sideBottomType = tileMap.getType((int) (y + height) / tileSize, (int) (xdest + cwidth) / tileSize);
                    int sideType = tileMap.getType((int) y / tileSize,(int) (xdest + width) / tileSize);
                    if (sideType == 1 || (sideBottomType == 0)) {
                        left = true;
                        right = false;
                        facingRight = true;
                        dx = -dx;
                    }
                    else {
                        left = false;
                        right = true;
                        facingRight = false;
                        xtemp += dx;
                    }
                }

            }
        }

        else if(this.type == JUMP_TYPE) {
            if(y != 0 && !falling) {
                int j = tileMap.getType((int) (ytemp - height) / tileSize, (int) x / tileSize);
                if (j == 0) jumpAllow = true;
            }
        }


    }

    public void dead() {
        dead = true;
    }

    public boolean isDead() { return dead; }

    public int getType() { return type; }

    public void reset() {
        dx = 0;
        jumping = false;
        xtemp = 0;
        ytemp = 0;
        xdest = 0;
        ydest = 0;
        xmap = 0;
        ymap = 0;
        endJumping = 0;
        setPosition(startX, startY);
        dead = false;
        jumpAllow = false;
    }

}
