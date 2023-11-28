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

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);       // improves rendering performance

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

    }
}
