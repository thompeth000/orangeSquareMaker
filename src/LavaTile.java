import java.awt.*;

/**
 * Created by thompeth000 on 4/19/2017.
 */
public class LavaTile extends Entity implements Tile {

    private boolean visible;

    public LavaTile(Color color, int x, int y, int width, int height, Game game, int index){
        super(color, x, y, width, height, game, index);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void setVisible(boolean a) {
        visible = a;
    }

    @Override
    public void setPos(int row, int col) {

    }

    @Override
    public void setPos(TilePos position) {
        setX(position.getCol() * 20);
        setY(position.getRow() * 20);

    }

    @Override
    public void offsetPos(int offset) {
        setX((getX()) - (offset % 20));
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
    public boolean isVisible(){
        return visible;
    }

    @Override
    public void interact(Entity ent, int side) {
        ent.kill(getGame().getControlVar());
    }


    public void checkCollisions(int i) {

    }


    public void update(int i) {

    }

    @Override
    public Tile cloneTile() {
        return new LavaTile(getColor(), getX(), getY(), getHeight(), getWidth(), getGame(), 0);
    }


    public boolean checkWallCollision() {
        return false;
    }


    public boolean isPlayerObject() {
        return false;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public void kill(int i){

    }
}