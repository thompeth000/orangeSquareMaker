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
    Entity selectedObject = new GroundTile(Color.GREEN, 0, 0, 20, 20, this, 0);
    int cursorX, cursorY, cameraOffset, gameLoopControl, levelLength, playerSpawnX, playerSpawnY, playerSpawnOffset, entCount;
    static long gameTime;
    final int HEIGHTINTILES = 30;
    final int WIDTHINTILES = 40;
    boolean click, dPressed, aPressed, pPressed, playerSpawnPlaced, wPressed, spacePressed, entityPlaced, eraseMode;
    String selectedObjString = "Ground Tile";
    final int SIMULATIONRADIUS = 500;

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
        entityPlaced = false;
        entCount = 1;



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
                    selectedObjString = "Ground Tile";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_2 && GameStats.isEditor()){
                    selectedObjString = "Lava Tile";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_3 && GameStats.isEditor()){
                    selectedObjString = "Coin";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_4 && GameStats.isEditor()){
                    selectedObjString = "Goal";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_5 && GameStats.isEditor()){
                    selectedObjString = "Player Spawn";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_6 && GameStats.isEditor()){
                    selectedObjString = "Goomba";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_7 && GameStats.isEditor()){
                    selectedObjString = "Koopa";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_8 && GameStats.isEditor()){
                    selectedObjString = "? Block (Mushroom)";
                    updateSelectedTile();
                }

                if(e.getKeyCode() == KeyEvent.VK_9 && GameStats.isEditor()){
                    selectedObjString = "? Block (Coin)";
                    updateSelectedTile();
                }



                if(e.getKeyCode() == KeyEvent.VK_0 && GameStats.isEditor()){
                    selectedObjString = "Erase";
                    updateSelectedTile();
                }

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spacePressed = true;
                }

                if (e.getKeyCode() == KeyEvent.VK_P) {




                        if (GameStats.isEditor()) {
                            startGame();
                        } else if(GameStats.isPlay()) {
                            entities.get(0).setDead(false);
                            GameStats.resetScore();
                            GameStats.resetLives();
                            GameStats.resetCoins();
                            resetTiles();
                            purgeEntities();
                            resetEntities();
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


                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spacePressed = false;
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
                    entityPlaced = false;
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

    public void resetEntities(){
        for(int i = 0; i < entities.size(); i++){
            entities.get(i).resetEnt();
        }
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

    public void loadSprites(){
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

        try {
            spriteSheet[2] = ImageIO.read(new File("resource/qBlock.png"));
        }
        catch(java.io.IOException e){
            System.out.println("Image not found!");
        }

        try {
            spriteSheet[3] = ImageIO.read(new File("resource/usedBlock.png"));
        }
        catch(java.io.IOException e){
            System.out.println("Image not found!");
        }

        try {
            spriteSheet[4] = ImageIO.read(new File("resource/mushroom.png"));
        }
        catch(java.io.IOException e){
            System.out.println("Image not found!");
        }

    }

    public void initGame(){

        loadSprites();


        loadTiles(0);
        entities = new ArrayList<Entity>();
        addEntity(new Player(Color.ORANGE, 0, 0, 20, 20, this, 0));
        timer = new Timer(17, this);
        timer.start();
    }

    public void startGame(){
        if(playerSpawnPlaced) {
            resetTiles();
            levelLength = findLevelLength();
            GameStats.setPlay();
            cameraOffset = playerSpawnOffset;
            resetEntities();
            purgeEntities();
            entities.get(0).setX(playerSpawnX);
            entities.get(0).setY(playerSpawnY);
        }
    }

    public void resetTiles(){
        for(int i = 0; i < tileMap.length; i++){
            for(int k = 0; k < tileMap[i].length; k++){
                tileMap[i][k].reset();
            }
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
            // printSimpleString(selectedObjString, getWidth(), -300, 20, g);
            g.drawString(selectedObjString, 10, 20);
            if(!playerSpawnPlaced){
                printSimpleString("Spawn location not specified.", getWidth(), -160, 570, g);
            }
            for (int i = 1; i < entities.size(); i++) {
                getEntity(i).paint(g);
            }
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


/*
            for (int i = 0; i < entities.get(0).getTileMap().length; i++) {
                for (int k = 0; k < entities.get(0).getTileMap()[i].length; k++) {
                    entities.get(0).getTile(i,k).paint(g);
                }
            }
*/


        }
        if(GameStats.isDeath()){
            setBackground(Color.BLACK);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Lucida Console", Font.BOLD, 24));
            printSimpleString("Score: " + GameStats.getScore(), getWidth(), -300, 20, g);
            printSimpleString("X " + GameStats.getLives(), getWidth(), 20, 300, g);
            g.setColor(Color.ORANGE);
            g.fillRect(360, 280, 20, 20);
        }
        if(GameStats.isGameOver()){
            setBackground(Color.BLACK);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Lucida Console", Font.BOLD, 24));
            printSimpleString("Score: " + GameStats.getScore(), getWidth(), -300, 20, g);
            printSimpleString("GAME OVER", getWidth(), 0, 300, g);
        }

        if(GameStats.isLevelEnd()){
            setBackground(Color.BLACK);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Lucida Console", Font.BOLD, 64));
            printSimpleString("COURSE CLEAR!", getWidth(), 0, 150, g);

            g.setFont(new Font("Lucida Console", Font.BOLD, 48));
            if(GameStats.getTextFlicker()) {
                printSimpleString("FINAL SCORE: " + GameStats.getScore(), getWidth(), 0, 350, g);
            }

            g.setFont(new Font("Lucida Console", Font.BOLD, 32));
            printSimpleString("PRESS SPACE TO PLAY AGAIN!", getWidth(), 0, 550, g);
        }

        if(GameStats.isTitle()){
            setBackground(Color.BLACK);
            g.setColor(Color.ORANGE);
            g.fillRect(50,20,700,300);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Lucida Console", Font.BOLD, 64));
            printSimpleString("SUPER ORANGE", getWidth(), 0, 140, g);
            printSimpleString("SQUARE MAKER", getWidth(), 0, 204, g);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Lucida Console", Font.BOLD, 48));
            if(GameStats.getTextFlicker()){
                printSimpleString("PRESS SPACE TO BEGIN!", getWidth(), 0, 420, g);
            }
            g.setFont(new Font("Lucida Console", Font.BOLD, 24));
            printSimpleString("By Ethan Thompson, 2017", getWidth(), -200, 570, g);
        }

    }

    public int getNextIndex(){
        return entities.size();
    }

    public void loadTiles(int offset){
        for(int i = 0; i < HEIGHTINTILES; i++){
            for(int k = 0; k < WIDTHINTILES; k++){
                //loadedTiles[i][k] = getTile(i, (offset / 20) + k).cloneTile();
                getTile(i, (offset / 20) + k).setPos(new TilePos(k, i, true));
                getTile(i, (offset / 20) + k).offsetPos(offset);
            }

        }
    }


    @Override
    public void actionPerformed(ActionEvent e){
        gameTime++;

        if(gameTime % 60 == 0)
            GameStats.toggleTextFlicker();

   if(GameStats.isEditor()) {
       if (aPressed) {
           scroll(-10);

           if (cameraOffset < 0) {
               scrollTo(0);
           }
       }

       if (dPressed) {
           scroll(10);

       }

       if (click) {
           boolean entityFound = false;
           if(eraseMode) {
               for(int i = 1; i < entities.size(); i++){
                   if(getEntity(i).getBounds().contains(cursorX, cursorY)){
                    removeEntity(i);
                    i--;
                    entityFound = true;
                    entCount--;
                   }
                   if(entityFound)
                       break;
               }
           }
           if(!entityFound) {
               if (selectedObject instanceof Tile) {
                   setTile(cursorX, cursorY, cameraOffset, (Tile) selectedObject);
               } else if (!entityPlaced) {
                   entities.add(selectedObject.clone(cursorY, cursorX));
                   entityPlaced = true;
                   entCount++;
               }
           }
       }

   }

   if(GameStats.isPlay()){
       for(gameLoopControl = 0; gameLoopControl < getNextIndex(); gameLoopControl++){
           if(Math.abs(entities.get(gameLoopControl).getX() - (getWidth() / 2)) < SIMULATIONRADIUS)
           entities.get(gameLoopControl).update(gameLoopControl);
       }
   }

   if(GameStats.isDeath()|| GameStats.isGameOver()){
       if(gameTime - GameStats.getDeathStartTime() > 120){
           startGame();
           (entities.get(0)).setDead(false);

       }
   }

   if(GameStats.isLevelEnd()){
       if(spacePressed) {
           entities.get(0).setDead(false);
           scrollTo(0);
           resetEntities();
           resetTiles();
           GameStats.resetCoins();
           GameStats.resetLives();
           GameStats.resetScore();
           GameStats.setEditor();
       }

   }

        if(GameStats.isTitle()){
            if(spacePressed) {
                GameStats.setEditor();
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
        playerSpawnOffset = offset;
        playerSpawnX = x;
        playerSpawnY = y - entities.get(0).getHeight();

    }
    tileMap[y / 20][(int) Math.floor(newX / 20.0)] = selected.cloneTile();
    tileMap[y / 20][(int) Math.floor(newX / 20.0)].setPos(new TilePos(newX, y, false));
}
    }

    public void updateSelectedTile(){
        eraseMode = false;
        switch(selectedObjString){
            case "Erase":
                selectedObject = new AirTile(Color.BLUE, 0, 0, 20, 20, this, 0);
                eraseMode = true;
                break;
            case "Ground Tile":
                selectedObject = new GroundTile(Color.GREEN, 0, 0, 20, 20, this, 0);
                break;
            case "Lava Tile":
                selectedObject = new LavaTile(Color.RED, 0, 0, 20, 20, this, 0);
                break;
            case "Coin":
                selectedObject = new CoinTile(Color.YELLOW, 0, 0, 20, 20, this, 0, true);
                break;
            case "Goal":
                selectedObject = new GoalTile(new Color(255,0,255), 0, 0, 20, 20, this, 0);
                break;
            case "Player Spawn":
                selectedObject = new PlayerStartTile(Color.ORANGE, 0, 0, 20, 20, this, 0);
                break;
            case "Goomba":
                selectedObject = new Goomba(new Color(139,69,19),0,0, 20, 20, this, 0);
                break;
            case "Koopa":
                selectedObject = new Koopa(Color.GREEN,0,0, 20, 20, this, 0);
                break;
            case "? Block (Mushroom)":
                selectedObject = new QuestionTile(Color.YELLOW, 0, 0, 20, 20, this, 0, true, true);
                break;
            case "? Block (Coin)":
                selectedObject = new QuestionTile(Color.YELLOW, 0, 0, 20, 20, this, 0, true, false);
                break;
            default:
                selectedObject = new GroundTile(Color.GREEN, 0, 0, 20, 20, this, 0);
        }
    }

    private void printSimpleString(String s, int width, int xPos, int yPos, Graphics g2d){
        //Contrary to the name, this actually makes things harder...
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
        if(entities.get(index) == null)
            return new AirTile(Color.BLUE, 0, 0, 20, 20, this, 0);
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
            entities.get(i).setX(entities.get(i).getX() - a);
        }
    }

    public void scrollTo(int a){
        scroll(a - cameraOffset);
    }

    public void purgeEntities(){
        while(entities.size() > entCount){
            removeEntity(entCount);
        }
    }



}
