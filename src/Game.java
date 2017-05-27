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
    int cursorX, cursorY, cameraOffset, gameLoopControl, playerSpawnX, playerSpawnY, playerSpawnOffset, entCount;
    static long gameTime;
    final int HEIGHTINTILES = 30;
    final int WIDTHINTILES = 40;
    boolean click, dPressed, aPressed, pPressed, fPressed, playerSpawnPlaced, wPressed, spacePressed, entityPlaced, eraseMode;
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
        spriteSheet = new BufferedImage[8];
        cameraOffset = 0;
        gameTime = 0;
        playerSpawnPlaced = false;
        entityPlaced = false;
        entCount = 1;


        //Used to prevent a null pointer exception when rendering the tilemap
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

                if (e.getKeyCode() == KeyEvent.VK_F) {
                    fPressed = true;
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
                    selectedObjString = "Brick Block";
                    updateSelectedTile();
                }


                if(e.getKeyCode() == KeyEvent.VK_E && GameStats.isEditor()){
                    selectedObjString = "Erase";
                    updateSelectedTile();
                }

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spacePressed = true;
                }

                if (e.getKeyCode() == KeyEvent.VK_P) {
                    //Switches between playing and editing
                        if (GameStats.isEditor()) {
                            startGame();
                        } else if(GameStats.isPlay()) {
                            entities.get(0).setDead(false);
                            GameStats.resetScore();
                            GameStats.resetLives();
                            GameStats.resetCoins();
                            GameStats.resetFireBallCount();
                            resetTiles();
                            purgeEntities();
                            resetEntities();
                            GameStats.setEditor();
                        }
                     }

                if (e.getKeyCode() == KeyEvent.VK_C) {
                    if(GameStats.isTitle()){
                        GameStats.setControls();
                    }
                    else if(GameStats.isControls()){
                        GameStats.setTitle();
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

                if (e.getKeyCode() == KeyEvent.VK_F) {
                    fPressed = false;
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

            @Override
            public void mouseDragged(MouseEvent e){
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

    public int getSimulationRadius(){
        return SIMULATIONRADIUS;
    }


    public boolean isFPressed(){
        return fPressed;
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

        try {
            spriteSheet[5] = ImageIO.read(new File("resource/fireFlower.png"));
        }
        catch(java.io.IOException e){
            System.out.println("Image not found!");
        }

        try {
            spriteSheet[6] = ImageIO.read(new File("resource/brickBlock.png"));
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
        //Resets the game to its default state
        if(playerSpawnPlaced) {
            resetTiles();
            GameStats.setPlay();
            cameraOffset = playerSpawnOffset;
            GameStats.resetFireBallCount();
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
            //Only paints the tiles within the bounds of the game window
            for (int i = 0; i < HEIGHTINTILES; i++) {
                for (int k = 0; k < WIDTHINTILES; k++) {
                    getTile(i, (cameraOffset / 20) + k).paint(g);
                }
            }
        }
        if(GameStats.isEditor()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Lucida Console", Font.BOLD, 24));
            g.drawString(selectedObjString, 10, 20);
            if(!playerSpawnPlaced){
                printSimpleString("Spawn location not specified.", getWidth(), -160, 570, g);
            }
            else {
                printSimpleString("Press P to start!", getWidth(), -250, 570, g);
            }
            //Paints all entities except for the player
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
            if(GameStats.getTextFlicker()) {
                printSimpleString("GAME OVER", getWidth(), 0, 300, g);
                printSimpleString("PRESS SPACE TO CONTINUE", getWidth(), 0, 320, g);
            }
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
            printSimpleString("Press C for controls.", getWidth(), 230, 570, g);
        }

        if(GameStats.isControls()){
            setBackground(Color.BLACK);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Lucida Console", Font.BOLD, 64));
            printSimpleString("CONTROLS", getWidth(), 0, 100, g);
            g.setFont(new Font("Lucida Console", Font.BOLD, 24));
            printSimpleString("0-9: Select Block", getWidth(), 0, 150, g);
            printSimpleString("E: Erase", getWidth(), 0, 210, g);
            printSimpleString("P: Switch Between Playing and Editing", getWidth(), 0, 270, g);
            printSimpleString("W, A, and D: Move", getWidth(), 0, 330, g);
            printSimpleString("F: Shoot Fireball", getWidth(), 0, 390, g);
            printSimpleString("Left Click: Place Tile", getWidth(), 0, 450, g);
            printSimpleString("Press C to return to title.", getWidth(), 0, 570, g);
            g.setFont(new Font("Lucida Console", Font.BOLD, 16));
            printSimpleString("You can also drag the mouse around to place tiles quickly!", getWidth(), 0, 510, g);

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
               //Erases either a tile, or an entity if one can be found at the cursor position.
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
                   //Only one entity can be placed at a time, hence the "entityPlaced" variable.
                   entityPlaced = true;
                   entCount++;
               }
           }
       }

   }

   if(GameStats.isPlay()){
       for(gameLoopControl = 0; gameLoopControl < getNextIndex(); gameLoopControl++){
           if(Math.abs(entities.get(gameLoopControl).getX() - (getWidth() / 2)) < SIMULATIONRADIUS && !entities.get(gameLoopControl).isActive()){
               //Activates entities if they get within a certain radius of the center of the screen
               entities.get(gameLoopControl).activate();
           }

           if(entities.get(gameLoopControl).isActive()) {
               entities.get(gameLoopControl).update(gameLoopControl);
           }
       }
   }

   if(GameStats.isDeath() || GameStats.isGameOver()){
       if((GameStats.isDeath() && gameTime - GameStats.getDeathStartTime() > 120) || (GameStats.isGameOver() && spacePressed)){
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

        //Finds the absolute X coordinate
        int newX = x + offset;

if(!(playerSpawnPlaced && selected instanceof PlayerStartTile)) {
    if(tileMap[y / 20][(int) Math.floor(newX / 20.0)] instanceof PlayerStartTile){
        playerSpawnPlaced = false;
    }
    if(selected instanceof PlayerStartTile){
        //Sets the player spawn location
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
        //Called whenever a key from 0-9 is pressed in edit mode.
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
            case "Brick Block":
                selectedObject = new BrickTile(Color.YELLOW, 0, 0, 20, 20, this, 0, true);
                break;
            default:
                selectedObject = new GroundTile(Color.GREEN, 0, 0, 20, 20, this, 0);
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

    public int getPlayerSpawnX(){
        return playerSpawnX;
    }

    public void addEntity(Entity ent){
        entities.add(ent);
    }

    public void removeEntity(int index){
        entities.remove(index);
        //Decrements the index field of all entities beyond the index of the entity being deleted to keep the actual index and the stored index from becoming out of sync.
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



    public void scroll(int a){
        //Scrolls everything by a certain number of pixels along the X axis
        cameraOffset += a;
        for(int i = 1; i < entities.size(); i++){
            entities.get(i).setX(entities.get(i).getX() - a);
        }
    }

    public void scrollTo(int a){
        scroll(a - cameraOffset);
    }

    public void purgeEntities(){
        //Gets rid of particles, fireballs, e.t.c
        while(entities.size() > entCount){
            removeEntity(entCount);
        }
    }



}
