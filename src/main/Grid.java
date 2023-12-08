package main;

import java.awt.*;

//temp for testing, might become permanent// currently doesnt work
//todo fix my spaghetti code
public class Grid  extends GamePanel{
    Point arrayPosition;
    Point tileCornerTL; // top left
    Point tileCornerBR; // bottom right

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

                    return(currentNearestGrid());
                }
            }
        }
        if(currentNearestGrid() != null){
            return (currentNearestGrid());
        }
        else{
            return null;
        }
    }


}
