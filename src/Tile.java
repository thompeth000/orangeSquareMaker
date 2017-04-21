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

    public void paint(Graphics g);

    public Tile cloneTile();
}
