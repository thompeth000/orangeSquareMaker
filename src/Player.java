import java.awt.*;

/**
 * Created by thompeth000 on 4/20/2017.
 */
public class Player extends Entity {

    private int invincibilityTimer;
    private int fireballSpamTimer;
    private boolean jumping;
    private int jumpTimer;



    public Player(Color color, int x, int y, int width, int height, Game game, int index){
        super(color, x, y, width, height, game, index);


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
                        if(getGame().iswPressed()){
                            jumping = true;
                            jumpTimer = 15;
                        }
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

        if(fireballSpamTimer > 0){
            fireballSpamTimer--;
        }

if(!isDead()) {
    if (getGame().isaPressed()) {
        if(getDx() > -5) {
            setDx(getDx() - 1);
        }
        setWalkingLeft(true);
    } else if (getGame().isdPressed()) {
        if(getDx() < 5){
            setDx(getDx() + 1);
        }
        setWalkingLeft(false);
    } else {
        if(!isAirborne()) {
            if(getDx() > 0) {
                setDx(getDx() - 2);
                if(getDx() < 0)
                    setDx(0);
            }
            else if(getDx() < 0){
                setDx(getDx() + 2);
                if(getDx() > 0)
                    setDx(0);
            }

        }
    }

    if(getGame().isFPressed() && GameStats.getPowerupState() == 2 && fireballSpamTimer <= 0 && GameStats.getFireBallCount() < 2){
        //There can only be two fireballs active at once
        getGame().addEntity(new Fireball(Color.RED, getX() + (getWidth() / 2), getY() + (getHeight() / 2), 15, 15, getGame(), getGame().getNextIndex(), isWalkingLeft()));
        GameStats.incrementFireBallCount();
        fireballSpamTimer = 15;
    }

    if(invincibilityTimer > 0){
        invincibilityTimer--;
    }

    if (getGame().iswPressed() && !isAirborne()) {
        setAirborne(true);
        setDy(-10);
        jumping = true;
        jumpTimer = 10;
    }

    if(jumpTimer <= 0 || !getGame().iswPressed() || !isAirborne() || getDy() >= 0){
        jumping = false;
        jumpTimer = 0;
    }

    if(jumping){
        setDy(-10);
        jumpTimer--;
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
            GameStats.setPowerupState(0);
            setHeight(20);

        }

    }


    public boolean isPlayerObject() {
        return true;
    }

    public boolean isEnemy(){
        return false;
    }

    @Override
    public void move(){
        //Overrides the default move method to account for the fact that the player scrolls the screen left and right

        int absX = getX() + getGame().getCameraOffset();
        updateY(getDy());

        if((absX < 400 || absX > 39600) && !(getGame().getCameraOffset() > 0 && getGame().getCameraOffset() < 39200)){
            updateX(getDx());
        }
        else {
            getGame().scroll((int)getDx());
        }

    }


    public void paint(Graphics g) {
        g.setColor(getColor());

        if(invincibilityTimer % 2 == 0) {
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }

    }

    public void interact(Entity ent){

    }

    public void reset(){
        invincibilityTimer = 0;
        setColor(Color.ORANGE);
        GameStats.setPowerupState(0);
        setHeight(20);
        setDx(0);
        setDy(0);
        jumping = false;
        jumpTimer = 0;
        setAirborne(true);
    }

    public void kill(int i, int deathType){
        if(GameStats.getPowerupState() == 1  && deathType == 0){
            setY(getY() + (getHeight() / 2));
            setHeight(20);
            invincibilityTimer = 60;
            GameStats.setPowerupState(0);
        }
        else if(GameStats.getPowerupState() == 2 && deathType == 0){
            invincibilityTimer = 60;
            setColor(Color.ORANGE);
            GameStats.setPowerupState(1);
        }else{
            setDead(true);
            setDx(0);
            setDy(-10);
        }
    }
}
