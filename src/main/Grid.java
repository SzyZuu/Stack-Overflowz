package main;

import java.awt.*;

//temp for testing, might become permanent// currently doesnt work
//todo fix my spaghetti code
public class Grid implements Runnable {

    int tileCornerTLy; // top left
    int tileCornerTLx; // top left
    int tileCornerBRy; // bottom right
    int tileCornerBRx; // bottom right

    GamePanel gp;

    Thread gridThread;

    public Grid(GamePanel gamp){
        this.gp = gamp;
    }

    public Point currentNearestGrid(){

        Point gridPoint = new Point();

        for (int i = 16; i >= 1 ; i--) {
            tileCornerTLx = gp.screenWidth - gp.tileSize * i;
            tileCornerBRx = tileCornerTLx + gp.tileSize;
            for (int j = 12; j >= 1; j--){
                tileCornerTLy = gp.screenHeight - gp.tileSize * j;
                tileCornerBRy = tileCornerTLy + gp.tileSize;
                /*
                System.out.println("Tlx " + tileCornerTLx);
                System.out.println("BRx " + tileCornerBRx);
                System.out.println("TLy " + tileCornerTLy);
                System.out.println("BRy " + tileCornerBRy);
                */


                if(gp.getMousePosition().x >= tileCornerTLx && gp.getMousePosition().x <= tileCornerBRx && gp.getMousePosition().y >= tileCornerTLy && gp.getMousePosition().y <= tileCornerBRy){
                    gridPoint.x= i;
                    gridPoint.y = j;

                    return gridPoint;
                }
            }
        }
        if(gridPoint == null){
            return gridPoint;
        }
        else{
            return null;
        }
    }
    public Point translate(){
        Point transPoint = new Point();

        transPoint.x = gp.maxScreenColumns - currentNearestGrid().x;
        transPoint.y = gp.maxScreenRows - currentNearestGrid().y;


        return transPoint;
    }

    public void startGridThread(){
        gridThread = new Thread(this);
        gridThread.start();
    }

    @Override
    public void run() {
        while(gridThread != null){
            currentNearestGrid();
            System.out.println(translate());
        }
    }
}
