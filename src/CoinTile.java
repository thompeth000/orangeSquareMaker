import java.awt.*;

/**
 * Created by thompeth000 on 4/19/2017.
 */
public class CoinTile extends Entity implements Tile {

    private boolean visible, collideable, used;

    public CoinTile(Color color, int x, int y, int width, int height, Game game, int index, boolean vis){
        super(color, x, y, width, height, game, index);
        visible = vis;
    }

    @Override
    public void destroy() {

    }

    public Entity clone(int originY, int originX){
        return null;
    }

    @Override
    public boolean getUsed(){
        return used;
    }

    @Override
    public void reset(){
        used = false;
        visible = true;

    }

    @Override
    public boolean isVisible(){
        return visible;
    }

    @Override
    public void setCollideable(boolean a){

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
    public TilePos getPos() {
        return new TilePos(getX() , getY(), false);
    }

    @Override
    public TilePos getAbsPos() {
        return null;
    }

    @Override
    public boolean isCollideable() {
        return false;
    }

    @Override
    public void setVisible(boolean a) {
        visible = a;
    }

    @Override
    public void interact(Entity ent, int side) {
        if((ent instanceof Player) && visible){
            GameStats.incrementScore(100);
            GameStats.incrementCoinCounter();
            visible = false;
            used = true;
        }
    }


    public void checkCollisions(int i) {

    }


    public void update(int i) {

    }

    @Override
    public Tile cloneTile() {
        return new CoinTile(getColor(), getX(), getY(), getHeight(), getWidth(), getGame(), 0, visible);
    }

    public void interact(Entity ent){

    }


    public boolean checkWallCollision() {
        return false;
    }


    public boolean isPlayerObject() {
        return true;
    }

    @Override
    public void paint(Graphics g) {
        if(visible) {
            g.setColor(getColor());
            g.drawImage(getGame().getSprite(1), getX(), getY(), getWidth(), getHeight(), null);
        }


    }

    @Override
    public void offsetPos(int offset) {
        setX((getX()) - (offset % 20));
    }

    public void kill(int i, int deathType){

    }
}
