/**
 * Created by thompeth000 on 4/20/2017.
 */
public class GameStats {
    public static boolean editor = true;
    public static boolean play, pause;

    public static boolean isEditor(){
        return editor;
    }

    public static boolean isPlay(){
        return play;
    }

    public static boolean isPause(){
        return pause;
    }

    public static void setPlay(){
        play = true;
        pause = false;
        editor = false;
    }

    public static void setEditor(){
        play = false;
        pause = false;
        editor = true;
    }

    public static void setPause(){
        play = false;
        pause = true;
        editor = false;
    }
}
