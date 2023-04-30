package mvc_components

import scala.swing._
// import javax.swing._
import game_components._

/**
 * @constructor Builds the controller, responsible for interfacing with the model
 * @param view the view to send the information to
 * @param model the model to get the information from
 */ 
class Controller(view: View, mod: Model) {
  
  private var model = mod

  /**
   * Enables card shuffling
   */ 
  def enableRandomness: Action = Action("Enable card shuffling") {
    Dialog.showMessage(view.frame.contents.head, "Clicking OK will end the current game and begin a new one with randomness enabled.", title = "Randomness Enable")
    model = new Model(false)
    initGame
  }

  /**
   * Disables card shuffling 
   */
  def disableRandomness: Action = Action("Disable card shuffling") {
    Dialog.showMessage(view.frame.contents.head, "Clicking OK will end the current game and begin a new one with randomness disabled.", title = "Randomness Disable")
    model = new Model(true)
    initGame
  }


  /**
   * Returns the current model
   * @return the model
   */ 
  def getModel(): Model = {
    return model
  }

  /**
   *Initializes the game 
   */
  def initGame = Action("Initialize Game") {
    val game = model.getGame
    game.initializeGame()

    view.textArea.text = game.doShowGameArea()
    var i = 0
    for(p <- view.playerHands){
      p.showCards(game.getPlayerList()(i).getDeck().get())
      i = i + 1
      view.update
    }
    view.playDeck.changeCard(game.topCard())
  }
  
  /**
   * Displays the game area 
   */ 
  def controllerGameArea: Action = Action("Show Game Area"){
    Dialog.showMessage(view.frame.contents.head, model.getGame.doShowGameArea(), title = "Current Game Area")
    view.textArea.text = model.getGame.doShowGameArea()
  }

  /**
   * Shows the player order
   */ 
  def playerOrder: Action = Action("Show Player Order"){
    Dialog.showMessage(view.frame.contents.head, model.getGame.doShowPlayerOrder(), title = "Player Order")
    view.textArea.text = model.getGame.doShowPlayerOrder()
  }

  /**
   * Sets the stratedy to DEFAULT for the given player 
   * @param p the index of the player to set the strategy for
   */
  def setStrategy1(p: Int): Action = Action("Set Default Strategy"){
    model.getGame.getPlayerList()(p).setStrategy(1)
    val mes = "Player %s Strategy Updated to Default!".format(model.getGame.getPlayerList()(p).getName())
    Dialog.showMessage(view.frame.contents.head, mes, title = "Player Strategy Update Success")
  }
  /**
   * Sets the stratedy to ABILITYFIRST for the given player 
   * @param p the index of the player to set the strategy for
   */
  def setStrategy2(p: Int): Action = Action("Set AbilityFirst Strategy"){
    model.getGame.getPlayerList()(p).setStrategy(2)
    val mes = "Player %s Strategy Updated to AbilityFirst!".format(model.getGame.getPlayerList()(p).getName())
    Dialog.showMessage(view.frame.contents.head, mes, title = "Player Strategy Update Success")
  }
  /**
   * Sets the stratedy to COLORFIRST for the given player 
   * @param p the index of the player to set the strategy for
   */
  def setStrategy3(p: Int): Action = Action("Set ColorFirst Strategy"){
    model.getGame.getPlayerList()(p).setStrategy(3)
    val mes = "Player %s Strategy Updated to ColorFirst!".format(model.getGame.getPlayerList()(p).getName())
    Dialog.showMessage(view.frame.contents.head, mes, title = "Player Strategy Update Success")
  }
  /**
   * Sets the stratedy to DRAWFIRST for the given player 
   * @param p the index of the player to set the strategy for
   */
  def setStrategy4(p: Int): Action = Action("Set DrawFirst Strategy"){
    model.getGame.getPlayerList()(p).setStrategy(4)
    val mes = "Player %s Strategy Updated to DrawFirst!".format(model.getGame.getPlayerList()(p).getName())
    Dialog.showMessage(view.frame.contents.head, mes, title = "Player Strategy Update Success")
  }
  /**
   * Gets the stratedy for the given player 
   * @param p the index of the player to get the strategy for
   */
  def getStrat(p: Int): Action = Action("Get Current Strategy"){
    val mes = model.getGame.getPlayerList()(p).getStrategy()
    Dialog.showMessage(view.frame.contents.head, mes, title = "Player %s Current Strategy".format(model.getGame.getPlayerList()(p).getName()))
  }
  /**
   * Clears the deck of the given player 
   * @param p the index of the player to clear the deck of
   */
  def clearDeck(p: Int): Action = Action("Clear Player Deck"){
    model.getGame.getPlayerList()(p).getDeck().get().clear
    Dialog.showMessage(view.frame.contents.head, "Player %s deck cleared!".format(model.getGame.getPlayerList()(p).getName()))
    view.playerHands(p).showAsEmpty
    view.update
  }
  /**
   * Gets the deck of the given player 
   * @param p the index of the player to get the deck of
   */
  def viewDeck(p: Int): Action = Action("View Player Deck"){
    val pre = "Player %s Deck!".format(model.getGame.getPlayerList()(p).getName())
    val msg = model.getGame.getPlayerList()(p).getDeck().printCards()
    Dialog.showMessage(view.frame.contents.head, msg, title = pre)
  }
  

