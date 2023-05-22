package ui;

import gameStates.GameStates;
import gameStates.GameStates.*;
import entities.Player;
import gameStates.Playing;
import main.Game;
import utils.Constants;
import utils.LoadSave;
import utils.Constants.Level1;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class StarMenu {

    Playing playing;
    private final float x,y,width,height;
    private BufferedImage[] star;
    private float bestTime=0,currentTime=0,timeForNextStar=0;

    public StarMenu(Playing playing) {
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
        if(bestTime < Level1.THIRD_STAR)
        for(int i = 0 ; i < 3 ; i++) {
                g.drawImage(star[0],(int)(x+x*0.23)+i*spacer,(int)(y+150),100,100,null);
        }
        else if(bestTime < Level1.SECOND_STAR)
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

        if(currentTime < Level1.THIRD_STAR)
            for(int i = 0 ; i < 3 ; i++) {
                g.drawImage(star[0],(int)(x+x*0.23)+i*spacer,(int)(y+400),100,100,null);
            }
        else if(currentTime < Level1.SECOND_STAR)
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
        if(currentTime> Level1.THIRD_STAR) {
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(formattedTimeForNextStar + " TO NEXT STAR", (int) (x), (int) y + 550);
        }
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("RETRY", (int)(x+x*0.44), (int)y+600);
        g.drawString("LEVEL SELECT", (int)(x+x*0.36), (int)y+650);
        g.drawString("NEXT LEVEL", (int)(x+x*0.39), (int)y+700);

    }
    public void setTime(StopWatch timer) {

        this.currentTime = timer.getElapsedTime();
        if(this.currentTime < this.bestTime)
            this.bestTime = currentTime;
        if(currentTime > Level1.SECOND_STAR) {
            this.timeForNextStar = currentTime - Level1.SECOND_STAR;
            if( currentTime > Level1.THIRD_STAR && currentTime < Level1.SECOND_STAR) {
                this.timeForNextStar = currentTime - Level1.THIRD_STAR;
            }
        }

    }

    public void KeyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_R :
                playing.resetAll(); //retry
                break;
            case KeyEvent.VK_ESCAPE :
                GameStates.gameState = GameStates.MENU; //menu
                break;
            case KeyEvent.VK_C :
                playing.loadNextLevel();
                playing.getLevelManager().setLevelChanged(true);
                break;
        }

    }

}
