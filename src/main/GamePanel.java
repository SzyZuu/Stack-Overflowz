package main;

import entity.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class GamePanel extends JPanel implements Runnable{

    // Screen Settings
    final int baseTileSize = 32; // Tile size 32x32
    final int scale = 2; // Scaling (tiles appear larger)

    public final int tileSize = baseTileSize * scale; // size of tile on screen 64x64

    public final int maxScreenColumns = 16; // Grid Size Horizontal
    public final int maxScreenRows = 12;   //Grid Size Vertical

    final int screenWidth = maxScreenColumns * tileSize; // 1024 Px
    final int screenHeight = maxScreenRows * tileSize;  // 768 Px
    //FPS
    int FPS = 60;
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();
    Recipes recipes = new Recipes();
    CraftingHandler craftingH = new CraftingHandler(recipes, this);
    Grid grid = new Grid(this);
    private List<CraftingListener> craftingListeners = new ArrayList<CraftingListener>();
    Thread gameThread;
    public boolean isGlobalPickedUp = false;
    public boolean repaintNeeded = true;
    public boolean cardHasBeenStacked = false;
    public boolean stackBiggerThen1 = false;
    int safetyDefault = -1;
    int checkedGridSlotX = safetyDefault;      //-1 as default empty
    int checkedGridSlotY = safetyDefault;
    int returnSlotX = 0;
    int returnSlotY = 0;
    int spawningSlotX = 0;
    int spawningSlotY = 0;

    int sellingSlotX = 15; //16-1
    int sellingSlotY = 0;
    int coins = 0;
    Card heldForCraft1;
    Card heldForCraft2;

    public ArrayList<Card> cardList = new ArrayList<Card>();
    Card card1 = new Card(this, keyH, mouseH, grid, 1);
    Card card2 = new Card(this, keyH, mouseH, grid, 2);

    //MainThread mainThread = new MainThread( this, grid);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);       // improves rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addMouseListener(mouseH);
        this.addListener(craftingH);
    }

    public void addListener(CraftingListener addCl){
        craftingListeners.add(addCl);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void startingCards(){
        cardList.add(card1);
        cardList.add(card2);

        grid.gridArray[1][1].add(card1);
        grid.gridArray[3][2].add(card2);

        for (Card card : cardList) {
            card.colorCard();
            card.setDefaultValues();
            card.initialGridSnap();
            card.saveStartingPos();
            System.out.println(card.pos);
        }
    }
    public void spawnNewCard(int id){

        Card cardZ = new Card(this, keyH, mouseH, grid, id);
        cardList.add(cardZ);
        grid.gridArray[0][0].add(cardZ);
        cardZ.colorCard();
        cardZ.setDefaultValues();
        cardZ.initialGridSnap();
        cardZ.saveStartingPos();
        System.out.println(cardZ.pos);
    }

    @Override
    public void run() {                             // game loop

        double drawInterval = (double) 1000000000 / FPS ; // 0.0166 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        recipes.readRecipes();

        while (gameThread != null){
            // 1 UPDATE: update information such as character positions
            update();
            // 2 DRAW: draw the screen with the updated screen information
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0){                         //prevent unnecessary wait if too slow
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void sequencedDraw(Graphics2D g2) {
        if (repaintNeeded) {
            for (Stack<Card>[] row : grid.gridArray) {
                for (Stack<Card> stack : row) {
                    if (!stack.isEmpty()) {
                        stack.peek().draw(g2);
                    }
                }
            }
            //repaintNeeded = false;
        }
    }
    public void stackCheck(){
        if(cardHasBeenStacked && stackBiggerThen1){

            if(checkedGridSlotX != -1 && checkedGridSlotY != -1) {
                grabCards();
            }
            cardHasBeenStacked = false;
            
        }
        else cardHasBeenStacked = false;
    }

    public void sellStack(){
        if(!grid.gridArray[sellingSlotX][sellingSlotY].empty()){
            grid.ghostCardPrevention();
            cardList.remove(grid.gridArray[sellingSlotX][sellingSlotY].pop());
             coins++;
        }
    }

    public void stackSizeChecker(int gridPosX, int gridPosY){
       if (grid.gridArray[gridPosX][gridPosY].size() > 1 && !isSpawningTile(gridPosX, gridPosY)) {
           checkedGridSlotX = gridPosX;
           checkedGridSlotY = gridPosY;
           stackBiggerThen1 = true;
       }
       else stackBiggerThen1 = false;
   }
   public boolean isSpawningTile( int gridPosX, int gridPosY){

        if(gridPosX != spawningSlotX && gridPosY != spawningSlotY || gridPosX == spawningSlotX && gridPosY != spawningSlotY || gridPosX != spawningSlotX && gridPosY == spawningSlotY){
            return false;
        }
        else{
            return true;
        }
   }

    public boolean isSellingTile( int gridPointX, int gridPointY){  // nolonger in use, if it'll be kept is a question for the future

        if(gridPointX != sellingSlotX && gridPointY != sellingSlotY || gridPointX == sellingSlotX && gridPointY != sellingSlotY || gridPointX != sellingSlotX && gridPointY == sellingSlotY){
            return false;
        }
        else{
            return true;
        }
    }

    public void grabCards(){
        if(!grid.gridArray[checkedGridSlotX][checkedGridSlotY].empty() && grid.gridArray[checkedGridSlotX][checkedGridSlotY].size() >1 && !isSpawningTile(checkedGridSlotX, checkedGridSlotY) ){ //&& !isSellingTile(checkedGridSlotX, checkedGridSlotY)
            heldForCraft1 = grid.gridArray[checkedGridSlotX][checkedGridSlotY].pop();
            heldForCraft2 = grid.gridArray[checkedGridSlotX][checkedGridSlotY].pop();
            returnSlotX = checkedGridSlotX;
            returnSlotY = checkedGridSlotY;
            checkAndCraft(heldForCraft1, heldForCraft2);
        }

        checkedGridSlotX= safetyDefault;
        checkedGridSlotY = safetyDefault;
    }

    public void returnCards(Card cx) {                                                  //formerly no valid recipes

        grid.gridArray[returnSlotX][returnSlotY].push(cx);

        if (heldForCraft1 == cx) {
            heldForCraft1 = null;
        }
        else if (heldForCraft2 == cx) {
            heldForCraft2 = null;
        }
    }

    public void deleteCard(Card card) {     // not in use yet

        cardList.remove(card); // if this is possible like with stacks, otherwise for loop or

        if (heldForCraft1 == card) {
            heldForCraft1 = null;
        }
        else if (heldForCraft2 == card) {
            heldForCraft2 = null;
        }
    }

    public void checkAndCraft(Card c1, Card c2){
        for(CraftingListener cl : craftingListeners)
            cl.doCraft(c1, c2);
    }

    public void update(){
        grid.ghostCardPrevention();
        for (Card card : cardList) {
            card.update();
        }
        stackCheck();
        sellStack();
    }
    public void tileMarkings(Graphics2D g2){
        g2.setColor(Color.RED);
        g2.drawRect(sellingSlotX *tileSize, sellingSlotY *tileSize, tileSize, tileSize);
        g2.setColor(Color.GRAY);
        g2.drawRect(spawningSlotX *tileSize, spawningSlotY *tileSize, tileSize, tileSize);

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;      //ensures that the graphics are 2d

        tileMarkings(g2);

        sequencedDraw(g2);                  //draw only first card in the stack
        for (Card card : cardList) {
            card.pickedUpDraw(g2);
        }

        //g2.drawString("fortnite", screenWidth /2 - 10, screenHeight/ 2 - 3);      // secret :D
        g2.setColor(Color.white);
        g2.drawString("Coins " + coins, screenWidth /2 - 30,10); //y 10 appears to be the bare mimimum for the text to be readable
        g2.dispose();
    }
}
