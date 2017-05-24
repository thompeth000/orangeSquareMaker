import java.awt.*;

/**
 * Created by thompeth000 on 5/23/2017.
 */
public class QuestionTile extends Entity implements Tile {
    private boolean visible, collideable, used, mushroom;
    int imageID = 2;

    public QuestionTile(Color color, int x, int y, int width, int height, Game game, int index, boolean vis, boolean mushroom){
        super(color, x, y, width, height, game, index);
        visible = vis;
        this.mushroom = mushroom;
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
        imageID = 2;

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
        return true;
    }

    @Override
    public void setVisible(boolean a) {
        visible = a;
    }

    @Override
    public void interact(Entity ent, int side) {

        if(((ent instanceof Player) && !used && side == 1) || (ent instanceof Koopa && !used && (side == 3 || side == 4) && ((Koopa) ent).isInShell())){
            Rectangle entCollision = new Rectangle(getX(), getY() - getHeight(), getWidth(), getHeight());
            for(int i = 1; i < getGame().getNextIndex(); i++){
                if(entCollision.intersects(getGame().getEntity(i).getBounds())){
                    getGame().getEntity(i).kill(i, 0);
                }
            }
            imageID = 3;
            if(mushroom) {
                getGame().addEntity(new SuperMushroom(Color.RED, getX() + 5, getY() - 16, 20, 20, getGame(), getGame().getNextIndex()));
            }
            else{
                getGame().addEntity(new Particle(Color.YELLOW, getX(), getY(), getWidth(), getHeight(), getGame(), getGame().getNextIndex(), 30, 0, -15));
                GameStats.incrementCoinCounter();
                GameStats.incrementScore(100);
            }
            used = true;
        }
    }


    public void checkCollisions(int i) {

    }


    public void update(int i) {

    }

    @Override
    public Tile cloneTile() {
        return new QuestionTile(getColor(), getX(), getY(), getHeight(), getWidth(), getGame(), 0, visible, mushroom);
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
            g.drawImage(getGame().getSprite(imageID), getX(), getY(), getWidth(), getHeight(), null);
        }


    }

    @Override
    public void offsetPos(int offset) {
        setX((getX()) - (offset % 20));
    }

    public void kill(int i, int deathType){

    }
}
