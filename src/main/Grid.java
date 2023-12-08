package main;

import java.awt.*;

//temp for testing, might become permanent// currently doesnt work
//todo fix my spaghetti code
public class Grid  {

    int tileCornerTLy; // top left
    int tileCornerTLx; // top left
    int tileCornerBRy; // bottom right
    int tileCornerBRx; // bottom right

    GamePanel gp;

    public Grid(GamePanel gamp){
        this.gp = gamp;
    }

    public Point currentNearestGrid(){

        for (int i = 16; i >= 1 ; i--) {
            tileCornerTL.x = screenWidth - tileSize * i;
            tileCornerBR.x = tileCornerTL.x + tileSize;
            for (int j = 12; j >= 1; j--){
                tileCornerTL.y = screenHeight - tileSize * j;
                tileCornerBR.y = tileCornerTL.y + tileSize;

                if(getMousePosition().x >= tileCornerTL.x && getMousePosition().x <= tileCornerBR.x && getMousePosition().y >= tileCornerTL.y && getMousePosition().y <= tileCornerBR.y){
                    currentNearestGrid().x= i;
                    currentNearestGrid().y = j;

                    return gridPoint;
                }
            }
        }
        if(gridPoint != null){
            return gridPoint;
        }
        else{
            return null;
        }
    }


}
