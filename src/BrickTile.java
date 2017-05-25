import java.awt.*;

/**
 * Created by Ethan on 5/24/2017.
 */
public class BrickTile extends Entity implements Tile {

    private boolean visible, used;
    private boolean collideable = true;


    public BrickTile(Color color, int x, int y, int width, int height, Game game, int index, boolean vis){
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
        visible = true;
        collideable = true;
        used = false;
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
        return collideable;
    }

    @Override
    public void setVisible(boolean a) {
        visible = a;
    }

    @Override
    public void interact(Entity ent, int side) {

        if(((ent instanceof Player) && !used && side == 1) || (ent instanceof Koopa && !used && (side == 3 || side == 4) && ((Koopa) ent).isInShell())) {
            Rectangle entCollision = new Rectangle(getX(), getY() - getHeight(), getWidth(), getHeight());
            for (int i = 1; i < getGame().getNextIndex(); i++) {
                if (entCollision.intersects(getGame().getEntity(i).getBounds())) {
                    getGame().getEntity(i).kill(i, 0);
                }
            }
            if (GameStats.getPowerupState() > 0 || ent instanceof Koopa) {
                Color particleColor = new Color(255, 97, 29);
                collideable = false;
                visible = false;
                used = true;
                GameStats.incrementScore(50);
                getGame().addEntity(new Particle(particleColor, getX(), getY(), 10, 10, getGame(), getGame().getNextIndex(), 120, -5, -15));
                getGame().addEntity(new Particle(particleColor, getX(), getY() + getHeight(), 10, 10, getGame(), getGame().getNextIndex(), 120, -5, -15));
                getGame().addEntity(new Particle(particleColor, getX() + getWidth(), getY(), 10, 10, getGame(), getGame().getNextIndex(), 120, 5, -15));
                getGame().addEntity(new Particle(particleColor, getX() + getWidth(), getY() + getHeight(), 10, 10, getGame(), getGame().getNextIndex(), 120, 5, -15));
            }
        }
    }


    public void checkCollisions(int i) {

    }

    public boolean isEnemy(){
        return false;
    }


    public void update(int i) {

    }

    @Override
    public Tile cloneTile() {
        return new BrickTile(getColor(), getX(), getY(), getHeight(), getWidth(), getGame(), 0, visible);
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
            g.drawImage(getGame().getSprite(6), getX(), getY(), getWidth(), getHeight(), null);
        }


    }

    @Override
    public void offsetPos(int offset) {
        setX((getX()) - (offset % 20));
    }

    public void kill(int i, int deathType){

    }
}
