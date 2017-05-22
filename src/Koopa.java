import java.awt.*;

/**
 * Created by thompeth000 on 5/22/2017.
 */

    public class Koopa extends Entity {
        private boolean inShell;
        private boolean moving;



        public Koopa(Color color, int x, int y, int width, int height, Game game, int index){
            super(color, x, y, width, height, game, index);
            moving = true;

            setDx(-4);

        }



        public void checkCollisions(int i) {
            doTileCollisions();

            if(inShell && moving){
                if(getDx() > 0 || getDx() < 0){
                    for(int a = 0; a < getGame().getNextIndex(); a++){
                        getGame().getEntity(a).kill(a, 0);
                    }
                }
            }
        }




        public Entity clone(int y, int x){
            return new Koopa(getColor(), x, y, getWidth(), getHeight(), getGame(), getGame().getNextIndex());
        }


        public void update(int i) {
            updateTileMap();

            if(getDx() == 0)
                toggleWalkingDirection();

            if(isWalkingLeft()){
                setDx(-5);
            }
            else
                setDx(5);


            if ((isAirborne() || isDead()) && getDy() < 30){
                setDy(getDy() + 1);
            }

            if(!isDead()) {
                checkCollisions(i);
            }

            if(moving) {
                move();
            }

            if(getY() > 600){


            }

        }



        public boolean checkWallCollision() {
            return false;
        }


        public boolean isPlayerObject() {
            return false;
        }





        public void paint(Graphics g) {
            g.setColor(getColor());
            g.fillRect(getX(), getY(), getWidth(), getHeight());

        }

        public void interact(Entity ent){
            if(inShell){
                if(ent.getX() + ent.getWidth() < getX() + (getWidth() / 2)){
                    setDx(5);
                    setWalkingLeft(false);
                }
                else{
                    setDx(-5);
                    setWalkingLeft(true);
                }
            }
        }

        public void kill(int i, int deathType){
            if(deathType == 1 || deathType == 2) {

                if (!inShell) {
                    inShell = true;
                    moving = false;
                }
                else{
                    moving = true;
                    setDx(-4);
                }
            }else{
                setDead(true);
                setDy(-10);
            }
        }
    }

