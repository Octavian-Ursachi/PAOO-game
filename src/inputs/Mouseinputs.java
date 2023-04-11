package inputs;

import gameStates.GameStates;
import main.Game;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouseinputs implements MouseListener , MouseMotionListener {

    private GamePanel gamePanel;

    public Mouseinputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

        switch (GameStates.gameState)
        {
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

        switch (GameStates.gameState)
        {
            case MENU :
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mousePressed(e);
                break;
        }

        /*
        public Rectangle playButton = new Rectangle(GAME_WIDTH/2 - 80,150,100,50);
        public Rectangle editorButton = new Rectangle(GAME_WIDTH/2 - 80,250,100,50);
        public Rectangle quitButton = new Rectangle(GAME_WIDTH/2 - 80,350,100,50);
         */
        //PLAY
//        if(GameStates.gameState == GameStates.MENU)
//        {
//            if(mx >= Game.GAME_WIDTH/2 - 80 && mx <= Game.GAME_WIDTH/2 + 20)
//            {
//                if(my >= 150 && my <= 200 )
//                {
//                    GameStates.gameState = GameStates.PLAYING;
//                }
//            }
//            //EDITOR
//            if(mx >= Game.GAME_WIDTH/2 - 80 && mx <= Game.GAME_WIDTH/2 + 20)
//            {
//                if(my >= 250 && my <= 300 )
//                {
//                    GameStates.gameState = GameStates.EDITOR;
//                }
//            }
//            //QUIT
//            if(mx >= Game.GAME_WIDTH/2 - 80 && mx <= Game.GAME_WIDTH/2 + 20)
//            {
//                if(my >= 350 && my <= 400 )
//                {
//                    System.exit(1);
//                }
//            }
//        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        switch (GameStates.gameState)
        {
            case MENU :
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseReleased(e);
                break;
        }
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

        switch (GameStates.gameState)
        {
            case MENU :
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
        }

    }
}
