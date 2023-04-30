package game_components
import scala.collection.mutable.ArrayBuffer

/**
 * @constructor Creates and empty deck
 * 
 */ 
class Deck{
    
    private var cards = ArrayBuffer[Card]()

    /**
     * Returns the deck
     * @return the ArrayBuffer[Card], or Deck
     */
    def get(): ArrayBuffer[Card] = {
        return cards
    }

    /**
     * Returns a format string with the card information filled out
     * @return the format string filled out
     * 
     */ 
    def printCards(): String = {
        var ret = "["
        for(c <- 0 until cards.length){
            val tmp = cards(c)
            if(tmp.getNum() == 10){
                if(c < cards.length - 1){
                    ret += "%s:%s, ".format(tmp.getColor(),tmp.getAbility())
                }
            
                else{
                    ret += ("%s:%s".format(tmp.getColor(),tmp.getAbility()))
                }                
            }
            else{
                if(c < cards.length - 1){
                    ret += "%s:%d, ".format(tmp.getColor(),tmp.getNum())
                }
            
                else{
                    ret += ("%s:%d".format(tmp.getColor(),tmp.getNum()))
                }
            }
        }
        ret += "]"
        return ret
    }

    /**
     * Adds a card to the deck
     * @param c the card to add
     */ 
    def addCard(c: Card): Unit = {
        cards.addOne(c)
    }

    /**
     * Removes a card from the deck and returns it
     * This is modified to act as a stack (FILO) by using the parameter
     * @param back determines whether or not to take the card from the back or the front
     * @return the card that was removed
     */ 
    def removeCard(back: Boolean): Card = {
        if(back) return cards.remove(cards.length-1) //remove from the back, which is the "top"
        else return cards.remove(0)
    }

}
