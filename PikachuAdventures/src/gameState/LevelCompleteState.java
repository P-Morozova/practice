package gameState;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import model.LevelStateModel;
import model.LevelStateModel.*;

import static gameState.GameStateManager.*;

public class LevelCompleteState extends GameState {

    private BufferedImage second;
    private int currentChoice = 0;
    private String[] options = {"Play built level", "Exit to Menu"};
    private Font font;
    private BufferedImage title;

    public LevelCompleteState(GameStateManager gsm) {
        this.gsm = gsm;
        LevelStateModel.flagIsGet = false;
        try {
            bg = new Background("D:\\PikachuAdventures\\Resources\\MENU1.png");
            second = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\complete1.png"));
            title = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\img_font.png")) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        font = new Font ("Arial", Font.CENTER_BASELINE, 30);
    }

    @Override
    public void init() {}

    protected void select() throws IOException {
        if(currentChoice == 0) {
            gsm.setState(BUILTLEVEL);
        }
        if(currentChoice == 1) {
            gsm.setState(MENUSTATE);
        }

    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
        g.setFont(font);
        g.drawImage(second, 20, 150, null);
        g.drawImage(title, 40, 60, (int) (title.getWidth() * 1.5), (int) (title.getHeight() * 1.5),null);
        bg.setPosition(0, 0);
        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g.setColor(Color.GREEN);
            }
            else {
                g.setColor(Color.BLACK);
            }
            g.drawString(options[i], 340, 250 + i * 50);
        }
    }

    @Override
    public void keyPressed(int k) {
        changeGS(k);
    }

    @Override
    public void keyReleased(int k) {}

    @Override
    public void keyTyped(char k) {}

    private void changeGS(int k) {
        if (k == KeyEvent.VK_ENTER || k == KeyEvent.VK_SPACE) {
            try {
                select();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (k == KeyEvent.VK_W || k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

}