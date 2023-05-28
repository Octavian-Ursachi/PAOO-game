package entities;

import main.Game;

import static utils.Constants.EnemyConstant.*;

public abstract class Enemy extends Entity{

    private int aniIndex,enemyState,enemyType,deathAnimType;
    private int aniTick,aniSpeed = 25;
    protected boolean oneTimeDeadAnim = false;

    protected float airSpeed = 0.f;
    protected double jumpSpeed = -1* Game.SCALE;
    protected float gravity = 0.04f;
    protected boolean airSpeedChanged = false;
    protected boolean hitFromLeft,hitFromRight;

    protected int randomValue;

    protected boolean active = true;

    public Enemy(float x, float y, int width, int height,int enemyType,int deathAnimType) throws EntityCoordsLessThanZeroException {
        super(x, y, width, height);
        this.enemyType = enemyType;
        this.deathAnimType = deathAnimType;
        initHitbox(x,y,width,height);
    }

    public void updatePosAfterDeath() {
        if (!airSpeedChanged) {
            airSpeed = (float) jumpSpeed;
            airSpeedChanged = true;
        }
        if (hitFromLeft) {
            hitbox.x--;
            hitbox.y = (int) (hitbox.y + airSpeed);
            airSpeed += gravity;
        } else if (hitFromRight) {
            hitbox.x++;
            hitbox.y = (int) (hitbox.y + airSpeed);
            airSpeed += gravity;
        }
    }
        protected void newState(int enemyState) {
        this.enemyState = enemyState;
        if(this.enemyState == DEAD)
            active = false;
        aniTick = 0;
        aniIndex = 0;
    }

    protected void updateAnimationTick(){
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(enemyType,enemyState)) {
                aniIndex = 0;
            } else if (enemyState == DEAD) {
                active = false;
            }
        }
    }

    public void resetEnemy() {
        airSpeedChanged = false;
        hitFromLeft = false;
        hitFromRight = false;
        hitbox.x = x;
        hitbox.y = y-10;
        newState(IDLE);
        active = true;
        oneTimeDeadAnim = false;
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
