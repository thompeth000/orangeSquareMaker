import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

/**
 * Created by thompeth000 on 4/13/2017.
 */
public class Game extends JPanel implements ActionListener {

    private final Color BACKCOLOR = new Color(40,150,220);
    Timer timer;
    ArrayList<Entity> entities;
    BufferedImage groundSprite;
    Tile[][] tileMap;
    BufferedImage[] spriteSheet;
    Tile selectedTile = new GroundTile(Color.GREEN, 0, 0, 20, 20, this, 0);
    int cursorX, cursorY, cameraOffset, gameLoopControl, levelLength, playerSpawnX, playerSpawnY, playerSpawnOffset;
    static long gameTime;
    final int HEIGHTINTILES = 30;
    final int WIDTHINTILES = 40;
    boolean click, dPressed, aPressed, playerSpawnPlaced, wPressed;
    String tileString = "Ground Tile";

    public Game(){
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("FINAL PROJECT");
        setPreferredSize(new Dimension(800,600));
        setBackground(BACKCOLOR);
        tileMap = new Tile[30][2000];
        spriteSheet = new BufferedImage[128];
        cameraOffset = 0;
        gameTime = 0;
        playerSpawnPlaced = false;

        for(int i = 0; i < tileMap.length; i++){
            for(int j = 0; j < tileMap[i].length; j++){
                tileMap[i][j] = new AirTile(Color.BLUE, j * 20, i * 20, 20, 20, this, 0);
            }
        }



        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = true;
                }

                if (e.getKeyCode() == KeyEvent.VK_D) {
                dPressed = true;
                }

                if (e.getKeyCode() == KeyEvent.VK_A) {
                aPressed = true;
                }

                if(e.getKeyCode() == KeyEvent.VK_1 && GameStats.isEditor()){
                    tileString = "Ground Tile";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_2 && GameStats.isEditor()){
                    tileString = "Lava Tile";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_3 && GameStats.isEditor()){
                    tileString = "Coin";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_4 && GameStats.isEditor()){
                    tileString = "Goal";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_5 && GameStats.isEditor()){
                    tileString = "Player Spawn";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_0 && GameStats.isEditor()){
                    tileString = "Erase";
                    updateSelectedTile();
                }

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {



                }

