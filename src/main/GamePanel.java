package main;

import entity.Card;

import javax.swing.*;
import java.awt.*;




public class GamePanel extends JPanel implements Runnable{

    // Screenz Settingz
    final int baseTileSize = 32; // Tile size 32x32
    final int scale = 2; // Scaling (tiles appear larger)

    public final int tileSize = baseTileSize * scale; // size of tile on screen 64x64

    final int maxScreenColumns = 16; // Grid Size Horizontal
    final int maxScreenRows = 12;   //Grid Size Vertical

    final int screenWidth = maxScreenColumns * tileSize; // 1024 Px
    final int screenHeight = maxScreenRows * tileSize;  // 768 Px

    //FPS
    int FPS = 60;
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();
    Grid grid = new Grid(this);

    Thread gameThread;

    Card card = new Card(this, keyH, mouseH, grid);



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

        double drawInterval = 1000000000 / FPS ; // 0.0166 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;



        while (gameThread != null){

            long currentTime = System.nanoTime();
            //System.out.println("current time" + currentTime);


            //System.out.println("its on, trust me"); // confirmation that the game is running, for safety purposes
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
    public void update(){
        card.update();
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;      //ensures that the graphics are 2d

        card.draw(g2);

        //g2.drawString("fortnite", screenWidth /2 - 10, screenHeight/ 2 - 3);      // secret :D

        g2.dispose();
    }
}
