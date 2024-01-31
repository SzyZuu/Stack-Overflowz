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
                    case 2:     // result Id, chop Wood
                        gp.spawnNewCard(r.recipe[card1.id][card2.id]);
                        gp.returnCards(card2);
                        gp.returnCards(card1);
                        break;
                    case 3:     // mine boulder
                        gp.returnCards(card2);
                        gp.returnCards(card1);
                        gp.spawnNewCard(r.recipe[card1.id][card2.id]);
                        break;
                    case 6:     // refine Wood
                        gp.spawnNewCard(r.recipe[card1.id][card2.id]);
                        gp.deleteCard(card2);
                        gp.returnCards(card1);
                        gp.score += gp.newestCard.scoreValue;
                        break;
                    case 7:     //refine Stone
                        gp.spawnNewCard(r.recipe[card1.id][card2.id]);
                        gp.deleteCard(card2);
                        gp.returnCards(card1);
                        gp.score += gp.newestCard.scoreValue; // scorevalues are in card
                        break;
                    case 8:     //make housing material
                        gp.spawnNewCard(r.recipe[card1.id][card2.id]);
                        gp.deleteCard(card2);
                        gp.deleteCard(card1);
                        gp.score += gp.newestCard.scoreValue;
                        break;
                    case 9:     //make house
                        gp.spawnNewCard(r.recipe[card1.id][card2.id]);
                        gp.deleteCard(card2);
                        gp.returnCards(card1);
                        gp.score += gp.newestCard.scoreValue;
                        break;
                    case 10:    //villager duplication
                        gp.spawnNewCard(r.recipe[card1.id][card2.id]);
                        gp.spawnNewCard(1);
                        gp.deleteCard(card2);
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
