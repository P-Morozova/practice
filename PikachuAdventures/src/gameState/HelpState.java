package gameState;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class HelpState extends GameState {

    public HelpState(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            bg = new Background("D:\\PikachuAdventures\\Resources\\Help.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws IOException {}

    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
    }

    @Override
    public void keyPressed(int k) throws IOException {
        if(k == KeyEvent.VK_ESCAPE) {
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

    @Override
    public void keyReleased(int k) {}

    @Override
    public void keyTyped(char k) {}

    @Override
    public void select() {}

}
