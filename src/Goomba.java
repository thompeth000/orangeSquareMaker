import java.awt.*;

/**
 * Created by Ethan on 5/20/2017.
 */
public class Goomba extends Entity {



    public Goomba(Color color, int x, int y, int width, int height, Game game, int index){
        super(color, x, y, width, height, game, index);


    }



    public void checkCollisions(int i) {
        doTileCollisions();
    }

    public void reset(){
        if(getX() > getGame().getPlayerSpawnX())
        setDx(-4);
    }




    public Entity clone(int y, int x){
        return new Goomba(getColor(), x, y, getWidth(), getHeight(), getGame(), getGame().getNextIndex());
    }

    public boolean isEnemy(){
        return true;
    }


    public void update(int i) {
        updateTileMap();

        if(getDx() == 0)
            toggleWalkingDirection();

        if(isWalkingLeft()){
            setDx(-4);
        }
        else
            setDx(4);


        if ((isAirborne() || isDead()) && getDy() < 30){
            setDy(getDy() + 1);
        }

        if(!isDead()) {
            checkCollisions(i);
        }
        move();

        if(getY() > 600){


        }

    }


    public boolean isPlayerObject() {
        return false;
    }



    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());

    }

    public void kill(int i, int deathType){
        setDead(true);
        setDy(-10);
    }

    public void interact(Entity ent){

    }
}
