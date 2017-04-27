import java.awt.*;
/**
 * Created by thompeth000 on 4/13/2017.
 */
public interface Tile {

    public void destroy();

    public void setPos(int row, int col);

    public void setPos(TilePos position);

    public TilePos getPos();

    public TilePos getAbsPos();

    public Rectangle getBounds();

    public int getX();

    public int getY();

    public void paint(Graphics g);

    public void offsetPos(int offset);

    public Tile cloneTile();
}
