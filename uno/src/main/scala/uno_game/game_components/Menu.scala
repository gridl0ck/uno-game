package game_components
//Created by Jalen Morgan
import game_components._
import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import scala.collection.mutable.Stack



/**
 * @constructor Creates the menu object that contains the
 * core functions to run the game
 * @param d a flag which determines if the game is to be run in debug mode or not. Debug is non-random to allow tests to work
 */ 
class Menu(d: Boolean) {
  private var numPlayers = 4
  var playerOrder = new PlayerQueue()//Array[String]("P1", "B1", "B2", "B3")
  private var playerConst = List.empty[Player]
  private var globalDeck = Stack.empty[Card] //initial distribution deck then turns into draw deck
  private var playDeck = new Deck // discard deck
  // private val numRounds = 2 //Change this to user defined
  private val debug = d
  private var isRunning = false
  var currRound = 1


  /**
   * Removes all references to certain objects
   * so the JVM can deallocate them
   */ 
  def clear(): Unit = {
    playerConst = List.empty[Player]
    playerOrder.get().clear()
    globalDeck.clear()
    playDeck.get().clear
    this.currRound = 1
  }

  /**
   * Returns if the game is running or not
   * @return if the game is running or not
   */ 
  def runningStatus: Boolean = return isRunning

  /**
   * Shuffles the deck of cards when it runs out to keep the game going
   * 
   */ 
  def reshuffle(): Unit = {
    
    for(c <- playDeck.get()){
      globalDeck.push(c)
    }
    playDeck.get().clear
    // else{
    //   playDeck.get()
    // }
  }
  /**
   * Initializes the base game
   * @param d determines whether or not we are in debug mode, which
   * generates similar output across all runs
   */ 
  def initializeGame(): Unit = {
    this.clear()
    isRunning = true
    createPlayers()
    generateDeck()
    playDeck.get().clear // clear the discard deck if it didnt get cleared already
    initialDeal(debug)
  }
  
  /**
   * Creates the player array and initializes them with their
   * names
   */ 
  def createPlayers(): Unit = {
    playerOrder.get().clear
    for(i <- 1 to numPlayers){
      playerOrder.addPlayer(new Player("P%s".format(i.toString)))
    }
    playerConst = playerOrder.get().toList
  }


  /**
  * Gets the top card from the stack for comparison
  * in the move functions and pushes it back
  * @return the top card
  */
  def topCard(): Card = {
    return playDeck.get()(playDeck.get().length - 1)
  }

  /**
   * Returns a boolean saying whether or not the supplied
   * card can be played
   * @param c the card to check
   * @return true or false if the card can be played
   */ 
  def canPlay(c: Card): Boolean = {
    val top = topCard() //gets the top card bc its appended to it
    if(c.getColorCode() == 5 || c.getColorCode() == top.getColorCode()) return true
    if(c.getNum() == top.getNum()) return true
    if(top.getColorCode() == 5) return true
    else false
  }

  /**
   * Returns the player queue for accessing individual players
   * @return the list of player pointers
   */ 
  def getPlayerList(): List[Player] = {
    return playerConst
  } 

  /**
   * Generates the deck of cards used for the entire game
   */ 
  def generateDeck(): Unit = {
    //val trash = globalDeck.removeCard(false)
    globalDeck.clear //clears the deck before doing anything
    for(c <- 1 to 4){ //Runs 4 times to simulate colors
      //1 == red
      //2 == green
      //3 == blue
      //4 == yellow
      //5 == any (change color and any of the draw cards)
      for(i <- 0 to 9){
        globalDeck.push(new Card(i, c, false, 0, i+1))
      }
      globalDeck.push(new Card(11,c,true,2,30))//Add the 4 Draw 2s
    }
    for(a <- 0 to 3){
      // globalDeck.push(new Card(10,5,true,1,20))//Add the 4 Color Change - REMOVED FROM FINAL IMPLEMENTATION
      
      globalDeck.push(new Card(12,5,true,3,40))//Add the 4 Draw 4s
    }
    // globalDeck.push(new Card(13,5,true,4,45))//Skip Turn - REMOVED FROM FINAL IMPLEMENTATION
    // globalDeck.push(new Card(14,5,true,5,50))//Swap Hand - REMOVED FROM FINAL IMPLEMENTATION
    globalDeck = globalDeck.reverse
  }

  /**
   * Deals the cards before the game begins
   * @param debug determines if the cards are shuffled or not
   * to make the game random or not for testing purposes
   */ 
  def initialDeal(debug: Boolean): Unit = {
    //Gives players 7 cards each
    if(!debug) {
      var hold = globalDeck.toList //This function breaks the entire thing so need to look into how to shuffle
      hold = Random.shuffle(hold)
      globalDeck.clear
      for(c <- hold){
        globalDeck.push(c)
      }
    }
      for(p <- playerOrder.get()){
        for(c <- 0 until 7){
          p.addToDeck(globalDeck.pop)
        }
      }
      playDeck.addCard(globalDeck.pop)
  }

  /**
   * Displays the game area with dynamic data
   * @return the format string with the game board
   */ 
  def doShowGameArea(): String = {
    var sb = "---Uno Game Board---\n"
    if(playerOrder.get().isEmpty) createPlayers()
    for(p <- playerConst){
      sb += ("Player %s:".format(p.getName()) + p.getDeck().printCards() + "\n")
    }
    sb += ("Discard Pile:" + playDeck.printCards() + "\n")

    return sb.toString
  }

