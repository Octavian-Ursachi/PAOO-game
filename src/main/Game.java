package main;

import gameStates.Menu;
import gameStates.GameStates;
import gameStates.Playing;
import dataBases.DataBaseManager;
import utils.LoadSave;

import java.awt.*;

public class Game implements Runnable {

    private DataBaseManager dataBaseManager;
    private static volatile Game instance; // pentru Sablonul de proiectare
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;

    public final static int TILE_DEFAULT_SIZE = 16;
    public final static float SCALE = 2.5f;
    public final static int TILES_IN_WIDTH = 36;
    public final static int TILES_IN_HEIGHT = 18;
    public final static int TILES_SIZE = (int)(TILE_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;


    private Game() {

        dataBaseManager = new DataBaseManager();
        dataBaseManager.readLevels();
        initClasses();

        dataBaseManager = new DataBaseManager();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();
    }

    public static Game getInstance() { //Sablon de proiectare de tip singleton
        Game result = instance;
        if(result == null) {
            synchronized (Game.class) {
                result = instance;
                if(result == null) {
                    instance = result = new Game();
                }
            }
        }
        return result;
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void startGameLoop()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (GameStates.gameState)
        {
            case PLAYING:
                playing.update();
                break;

            case MENU:
                menu.update();
                break;

            case OPTIONS:

                break;

            case QUIT:
                System.exit(1);
                break;
        }

    }

    public void render(Graphics g) {
        switch (GameStates.gameState)
        {
            case PLAYING:
                playing.draw(g);
                break;

            case MENU:
                menu.draw(g);
                break;

            case OPTIONS:

                break;

            case QUIT:
                //
                break;
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

                if(deltaU >= 1 ) {   //LOOP-UL ce se ocupa de logica jocului
                    update();
                    updates++;
                    deltaU--;
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

    public void windowFocusLost(){
        if(GameStates.gameState == GameStates.PLAYING) {
           // playing.getPlayer().resetDirBooleans();
        }
    }

    public Menu getMenu(){
        return menu;
    }

    public Playing getPlaying(){
        return  playing;
    }

    public DataBaseManager getDataBaseManager() {
        return dataBaseManager;
    }

}
