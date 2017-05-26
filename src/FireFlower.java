import java.awt.*;

/**
 * Created by Ethan on 5/24/2017.
 */
public class FireFlower extends Entity {
    public FireFlower(Color color, int x, int y, int width, int height, Game game, int index){
        super(color, x, y, width, height, game, index);


    }



    public void checkCollisions(int i) {
        doTileCollisions();
    }

    public void reset(){

    }




    public Entity clone(int y, int x){
        return null;
    }


    public void update(int i) {

    }


    public boolean isPlayerObject() {
        return true;
    }



    public void paint(Graphics g) {
        g.setColor(getColor());
        g.drawImage(getGame().getSprite(5), getX(), getY(), getWidth(), getHeight(), null);

    }

    public void kill(int i, int deathType){
        setDy(-10);
    }

    public void interact(Entity ent){
        if(ent instanceof Player){
            if(GameStats.getPowerupState() == 0) {
                ent.setHeight(ent.getHeight() * 2);
                ent.setY(getY() - (ent.getHeight() / 2));
            }
            ent.setColor(Color.RED);
            GameStats.setPowerupState(2);
            GameStats.incrementScore(1000);
            remove(getGame().getControlVar());
        }

    }

    public boolean isEnemy(){
        return false;
    }
}