  /**
   * Advances the player order
   */ 
  def advPlayOrd: Action = Action("Advance Player Order"){
    val men = model.getGame
    val tmp = men.doShowPlayerOrder()
    men.doAdvancedPlayerOrder()
    val msg = "Previous: %s\n New: %s".format(tmp, men.doShowPlayerOrder())
    Dialog.showMessage(view.frame.contents.head, msg, title = "Updated Player Order")
    // view.textArea.text = str
  }

  /**
   * Checks for the winner
   */ 
  def checkWin: Action = Action("Check for the Winner"){
    // view.textArea.text = model.getGame.checkForWinner()._1
    Dialog.showMessage(view.frame.contents.head, model.getGame.checkForWinner()._1, title = "Check for Winner")
  }

  def checkWinFunc: Unit = {

    Dialog.showMessage(view.frame.contents.head, model.getGame.checkForWinner()._1, title = "Check for Winner")
    val tmp = Dialog.showConfirmation(view.frame.contents.head, "Do you want to play again?",optionType = Dialog.Options.YesNo, title = "Play Again?")
    if(tmp != Dialog.Result.Ok) sys.exit(0)
  }

  def moveRec: Unit = {
    val curr = model.getGame
    if(!curr.runningStatus) curr.initializeGame()
    val currPlayer = model.getGame.playerOrder.get()(0) //pointer to the current player
    val add = curr.doMove()
    if(add._1.contains("won")) this.checkWinFunc
    else {
      view.textArea.text = curr.doShowGameArea() + "\n" + add._1
      if(add._2 != None){
        view.playDeck.changeCard(add._2.get)
      }
      var ind = 0
      for(p <- 0 until curr.getPlayerList().length){
        if(curr.getPlayerList()(p) == currPlayer) ind = p
      }
      view.playerHands(ind).showCards(currPlayer.getDeck().get())
      val next = curr.playerOrder.get()(curr.playerOrder.get().length - 1)
      println("Next Player: %s. Message: %s".format(next.getName(), add._1))
      if(add._1.contains(next.getName() + " had to draw")) view.playerHands((ind+1)%4).showCards(next.getDeck().get())
      view.update
    }
  }
  
  /**
   * Executes a player move
   */ 
  def move: Action = Action("Do Move"){
    moveRec
  }

  /**
   * Executes 4 player moves
   */ 
  def turn: Action = Action("Do Turn"){
    // view.textArea.text = model.getGame.doTurn()
    for(i <- 0 until 4){
      this.move()
    }
  }

  /**
   * Runs the game
   */ 
  def doGame: Action = Action("Do Game"){
    // view.textArea.text = model.getGame.doShowGameArea() + "\n" + model.getGame.doGame(true)
    if(!model.getGame.runningStatus) initGame
    Dialog.showMessage(view.frame.contents.head, "Clicking OK will run the game to completion.", title = "Run Game to Completion")
    // println("Here3")
    while(model.getGame.runningStatus){
      // println("Here2")
      this.moveRec
      // view.update
      // Thread.sleep(1500)
    }
  }

  /**
   * Exits the application
   */ 
  def exit = Action("Exit") {
    sys.exit(0)
  }
}