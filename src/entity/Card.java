package entity;

import main.GamePanel;
import main.Grid;
import main.KeyHandler;
import main.MouseHandler;
import java.awt.*;
import java.util.Random;

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
    Color color;

    public Card(GamePanel gp, KeyHandler keyH, MouseHandler mouseH, Grid g){
        this.gp = gp;
        this.keyH = keyH;
        this.mouseH = mouseH;
        this. grid = g;
        setDefaultValues();
        initialGridSnap();
        offsetMath = (float) gp.tileSize / 2;
        offset = Math.round(offsetMath);
    }

    public void pickUp(){
        if(!gp.isGlobalPickedUp){
            if (mouseH.pressed && isSelected()) {
                //grid.gridArray[grid.independentTranslate(this).x][grid.independentTranslate(this).y].peek().
                isPickedUp = true;
                gp.isGlobalPickedUp = true;
                grid.clearGridArraySlot();
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


    public void gridSnap(){
        currentGridPosX = grid.translate().x;
        currentGridPosY = grid.translate().y;
        pos.x = currentGridPosX * gp.tileSize;
        pos.y = currentGridPosY * gp.tileSize;
        grid.setGridArray(this);
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
            if (this == grid.gridArray[currentGridPosX][currentGridPosY].peek()){
                return true;
            }
        }
        return false;
    }


    public void setDefaultValues() {
        pos.x = 0;
        pos.y = 0;
    }

    public void colorCard(){
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        color = new Color(r, g, b);
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
