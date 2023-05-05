package gameStates;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.*;

public class Menu extends State implements Statemethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundIMG,backgroundIMGgreen;
    private int menuX,menuY,menuWidth,menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }

    private void loadBackground() {
        backgroundIMG = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        backgroundIMGgreen = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_GREEN);
        menuWidth = (int)(backgroundIMG.getWidth()/ 1.5 * SCALE);
        menuHeight = (int)(backgroundIMG.getHeight() / 1.5* SCALE);
        menuX = GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int)(10 * SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(GAME_WIDTH / 2,(int)(80* Game.SCALE),0,GameStates.PLAYING);
        buttons[1] = new MenuButton(GAME_WIDTH / 2,(int)(120* Game.SCALE),1,GameStates.OPTIONS);
        buttons[2] = new MenuButton(GAME_WIDTH / 2,(int)(160* Game.SCALE),2,GameStates.QUIT);

    }


//    public void render(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        Font fnt0 = new Font("arial",Font.BOLD,50);
//        g.setFont(fnt0);
//        g.setColor(Color.black);
//        g.drawString("NINJA FROGGY",GAME_WIDTH/3,GAME_HEIGHT/6);
//
//
//        Font fnt1 = new Font("arial", Font.BOLD,30);
//        g.setFont(fnt1);
//        g.drawString("Play", playButton.x+19,playButton.y+30);
//        g2d.draw(playButton);
//        g.drawString("Edit", editorButton.x+19,editorButton.y+30);
//        g2d.draw(editorButton);
//        g.drawString("Quit", quitButton.x+19,quitButton.y+30);
//        g2d.draw(quitButton);
//        g2d.draw(editorButton);
//
//
//    }

    @Override
    public void update() {
        for(MenuButton mb : buttons)
            mb.update();


    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundIMGgreen,0,0,GAME_WIDTH,GAME_HEIGHT,null);

        g.drawImage(backgroundIMG,menuX,menuY,menuWidth,menuHeight,null);

        for(MenuButton mb : buttons)
            mb.draw(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb : buttons){
            if(isIn(e,mb)) {
                mb.setMousePressed(true);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton mb : buttons){
            if(isIn(e,mb)) {
                if(mb.isMousePressed()){
                    mb.applyGameState();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for(MenuButton mb : buttons)
            mb.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb : buttons)
            mb.setMouseOver(false);

        for(MenuButton mb : buttons)
            if(isIn(e,mb)){
                mb.setMouseOver(true);
                break;
            }
    }

    @Override
    public void KeyPressed(KeyEvent e) {

    }

    @Override
    public void KeyReleased(KeyEvent e) {

    }
}
