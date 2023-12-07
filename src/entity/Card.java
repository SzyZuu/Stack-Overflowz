package entity;

import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Card extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    MouseHandler mouseH;

    boolean isPickedUp = false;
    int offset = 32; //Math.round(gp.tileSize/2);

    public Card(GamePanel gp, KeyHandler keyH, MouseHandler mouseH){
        this.gp = gp;
        this.keyH = keyH;
        this.mouseH = mouseH;
        setDefaultValues();
    }

    public void pickUp(){
        if(mouseH.pressed && isSelected()){
            isPickedUp = true;

            if(isPickedUp){
                pos.x = gp.getMousePosition().x - offset;
                pos.y = gp.getMousePosition().y - offset;
                //pos = gp.getMousePosition();
                if(mouseH.pressed == false){
                    isPickedUp = false;
                }
            }
        }
    }

    public boolean isSelected(){
        if(gp.getMousePosition().x >= pos.x && gp.getMousePosition().y >= pos.y && gp.getMousePosition().x <= pos.x + gp.tileSize && gp.getMousePosition().y <= pos.y + gp.tileSize ){
            return true;
        }
        else return false;
    }

    public void setDefaultValues() {
        pos.x = 100;
        pos.y = 100;
    }
    public void update(){
        pickUp();
        System.out.println(gp.getMousePosition());
        System.out.println(pos);

    }
    public void draw(Graphics2D g2){
        g2.setColor(Color.white);

        g2.fillRect(pos.x, pos.y , gp.tileSize, gp.tileSize);
    }

}
