package entities;

import static utils.Constants.EnemyConstant.*;

public abstract class Enemy extends Entity{

    private int aniIndex,enemyState,enemyType;
    private int aniTick,aniSpeed = 25;

    protected boolean active = true;

    public Enemy(float x, float y, int width, int height,int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x,y,width,height);
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        if(this.enemyState == DEAD)
            active = false;
        aniTick = 0;
        aniIndex = 0;
    }

    private void updateAnimationTick(){
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(enemyType,enemyState)) {
                aniIndex = 0;
            } else if (enemyState == DEAD)
                active = false;
        }
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y-10;
        newState(IDLE);
        active = true;
    }

    public void update() {
        updateAnimationTick();
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState(){
        return enemyState;
    }

    public boolean isActive() {
        return active;
    }

}
