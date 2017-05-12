import java.awt.*;

/**
 * Created by thompeth000 on 4/14/2017.
 */
public class GroundTile extends Entity implements Tile {

    private boolean visible;

    public GroundTile(Color color, int x, int y, int width, int height, Game game, int index){
        super(color, x, y, width, height, game, index);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void offsetPos(int offset) {
        setX((getX()) - (offset % 20));
    }

    @Override
    public void setPos(int row, int col) {

    }

    @Override
    public boolean isVisible(){
        return visible;
    }

    @Override
    public void setVisible(boolean a) {
        visible = a;
    }

    @Override
    public void setPos(TilePos position) {
        setX(position.getCol() * 20);
        setY(position.getRow() * 20);

    }

    @Override
    public TilePos getPos() {
        return new TilePos(getX() , getY(), false);
    }

    @Override
    public TilePos getAbsPos() {
        return null;
    }

    @Override
    public boolean isCollideable() {
        return true;
    }

    @Override
    public void interact(Entity ent, int side) {

    }


    public void checkCollisions(int i) {

    }


    public void update(int i) {

    }

    @Override
    public Tile cloneTile() {
        return new GroundTile(getColor(), getX(), getY(), getHeight(), getWidth(), getGame(), 0);
    }


    public boolean checkWallCollision() {
        return false;
    }


    public boolean isPlayerObject() {
        return true;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public void kill(int i){

    }
}
