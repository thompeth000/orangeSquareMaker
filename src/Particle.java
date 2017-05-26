import java.awt.*;

/**
 * Created by Ethan on 5/23/2017.
 */
public class Particle extends Entity {
    private int lifetime, frame;
    public Particle(Color color, int x, int y, int width, int height, Game game, int index, int lifetime, int dx, int dy){
        super(color, x, y, width, height, game, index);
        this.lifetime = lifetime;
        setDx(dx);
        setDy(dy);

    }


    public void reset() {

    }


    public void checkCollisions(int i) {

    }

    public boolean isEnemy(){
        return false;
    }


    public void update(int i) {
        frame++;
        if(frame >= lifetime){
            remove(getGame().getControlVar());
        }

        if(getDy() < 30) {
            setDy(getDy() + 1);
        }

        move();

    }


    public void kill(int i, int deathType) {

    }


    public Entity clone(int y, int x) {
        return null;
    }


    public void interact(Entity ent) {

    }


    public boolean isPlayerObject() {
        return true;
    }


    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}
