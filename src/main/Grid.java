package main;

import entity.Card;

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

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 12; j++) {
                gridArray[i][j] = new Stack<>();
            }
        }
    }

    public Stack<Card>[][] gridArray = new Stack[16][12];

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
            return transPoint;

        }
        else {
            return null;
        }
    }

    public Point independentTranslate(Card c){
        Point iTrans = new Point();
        iTrans.x = gp.maxScreenColumns - Math.round(c.pos.x / gp.tileSize);
        iTrans.y = gp.maxScreenRows - Math.round(c.pos.y / gp.tileSize);
        return iTrans;
    }
    public void setInitialGridArray(Card c){
        gridArray[independentTranslate(c).x][independentTranslate(c).y].push(c) ;
    }

    public void setGridArray(Card card){
        gridArray[translate().x][translate().y].push(card) ;
    }

    public void clearGridArraySlot() {
        int x = translate().x;
        int y = translate().y;

        if (x >= 0 && x < 16 && y >= 0 && y < 12) {
            Stack<Card> stack = gridArray[x][y];
            if (!stack.empty()) {
                stack.pop();
            }
        }
    }

    public void startGridThread(){
        gridThread = new Thread(this);
        gridThread.start();
    }

    @Override
    public void run() {
        while(gridThread != null){
            //currentNearestGrid();
            //translate();
        }
    }
}
