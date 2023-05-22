package inputs;

import entities.Player;
import gameStates.GameStates;
import main.Game;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utils.Constants.Directions.*;

public class Keyboardinputs implements KeyListener {

    private GamePanel gamePanel;
    public Keyboardinputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameStates.gameState)
        {
            case MENU:
                gamePanel.getGame().getMenu().KeyPressed(e);
                break;

            case PLAYING:
                gamePanel.getGame().getPlaying().KeyPressed(e);
                gamePanel.getGame().getPlaying().getStarMenu().KeyPressed(e);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameStates.gameState)
        {
            case MENU:
                gamePanel.getGame().getMenu().KeyReleased(e);
                break;

            case PLAYING:
                gamePanel.getGame().getPlaying().KeyReleased(e);
                break;
        }
    }
}
