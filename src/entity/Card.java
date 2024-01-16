package entity;

import main.GamePanel;
import main.Grid;
import main.KeyHandler;
import main.MouseHandler;
import java.awt.*;

public class Card extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    MouseHandler mouseH;
    Grid grid;

    boolean isPickedUp = false;
    //int offset = 32; //Math.round(gp.tileSize/2);
    float offsetMath;
    int offset;

    int currentGridPosX;
    int currentGridPosY;
    int startingGridPosX;
    int startingGridPosY;
    public int value;
    public int id;
    Color color;

    public Card(GamePanel gp, KeyHandler keyH, MouseHandler mouseH, Grid g, int CardId){
        this.gp = gp;
        this.keyH = keyH;
        this.mouseH = mouseH;
        this. grid = g;
        this.id = CardId;
        offsetMath = (float) gp.tileSize / 2;
        offset = Math.round(offsetMath);
    }

    public void pickUp(){
        if(!gp.isGlobalPickedUp && !shitsFucked()){
            if (mouseH.pressed && isSelected()) {
                isPickedUp = true;
                gp.isGlobalPickedUp = true;
                grid.clearGridArraySlot(this);
                gp.repaintNeeded = true;
            }
        }

        if(isPickedUp){
            pos.x = gp.getMousePosition().x - offset;
            pos.y = gp.getMousePosition().y - offset;

            gp.repaintNeeded = true;
            if(!mouseH.pressed){
                isPickedUp = false;
                gp.isGlobalPickedUp = false;
                gridSnap();
                //gp.repaintNeeded = true;
                grid.currentUsedStacks();
            }
        }
    }
    public boolean shitsFucked(){ //just for testing
        return pos == null;
    }

    public void gridSnap(){
        currentGridPosX = grid.translate().x;
        currentGridPosY = grid.translate().y;
        pos.x = currentGridPosX * gp.tileSize;
        pos.y = currentGridPosY * gp.tileSize;
        grid.setGridArray(this);

        gp.cardHasBeenStacked = true;
        gp.stackSizeChecker(currentGridPosX, currentGridPosY);
    }
    public void initialGridSnap(){
        pos.x = grid.independentTranslate(this).x * gp.tileSize;
        pos.y = grid.independentTranslate(this).y * gp.tileSize;
        grid.setInitialGridArray(this);
    }

    public boolean isSelected(){
            return gp.getMousePosition().x >= pos.x && gp.getMousePosition().y >= pos.y && gp.getMousePosition().x <= pos.x + gp.tileSize && gp.getMousePosition().y <= pos.y + gp.tileSize && isAtTop();
    }

    public boolean isAtTop(){
        if(!grid.gridArray[currentGridPosX][currentGridPosY].isEmpty()){
            return this == grid.gridArray[currentGridPosX][currentGridPosY].peek();
        }
        return false;
    }

    public void setDefaultValues() {
        for (int i = 0; i < gp.maxScreenColumns; i++) {
            for (int j = 0; j < gp.maxScreenRows; j++) {
                if (!grid.gridArray[i][j].isEmpty() && grid.gridArray[i][j].search(this) != -1) {
                    pos.x = i * gp.tileSize;
                    pos.y = j * gp.tileSize;

                    currentGridPosX = i;
                    currentGridPosY = j;

                }
            }
        }
    }

    public void saveStartingPos(){
        this.startingGridPosX = this.currentGridPosX;
        this.startingGridPosY = this.currentGridPosY;
    }

    public int getStartingGridPosX() {
        return startingGridPosX;
    }

    public int getStartingGridPosY() {
        return startingGridPosY;
    }

    public void colorCard(){
        switch(id){
            case 1:                     //Villager
                color = Color.WHITE;
                value = 0; // selling slaves is bad, using isnt
                break;
            case 2:                     //Wood
                color = new Color(88, 57, 39);
                value = 1;
                break;
            case 3:                     //Stone
                color = Color.GRAY;
                value = 1;
                break;
            case 4:                     //Tree
                color = new Color(34, 139, 34);
                value = 0;
                break;
            case 5:                     //Boulder
                color = Color.DARK_GRAY;
                value = 0;
                break;
            case 6:                     //Plank
                color = new Color(222, 184, 135);
                value = 2;
                break;
            case 7:                     //Brick
                color = new Color(165, 42, 42);
                value = 2;
                break;
            default:
                color = Color.MAGENTA;  // mega-manta
                value = 69;
                break;
        }
    }
    public void update(){
        pickUp();
    }
    public void draw(Graphics2D g2){
        g2.setColor(color);

        g2.fillRect(pos.x, pos.y , gp.tileSize, gp.tileSize);
    }

    public void pickedUpDraw(Graphics2D g2){

        if(isPickedUp){
            g2.setColor(color);

            g2.fillRect(pos.x, pos.y , gp.tileSize, gp.tileSize);
        }
    }
}
