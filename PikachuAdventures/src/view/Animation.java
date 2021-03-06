package view;

import java.awt.image.BufferedImage;

public class Animation {
    private BufferedImage[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;

    public Animation() {}

    public void setFrames (BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    public void setDelay(long d) {
        delay = d;
    }

    public void setFrame(int i) {
        currentFrame = i % frames.length;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public BufferedImage getImage() {return frames[currentFrame];}

    public void update() {
        if(delay == -1) return;
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length) {
            currentFrame = 0;
        }
    }

}
