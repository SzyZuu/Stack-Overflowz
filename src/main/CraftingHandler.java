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
                int cardToSpawn = r.recipe[card1.id][card2.id];
                gp.spawnNewCard(r.recipe[card1.id][card2.id]);
                gp.score += gp.newestCard.scoreValue;
                if(cardToSpawn == 2 || cardToSpawn == 3){
                    gp.returnCards(card2);
                    gp.returnCards(card1);
                } else if (cardToSpawn == 8) {
                    gp.deleteCard(card2);
                    gp.deleteCard(card1);
                } else if (cardToSpawn == 10) {
                    gp.spawnNewCard(1);
                    gp.deleteCard(card2);
                    gp.returnCards(card1);
                } else {
                    gp.deleteCard(card2);
                    gp.returnCards(card1);
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
