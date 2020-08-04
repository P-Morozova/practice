package view;

import gameState.GameState;
import gameState.GameStateManager;
import model.LevelStateModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import static gameState.GameStateManager.*;
import static model.PlayerModel.levelStart;

public class LevelStateView  extends GameState {
    LevelStateModel lsm;
    BufferedImage died;
    Background bg;

   public LevelStateView(GameStateManager gsm) {
        this.gsm = gsm;
       try {
           lsm = new LevelStateModel();
       } catch (IOException e) {
           e.printStackTrace();
       }
       try {
           died = ImageIO.read(new File("D:\\PikachuAdventures\\Resources\\died.png"));
           bg = new Background("D:\\PikachuAdventures\\Resources\\levelBG.png");
           bg.setPosition(0, 0);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }


    @Override
    public void init() throws IOException {
        lsm.init();
        if(gsm.getCurrentState() == LEVELSTATE) {
            JFileChooser jfc = new JFileChooser("D:\\PikachuAdventures\\Resources");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("", "txt");
            jfc.setFileFilter(filter);
            int k = jfc.showSaveDialog(null);
            if(k == JFileChooser.CANCEL_OPTION) {
                gsm.setState(MENUSTATE);
                lsm.getPlayer().levelStart(false);
            }
            else if (k == JFileChooser.APPROVE_OPTION) {
                String fileType = getFileExtension(jfc.getSelectedFile());
                while(!fileType.equals("txt")) {
                    JOptionPane.showMessageDialog(new JDialog(), "Incorrect file extension! Possible extension: txt.");
                    if(jfc.showSaveDialog(null) == JFileChooser.CANCEL_OPTION) {
                        gsm.setState(MENUSTATE);
                        return;
                    }
                    fileType = getFileExtension(jfc.getSelectedFile());
                }
                lsm.getTileMap().loadMap(jfc.getSelectedFile().getAbsolutePath());
                lsm.getPlayer().levelStart(true);
            }
        }
        else if(gsm.getCurrentState() == BUILTLEVEL){
            lsm.getTileMap().loadMap("D:\\PikachuAdventures\\Resources\\testLevel.txt");
            lsm.getPlayer().levelStart(true);
        }
    }

    public void update() {
       lsm.update();
       if(lsm.levelEnd()) {
           try {
               LevelStateModel.flagIsGet = false;
               gsm.setState(ENDLEVEL);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }

    public void draw(Graphics2D g) {
        g.setFont(new Font("Arial", Font.CENTER_BASELINE, 12));
        bg.draw(g);
        PlayerView pv = lsm.getPlayer();
        pv.draw(g);
        lsm.getTileMap().draw(g);
        lsm.getFlag().draw(g);

        if(pv.isDead() && levelStart) {
            g.drawImage(died, 80, 10, null);
        }
        ArrayList<PokeballView> poke = lsm.getPokeballs();
        for(int i = 0; i < poke.size(); i++) {
            poke.get(i).draw(g);
        }
        ArrayList<EnemyView> enemies = lsm.getEnemies();
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
        g.drawString(pv.getPokeCount()+"/"+lsm.getMaxPokeballQuantity(), 25, 20);
        g.drawString(pv.getHealth()+"/"+pv.getMaxHealth(), 25, 40);
        g.drawString(pv.getAtt()+"/"+lsm.getMaxAtt(), 25, 55);

    }

    @Override
    public void keyPressed(int k) throws IOException {
        lsm.keyPressed(k);
        if(k == KeyEvent.VK_ESCAPE) {
            levelStart = false;
            lsm.setFlagGet(false);
            gsm.setState(gsm.MENUSTATE);
        }
    }

    @Override
    public void keyReleased(int k) {
        lsm.keyReleased(k);
    }

    @Override
    public void keyTyped(char k) {
        lsm.keyTyped(k);
    }

    @Override
    protected void select() {}

}
