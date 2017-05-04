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
    doTileCollisions();
    if(isAirborne()){
        setDy(getDy() + 1);

    }
    else{

    }
    move();
    checkCollisions(i);

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
}
