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
                        gp.returnCards(card2);
                        gp.returnCards(card1);
                        break;
                    default:
                        gp.returnCards(card2);
                        gp.returnCards(card1);
                        break;
                }
            }else {
                gp.returnCards(card2);
                gp.returnCards(card1);
            }
        }else {
            gp.returnCards(card2);
            gp.returnCards(card1);
    }
    }

    public CraftingHandler(Recipes r, GamePanel gp){
        this.r = r;
        this.gp = gp;
    }
}
