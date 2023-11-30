package main;

import javax.swing.*;
import java.awt.*;




public class GamePanel extends JPanel implements Runnable{

    // Screenz Settingz
    final int baseTileSize = 32; // Tile site 32x32
    final int scale = 2; // Scaling (tiles appear larger)

    final int tileSize = baseTileSize * scale; // size of tile on screen 64x64

    final int maxScreenColums = 16; // Grid Size Horizontal
    final int maxScreenRows = 12;   //Grid Size Vertical

    final int screenWidth = maxScreenColums * tileSize; // 1024 Px
    final int screenHeight = maxScreenRows * tileSize;  // 768 Px

    //FPS
    int FPS = 60;


    Thread gameThread;

    KeyHandler keyH = new KeyHandler();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);       // improves rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
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
            System.out.println("current time" + currentTime);


            //System.out.println("its on, trust me"); // confirmation that the game is running, for safety purposes
            // 1 UPDATE: update information such as character positions
            update();
            // 2 DRAW: draw the screen with the updated screen information
            repaint();


        }
    }
    public void update(){

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;      //ensures that the graphics are 2d

        g2.setColor(Color.white);

        g2.fillRect(100, 100, tileSize, tileSize);

        //g2.drawString("fortnite", screenWidth /2 - 10, screenHeight/ 2 - 3);      // secret :D

        g2.dispose();
    }
}
