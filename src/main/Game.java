package main;

import entities.Player;
import levels.Level;
import levels.LevelManager;
import GameStates.GameStates;

import java.awt.*;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
    private LevelManager levelManager;
    private Menu menu;
    private Editor editor;

    public final static int TILE_DEFAULT_SIZE = 16;
    public final static float SCALE = 4f;
    public final static int TILES_IN_WIDTH = 20;
    public final static int TILES_IN_HEIGHT = 11;
    public final static int TILES_SIZE = (int)(TILE_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;


    public Game() {

        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void initClasses() {
        player = new Player(200,200);
        levelManager = new LevelManager(this);
        menu = new Menu();


    }

    private void startGameLoop()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
        levelManager.update();
    }

    public void render(Graphics g){
        if(GameStates.gameState == GameStates.GAME)
        {
            levelManager.draw(g);
            player.render(g);
        } else if (GameStates.gameState == GameStates.MENU)
        {
            menu.render(g);
        } else if (GameStates.gameState == GameStates.EDITOR)
        {

        }


    }


    //ep 03,06
    @Override
    public void run() {

        double timePerUpdate = 1000000000 / UPS_SET;  //cat dureaza fiecare update in nano secunde
        double timePerFrame = 1000000000  / FPS_SET;  //cat dureaza fiecare frame in nano secunde

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true){
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(GameStates.gameState == GameStates.GAME)
            {
                if(deltaU >= 1 ) {   //LOOP-UL ce se ocupa de logica jocului
                    update();
                    updates++;
                    deltaU--;
                }
            }


            if(deltaF >= 1) {   //LOOP-ul ce se ocupa de randarea imaginilor
                gamePanel.repaint();
                frames++;
                deltaF--;
            }


            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS : " + frames + "| UPS : " + updates);
                frames = 0;
                updates = 0;
            }


        }

    }

    public Player getPlayer(){
        return player;
    }

}
