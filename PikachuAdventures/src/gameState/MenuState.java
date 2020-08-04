package gameState;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static gameState.GameStateManager.*;

public class MenuState extends GameState {

    private int currentChoice = 0;
    private String[] options = {"Start", "Choose Level", "Help", "Quit"};
    private Color titleColor;
    private Font font;
    private BufferedImage second;
    private BufferedImage title;

    public MenuState(GameStateManager gsm) throws IOException {
        this.gsm = gsm;
        bg = new Background("D:\\PikachuAdventures\\Resources\\MENU1.png");
        second = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\MENU2.gif"));
        title = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\img_fonts.png"));
        bg.setPosition(0, 0);
        font = new Font ("Arial", Font.CENTER_BASELINE, 30);
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
        g.setColor(titleColor);
        g.setFont(font);
        g.drawImage(second, 400, 188, null);
        g.drawImage(title, 40, 60, (int) (title.getWidth() * 1.5), (int) (title.getHeight() * 1.5),null);
        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g.setColor(Color.GREEN);
            }
            else {
                g.setColor(Color.BLACK);
            }
            g.drawString(options[i], 90, 240 + i * 50);
        }
    }

    @Override
    public void init() {}

    @Override
    public void update() {}

    protected void select() throws IOException {
        if(currentChoice == 0) {
            gsm.setState(BUILTLEVEL);
        }
        if(currentChoice == 1) {
            gsm.setState(LEVELSTATE);
        }
        if(currentChoice == 2) {
            gsm.setState(HELPSTATE);
        }
        if(currentChoice == 3) {
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int k) throws IOException {
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