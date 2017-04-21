/**
 * Created by thompeth000 on 4/13/2017.
 */
public class TilePos {
    private int row;
    private int col;

    public TilePos(double x, double y, boolean tileCoordInput){
        if(tileCoordInput){
            row = (int) y;
            col = (int) x;
        }
        else{
            row = (int)(y / 30);
            col = (int)(x / 2048);
        }

    }

    public int getCol(){
        return col;
    }

    public int getRow(){
        return row;
    }

    public int getRelativeCol(){
        return 5;
    }

}
