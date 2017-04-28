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
        collideableTiles = new Tile[(height * 3) / 20][(width * 3) / 20];

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

    public void setAirborne(boolean a){
        airborne = a;
    }

    public double calcMovementVector(){
        return Math.atan2(dy, dx);
    }

    public void doTileCollisions(){
        double v = calcMovementVector();

        for(int j = 0; j < collideableTiles.length; j++){
            for(int k = 0; k < collideableTiles[0].length; k++){
                if(!(getTile(j,k) instanceof AirTile) && getBounds().intersects(collideableTiles[j][k].getBounds())){
                    if(dy < 0 && y > collideableTiles[j][k].getY() + 15){
                        y = (int)(y - dy);
                        dy = 0;
                    }
                    else if(dy >= 0 && (y + height) <= collideableTiles[j][k].getY() + 10){
                        y = (int)(y - dy);
                        dy = 0;
                        airborne = false;
                    }
                    else if(dx < 0 && x)
                }
            }
        }
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
                collideableTiles[i][j] = game.getTile((y - height) + (i * 20), (x - width) + (j * 20)).cloneTile();
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

    public Tile getTile(int y, int x){
        return collideableTiles[y][x];
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
