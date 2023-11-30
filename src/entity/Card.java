package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Card extends Entity {

    GamePanel gp;
    KeyHandler keyH;



    public Card(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
    }
    public void setDefaultValues() {

        x = 100;
        y = 100;


    }
    public void update(){







    }
    public void draw(Graphics2D g2){
        g2.setColor(Color.white);

        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }

}
