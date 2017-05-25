/**
 * Created by thompeth000 on 4/20/2017.
 */
public class GameStats {
    public static boolean title = true;
    public static boolean play, editor, pause, death, gameOver, levelEnd, textFlicker;
    public static int score;
    public static int lives = 3;
    public static int powerupState = 0; //0: Small, 1: Mushroom, 2: Fire Flower
    public static long deathStartTime;
    public static int coinCounter = 0;
    public static int fireBallCount;

    public static boolean isEditor(){
        return editor;
    }

    public static void setPowerupState(int state){

            powerupState = state;

    }

    public static int getPowerupState(){
        return powerupState;
    }

    public static void incrementCoinCounter(){
        coinCounter++;
        if(coinCounter >= 100){
            lives++;
            coinCounter = 0;
        }
    }

    public static boolean isTitle(){
        return title;
    }

    public static int getCoinCounter(){
        return coinCounter;
    }

    public static void resetCoins(){
        coinCounter = 0;
    }

    public static void incrementScore(int num){
        score += num;
    }

    public static int getScore(){
        return score;
    }

    public static boolean isGameOver(){
        return gameOver;
    }

    public static long getDeathStartTime(){
        return deathStartTime;
    }

    public static void resetScore(){
        score = 0;
    }

    public static int getLives(){
        return lives;
    }

    public static void resetLives(){
        lives = 3;
    }

    public static int getFireBallCount(){
        return fireBallCount;
    }

    public static void resetFireBallCount(){
        fireBallCount = 0;
    }

    public static void incrementFireBallCount(){
        fireBallCount++;
    }

    public static void decrementFireBallCount(){
        fireBallCount--;
    }

    public static void incrementLives(int num){
        lives += num;
    }

    public static boolean isPlay(){
        return play;
    }

    public static boolean isPause(){
        return pause;
    }

    public static boolean isDeath(){return death;}

    public static void setPlay(){
        play = true;
        pause = false;
        editor = false;
        death = false;
        gameOver = false;
        levelEnd = false;
        title = false;
    }

    public static void setEditor(){
        play = false;
        pause = false;
        editor = true;
        death = false;
        gameOver = false;
        levelEnd = false;
        title = false;
    }

    public static void setPause(){
        play = false;
        pause = true;
        editor = false;
        death = false;
        gameOver = false;
        levelEnd = false;
        title = false;
    }

    public static void setDeath(boolean outOfLives){
        if(!outOfLives) {
            play = false;
            pause = false;
            editor = false;
            death = true;
            gameOver = false;
            levelEnd = false;
            title = false;
        }
        else{
            play = false;
            pause = false;
            editor = false;
            death = false;
            gameOver = true;
            levelEnd = false;
            title = false;
            lives = 3;
            score = 0;
        }
        deathStartTime = Game.getTime();
    }

    public static void setLevelEnd(){
        play = false;
        pause = false;
        editor = false;
        death = false;
        gameOver = false;
        levelEnd = true;
        title = false;
    }

    public static void setTitle(){
        play = false;
        pause = false;
        editor = false;
        death = false;
        gameOver = false;
        levelEnd = false;
        title = true;
    }

    public static boolean isLevelEnd(){
        return levelEnd;
    }

    public static boolean getTextFlicker(){
        return textFlicker;
    }

    public static void toggleTextFlicker(){
        if(textFlicker){
            textFlicker = false;
        }
        else
            textFlicker = true;
    }
}
