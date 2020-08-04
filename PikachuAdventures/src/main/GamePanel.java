package main;
import gameState.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final int SCALE = 2;

    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000/FPS;

    private BufferedImage img;
    private Graphics2D g;

    private GameStateManager gsm;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus(true);
    }

    public void init() throws IOException {
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) img.getGraphics();
        running = true;
        gsm = new GameStateManager();
    }

    @Override
    public void run() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long start, elapsed, wait;
        while(running) {
            start = System.currentTimeMillis();
            update();
            draw();
            drawToScreen();
            elapsed = System.currentTimeMillis() - start;
            wait = targetTime - elapsed;
            if(wait < 0) {
                wait = 5;
            }
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        gsm.keyTyped(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            gsm.keyPressed(e.getKeyCode());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e.getKeyCode());
    }

    private void update() {
        gsm.update();
    }

    private void draw() {
        gsm.draw(g);
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        if(gsm.getCurrentState() == GameStateManager.LEVELSTATE || gsm.getCurrentState() == GameStateManager.BUILTLEVEL)
            g2.drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        else
            g2.drawImage(img, 0, 0, WIDTH, HEIGHT,null);
    }

    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

}