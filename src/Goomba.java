import java.awt.*;

/**
 * Created by Ethan on 5/20/2017.
 */
public class Goomba extends Entity {
    private boolean dead;


    public Goomba(Color color, int x, int y, int width, int height, Game game, int originY, int originX, int index){
        super(color, x, y, width, height, game, index);
        dead = false;
        setX(originX);
        setY(originY);
        setDx(-4);

    }



    public void checkCollisions(int i) {
        doTileCollisions();
    }

    public void setDead(boolean b){
        dead = b;
    }


    public Entity clone(int originY, int originX){
        return new Goomba(getColor(), originX, originY, getWidth(), getHeight(), getGame(), originY, originX, getGame().getNextIndex());
    }


    public void update(int i) {
        updateTileMap();

        if(isWalkingLeft()){
            setDx(-5);
        }
        else
            setDx(5);


        if ((isAirborne() || dead) && getDy() < 30){
            setDy(getDy() + 1);
        }

        if(!dead) {
            checkCollisions(i);
        }
        move();

        if(getY() > 600){


        }

    }



    public boolean checkWallCollision() {
        return false;
    }


    public boolean isPlayerObject() {
        return true;
    }



    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());

    }

    public void kill(int i){
        dead = true;
        setDy(-10);
    }
}
