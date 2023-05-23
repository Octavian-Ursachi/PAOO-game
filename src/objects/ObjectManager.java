package objects;

import entities.Player;
import gameStates.Playing;
import levels.Level;
import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utils.Constants.GameObjectsConstants.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage spikesImgs;
    private ArrayList<Spikes> spikes;
    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
        spikes = new ArrayList<>();
        spikes.add(new Spikes(300,300,0));
    }

    public void checkSpikesTouched(Player player) {
        for(Spikes s : spikes) {
            if(s.getHitbox().intersects(player.getHitbox()) && player.getHitbox().x < s.getHitbox().x && !player.getInAir() ) {
                player.kill();
                player.setHitFromLeft(true);
                player.setHitFromRight(false);
                player.setHitFromAbove(false);
                System.out.println("HitFromLeft");
                return;
            } else if(s.getHitbox().intersects(player.getHitbox()) && player.getHitbox().x > s.getHitbox().x && !player.getInAir()) {
                player.kill();;
                player.setHitFromLeft(false);
                player.setHitFromRight(true);
                player.setHitFromAbove(false);
                System.out.println("HitFromRight");
                return;
            } else if(s.getHitbox().intersects(player.getHitbox()) && player.getHitbox().y < s.getHitbox().y && player.getInAir()) {
                player.kill();
                player.setHitFromLeft(false);
                player.setHitFromRight(false);
                player.setHitFromAbove(true);
                System.out.println("HitFromAbove");
                return;
            }
        }
    }

    public void loadObjects(Level newLevel) {
        spikes = newLevel.getSpikes();
    }

    private void loadImgs() {
        BufferedImage spikesSprite = LoadSave.GetSpriteAtlas("spikes.png");
        spikesImgs = spikesSprite;
    }

    public void update() {
        for(Spikes s : spikes) {
            if(s.isActive()) {
                s.update();
            }
        }
    }

    public void draw(Graphics g,int yLvlOffset) {
        drawSpikes(g,yLvlOffset);
    }

    private void drawSpikes(Graphics g, int yLvlOffset) {
        for(Spikes s : spikes) {
            if(s.isActive()) {
                g.drawImage(spikesImgs,
                        (int)(s.getHitbox().x),
                        (int)(s.getHitbox().y-s.yDrawOffset/2)-yLvlOffset,
                        Constants.GameObjectsConstants.SPIKES_WIDTH,
                        Constants.GameObjectsConstants.SPIKES_HEIGHT,
                        null);
            }
        }
    }

}
