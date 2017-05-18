/**
 * Created by thompeth000 on 4/20/2017.
 */
public class GameStats {
    public static boolean editor = true;
    public static boolean play, pause, death, gameOver;
    public static int score;
    public static int lives = 3;
    public static long deathStartTime;
    public static int coinCounter = 0;

    public static boolean isEditor(){
        return editor;
    }

    public static void incrementCoinCounter(){
        coinCounter++;
        if(coinCounter >= 100){
            lives++;
            coinCounter = 0;
        }
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
    }

    public static void setEditor(){
        play = false;
        pause = false;
        editor = true;
        death = false;
        gameOver = false;
    }

    public static void setPause(){
        play = false;
        pause = true;
        editor = false;
        death = false;
        gameOver = false;
    }

    public static void setDeath(boolean outOfLives){
        if(!outOfLives) {
            play = false;
            pause = false;
            editor = false;
            death = true;
            gameOver = false;
        }
        else{
            play = false;
            pause = false;
            editor = false;
            death = false;
            gameOver = true;
            lives = 3;
            score = 0;
        }
        deathStartTime = Game.getTime();
    }
}
