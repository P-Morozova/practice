package model;

import view.*;
import java.awt.*;

import static model.LevelStateModel.*;

public class PlayerModel extends MapObject {
    private int health;
    private int maxHealth;
    private int att;
    public static boolean levelStart;
    private boolean changeAnimations;
    private boolean dead;
    private boolean flinching;
    private long flinchTimeStart;

    private boolean firing;
    protected AttackView lightning;

    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 2;
    private static final int FIREBALL = 3;
    private static final int DIED = 4;

    private long delay;
    public int pokeCount;

    public PlayerModel(TileMap tm) {
        super(tm);
        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;
        pokeCount = 0;
        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.10;
        maxFallSpeed = 4.0;
        jumpStart = -3.8;
        stopJumpSpeed = 0.1;
        facingRight = true;
        health = maxHealth = 3;
        att = 50;
        lightning = new AttackView(tm);
        dead = false;
        currentAction = IDLE;
        delay = 400;
        lightning.setPosition(x + width, y, false);
    }

    public int getCurrentAction() { return currentAction; }

    public AttackView getLightning() { return lightning; }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setFiring(boolean b) {
        firing = b;
    }

    public void shoot() {
        firing = true;
    }

    public Rectangle getRectangle() {
        return new Rectangle((int) (x - cwidth / 2 + xmap), (int) (y - cheight / 2 + ymap), cwidth, cheight);
    }

    public boolean isChangeAnimations() { return changeAnimations; }

    public void update() {

        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if (flinching) {
            long elapsed = (System.currentTimeMillis() - flinchTimeStart);
            if (elapsed > 2000) {
                flinching = false;
            }
        }

        checkGetPokeball();

        checkEnemyDie();
        checkIntersectEnemy();

        if(health == 0) {
            dead = true;
        }

        if(dead) {
            if (currentAction != DIED) {
                currentAction = DIED;
                changeAnimations = true;
                return;
            }
        }

        if(!firing) {
            lightning.isDraw(false);
        }

        if(firing && att > 0) {
            changeAnimations = false;
            if(currentAction != FIREBALL) {
                currentAction = FIREBALL;
                changeAnimations = true;
                delay = 10;
                lightning.isDraw(true);
                att -= 10;
                cheight = 28;
            }
        }

        else if(dy > 0) {
            changeAnimations = false;
            if (currentAction != FALLING) {
                currentAction = FALLING;
                delay = 100;
                changeAnimations = true;
            }
            cheight = 20;
        }

        else if(dy < 0) {
            changeAnimations = false;
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                delay = -1;
                changeAnimations = true;
            }
            cheight = 20;
        }

        else if(left || right) {
            changeAnimations = false;
            if (currentAction != WALKING) {
                currentAction = WALKING;
                delay = 40;
                changeAnimations = true;
            }
            cheight = 20;
        }

