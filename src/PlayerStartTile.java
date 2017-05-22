import java.awt.*;

/**
 * Created by thompeth000 on 4/26/2017.
 */
public class PlayerStartTile extends Entity implements Tile {

    private boolean visible;

    public PlayerStartTile(Color color, int x, int y, int width, int height, Game game, int index){
        super(color, x, y, width, height, game, index);
    }

    @Override
    public void destroy() {

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
        return false;
    }

    @Override
    public void interact(Entity ent, int side) {

    }


    public void checkCollisions(int i) {

    }

    public Entity clone(int originY, int originX){
        return null;
    }

    @Override
    public boolean getUsed(){
        return false;
    }

    @Override
    public void reset(){

    }


    public void update(int i) {

    }

    @Override
    public void setVisible(boolean a) {
        visible = a;
    }

    @Override
    public void setCollideable(boolean a){

    }

    @Override
    public boolean isVisible(){
        return visible;
    }

    @Override
    public Tile cloneTile() {
        return new PlayerStartTile(getColor(), getX(), getY(), getHeight(), getWidth(), getGame(), 0);
    }


    public boolean checkWallCollision() {
        return false;
    }


    public boolean isPlayerObject() {
        return true;
    }

    @Override
    public void paint(Graphics g) {
       if(GameStats.isEditor()) {
           g.setColor(getColor());
           g.fillOval(getX(), getY(), getWidth(), getHeight());
       }
    }

    public void interact(Entity ent){

    }

    public void kill(int i, int deathType){

    }
}
