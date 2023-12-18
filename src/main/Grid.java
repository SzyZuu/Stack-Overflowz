package main;

import java.awt.*;
import java.util.Stack;

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
    Object[][] gridArray = new Object[16][12];
    Stack<Object> stk = new Stack<>();
    public Point currentNearestGrid(){

        Point gridPoint = new Point();

        for (int i = 16; i >= 1 ; i--) {
            tileCornerTLx = gp.screenWidth - gp.tileSize * i;
            tileCornerBRx = tileCornerTLx + gp.tileSize;
            for (int j = 12; j >= 1; j--){
                tileCornerTLy = gp.screenHeight - gp.tileSize * j;
                tileCornerBRy = tileCornerTLy + gp.tileSize;
                if(gp.getMousePosition().x >= tileCornerTLx && gp.getMousePosition().x <= tileCornerBRx && gp.getMousePosition().y >= tileCornerTLy && gp.getMousePosition().y <= tileCornerBRy){
                    gridPoint.x= i;
                    gridPoint.y = j;
                    return gridPoint;
                }
            }
        }
            return gridPoint;
    }
    public Point translate(){
        Point transPoint = new Point();
        if(currentNearestGrid() != null ) {
            transPoint.x = gp.maxScreenColumns - currentNearestGrid().x;
            transPoint.y = gp.maxScreenRows - currentNearestGrid().y;
            System.out.println(gridArray[transPoint.x][transPoint.y]);
            return transPoint;

        }
        else {
            return null;
        }

    }
    public void setGridArray(){
        gridArray[translate().x][translate().y] = true;
    }
    public void clearGridArraySlot(){
        gridArray[translate().x][translate().y] = false;
    }

    public void startGridThread(){
        gridThread = new Thread(this);
        gridThread.start();
    }

    @Override
    public void run() {
        while(gridThread != null){
            currentNearestGrid();
            translate();

        }
    }
}