        else {
            changeAnimations = false;
            if (currentAction != IDLE) {
                currentAction = IDLE;
                delay = 400;
                changeAnimations = true;
            }
            cheight = 20;

        }
        if(right) facingRight = true;
        if(left) facingRight = false;
        lightning.setPosition(x + cwidth, y, facingRight);
        lightning.update();
    }

    public void checkEnemyDie() {
        for(int i = 0; i < getEnemiesLen(); i++) {
            if(lightning.intersects(getEnemy(i).getEm()) && !getEnemy(i).isDead() && lightning.getDraw())  {
                getEnemy(i).dead();
            }
        }

    }

    public void checkGetPokeball() {
        for(int i = 0; i < getPokeballsLen(); i++) {
            if(intersects(getPokeball(i).getPm()) && !getPokeball(i).isGet())  {
                getPokeball(i).setGet();
                if(pokeCount < 50) {
                    pokeCount++;
                }

                if(pokeCount % 10 == 0) {
                    if(health != maxHealth) {
                        health += 1;
                    }
                }

                if(pokeCount % 5 == 0) {
                    if(att != 50) {
                        att += 10;
                    }
                }
            }
        }

    }

    public void checkIntersectEnemy() {
        for(int i = 0; i < getEnemiesLen(); i++) {
            if(!dead) {
                if (intersects(getEnemy(i).getEm()) && !getEnemy(i).isDead() && !flinching) {
                    health -= 1;
                    flinching = true;
                    flinchTimeStart = System.currentTimeMillis();
                }
            }
        }
    }


    public long getDelay() { return delay; }

    public int getAtt() {
        return att;
    }

    private void getNextPosition() {
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
        else {
            if (dx > 0) {
                dx -= stopSpeed;
                if(dx < 0) {
                    dx = 0;
                }
            }
            else if (dx < 0) {
                dx += stopSpeed;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }

        if((currentAction == FIREBALL) && !(jumping || falling)) {
            dx = 0;
        }

        if(jumping && !falling) {
            dy = jumpStart;
            falling = true;
        }

        if(falling) {
            dy += fallSpeed;
            if (dy > 0) jumping = false;
            if (dy < 0 && !jumping) dy += stopJumpSpeed;
            if(dy > maxFallSpeed) dy = maxFallSpeed;
        }
    }

    private void calculateCorners(double x, double y) {
        int leftTile = (int)(x - cwidth / 2) / tileSize;
        int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
        int topTile = (int)(y - cheight / 2) / tileSize;
        int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;

        if(topTile < 0 || bottomTile >= tileMap.getNumRows() || leftTile < 0 || rightTile >= tileMap.getNumCols() && levelStart) {
            topLeft = topRight = bottomLeft = bottomRight = false;
            if(y > 0)
                dead = true;
            return;
        }

        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);

        topLeft = tl == TileMap.Tile.BLOCKED;
        topRight = tr == TileMap.Tile.BLOCKED;
        bottomLeft = bl == TileMap.Tile.BLOCKED;
        bottomRight = br == TileMap.Tile.BLOCKED;
    }

    private void checkTileMapCollision() {
        currCol = (int)x / tileSize;
        currRow = (int)y / tileSize;
        xdest = x + dx;
        ydest = y + dy;
        xtemp = x;
        ytemp = y;

        calculateCorners(x, ydest);
        if(dy < 0) {
            if(topLeft || topRight) {
                dy = 0;
                ytemp = currRow * tileSize + cheight / 2;
            }
            else {
                ytemp += dy;
            }
        }
        if(dy > 0) {
            if (bottomLeft || bottomRight) {
                dy = 0;
                falling = false;
                ytemp = (currRow + 1) * tileSize - cheight / 2;
            }
            else {
                ytemp += dy;
            }
        }

        calculateCorners(xdest, y);
        if(dx < 0) {
            if(topLeft || bottomLeft) {
                dx = 0;
                xtemp = currCol * tileSize + cwidth / 2;
            }
            else {
                xtemp += dx;
            }
        }
        if(dx > 0) {
            if (topRight || bottomRight) {
                dx = 0;
                xtemp = (currCol + 1) * tileSize - cwidth / 2;
            }
            else {
                xtemp += dx;
            }
        }

        if(!falling) {
            calculateCorners(x,ydest + 1);
            if(!bottomRight && !bottomLeft) {
                falling = true;
            }
        }

    }


    public void reset() {
        xmap = 0;
        ymap = 0;
        xtemp = x;
        ytemp = y;
        xdest = x;
        ydest = y;
        flinching = false;
        att = 50;
        dx = 0;
        dy = 0;
        pokeCount = 0;
        dead = false;
        health = 3;
        flinchTimeStart = 0;
        setPosition(startX, startY);
        tileMap.setPosition(0, 0);
        setMapPosition();
    }

    public boolean isDead() {
        return dead;
    }
    public boolean isFlinching() { return flinching; }
    public long getFlinchTimeStart() { return flinchTimeStart; }

    public int getPokeCount() {
        return pokeCount;
    }
}
