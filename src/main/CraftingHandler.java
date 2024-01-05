package main;

import entity.Card;

public class CraftingHandler implements CraftingListener{

    Recipes r;
    GamePanel gp;
    @Override
    public void doCraft(Card card1, Card card2) {
        if(r.recipe[card1.id][card2.id] != 0){
            //spawn the card; spawning method needed
        }else {
            gp.noValidRecipe();
        }
    }

    public CraftingHandler(Recipes r, GamePanel gp){
        this.r = r;
        this.gp = gp;
    }
}
