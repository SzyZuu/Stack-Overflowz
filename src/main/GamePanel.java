package main;

import entity.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
    Grid grid = new Grid(this);

    Thread gameThread;
    public boolean isGlobalPickedUp;
    public boolean repaintNeeded = true;
    ArrayList<Card> cardList = new ArrayList<>();
    Card card1 = new Card(this, keyH, mouseH, grid);
    Card card2 = new Card(this, keyH, mouseH, grid);

    //MainThread mainThread = new MainThread( this, grid);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);       // improves rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addMouseListener(mouseH);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {                             // game loop

        double drawInterval = (double) 1000000000 / FPS ; // 0.0166 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        cardList.add(card1);
        cardList.add(card2);

        card2.pos.x += 128;         //move second card to the side so no overlap
        card1.colorCard();
        card2.colorCard();
        grid.gridArray[0][0].add(card1);
        grid.gridArray[2][0].add(card2);

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




    public void update(){
        card1.update();         //update both cards, later needs to be changed to call update on EACH card automatically
        card2.update();
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;      //ensures that the graphics are 2d

        sequencedDraw(g2);
        for (Card card : cardList) {
            card.pickedUpDraw(g2);
        }

        //g2.drawString("fortnite", screenWidth /2 - 10, screenHeight/ 2 - 3);      // secret :D

        g2.dispose();
    }
}
