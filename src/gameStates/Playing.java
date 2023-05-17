package gameStates;


import entities.EnemyManager;
import entities.Player;
import entities.Shuriken;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.StarMenu;
import ui.StopWatch;
import ui.TopBar;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Playing extends State implements Statemethods {

    private Player player;

    private EnemyManager enemyManager;

    private boolean releaseToJumpAgain = true;
    private LevelManager levelManager;
    private GameOverOverlay gameOverOverlay;
    private TopBar topBar;
    private StopWatch timer;

    private StarMenu starMenu;

    private int yLvlOffset;
    private int midBorder = (int) (0.5 * Game.GAME_HEIGHT); // pentru nivele care depasesc height-ul camerei
    private int lvlTilesHeight = LoadSave.GetLevelData().length;
    private int maxTilesOffset = lvlTilesHeight - Game.TILES_IN_HEIGHT;
    private int maxLvlOffsetY = maxTilesOffset * Game.TILES_SIZE;

    private boolean gameOver;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(100,200,this);
        enemyManager = new EnemyManager(this);
        gameOverOverlay = new GameOverOverlay(this);
        timer = new StopWatch(this);
        topBar = new TopBar(this,timer,player);
        starMenu = new StarMenu(this);

    }

    public Player getPlayer(){
        return player;
    }


    @Override
    public void update() {
        if(!gameOver) {
            levelManager.update();
            player.update();
            enemyManager.update();
            checkCloseToBorder();
            timer.startTimer();
            if(gameOver)
                timer.stop();
        }
    }

    private void checkCloseToBorder() {
        int playerY = (int) player.getHitbox().y;

        int diff = playerY - yLvlOffset;
        yLvlOffset += diff - midBorder;

        if(yLvlOffset > maxLvlOffsetY)
            yLvlOffset = maxLvlOffsetY;
        else if(yLvlOffset < 0)
            yLvlOffset = 0;

    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g,yLvlOffset);
        player.render(g,yLvlOffset);
        enemyManager.draw(g,yLvlOffset);
        topBar.draw(g);

        if(gameOver)
        {
            System.out.println(timer.getElapsedTime());
            if(timer.getElapsedTime() > 10) {
                gameOverOverlay.draw(g);
            } else {
                starMenu.draw(g);
            }
        }



    }

    public void resetAll() {
        gameOver = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        timer.resetAll();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }
    public void checkEnemyHitWithShuriken(Shuriken[] shurikens) {
        enemyManager.checkEnemyHitWithShuriken(shurikens);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void KeyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.keyPressed(e);
        else
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_Z:
                    player.setAttacking(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    GameStates.gameState = GameStates.MENU;
                    break;
                case KeyEvent.VK_X:
                    player.setThrowShuriken(true);
                    break;
                case KeyEvent.VK_R:
                    resetAll();
                    break;
            }
    }

    @Override
    public void KeyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                player.setLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setRight(false);
                break;
            case KeyEvent.VK_Z:
                player.setAttacking(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                player.setFirstJump(true);
                break;
        }
    }

}
