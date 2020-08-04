package gameState;
import view.LevelStateView;

import java.awt.*;
import java.io.IOException;
import java.util.*;

public class GameStateManager {
    private ArrayList<GameState> gameStates;
    private int currentState;
    public static final int MENUSTATE = 0;
    public static final int HELPSTATE = 1;
    public static final int LEVELSTATE = 2;
    public static final int BUILTLEVEL = 3;
    public static final int ENDLEVEL = 4;

    public GameStateManager() throws IOException {
        gameStates = new ArrayList<GameState>();
        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new HelpState(this));
        gameStates.add(new LevelStateView(this));
        gameStates.add(new LevelStateView(this));
        gameStates.add(new LevelCompleteState(this));

    }

    public void setState(int state) throws IOException {
        currentState = state;
        gameStates.get(currentState).init();
    }

    public int getCurrentState() {return currentState;}

    public void update() {
        gameStates.get(currentState).update();
    }

    public void draw(Graphics2D g) {
        gameStates.get(currentState).draw(g);
    }

    public void keyPressed(int k) throws IOException {
        gameStates.get(currentState).keyPressed(k);
    }

    public void keyReleased(int k) {
        gameStates.get(currentState).keyReleased(k);
    }

    public void keyTyped(char k) { gameStates.get(currentState).keyTyped(k); }

}
