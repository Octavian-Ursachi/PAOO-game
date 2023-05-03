package gameStates;


import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
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

        if(gameOver)
            gameOverOverlay.draw(g);

    }

    public void resetAll() {
        gameOver = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
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
                    if(releaseToJumpAgain) {
                        player.setJump(true);
                    }
                    if(!releaseToJumpAgain)
                    {
                        player.setJump(false);
                    }
                    releaseToJumpAgain = false;
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    GameStates.gameState = GameStates.MENU;
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
                releaseToJumpAgain = true;
                player.setJump(false);
                player.incJCounter();
                break;
        }
    }
}
