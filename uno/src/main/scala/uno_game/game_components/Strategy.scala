package game_components

/**
 * @constructor The default strategy for the player to employ
 *
 */ 
class Strategy {
  // Default strategy of playing the highest valued card of matching color
  /**
   * Default move method for the default strategy
   * @param d the deck to move on
   * @param men the menu
   * 
   * @return the option from the helper function
   */ 
  val name = "Default"
  def doMove(d: Deck, men: Menu): Option[(Card, Int)] = {
    return lookForHighestVal(d, men)
  }

  /**
   * Checks the deck for the highest valued card to play first
   * @param d the deck to check
   * @param m the menu, passed to check the top deck card
   * @return an Option specifying whether a playable card was found or not,
   * and the index of the card so it can be removed
   */ 
  def lookForHighestVal(d: Deck, m: Menu): Option[(Card, Int)] = {
    val working = d.get() //the Player's Hand
    var large = -1 //working(0).getPointValue()
    var tmp = new Card(-1,-1,false,-1,-1) //creates a tmp card
    var ind = -1
    for(i <- 0 until working.length){
      // println("Current Card: %s:%d".format(working(i).getColor(), working(i).getNum()))
      if(m.canPlay(working(i))){
        if(working(i).getPointValue() >= large){
          tmp = working(i)
          ind = i
          // println("Reassigned: %s:%d".format(working(i).getColor(), working(i).getNum()))
        }
      }
    }
    if(ind == -1) return None
    
    return Some((tmp, ind))
  }    

}

/**
 * @constructor The strategy for the player to employ that plays the Ability cards first
 *
 */ 
class PlayAbilityFirst extends Strategy {

  override val name = "AbilityFirst"
  override def doMove(d: Deck, m: Menu): Option[(Card, Int)] = {
    val play = findAbility(d, m)
    if(play != None) return play
    else return this.lookForHighestVal(d, m)
  }

  def findAbility(d: Deck, men: Menu): Option[(Card, Int)] = {
    val working = d.get()
    for(i <- 0 until working.length){
      val tmp = working(i)
      if(tmp.isSpecial()) return Some((tmp, i))
    }
    return None
  }
}


/**
 * @constructor The strategy for the player to employ that plays the Color cards first
 *
 */ 
class PlayColorFirst extends Strategy {
  /**
  * Overrides the default strategy doMove to implement the 
  * new play criteria for the Color First Player. If a card is not found
  * using the strategy, the default strategy is employed
  * @param d the player deck
  * @param men the menu to operate on
  * @return an Option specifying whether a playable card was found or not,
  * and the index of the card so it can be removed
  */
  override val name = "ColorFirst"
  override def doMove(d: Deck, m: Menu): Option[(Card, Int)] = {
    val play = sameColor(d, m)
    if(play != None) return play
    else return this.lookForHighestVal(d, m)
  }

  def sameColor(d: Deck, men: Menu): Option[(Card, Int)] = {
    val top = men.topCard()
    val working = d.get()
    for(ind <- 0 until working.length){
      if(top.getColorCode() == working(ind).getColorCode()) {
        //println("Reassigned: %s".format(working(ind).getColor()))
        return Some((working(ind), ind))
      }
    }
    return None
  }
}


/**
 * @constructor The strategy for the player to employ that draws cards first, then plays the Highest Valued Cards when it reaches a condition
 * @param n the number of cards to draw
 */ 
class DrawFirst(n: Int) extends Strategy {

  private var remainder = n

  override val name = "DrawFirst"
  override def doMove(d: Deck, m: Menu): Option[(Card, Int)] = {
    if(remainder > 0) {
      remainder = remainder - 1
      return None
    }
    else return this.lookForHighestVal(d, m)
  }



}