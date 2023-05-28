package ui;

import gameStates.GameStates;
import gameStates.GameStates.*;
import entities.Player;
import gameStates.Playing;
import levels.LevelManager;
import main.Game;
import utils.Constants;
import utils.LoadSave;
import utils.Constants.Level1;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.nio.BufferOverflowException;
import java.text.DecimalFormat;

public class StarMenu {

    LevelManager levelManager;
    Playing playing;
    private final float x,y,width,height;
    private BufferedImage[] star,buttons;
    private float bestTime=0,currentTime=0,timeForNextStar=0;
    private boolean starMenuActive = false;

    public StarMenu(Playing playing, LevelManager levelManager) {
        this.levelManager = levelManager;
        this.playing = playing;
        this.x = (float)(Game.GAME_WIDTH) / 3;
        this.y = 0;
        this.width = (float)Game.GAME_WIDTH / 3;
        this.height = Game.GAME_HEIGHT;
        loadImages();
    }

    public void loadImages() {
        star = new BufferedImage[2];
        star[0] = LoadSave.GetSpriteAtlas("Star.png");
        star[1] = LoadSave.GetSpriteAtlas("Star_frame.png");
        buttons = new BufferedImage[3];
        buttons[0] = LoadSave.GetSpriteAtlas("Esc-Key.png").getSubimage(0,0,32,32);
        buttons[1] = LoadSave.GetSpriteAtlas("C-Key.png").getSubimage(0,0,32,32);
        buttons[2] = LoadSave.GetSpriteAtlas("R-Key.png").getSubimage(0,0,32,32);
    }
    public void draw(Graphics g) {

        String formattedCurrentTime = String.format("%.02f", currentTime);
        String formattedBestTime = String.format("%.02f", bestTime);
        String formattedTimeForNextStar = String.format("%.02f", timeForNextStar);

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect((int)x, (int)y, (int)width, (int)height);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("BEST:", (int)(x+x*0.44), (int)y+100);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString(formattedBestTime, (int)(x+x*0.37), (int)y+150);
        //STARS
        int spacer = 80;
        if(bestTime < levelManager.getLevels().get(levelManager.getLvlIndex()).thirdStar)
        for(int i = 0 ; i < 3 ; i++) {
                g.drawImage(star[0],(int)(x+x*0.23)+i*spacer,(int)(y+150),100,100,null);
        }
        else if(bestTime < levelManager.getLevels().get(levelManager.getLvlIndex()).secondStar)
            for(int i = 0 ; i < 3 ; i++) {
                if(i<=1)
                    g.drawImage(star[0],(int)(x+x*0.23)+i*spacer,(int)(y+150),100,100,null);
                else
                    g.drawImage(star[1],(int)(x+x*0.23)+i*spacer,(int)(y+150),100,100,null);
            }
        else if(bestTime < 10)
            for(int i = 0 ; i < 3 ; i++) {
                if(i<1)
                    g.drawImage(star[0],(int)(x+x*0.23)+i*spacer,(int)(y+150),100,100,null);
                else
                    g.drawImage(star[1],(int)(x+x*0.23)+i*spacer,(int)(y+150),100,100,null);
            }
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("YOU DID IT IN:", (int)(x+x*0.13), (int)y+300);
        g.setFont(new Font("Arial", Font.BOLD, 120));
        g.drawString(formattedCurrentTime, (int)(x+x*0.25), (int)y+400);

        if(currentTime < levelManager.getLevels().get(levelManager.getLvlIndex()).thirdStar)
            for(int i = 0 ; i < 3 ; i++) {
                g.drawImage(star[0],(int)(x+x*0.23)+i*spacer,(int)(y+400),100,100,null);
            }
        else if(currentTime < levelManager.getLevels().get(levelManager.getLvlIndex()).secondStar)
            for(int i = 0 ; i < 3 ; i++) {
                if(i<=1)
                    g.drawImage(star[0],(int)(x+x*0.23)+i*spacer,(int)(y+400),100,100,null);
                else
                    g.drawImage(star[1],(int)(x+x*0.23)+i*spacer,(int)(y+400),100,100,null);
            }
        else if(currentTime < 10)
            for(int i = 0 ; i < 3 ; i++) {
                if(i<1)
                    g.drawImage(star[0],(int)(x+x*0.23)+i*spacer,(int)(y+400),100,100,null);
                else
                    g.drawImage(star[1],(int)(x+x*0.23)+i*spacer,(int)(y+400),100,100,null);
            }
        if(currentTime> levelManager.getLevels().get(levelManager.getLvlIndex()).thirdStar) {
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(formattedTimeForNextStar + " TO NEXT STAR", (int) (x), (int) y + 550);
        }
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawImage(buttons[2],(int)(x+x*0.25), (int)y+570,50,50,null);
        g.drawString("RETRY", (int)(x+x*0.44), (int)y+600);
        g.drawImage(buttons[0],(int)(x+x*0.25), (int)y+620,50,50,null);
        g.drawString("MENU", (int)(x+x*0.45), (int)y+650);
        g.drawImage(buttons[1],(int)(x+x*0.25), (int)y+670,50,50,null);
        g.drawString("NEXT LEVEL", (int)(x+x*0.39), (int)y+700);

    }
    public void setTime(StopWatch timer) {
        starMenuActive = true;

        this.currentTime = timer.getElapsedTime();
        bestTime = levelManager.getLevels().get(levelManager.getLvlIndex()).bestTime;
        if(this.currentTime < levelManager.getLevels().get(levelManager.getLvlIndex()).bestTime) {
            levelManager.getLevels().get(levelManager.getLvlIndex()).bestTime = currentTime;
            levelManager.getDataBaseManager().writeBestTime(currentTime,levelManager.getLvlIndex());

        }
        if(currentTime > levelManager.getLevels().get(levelManager.getLvlIndex()).secondStar) {
            this.timeForNextStar = currentTime - levelManager.getLevels().get(levelManager.getLvlIndex()).secondStar;
        }
        if( currentTime > levelManager.getLevels().get(levelManager.getLvlIndex()).thirdStar && currentTime < levelManager.getLevels().get(levelManager.getLvlIndex()).secondStar) {
            this.timeForNextStar = currentTime - levelManager.getLevels().get(levelManager.getLvlIndex()).thirdStar;
        }

    }

    public void KeyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_R :
                playing.resetAll(); //retry
                starMenuActive = false;
                break;
            case KeyEvent.VK_ESCAPE :
                GameStates.gameState = GameStates.MENU; //menu
                starMenuActive = false;
                break;
            case KeyEvent.VK_C :
                if(starMenuActive) {
                    playing.loadNextLevel();
                    playing.getLevelManager().setLevelChanged(true);
                    starMenuActive = false;
                }
                break;
            case KeyEvent.VK_N:
                playing.loadNextLevel();
                playing.getLevelManager().setLevelChanged(true);
                break;
        }

    }

    public float getBestTime(){return  bestTime;}

    public void setBestTime(float bestTime) {
        this.bestTime = bestTime;
    }

}
