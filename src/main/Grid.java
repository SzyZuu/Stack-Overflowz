package main;

import entity.Card;

import java.awt.*;
import java.util.Stack;

public class Grid /*implements Runnable*/ {

    int tileCornerTLy; // top left
    int tileCornerTLx; // top left
    int tileCornerBRy; // bottom right
    int tileCornerBRx; // bottom right

    GamePanel gp;

    Thread gridThread;

    public Grid(GamePanel gameP){
        this.gp = gameP;

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

    public Point independentTranslate(Card c) {
        Point iTrans = new Point();
        int x = Math.round((float) c.pos.x / gp.tileSize);
        int y = Math.round((float) c.pos.y / gp.tileSize);

        // Check if the indices are within the valid range
        if (x >= 0 && x < 16 && y >= 0 && y < 12) {
            iTrans.x = x;
            iTrans.y = y;
        } else {
            iTrans = null; // null or default values
        }

        return iTrans;
    }

    public void setInitialGridArray(Card c){
        gridArray[independentTranslate(c).x][independentTranslate(c).y].push(c) ;
    }

    public void setGridArray(Card card){
        gridArray[translate().x][translate().y].push(card) ;
    }

    public void clearGridArraySlot(Card c) {
        int x = translate().x;
        int y = translate().y;

        if (x >= 0 && x < 16 && y >= 0 && y < 12) {
            Stack<Card> stack = gridArray[x][y];
            if (!stack.empty() && c.isAtTop()) {
                stack.pop();
            }
        }
    }

    public void ghostCardPrevention(){                               //smtIsNotVeryYes
        for (int i = 0; i < gp.maxScreenColumns; i++) {
            for (int j = 0; j < gp.maxScreenRows; j++) {
                for(int l = 0; l < gp.cardList.size(); l++){
                    if (!gridArray[i][j].isEmpty() && gridArray[i][j].search(gp.cardList.get(l)) != -1) {
                        if(i != gp.cardList.get(l).getStartingGridPosX() && j != gp.cardList.get(l).getStartingGridPosY()){
                            //if(gridArray[gp.cardList.get(l).getStartingGridPosX()][gp.cardList.get(l).getStartingGridPosY()].search(gp.cardList.get(l)) != -1){
                                gridArray[gp.cardList.get(l).getStartingGridPosX()][gp.cardList.get(l).getStartingGridPosY()].remove( gp.cardList.get(l));
                            //}
                        }
                    }
                }
            }
        }
    }

    public void currentUsedStacks(){
        for(int i = 0; i < gp.maxScreenColumns; i++){
            for(int j = 0; j < gp.maxScreenRows; j++){
                if(!gridArray[i][j].isEmpty()){
                    System.out.println("x" + i);
                    System.out.println("y" + j);
                    System.out.println(gridArray[i][j].peek());
                }
            }
        }
    }

    /*public void startGridThread(){
        gridThread = new Thread(this);)
        gridThread.start();
    }

    @Override
    public void run() {
        while(gridThread != null){
            //currentNearestGrid();
            //translate();
        }
    }*/
}
