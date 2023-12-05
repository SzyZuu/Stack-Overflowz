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

    public Card(GamePanel gp, KeyHandler keyH, MouseHandler mouseH){
        this.gp = gp;
        this.keyH = keyH;
        this.mouseH = mouseH;
        setDefaultValues();
    }
    public void setDefaultValues() {
        x = 100;
        y = 100;
    }
    public void update(){
        //System.out.println(mouseH.getMousePos());

    }
    public void draw(Graphics2D g2){
        g2.setColor(Color.white);

        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }

}
