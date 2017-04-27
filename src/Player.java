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
for(int j = 0; j < getTileMap().length; j++){
    for(int k = 0; k < getTileMap()[0].length; k++){
        if(!(getTile(j,k) instanceof AirTile) && getBounds().intersects(getTile(j,k).getBounds())){
            if(getDy() < 0 && getY() > getTile(j,k).getY() + 10 && getTile(j,k).getX() + 20 > getX()){
                setY((int)(getY() - getDy()));
                setDy(0);
            }
            else if(getDy() >= 0 && (getY() + getHeight()) <= getTile(j,k).getY() + 10){
                setY((int)(getY() - getDy()));
                setDy(0);
                setAirborne(false);
            }
            else if(getDx() < 0 &&)
        }
    }
}
    }


    public void update(int i) {
    updateTileMap();
    if(isAirborne()){
        setDy(getDy() + 2);
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

    }
}
