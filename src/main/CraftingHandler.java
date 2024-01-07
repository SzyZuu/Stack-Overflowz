package main;

import entity.Card;

public class CraftingHandler implements CraftingListener{

    Recipes r;
    GamePanel gp;
    @Override
    public void doCraft(Card card1, Card card2) {
        if(card1.id < r.calcCardIds && card2.id <r.calcCardIds){
            if(r.recipe[card1.id][card2.id] != 0){
                //spawn the card; spawning method needed
                switch (r.recipe[card1.id][card2.id]){
                    case 3: // result Id
                        gp.spawnNewCard(3);
                        gp.returnCards();
                        break;
                    default:
                        gp.returnCards();
                        break;
                }
            }else {
                gp.returnCards();
            }
        }else {
        gp.returnCards();
    }
    }

    public CraftingHandler(Recipes r, GamePanel gp){
        this.r = r;
        this.gp = gp;
    }
}
