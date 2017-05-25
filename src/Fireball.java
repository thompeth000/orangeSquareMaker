import java.awt.*;

/**
 * Created by Ethan on 5/24/2017.
 */
public class Fireball extends Entity {
    public Fireball(Color color, int x, int y, int width, int height, Game game, int index, boolean left){
        super(color, x, y, width, height, game, index);

        if(left){
            setDx(-5);
        }
        else
            setDx(5);

    }



    public void checkCollisions(int i) {
        doTileCollisions();
        boolean entFound = false;
        for(int k = 1; k < getGame().getNextIndex(); k++){
            if(!entFound && getGame().getEntity(k).isEnemy() && !getGame().getEntity(k).isDead() && getBounds().intersects(getGame().getEntity(k).getBounds())) {
                getGame().getEntity(k).kill(k, 0);
                GameStats.decrementFireBallCount();
                remove(i);
                entFound = true;
            }
        }
    }

    public void reset(){

    }




    public Entity clone(int y, int x){
        return new Goomba(getColor(), x, y, getWidth(), getHeight(), getGame(), getGame().getNextIndex());
    }

    public boolean isEnemy(){
        return false;
    }


    public void update(int i) {
        updateTileMap();

        if(getY() > 600 || getDx() == 0 || Math.abs(getX() + getDx() - 400) > getGame().getSimulationRadius() - getWidth()){
            GameStats.decrementFireBallCount();
            remove(i);
        }

        if(!isAirborne()){
            setAirborne(true);
            setDy(-10);
        }


        if ((isAirborne()) && getDy() < 30){
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



    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());

    }

    public void kill(int i, int deathType){
        setDy(-10);
    }

    public void interact(Entity ent){


    }
}
