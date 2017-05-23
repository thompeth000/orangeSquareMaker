import java.awt.*;

/**
 * Created by thompeth000 on 5/23/2017.
 */
public class SuperMushroom extends Entity {

    public SuperMushroom(Color color, int x, int y, int width, int height, Game game, int index){
        super(color, x, y, width, height, game, index);

        setDx(-4);

    }



    public void checkCollisions(int i) {
        doTileCollisions();
    }

    public void reset(){

    }




    public Entity clone(int y, int x){
        return new Goomba(getColor(), x, y, getWidth(), getHeight(), getGame(), getGame().getNextIndex());
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
        setDead(true);
        setDy(-10);
    }

    public void interact(Entity ent){
        if(ent instanceof Player){
            ((Player)ent).setBig();
            ent.setY(ent.getY() - 20);
            GameStats.incrementScore(1000);
            remove(getGame().getControlVar());
        }

    }
}
