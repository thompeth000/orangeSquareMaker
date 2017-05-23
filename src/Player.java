import java.awt.*;

/**
 * Created by thompeth000 on 4/20/2017.
 */
public class Player extends Entity {
    private boolean big;
    private int invincibilityTimer;


    public Player(Color color, int x, int y, int width, int height, Game game, int index){
        super(color, x, y, width, height, game, index);


    }

    public void setBig(){
        big = true;
        setHeight(40);
    }



    public void checkCollisions(int i) {
        doTileCollisions();

        for(int k = 1; k < getGame().getNextIndex(); k++) {
            Entity ent = getGame().getEntity(k);

            if (invincibilityTimer == 0 && !ent.isDead() && getBounds().intersects(ent.getBounds())) {

                if (!ent.isPlayerObject() ) {
                    if (getX() - getDx() + getWidth() > ent.getX() - ent.getDx() && getX() - getDx() < ent.getX() + ent.getWidth() - ent.getDx() && getY() - getDy() + getHeight() > ent.getHeight()) {
                        ent.kill(k, 1);
                        setDy(-10);
                        GameStats.incrementScore(100);

                    } else
                        kill(i, 0);

                }
                else
                ent.interact(this);
            }
        }
    }



    public Entity clone(int originY, int originX){
        return null;
    }


    public void update(int i) {
        updateTileMap();

if(!isDead()) {
    if (getGame().isaPressed()) {
        setDx(-5);
    } else if (getGame().isdPressed()) {
        setDx(5);
    } else {
        setDx(0);
    }

    if(invincibilityTimer > 0){
        invincibilityTimer--;
    }

    if (getGame().iswPressed() && !isAirborne()) {
        setAirborne(true);
        setDy(-15);
    }
}

        if ((isAirborne() || isDead()) && getDy() < 30){
            setDy(getDy() + 1);
    }

    if(!isDead()) {
        checkCollisions(i);
    }
        move();

        if(getY() > 600){
            GameStats.incrementLives(-1);
            GameStats.setDeath(GameStats.getLives() == 0);
            setDx(0);
            setDy(0);
            big = false;
            setHeight(20);

        }

    }



    public boolean checkWallCollision() {
        return false;
    }


    public boolean isPlayerObject() {
        return true;
    }

    @Override
    public void move(){

        int absX = getX() + getGame().getCameraOffset();
        updateY(getDy());

        if((absX < 400 || absX > 39600) && !(getGame().getCameraOffset() > 0 && getGame().getCameraOffset() < 39200)){
            System.out.println(absX);
            updateX(getDx());
        }
        else {
            System.out.println("Scrolling");
            getGame().scroll((int) Math.round(getDx()));
        }

    }


    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());

    }

    public void interact(Entity ent){

    }

    public void reset(){
        invincibilityTimer = 0;
        big = false;
    }

    public void kill(int i, int deathType){
        if(big && deathType == 0){
            setHeight(20);
            invincibilityTimer = 60;
        }else {
            setDead(true);
            setDx(0);
            setDy(-10);
        }
    }
}
