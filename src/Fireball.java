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
        for(int k = 1; k < getGame().getNextIndex(); k++){
            if(getGame().getEntity(k).isEnemy() && getBounds().intersects(getGame().getEntity(k).getBounds())) {
                getGame().getEntity(k).kill(k, 0);
                remove(getGame().getControlVar());
                GameStats.decrementFireBallCount();
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

        if(!isAirborne()){
            setAirborne(true);
            setDy(-10);
        }


        if ((isAirborne()) && getDy() < 30){
            setDy(getDy() + 1);
        }

        checkCollisions(i);

        move();

        if(getY() > 600 || getDx() == 0 || Math.abs(getX() + getDx() - 400) > getGame().getSimulationRadius() - getWidth()){
            GameStats.decrementFireBallCount();
            remove(i);
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

    public void kill(int i, int deathType){
        setDy(-10);
    }

    public void interact(Entity ent){


    }
}
