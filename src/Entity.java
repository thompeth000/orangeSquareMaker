import java.awt.*;

/**
 * Created by thompeth000 on 4/13/2017.
 */
public abstract class Entity {
    private int x, y, width, height, index;
    private boolean airborne;
    private double dx, dy;
    private Game game;
    private Color color;
    private Tile[][] collideableTiles;

    public void move(){
        x += dx;
        y += dy;

    }



    public Entity(Color color, int x, int y, int width, int height, Game game, int index) {
        this.game = game;
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;


        if(!(this instanceof Tile))
        collideableTiles = new Tile[(height * 5) / 20][width * 5 / 20];

        this.index = index;
    }

    public abstract void checkCollisions(int i);

    public abstract void update(int i);

    public void kill(int i){
        if(index <= i){
            game.decrementControlVariable();
        }
        game.removeEntity(index);
    }

    public abstract boolean checkWallCollision();

    public void decrementIndex(){
        index--;
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }

    public int getX() {
        return x;
    }

    public Tile[][] getTileMap(){
        return collideableTiles;
    }

    public abstract boolean isPlayerObject();

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getIndex() {
        return index;
    }

    public void updateTileMap(){
        for(int i = 0; i < collideableTiles.length; i++){
            for(int j = 0; j < collideableTiles[0].length; j++){
                collideableTiles[i][j] = game.getTile(i ,j + (x / 20)).cloneTile();
            }
        }
    }

    public boolean isAirborne(){
        return airborne;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getDx() {
        return dx;
    }



    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Game getGame() {
        return game;
    }

    public abstract void paint(Graphics g);
}
