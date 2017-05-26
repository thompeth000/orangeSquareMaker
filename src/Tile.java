import java.awt.*;
/**
 * Created by thompeth000 on 4/13/2017.
 */
public interface Tile {

    public void setPos(int row, int col);

    public void setPos(TilePos position);

    public TilePos getPos();

    public Rectangle getBounds();

    public boolean getUsed();

    public void reset();

    public int getX();

    public int getY();

    public boolean isCollideable();

    public void setVisible(boolean a);

    public boolean isVisible();

    public void setCollideable(boolean a);

    public void interact(Entity ent, int side);

    public void paint(Graphics g);

    public void offsetPos(int offset);

    public Tile cloneTile();
}
