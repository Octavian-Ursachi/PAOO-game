package inputs;

import GameStates.GameStates;
import main.Game;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouseinputs implements MouseListener , MouseMotionListener {

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();


        /*
        public Rectangle playButton = new Rectangle(GAME_WIDTH/2 - 80,150,100,50);
        public Rectangle editorButton = new Rectangle(GAME_WIDTH/2 - 80,250,100,50);
        public Rectangle quitButton = new Rectangle(GAME_WIDTH/2 - 80,350,100,50);
         */
        //PLAY
        if(GameStates.gameState == GameStates.MENU)
        {
            if(mx >= Game.GAME_WIDTH/2 - 80 && mx <= Game.GAME_WIDTH/2 + 20)
            {
                if(my >= 150 && my <= 200 )
                {
                    GameStates.gameState = GameStates.GAME;
                }
            }
            //EDITOR
            if(mx >= Game.GAME_WIDTH/2 - 80 && mx <= Game.GAME_WIDTH/2 + 20)
            {
                if(my >= 250 && my <= 300 )
                {
                    GameStates.gameState = GameStates.EDITOR;
                }
            }
            //QUIT
            if(mx >= Game.GAME_WIDTH/2 - 80 && mx <= Game.GAME_WIDTH/2 + 20)
            {
                if(my >= 350 && my <= 400 )
                {
                    System.exit(1);
                }
            }
        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
