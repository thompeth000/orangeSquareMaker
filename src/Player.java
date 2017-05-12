import java.awt.*;

/**
 * Created by thompeth000 on 4/20/2017.
 */
public class Player extends Entity {

    public Player(Color color, int x, int y, int width, int height, Game game, int index){
        super(color, x, y, width, height, game, index);

    }

    public void checkCollisions(int i) {

        //UNFINISHED
        doTileCollisions();
    }


    public void update(int i) {
        updateTileMap();


        if (getGame().isaPressed()) {
            setDx(-5);
        } else if (getGame().isdPressed()) {
            setDx(5);
        } else {
            setDx(0);
        }

        if (getGame().iswPressed() && !isAirborne()) {
            setAirborne(true);
            setDy(-15);
        }


        if (isAirborne()){
            setDy(getDy() + 1);
    }

        checkCollisions(i);
        move();
    }



    public boolean checkWallCollision() {
        return false;
    }


    public boolean isPlayerObject() {
        return true;
    }

    @Override
    public void move(){

        int absX = getX() + getGame().getCameraOffset();
        updateY(getDy());

        if(absX < 400 || absX > 1600){
            System.out.println(absX);
            updateX(getDx());
        }
        else {
            System.out.println("Scrolling");
            getGame().setOffset(getGame().getCameraOffset() + (int) Math.round(getDx()));
        }

    }


    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());

    }

    public void kill(int i){

    }
}
