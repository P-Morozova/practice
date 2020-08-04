package main;
import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new GamePanel());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
