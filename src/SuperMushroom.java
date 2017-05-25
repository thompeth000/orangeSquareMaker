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

    public boolean isEnemy(){
        return false;
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


        if ((isAirborne()) && getDy() < 30){
            setDy(getDy() + 1);
        }

        checkCollisions(i);

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
        g.drawImage(getGame().getSprite(4), getX(), getY(), getWidth(), getHeight(), null);

    }

    public void kill(int i, int deathType){
        setDy(-10);
    }

    public void interact(Entity ent){
        if(ent instanceof Player){
            if(GameStats.getPowerupState() == 0){
                ent.setHeight(ent.getHeight() * 2);
                ent.setY(getY() - (ent.getHeight() / 2));
                GameStats.setPowerupState(1);
            }
            GameStats.incrementScore(1000);
            remove(getGame().getControlVar());
        }

    }
}
