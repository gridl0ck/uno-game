package mvc_components
import game_components._

import scala.swing._
import BorderPanel.Position._
import java.awt.geom.Rectangle2D
import java.awt.geom.Ellipse2D
import java.awt.Color
import scala.collection.mutable.ArrayBuffer
import java.awt.image.BufferedImage
import scala.swing.Orientation

/***
In my View class, I borrowed the code from the guiCards demo, specifically the PlayerHands, CardPanel, HiddenCardPanel, and the gameArea. I then heavily modified the code
to allow it to function with my cards, but the original code is the intellectual property of COL Christa Chewar.
***/

/**
 * @constructor this creates a view that handles all the visual aspects of my game
 * @param mod the model to include with the view. This holds all the game logic
 * 
 */ 
class View(mod: Model) {

  var controller: Option[Controller] = None // Creates throwaway objects to satisfy compiler
  var model: Option[Model] = Some(mod)

  // Components
  val textArea = new TextArea

  /** View.init
    * @param controller
    */
  def init(cont: Controller, mod: Model): Unit = {
    controller = Some(cont)
    model = Some(mod)
  }

  var playDeck = new CardPanel

  val drawDeck = new HiddenCardPanel

  val deckSpaces = new BoxPanel(Orientation.Horizontal) {
    contents += drawDeck
    contents += playDeck   
    background = Color.green
    preferredSize = new Dimension(254,198)
  }

  val cardSpaces = new BorderPanel {    
    layout += new Label("P1 play") -> West      
    layout += new Label("P2 play") -> North
    layout += new Label("P3 play") -> East
    layout += new Label("P4 play") -> South
    layout += deckSpaces -> Center      
  }

  val playerHands = new PlayerHands

  val south = new BoxPanel(Orientation.Vertical) {
    preferredSize = new Dimension(500,150)
    contents += playerHands(3)
    // contents += buttons
    opaque = false
  }

  val gameArea = new BorderPanel {
    background = Color.darkGray
    layout += playerHands(0) -> West      
    layout += playerHands(1) -> North
    layout += playerHands(2) -> East
    layout += south -> South
    layout += cardSpaces -> Center      
  }

  if(controller == None) {
    controller = Some(new Controller(this, new Model(true)))
    model = Some(controller.get.getModel())
  }

  val debug = new MainFrame {
    title = "Debug"
    // val content = new BorderPanel
  }

  val frame = new MainFrame {
    title = "UNO Game"
    contents = gameArea
    centerOnScreen()

    menuBar = new MenuBar {
      contents += new scala.swing.Menu("Randomness") {
        contents += new MenuItem(controller.get.enableRandomness)
        contents += new MenuItem(controller.get.disableRandomness)
      }
      contents += new scala.swing.Menu("Milestone 1") {
        contents += new MenuItem(controller.get.controllerGameArea)
        contents += new MenuItem(controller.get.playerOrder)
        contents += new MenuItem(controller.get.advPlayOrd)
        contents += new Separator
        contents += new MenuItem(controller.get.exit) // end Exit menuItem
      } // end Menu 1
      contents += new scala.swing.Menu("Milestone 3"){
        contents += new MenuItem(controller.get.initGame)
        contents += new MenuItem(controller.get.checkWin)
        contents += new MenuItem(controller.get.move)
        contents += new MenuItem(controller.get.turn)
        contents += new MenuItem(controller.get.doGame)
      }
      contents += new scala.swing.Menu("Player 1"){
        contents += new MenuItem(controller.get.getStrat(0))
        contents += new MenuItem(controller.get.setStrategy1(0))
        contents += new MenuItem(controller.get.setStrategy2(0))
        contents += new MenuItem(controller.get.setStrategy3(0))
        contents += new MenuItem(controller.get.setStrategy4(0))
        contents += new Separator
        contents += new MenuItem(controller.get.viewDeck(0))
        contents += new MenuItem(controller.get.clearDeck(0))
      }
      contents += new scala.swing.Menu("Player 2"){
        contents += new MenuItem(controller.get.getStrat(1))
        contents += new MenuItem(controller.get.setStrategy1(1))
        contents += new MenuItem(controller.get.setStrategy2(1))
        contents += new MenuItem(controller.get.setStrategy3(1))
        contents += new MenuItem(controller.get.setStrategy4(1))
        contents += new Separator
        contents += new MenuItem(controller.get.viewDeck(1))
        contents += new MenuItem(controller.get.clearDeck(1))
      }
      contents += new scala.swing.Menu("Player 3"){
        contents += new MenuItem(controller.get.getStrat(2))
        contents += new MenuItem(controller.get.setStrategy1(2))
        contents += new MenuItem(controller.get.setStrategy2(2))
        contents += new MenuItem(controller.get.setStrategy3(2))
        contents += new MenuItem(controller.get.setStrategy4(2))
        contents += new Separator
        contents += new MenuItem(controller.get.viewDeck(2))
        contents += new MenuItem(controller.get.clearDeck(2))
      }
      contents += new scala.swing.Menu("Player 4"){
        contents += new MenuItem(controller.get.getStrat(3))
        contents += new MenuItem(controller.get.setStrategy1(3))
        contents += new MenuItem(controller.get.setStrategy2(3))
        contents += new MenuItem(controller.get.setStrategy3(3))
        contents += new MenuItem(controller.get.setStrategy4(3))
        contents += new Separator
        contents += new MenuItem(controller.get.viewDeck(3))
        contents += new MenuItem(controller.get.clearDeck(3))
      }
    } // end MenuBar
    visible = true    
  }

