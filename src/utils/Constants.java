package utils;

import main.Game;

public class Constants {

    public static class ShurikenConstant {
        public static final int SHURIKEN_WIDTH_DEFAULT = 10;
        public static final int SHURIKEN_HEIGHT_DEFAULT = 2;
        public static final int SHURIKEN_DRAW_WIDTH = (int)(20 * Game.SCALE);
        public static final int SHURIKEN_DRAW_HEIGHT = (int)(15 * Game.SCALE);
        public static final int SHURIKEN_WIDTH = (int)(SHURIKEN_WIDTH_DEFAULT * Game.SCALE);
        public static final int SHURIKEN_HEIGHT = (int)(SHURIKEN_HEIGHT_DEFAULT * Game.SCALE);
        public static final float SHURIKEN_SPEED = 2f * Game.SCALE;
        public static final int SHURIKEN_POS_OFFSET = 20;
    }

    public static class EnemyConstant {
        public static final int PIGGY = 200;

        public static final int IDLE = 0;
        public static final int DEAD = 1;
        public static final int HIT = 2;

        public static final int BLOOD1 = 0;
        public static final int BLOOD2 = 1;
        public static final int BLOOD3 = 2;

        public static final int PIGGY_WIDTH_DEFAULT = 34 ;
        public static final int PIGGY_HEIGHT_DEFAULT = 28 ;
        public static final int PIGGY_WIDTH = (int)(PIGGY_WIDTH_DEFAULT * Game.SCALE * 1.5) ;
        public static final int PIGGY_HEIGHT = (int)(PIGGY_HEIGHT_DEFAULT * Game.SCALE * 1.5) ;

        public static final int PIGGY_DRAWOFFSET_X = (int)(18 * Game.SCALE);
        public static final int PIGGY_DRAWOFFSET_Y = (int)(16 * Game.SCALE);


        public static int GetSpriteAmount(int enemy_type,int enemy_state){
            switch (enemy_type) {
                case PIGGY:
                    switch (enemy_state) {
                        case IDLE:
                            return 11;
                        case DEAD:
                            return 6;
                        case HIT:
                            return 2;
                    }
            }
            return 0;
        }


    }
    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH =(int)( B_WIDTH_DEFAULT * Game.SCALE) / 2;
            public static final int B_HEIGHT =(int)( B_HEIGHT_DEFAULT * Game.SCALE) / 2;

        }
    }
    public static class Directions{

        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;

    }

    public static class PlayerConstants{

        public static final int RUNNING = 0;
        public static final String RUNNING_ATLAS = "_Run.png";
        public static final int IDLE = 1;
        public static final String IDLE_ATLAS = "_Idle.png";
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK = 6;
        public static final String ATTACK_ATLAS = "_Attack.png";
        public static final int ATTACK_JUMP = 7;

        public static int GetSpriteAmount(int player_action){

            switch (player_action){

                case IDLE -> {
                    return 10;
                }
                case RUNNING -> {
                    return 10;
                }
                case JUMP -> {
                    return 3;
                }
                case ATTACK -> {
                    return 1;
                }


                default -> {
                    return 1;
                }

            }
        }

    }

    public static class Level1 {
        public static final float SECOND_STAR = 6;
        public static final float THIRD_STAR = 4;
    }

}
