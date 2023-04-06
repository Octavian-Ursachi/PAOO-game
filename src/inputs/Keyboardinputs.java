package inputs;

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
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                gamePanel.getGame().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                gamePanel.getGame().getPlayer().setRight(true);
                break;
            case KeyEvent.VK_UP:
                gamePanel.getGame().getPlayer().setUp(true);
                break;
            case KeyEvent.VK_DOWN:
                gamePanel.getGame().getPlayer().setDown(true);
                break;
            case KeyEvent.VK_Z:
                gamePanel.getGame().getPlayer().setAttacking(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                gamePanel.getGame().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                gamePanel.getGame().getPlayer().setRight(false);
                break;
            case KeyEvent.VK_UP:
                gamePanel.getGame().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_DOWN:
                gamePanel.getGame().getPlayer().setDown(false);
                break;
        }
    }
}