  /**
   * The update function updates the assets on the frame 
   */  
  def update : Unit = {
    frame.repaint()
    for(panel <- playerHands) panel.repaint()
  }


  /**
   * BORROWED AND MODIFIED FROM guiCards Lesson, CS403
   * @constructor The CardPanel class provides a template for a panel of cards
   */ 
  class CardPanel extends Panel {

    var image = javax.imageio.ImageIO.read(new java.io.File("res/empty.png"))
    
    /**
     * Displays the cards as an empty space, with the back facing you
     */ 
    def showAsEmpty : Unit = {
      image = javax.imageio.ImageIO.read(new java.io.File("res/empty.png"))
      this.repaint()
    }
    
    /**
     * Displays the actual card asset
     * @param card the card to be displayed
     */ 
    def changeCard(card : Card) : Unit = {
      println("Card to be displayed: %s:%d".format(card.getColor(), card.getNum()))
      image = javax.imageio.ImageIO.read(new java.io.File("res/" + card.getColor() + card.getNum().toString + ".png"))
      
      this.repaint() 
    }
    
    override def paint(g: Graphics2D) : Unit = {
      g.drawImage(image, 18, 48, null)
    }
  }

  /**
   * BORROWED AND MODIFIED FROM guiCards Lesson, CS403
   * The HiddenCardPanel class provides a template for a panel of cards with their backs turned
   */ 
  class HiddenCardPanel extends Panel {

    var image = javax.imageio.ImageIO.read(new java.io.File("res/back.png"))

    /**
     * Displays the cards as an empty space
     */ 
    def showAsEmpty : Unit = {
      image = javax.imageio.ImageIO.read(new java.io.File("res/empty.png")) //change "back" to "empty"
      this.repaint()
    }
    
    /**
     * Shows the back of the card
     */ 
    def showAsHidden : Unit = {
      image = javax.imageio.ImageIO.read(new java.io.File("res/back.png"))
      this.repaint()
    }
    
    override def paint(g: Graphics2D) : Unit = {
      g.drawImage(image, 54, 48, null)
    }
  }

  /**
   * BORROWED AND MODIFIED FROM guiCards Lesson, CS403
   * The HiddenCardPanel class provides a template for a panel of cards in the player's hand
   */
  class PlayerHandPanel(orientation : Char) extends Panel {

    preferredSize = new Dimension(72,96)

    var images = new ArrayBuffer[BufferedImage]
    images += javax.imageio.ImageIO.read(new java.io.File("res/empty.png")) //change "back" to "empty"

    /**
     * Displays the cards as an empty space
     */ 
    def showAsEmpty : Unit = {
      images.clear
      images += javax.imageio.ImageIO.read(new java.io.File("res/empty.png")) //change "back" to "empty"
      this.repaint()
    }
    /**
     * Updates the player hand visual asset set
     * @param cards the current player's hand
     */ 
    def showCards(cards : ArrayBuffer[Card]) : Unit = {
      images.clear
      for (card <- cards) {
        images += javax.imageio.ImageIO.read(new java.io.File("res/" + card.getColor() + card.getNum().toString + ".png"))
      }
      super.repaint() 
    }
    
    override def paint(g: Graphics2D) : Unit = {
      var offset = 36
      for (image <- images) {
        if (orientation == 'v') g.drawImage(image, 0, offset-40, null)
        else g.drawImage(image, offset+72, 0, null)
        offset += 30
      }      
    }    
  }

  class PlayerHands extends ArrayBuffer[PlayerHandPanel] {

    this += new PlayerHandPanel('v')
    this += new PlayerHandPanel('h')
    this += new PlayerHandPanel('v')
    this += new PlayerHandPanel('h') 
    
    def reset : Unit = {    
      for panel <- this yield panel.showAsEmpty      
    }
  }

}