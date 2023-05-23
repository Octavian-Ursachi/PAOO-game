package objects;

import main.Game;

public class Spikes extends GameObject{
    public Spikes(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(16,8);
        xDrawOffset = 0;
        yDrawOffset = (int)(16 * Game.SCALE);
        //hitbox.y += yDrawOffset;
    }

    public void update() {
        updateAnimationTick();
    }

}
