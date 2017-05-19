import java.awt.*;



/**
 * Created by thompeth000 on 4/13/2017.
 */
public abstract class Entity {

    private boolean airborne;
    private int x, y, width, height, index;
    private double dx, dy;
    private Game game;
    private Color color;
    private Tile[][] collideableTiles;
    private final int ARRAYHEIGHT = 5;
    private final int ARRAYWIDTH = 4;



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
        dx = 10;


        if(!(this instanceof Tile)) {
            collideableTiles = new Tile[ARRAYHEIGHT][ARRAYWIDTH];
            airborne = true;
        }

        this.index = index;
    }

    public abstract void checkCollisions(int i);

    public abstract void update(int i);

    public void remove(int i){
        if(index <= i){
            game.decrementControlVariable();
        }
        game.removeEntity(index);
    }

    public abstract void kill(int i);

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



        Rectangle next = new Rectangle(x + (int)dx,y + (int)dy, width, height);

        airborne = true;



        for(int j = 0; j < collideableTiles.length; j++){
            for(int k = 0; k < collideableTiles[0].length; k++){
                if(!(getTile(j,k) instanceof AirTile) && next.intersects(collideableTiles[j][k].getBounds())){

                    if(x >= collideableTiles[j][k].getX() + 20){
                        if(collideableTiles[j][k].isCollideable()) {
                            x = collideableTiles[j][k].getX() + 20;
                            System.out.println("Collision 3");
                            dx = 0;
                        }
                        collideableTiles[j][k].interact(this, 3);
                    } else if(x + width < collideableTiles[j][k].getX() + 1) {
                        if(collideableTiles[j][k].isCollideable()) {
                            x = collideableTiles[j][k].getX() - width;
                            dx = 0;
                            System.out.println("Collision 4");
                        }
                        collideableTiles[j][k].interact(this, 4);
                    }else if (y >= collideableTiles[j][k].getY() + 20) {
                        if(collideableTiles[j][k].isCollideable()) {
                            y = collideableTiles[j][k].getY() + 20;
                            dy = 0;
                            System.out.println("Collision 1");
                        }
                        collideableTiles[j][k].interact(this, 1);
                        }else if (y + height < collideableTiles[j][k].getY() + 1) {
                        if(collideableTiles[j][k].isCollideable()) {
                            y = collideableTiles[j][k].getY() - height;
                            dy = 0;
                            airborne = false;
                            System.out.println("Collision 2");
                        }
                        collideableTiles[j][k].interact(this, 2);
                        }





                    }


                }


            }

        }


    public abstract Entity clone(int originY, int originX);

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

    public void updateX(double dx){
        x += dx;
    }

    public void updateY(double dy){
        y += dy;
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
                collideableTiles[i][j] = game.getTile(((y - 10) / 20) + i, ((x - 20) / 20) + j + (getGame().getCameraOffset() / 20));
                collideableTiles[i][j].setPos(new TilePos(((x - 20) / 20) + j,((y - 10) / 20) + i, true));
                collideableTiles[i][j].offsetPos(getGame().getCameraOffset());
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