                if (e.getKeyCode() == KeyEvent.VK_P) {
                    if(GameStats.isEditor()){
                        levelLength = findLevelLength();
                        GameStats.setPlay();
                        if(playerSpawnPlaced) {
                            entities.get(0).setX(playerSpawnX);
                            entities.get(0).setY(playerSpawnY);
                            cameraOffset = playerSpawnOffset;
                        }
                    }
                    else {
                        for(int i = 0; i < tileMap.length; i++){

                        }
                        GameStats.setEditor();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_W){
                 wPressed = false;
                }

                if(e.getKeyCode() == KeyEvent.VK_D){
                dPressed = false;
                }

                if(e.getKeyCode() == KeyEvent.VK_A){
                aPressed = false;
                }

            }
        });

        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                super.mouseMoved(e);
                cursorX = e.getX();
                cursorY = e.getY();
            }
        });

        addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(e.getButton() == MouseEvent.BUTTON1){
                    click = true;

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(e.getButton() == MouseEvent.BUTTON1){
                    click = false;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

    }

    public static void main(String[] args){
        Game game = new Game();
        game.initGame();

    }

    public int getControlVar(){
        return gameLoopControl;
    }

    public boolean isdPressed() {
        return dPressed;
    }

    public boolean isaPressed() {
        return aPressed;
    }

    public boolean iswPressed() {
        return wPressed;
    }

    public int getCameraOffset(){
        return cameraOffset;
    }

    public BufferedImage getSprite(int spriteID){
        return spriteSheet[spriteID];
    }

    public void initGame(){

        try {
            spriteSheet[0] = ImageIO.read(new File("resource/ground.png"));
        }
        catch(java.io.IOException e){
            System.out.println("Image not found!");
        }

        try {
            spriteSheet[1] = ImageIO.read(new File("resource/coin.png"));
        }
        catch(java.io.IOException e){
            System.out.println("Image not found!");
        }

        loadTiles(0);
        entities = new ArrayList<Entity>();
        addEntity(new Player(Color.ORANGE, 0, 0, 15, 40, this, 0));
        timer = new Timer(1000/60, this);
        timer.start();
    }

    public void startGame(){
        levelLength = findLevelLength();
        GameStats.setPlay();
        if(playerSpawnPlaced) {
            entities.get(0).setX(playerSpawnX);
            entities.get(0).setY(playerSpawnY);
            cameraOffset = playerSpawnOffset;
        }
    }


    public Tile[][] getTileMap(){
        return tileMap;
    }

    public static long getTime(){
        return gameTime;
    }



    public void paint(Graphics g){
        super.paint(g);
        setBackground(BACKCOLOR);

        if(GameStats.isEditor() || GameStats.isPlay()) {
            for (int i = 0; i < HEIGHTINTILES; i++) {
                for (int k = 0; k < WIDTHINTILES; k++) {
                    getTile(i, (cameraOffset / 20) + k).paint(g);
                }
            }
        }
        if(GameStats.isEditor()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Lucida Console", Font.BOLD, 24));
            printSimpleString(tileString, getWidth(), -300, 20, g);
        }
        if(GameStats.isPlay()){
            for (Entity obj : entities) {
                obj.paint(g);
            }
            g.setColor(Color.YELLOW);
            g.fillOval(40,60, 20,20);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Lucida Console", Font.BOLD, 24));
            printSimpleString("Score: " + GameStats.getScore(), getWidth(), -300, 20, g);
            printSimpleString("Lives: " + GameStats.getLives(), getWidth(), -300, 50, g);
            printSimpleString("X " + GameStats.getCoinCounter(), getWidth(), -300, 80, g);

            //FOR TILE COLLISION DEBUGGING
            /*
            for (int i = 0; i < entities.get(0).getTileMap().length; i++) {
                for (int k = 0; k < entities.get(0).getTileMap()[i].length; k++) {
                    entities.get(0).getTile(i,k).paint(g);
                }
            }
            */
            //FOR TILE COLLISION DEBUGGING
        }
        if(GameStats.isDeath()){
            setBackground(Color.BLACK);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Lucida Console", Font.BOLD, 24));
            printSimpleString("Score: " + GameStats.getScore(), getWidth(), -300, 20, g);
            printSimpleString("X " + GameStats.getLives(), getWidth(), 20, 300, g);
            g.setColor(Color.ORANGE);
            g.fillRect(370, 260, 15, 40);
        }
        if(GameStats.isGameOver()){
            setBackground(Color.BLACK);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Lucida Console", Font.BOLD, 24));
            printSimpleString("Score: " + GameStats.getScore(), getWidth(), -300, 20, g);
            printSimpleString("GAME OVER", getWidth(), 0, 300, g);
        }

    }

    public int getNextIndex(){
        return entities.size();
    }

    public void loadTiles(int offset){
        for(int i = 0; i < HEIGHTINTILES; i++){
            for(int k = 0; k < WIDTHINTILES; k++){
                //loadedTiles[i][k] = getTile(i, (offset / 20) + k).cloneTile();
                tileMap[i][(offset / 20) + k].setPos(new TilePos(k, i, true));
                tileMap[i][(offset / 20) + k].offsetPos(offset);
            }

        }
    }


    @Override
    public void actionPerformed(ActionEvent e){
        gameTime++;

   if(GameStats.isEditor()) {
       if (aPressed) {
           cameraOffset -= 10;

           if (cameraOffset < 0) {
               cameraOffset = 0;
           }
       }

       if (dPressed) {
           cameraOffset += 10;

       }

       if (click) {
           setTile(cursorX, cursorY, cameraOffset, selectedTile);
       }

   }

   if(GameStats.isPlay()){
       for(gameLoopControl = 0; gameLoopControl < getNextIndex(); gameLoopControl++){
           entities.get(gameLoopControl).update(gameLoopControl);
       }
   }

   if(GameStats.isDeath()|| GameStats.isGameOver()){
       if(gameTime - GameStats.getDeathStartTime() > 120){
           startGame();
       }
   }

    repaint();

    loadTiles(cameraOffset);

    }

    public void setTile(int x, int y, int offset, Tile selected){

        int newX = x + offset;

if(!(playerSpawnPlaced && selected instanceof PlayerStartTile)) {
    if(tileMap[y / 20][(int) Math.floor(newX / 20.0)] instanceof PlayerStartTile){
        playerSpawnPlaced = false;
    }
    if(selected instanceof PlayerStartTile){

        playerSpawnPlaced = true;
        playerSpawnOffset = offset / 800 * 800;
        playerSpawnX = playerSpawnOffset + (newX % 800);
        playerSpawnY = y - entities.get(0).getHeight();

    }
    tileMap[y / 20][(int) Math.floor(newX / 20.0)] = selected.cloneTile();
    tileMap[y / 20][(int) Math.floor(newX / 20.0)].setPos(new TilePos(newX, y, false));
}
    }

    public void updateSelectedTile(){
        switch(tileString){
            case "Erase":
                selectedTile = new AirTile(Color.BLUE, 0, 0, 20, 20, this, 0);
                break;
            case "Ground Tile":
                selectedTile = new GroundTile(Color.GREEN, 0, 0, 20, 20, this, 0);
                break;
            case "Lava Tile":
                selectedTile = new LavaTile(Color.RED, 0, 0, 20, 20, this, 0);
                break;
            case "Coin":
                selectedTile = new CoinTile(Color.YELLOW, 0, 0, 20, 20, this, 0, true);
                break;
            case "Goal":
                selectedTile = new GoalTile(new Color(255,0,255), 0, 0, 20, 20, this, 0);
                break;
            case "Player Spawn":
                selectedTile = new PlayerStartTile(Color.ORANGE, 0, 0, 20, 20, this, 0);
                break;
            default:
                selectedTile = new GroundTile(Color.GREEN, 0, 0, 20, 20, this, 0);
        }
    }

    private void printSimpleString(String s, int width, int xPos, int yPos, Graphics g2d){
        int stringLen = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int start = width / 2 - stringLen / 2;
        g2d.drawString(s, start + xPos, yPos);
    }

    public void setOffset(int a){
        cameraOffset = a;
    }

    public void addToOffset(int a){
        cameraOffset += a;
    }

    public void addEntity(Entity ent){
        entities.add(ent);
    }

    public void removeEntity(int index){
        entities.remove(index);
        for(int i = index; i < entities.size(); i++) {
            entities.get(i).decrementIndex();
        }
    }

    public Entity getEntity(int index){
        return entities.get(index);
    }

    public Tile getTile(int row, int col){
         if(row < tileMap.length && row >= 0 && col < tileMap[0].length && col >= 0)
            return tileMap[row][col];

            return new AirTile(Color.BLUE, col * 20, row * 20, 20, 20, this, 0);


    }



    public void decrementControlVariable(){
        gameLoopControl--;
    }

    public void setTile(TilePos pos, Tile newTile){
        tileMap[pos.getRow()][pos.getCol()] = newTile.cloneTile();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }

    public int findLevelLength(){


        for(int i = tileMap.length - 1; i >= 0; i--){
            for(int j = tileMap[0].length - 1; j >= 0; j--){
                if(!(tileMap[i][j] instanceof AirTile)){
                    System.out.println(i * 20);
                    return (i + 1) * 20;
                }
            }
        }
        return 0;
    }

    public int getLevelLength(){
        return levelLength;
    }

    public void scroll(int a){
        cameraOffset += a;
        for(int i = 1; i < entities.size(); i++){
            entities.get(i).setX(entities.get(i).getX() + a);
        }
    }

    public void scrollTo(int a){
        scroll(a - cameraOffset);
    }



}
