package gameStates;

import main.Game;
import ui.MenuButton;
import ui.MenuButtonBuilder;
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
        MenuButtonBuilder builder = new MenuButtonBuilder();

        builder.xPos(GAME_WIDTH / 2)
                .yPos((int) (80 * Game.SCALE))
                .rowIndex(0)
                .state(GameStates.PLAYING);
        buttons[0] = builder.build();

        builder.xPos(GAME_WIDTH / 2)
                .yPos((int) (120 * Game.SCALE))
                .rowIndex(1)
                .state(GameStates.OPTIONS);
        buttons[1] = builder.build();

        builder.xPos(GAME_WIDTH / 2)
                .yPos((int) (160 * Game.SCALE))
                .rowIndex(2)
                .state(GameStates.QUIT);
        buttons[2] = builder.build();
    }

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