  /**
   * Sets the strategy of the supplied player to the given strategy
   * @param p the player to change the strategy of
   * @param st the string depicting the strategy
   */ 
  def setStrategy(p: Player, st: String): Unit = {
    if(st.equals("default")) p.setStrategy(1)
    else if(st.equals("ability")) p.setStrategy(2)
    else if(st.equals("color")) p.setStrategy(3)
    else if(st.equals("draw")) p.setStrategy(4)
    // else print("ERR: %s - Invalid Strategy!".format(st))
  }

  /**
   * Builds a string with all player strategies
   * @return the string with all player strategies
   */ 
  def doShowPlayerStrategy(): String = {
    var sb = new StringBuilder("")
    for(p <- playerConst){
        sb ++= p.getStrategy()
    }
    return sb.toString
  }

  /**
   * Shows the player order as a format string
   * to allow for custom player names
   * 
   * @return the current order of the players as a format string
   */ 
  def doShowPlayerOrder(): String = {
    if(playerOrder.get().isEmpty) createPlayers()
    //Shows the array stating the current player order
    //This will be randomly defined eventually
    var str = "[%s, %s, %s, %s]".format(playerOrder.get()(0).getName(),playerOrder.get()(1).getName(),playerOrder.get()(2).getName(),playerOrder.get()(3).getName())
    return str
  }

  /**
   * Gives the menu a way to change the player order internally
   */ 
  def doAdvancedPlayerOrder(): Unit = {
    if(playerOrder.get().isEmpty) createPlayers()
    playerOrder.advanceOrder()
  }

  /**
   * Checks if the win conditions for the game have been met
   * and calculates the winner
   * 
   * @return string with the winning details
   */ 
  def checkForWinner(): (String, Option[Player]) = {
    // if(currRound < numRounds){
    //   return "ERR: Game is not complete yet or in the middle of the round!\n"
    // }
    var tmp = new Player("tmp")
    var assigned = false
    for(p <- playerOrder.get()){
      if(!assigned){
        if(p.getDeck().get().size <= 0) {
          isRunning = false
          tmp = p
          assigned = true
        }
      }
    }
    if(tmp.getName().equals("tmp")){
      return ("ERR: All Players have at least 1 card remaining!\n", None)
    }

    else{
      return ("Player %s has won!\n".format(tmp.getName()), Some(tmp))
    }

  }
  

  def drawCard(): Card = {
    if(globalDeck.isEmpty) reshuffle()
    return globalDeck.pop()
  }
  /**
   * Executes the current player's move
   * @return the string
   */ 
  def doMove(): (String, Option[Card]) = {
    //Mock Logic
    //1.) Get the player at the front of the queue
    //2.) Enable their turn, which allows them to play a card
    //3.) They will be presented with their deck and they can select a card
    //using the index of the card
    val win = checkForWinner()
    if(!(win._2 == None)) return (win._1, None)
    val player = playerOrder.get()(0)
    playerOrder.advanceOrder() // moves to the next turn
    val disc = player.move(this) //returns the card to play
    if(disc.equals(None)){
      var str = "Player %s appears to have to no usable cards! Player %s has drawn 1 card!\n".format(player.getName(),player.getName(),player.addToDeck(drawCard()))
      return (str, None)
    }
    else if(disc.get._1.isSpecial()){
      playDeck.addCard(player.getDeck().get().remove(disc.get._2)) // remove the card from the player deck and add to
      var uno = ""
      if(player.getDeck().get().length == 1) uno = " Player %s has 1 card remaining! UNO!\n".format(player.getName())
      val spec = disc.get._1.doSpecial(this)
      return ("Player %s played card %s!%s\n".format(player.getName(), disc.get._1.getAbility(), (" " + spec + uno)), Some(disc.get._1))
      //return disc.get._1.doSpecial(this)
    }
    else {
      playDeck.addCard(player.getDeck().get().remove(disc.get._2)) // remove the card from the player deck and add to discard
      var uno = ""
      if(player.getDeck().get().length == 1) uno = "Player %s has 1 card remaining! UNO!\n".format(player.getName())
      return ("Player %s played card %s:%d!%s\n".format(player.getName(), disc.get._1.getColor(),disc.get._1.getNum(), uno), Some(disc.get._1))
    }
  }

  /**
   * Executes doMove 4 times
   * @return the string
   */ 
  def doTurn(): String = {
    var str = ""
    for(i <- 0 to 3){
      val wow = this.doMove()._1
      if(wow.contains("won")) return wow
      str += wow
      // str += "\n"
    }
    return str
  }

  /**
   * Executes the game
   * @param d enable or disable debug mode
   * 
   */ 
  def doGame(d: Boolean): String = {
    startGame(d)
  }

  //Main game logic
  /**
   * This method will contain the start game logic
   * @param b will enable the debug mode
   * 
   */ 
  def startGame(b: Boolean): String = {
    // while(currRound < numRounds){
    //   return Nil
    // }
    for(i <- 0 until 4){
      this.doMove()
    }
    // currRound = 2
    return checkForWinner()._1
  }


}
