package utils;

public class Constants {

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
                    return 4;
                }


                default -> {
                    return 1;
                }

            }
        }

    }

}
