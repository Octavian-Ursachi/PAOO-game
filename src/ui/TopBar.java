package ui;

import entities.Player;
import entities.Shuriken;
import gameStates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class TopBar {

    private Player player;
    private Playing playing;
    private StopWatch timer;
    private BufferedImage[] shurikenSlots;

    public TopBar(Playing playing,StopWatch timer, Player player) {
        this.player = player;
        this.playing = playing;
        this.timer = timer;
        shurikenSlots = new BufferedImage[2];
        loadSlots();
    }

    public void draw(Graphics g) {

        //TOPBAR
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 20, Game.GAME_WIDTH, Game.GAME_HEIGHT / 10);

        //TIMER
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("TIME", Game.GAME_WIDTH / 2, Game.GAME_HEIGHT / 20);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        if(timer.getElapsedTime() < 10)
            g.drawString(String.format("%.2f", timer.getElapsedTime()), Game.GAME_WIDTH / 2 - 25, Game.GAME_HEIGHT / 10);
        else  {
            g.setColor(Color.RED);
            g.drawString(String.format("%.2f", timer.getElapsedTime()), Game.GAME_WIDTH / 2 - 25, Game.GAME_HEIGHT / 10);
        }
        //Shurikens
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("SHURIKENS",95,40);
        for(int i = 0 ; i < 3 ; i++) {
            if(!player.getShurikens()[i].used)
                g.drawImage(shurikenSlots[1],80 + Game.TILES_IN_WIDTH * i,60,Game.TILES_SIZE/2,Game.TILES_SIZE/2,null);
            else
                g.drawImage(shurikenSlots[0],80 + Game.TILES_IN_WIDTH * i,60,Game.TILES_SIZE/2,Game.TILES_SIZE/2,null);

        }
        //BEST
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("BEST TIME",Game.GAME_WIDTH-150,40);

    }

    public void loadSlots() {
        shurikenSlots[0] = LoadSave.GetSpriteAtlas(LoadSave.SHURIKEN_EMPTY_SLOT);
        shurikenSlots[1] = LoadSave.GetSpriteAtlas(LoadSave.SHURIKEN_FULL_SLOT);
    }

}
