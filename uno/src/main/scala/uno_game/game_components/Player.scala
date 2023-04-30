package game_components

import scala.collection.mutable

/**
 * @constructor Takes a string and creates a Player with that name
 * The Default Player move behavior is to play the highest valued card in their deck
 * as if they have no high-value cards at the end of the round, the player who
 * wins will not receive those points.
 * @param n the player name
 * 
 */ 
class Player(n: String){
    private var hand = new Deck
    private val name = n
    private var score = 0
    private var strat = new Strategy()

    /**
     * Calls the strategy doMove function but passes in the player hand and
     * the menu, so operations can be done using other players and the top
     * card of the deck
     * @param men the menu
     * 
     * @return an option saying whether or not a playable card was found
     */ 
    def move(men: Menu): Option[(Card, Int)] = {
        return strat.doMove(hand, men) //uses inherited doMove from Strategy
    }

    /**
     * Returns the player's current score
     * @return the player's score
     */ 
    def getScore(): (Int,String) = {
        var ret = new mutable.StringBuilder
        for(card <- hand.get()){
            score += card.getPointValue()
            ret ++= "Card %s - %d\n".format(card.getColor(), card.getPointValue())
        }
        return (score, ret.toString)
    }

    /**
     * Resets the score of the player
     */ 
    def resetScore(): Unit = {
        score = 0
    }

    /**
     * Returns the name of the player
     * @return the name of the player
     */ 
    def getName(): String = {
        return n
    }

    /**
     * Returns the number of cards in
     * the player's hand
     * @return number of cards in player's hand
     */ 
    def cardCount(): Int = {
        return hand.get().length
    }

    /**
     * This will serve as the "draw" function
     * @param c the card to add to the deck
     */ 
    def addToDeck(c: Card): Unit = {
        hand.addCard(c)
    }
    /**
     * returns the player's deck
     * @return the player's deck
     */ 
    def getDeck(): Deck = {
        return hand
    }

    /**
     * Takes in an int describing the strategy and assigns it to the player 
     * @param strat the ID of the strategy to assign to the player
     * ID 1 - The Default Strategy
     * ID 2 - The PlayColorFirst Strategy
     * ID 3 - The PlayAbilityFirst Strategy
     * ID 4 - The DrawFirst Strategy
     */ 
    def setStrategy(st: Int): Unit = {
        if(st == 1) strat = new Strategy()
        else if(st == 2) strat = new PlayAbilityFirst()
        else if(st == 3) strat = new PlayColorFirst()
        else if(st == 4) strat = new DrawFirst(4)
    }

    /**
     * Prints the string saying the player strategy
     * @return the string with the player strategy
     */ 
    def getStrategy(): String = {
        return "Player %s Strategy: %s\n".format(name, strat.name)
    }

    /**
     * Plays a card using the ID of the card
     * @param c the card ID to be used
     * @return anything
     */ 
    // def playCard(c: Card): Unit = {
    //     if(c.isSpecial()) c.doSpecial()
    //     else{
    //         println("Surprise")
    //     }
    // }
}