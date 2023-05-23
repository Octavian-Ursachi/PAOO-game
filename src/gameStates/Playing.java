package gameStates;


import entities.EnemyManager;
import entities.Player;
import entities.Shuriken;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
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
    private ObjectManager objectManager;
    private EnemyManager enemyManager;
    private LevelManager levelManager;
    private GameOverOverlay gameOverOverlay;
    private TopBar topBar;
    private StopWatch timer;
    private boolean levelCompleted = false;
    private StarMenu starMenu;

    private int yLvlOffset;
    private int midBorder = (int) (0.5 * Game.GAME_HEIGHT); // pentru nivele care depasesc height-ul camerei
//    private int lvlTilesHeight = LoadSave.GetLevelData().length;
//    private int maxTilesOffset = lvlTilesHeight - Game.TILES_IN_HEIGHT;
    private int maxLvlOffsetY;

    private boolean gameOver;

    public Playing(Game game) {
        super(game);
        initClasses();
        
        calcLvlOffset();
        loadStartLevel();
    }


    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
    }
    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calcLvlOffset() {
        maxLvlOffsetY = levelManager.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        initPlayer(this);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);
        gameOverOverlay = new GameOverOverlay(this);
        timer = new StopWatch(this);
        topBar = new TopBar(this,timer,player);
        starMenu = new StarMenu(this);

    }

    private void initPlayer(Playing playing) {
        int[] coord = LoadSave.getPlayerCoord(levelManager);
        player = new Player(coord[0],coord[1],playing);
    }

    public Player getPlayer(){
        return player;
    }


    @Override
    public void update() {
        if(!gameOver) {
            levelManager.update();
            objectManager.update();
            player.update();
            enemyManager.update();
            checkCloseToBorder();
            timer.startTimer();
            if(gameOver) {
                timer.stop();
            }
        } else {
            player.updatePosAfterDeath();
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
        if(player.isDead)
            gameOver = true;

        levelManager.draw(g,yLvlOffset);
        player.render(g,yLvlOffset);
        enemyManager.draw(g,yLvlOffset);
        objectManager.draw(g,yLvlOffset);
        if(!gameOver)
            topBar.draw(g);

        if(gameOver)
        {
            if(timer.getElapsedTime() > 10 || player.isDead) {
                gameOverOverlay.draw(g);
            } else {
                starMenu.setTime(timer);
                starMenu.draw(g);
            }
        }



    }

    public void resetAll() {
        gameOver = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        timer.resetAll();
        timer.stop();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkSpikesTouched(Player player) {
        objectManager.checkSpikesTouched(player);
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }
    public void checkEnemyHitWithShuriken(Shuriken[] shurikens) {
        enemyManager.checkEnemyHitWithShuriken(shurikens);
    }

    public StarMenu getStarMenu() {return starMenu;}

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

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetY = lvlOffset;
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

    public EnemyManager getEnemyManager() {return enemyManager;}

    public void setLevelCompleted(boolean b) {
        this.levelCompleted = b;
    }

    public LevelManager getLevelManager() {return levelManager;}

    public ObjectManager getObjectManager() {return objectManager;}
}
