package game_components

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._
import scala.language.postfixOps
import scala.collection.mutable._

class Uno_Test extends AnyFunSpec with Matchers{
    describe("A simple UNO Clone"){
        describe("Displays the menu and several game variables."){
            it("Shows the values of the current game area"){
                val expectedResult = 
                    "---Uno Game Board---\n" +
                    "Player P1:[]\n"+
                    "Player P2:[]\n"+
                    "Player P3:[]\n"+
                    "Player P4:[]\n"+
                    "Discard Pile:[]\n"
                    // "---Uno Game Board---\n" + 
                    // "Player P1:[A:sw, A:st, A:d4, A:d2, A:cc, A:d4, A:d2]\n" + 
                    // "Player P2:[A:cc, A:d4, A:d2, A:cc, A:d4, A:d2, A:cc]\n" +
                    // "Player P3:[Y:9, Y:8, Y:7, Y:6, Y:5, Y:4, Y:3]\n" +
                    // "Player P4:[Y:2, Y:1, Y:0, B:9, B:8, B:7, B:6]\n" +
                    // "Discard Pile:[]\n"
                val men = new Menu(true)
                // men.initializeGame()
                men.createPlayers()
                men.doShowGameArea() should equal (expectedResult)
            }
            it("Shows the current player order as a format string"){
                val playerArr = "[P1, P2, P3, P4]"
                val men = new Menu(true)
                men.createPlayers()
                men.doShowPlayerOrder() should equal (playerArr)
            }
            it("Shows the player order after being advanced"){
                val expected1 = "[P2, P3, P4, P1]"
                val expected2 = "[P3, P4, P1, P2]"
                val expected3 = "[P4, P1, P2, P3]"
                val expected4 = "[P1, P2, P3, P4]"
                val men = new Menu(true)
                men.initializeGame()
                men.doAdvancedPlayerOrder()
                men.doShowPlayerOrder() should equal (expected1)
                men.doAdvancedPlayerOrder()
                men.doShowPlayerOrder() should equal (expected2)
                men.doAdvancedPlayerOrder()
                men.doShowPlayerOrder() should equal (expected3)
                men.doAdvancedPlayerOrder()
                men.doShowPlayerOrder() should equal (expected4)
            }
            it("Initializes the game area to the default values"){
                val menu = new Menu(true)
                val sb = new StringBuilder("") //initial
                sb ++= "---Uno Game Board---\n"
                sb ++= "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, R:6]\n"
                sb ++= "Player P2:[R:7, R:8, R:9, R:11, G:0, G:1, G:2]\n"
                sb ++= "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9]\n"
                sb ++= "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n"
                sb ++= "Discard Pile:[B:6]\n"
                menu.initializeGame()
                menu.doShowGameArea() should equal (sb.toString) //Initial board
                val expectedResult2 = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5]\n" +
                    "Player P2:[R:7, R:8, R:9, R:11, G:0, G:1, G:2]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6, R:6]\n"
                menu.doMove() //P1 plays their R:6
                menu.doShowGameArea() should equal (expectedResult2) //after one move where P1 plays the R:6
                val expectedResult3 = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5]\n" +
                    "Player P2:[R:7, R:8, R:9, G:0, G:1, G:2]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9, B:7, B:8]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6, R:6, R:11]\n"
                menu.doMove() //P2 Plays the R:11 (d2) Card
                menu.doShowGameArea() should equal (expectedResult3)
                // val expectedResult4 = 
                //     "---Uno Game Board---\n" +
                //     "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5]\n" +
                //     "Player P2:[R:7, R:8, R:9, G:0, G:1, G:2]\n" +
                //     "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9]\n" +
                //     "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                //     "Discard Pile:[B:6, R:6, R:11, G:11]\n"

                // menu.doMove() //P4 plays G:11
                // menu.doShowGameArea() should equal (expectedResult4)
                // val expectedResult5 = 
                //     "---Uno Game Board---\n" +
                //     "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, R:6, B:9]\n" +
                //     "Player P2:[R:7, R:9, G:0, G:1, G:2, G:3]\n" +
                //     "Player P3:[G:4, G:5, G:6, G:7, G:9, B:0]\n" +
                //     "Player P4:[B:1, B:2, B:3, B:4, B:5, B:6, B:7, Y:0]\n" +
                //     "Discard Pile:[B:8, R:8, G:8]\n"
                // menu.doMove() //P4 has to draw a card and its Y:0
                // menu.doShowGameArea() should equal (expectedResult5)
                menu.initializeGame() // resets to initial 
                menu.doShowGameArea() should equal (sb.toString)
            }

            it("Checks for a winner of the game"){
                val exp1 = "ERR: All Players have at least 1 card remaining!\n"
                val exp2 = "Player P1 has won!\n"
                val menu = new Menu(true)
                menu.initializeGame()
                menu.checkForWinner()._1 should equal (exp1)
                // menu.currRound = 2 //Sets the current round to 2 so the first condition is met
                menu.playerOrder.get()(0).getDeck().get().clear // Clears the deck to simulate running out of cards
                menu.checkForWinner()._1 should equal (exp2)
            }

            it("Executes a single player's turn"){
                val menu = new Menu(true) 
                val expectedResult = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5]\n" +
                    "Player P2:[R:7, R:8, R:9, G:0, G:1, G:2]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9, B:7, B:8]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6, R:6, R:11]\n"
                
                val exp1 = "Player P2 played card d2! Player P3 had to draw 2 cards!\n" //Special Card message
                menu.initializeGame() //running with true enables predictable outcomes
                menu.doMove() //P1s move doesnt change the board but makes them draw cards
                menu.doMove()._1 should equal (exp1)
                menu.doShowGameArea() should equal (expectedResult)
                
            }

            it("Executes 4 player moves as doTurn"){
                val menu = new Menu(true)
                //Player P1 has to Draw Cards! Player P1 has drawn 1 card!
                //Player P2 played card R:8!
                //Player P3 played card G:8!
                //Player P4 has to Draw Cards! Player P4 has drawn 1 card!
                val exp1 = "Player P1 played card R:6!" //Special Card message
                val exp2 = "Player P2 played card d2! Player P3 had to draw 2 cards!" //Normal card message
                val exp3 = "Player P4 played card d2! Player P1 had to draw 2 cards!"
                val exp4 = "Player P2 played card G:2!" // Change card
                menu.initializeGame()
                menu.doTurn() should equal (exp1 + "\n" + exp2 + "\n" + exp3 + "\n" + exp4 + "\n")

                //This is only testing the doTurn, and as such should not be littered with the scoreboard. Everything is assumed to be working
                //if this command runs
            }

            it("Runs the game to completion"){
                val menu = new Menu(true)
                val sb = new StringBuilder("") //initial
                sb ++= "---Uno Game Board---\n"
                sb ++= "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, R:6]\n"
                sb ++= "Player P2:[R:7, R:8, R:9, R:11, G:0, G:1, G:2]\n"
                sb ++= "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9]\n"
                sb ++= "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n"
                sb ++= "Discard Pile:[B:6]\n"
                menu.initializeGame()
                menu.doShowGameArea() should equal (sb.toString) //Initial board
                val expectedResult2 = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5]\n" +
                    "Player P2:[R:7, R:8, R:9, R:11, G:0, G:1, G:2]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6, R:6]\n"
                menu.doMove() //P1 plays R:6
                menu.doShowGameArea() should equal (expectedResult2) //after one move where P1 plays
                val expectedResult3 = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5]\n" +
                    "Player P2:[R:7, R:8, R:9, G:0, G:1, G:2]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9, B:7, B:8]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6, R:6, R:11]\n"
                menu.doMove() //P2 Plays the d2 (R:11) Card
                menu.doShowGameArea() should equal (expectedResult3) //after one move where P2 plays
                val expectedResult4 = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, B:9, B:11]\n" +
                    "Player P2:[R:7, R:8, R:9, G:0, G:1, G:2]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9, B:7, B:8]\n" +
                    "Player P4:[B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6, R:6, R:11, G:11]\n"

                menu.doMove() //P3 plays G:8
                menu.doShowGameArea() should equal (expectedResult4)
                val expectedResult5 = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, B:9, B:11]\n" +
                    "Player P2:[R:7, R:8, R:9, G:0, G:1]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9, B:7, B:8]\n" +
                    "Player P4:[B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6, R:6, R:11, G:11, G:2]\n"
                menu.doMove() //P4 has to draw a card and its Y:0
                menu.doShowGameArea() should equal (expectedResult5)
                //menu.playerOrder.get()(0).getDeck().get().clear //clears P4's deck simulating running out of cards
                // menu.currRound = 2 // sets the current round to 2 to allow for the win condition to be met
                menu.getPlayerList()(0).getDeck().get().clear //clears the player Deck for P1
                val win = "Player P1 has won!\n"
                menu.checkForWinner()._1 should equal (win) //Checks for the plyer with no cards.
            }
        }
        describe("Manages Player Strategies"){
            it("Sets a Player Strategy and allows you to see it"){
                val men = new Menu(true)
                men.initializeGame()

                val playList = men.getPlayerList()
                val p1 = playList(0)
                val t1 = p1.getStrategy() 
                t1 should equal ("Player P1 Strategy: Default\n")
                men.setStrategy(p1,"color")
                val t2 = p1.getStrategy()
                t2 should equal ("Player P1 Strategy: ColorFirst\n")
            }
            it("Shows all the player strategies"){
                val men = new Menu(true)
                men.initializeGame()

                val pList = men.getPlayerList()
                var sb = men.doShowPlayerStrategy()
                // for(p <- pList){
                //     sb += p.getStrategy()
                // }
                var test = ""
                test += "Player P1 Strategy: Default\n"
                test += "Player P2 Strategy: Default\n"
                test += "Player P3 Strategy: Default\n"
                test += "Player P4 Strategy: Default\n"

                val p3 = men.getPlayerList()(2)
                men.setStrategy(p3, "ability") //updates to play AbilityFirst CHANGE TO SETSTRATGY
                //takes a player and strategy
                val sb2 = men.doShowPlayerStrategy()
                // for(p <- pList){
                //     sb2 ++= p.getStrategy()
                // }
                val test2 = new StringBuilder("")
                test2 ++= "Player P1 Strategy: Default\n"
                test2 ++= "Player P2 Strategy: Default\n"
                test2 ++= "Player P3 Strategy: AbilityFirst\n"
                test2 ++= "Player P4 Strategy: Default\n"

                test should equal (sb.toString) //Change to (sb.toString) and uncomment builder code
                test2.toString should equal (sb2) //likewise
            }
            
            it("Executes Strategy #1: Default"){
                val menu = new Menu(true) 
                val expectedResult = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5]\n" +
                    "Player P2:[R:7, R:8, R:9, G:0, G:1, G:2]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9, B:7, B:8]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6, R:6, R:11]\n"
                
                val exp1 = "Player P2 played card d2! Player P3 had to draw 2 cards!\n" //Special Card message REMOVE SPACE AT END OF LINE
                menu.initializeGame() //running with true enables predictable outcomes
                menu.doMove() //P1s move doesnt change the board but makes them draw cards
                menu.doMove()._1 should equal (exp1)
                menu.doShowGameArea() should equal (expectedResult)
            }

            it("Executes Strategy #2: PlayAbilityFirst"){
                val m = new Menu(true)
                m.initializeGame()
                m.doAdvancedPlayerOrder()

                val m1 = m.doMove()._1 //uses P2 because the can move first in the non-randomized simulation
                val g1 = m.doShowGameArea() // shows that the card was played according to the default strategy
                val t1 = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, R:6]\n" +
                    "Player P2:[R:7, R:8, R:9, R:11, G:0, G:1, G:2, B:7]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6]\n" + 
                    "\n" +
                    "Player P2 appears to have to no usable cards! Player P2 has drawn 1 card!\n"
                val gameArea = m.doShowGameArea()

                m.initializeGame() // reinitialize the game so I can change the player strategy and execute it
                m.getPlayerList()(1).addToDeck(new Card(11, 3, true, 2, 30)) // Adds a new card to P2s hand.
                m.setStrategy(m.getPlayerList()(1), "ability") //Changes P2 strategy to play the ability card first
                m.doAdvancedPlayerOrder() //ensure P2 is next when doMove is called
                val t2 = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, R:6]\n" +
                    "Player P2:[R:7, R:8, R:9, R:11, G:0, G:1, G:2, B:11]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6]\n"
                
                (g1 + "\n" + m1) should equal (t1) //remove space in "\n "
                m.doShowGameArea() should equal (t2)

                val t3 =
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, R:6]\n" +
                    "Player P2:[R:7, R:8, R:9, G:0, G:1, G:2, B:11]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9, B:7, B:8]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6, R:11]\n" + 
                    "\n" + 
                    "Player P2 played card d2! Player P3 had to draw 2 cards!\n"
                val mv3 = (m.doMove())._1
                (m.doShowGameArea() + "\n" + mv3) should equal (t3) //remove space in "\n "
            }

            it("Executes Strategy #3: PlayColorFirst"){
                val m = new Menu(true)
                m.initializeGame()
                m.doAdvancedPlayerOrder()
                m.doAdvancedPlayerOrder()//Advances it to P3

                val m1 = m.doMove()._1 //uses P3 because the can move first in the non-randomized simulation
                val g1 = m.doShowGameArea() // shows that the card was played according to the default strategy
                val t1 = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, R:6]\n" +
                    "Player P2:[R:7, R:8, R:9, R:11, G:0, G:1, G:2]\n" +
                    "Player P3:[G:3, G:4, G:5, G:7, G:8, G:9]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6, G:6]\n" + 
                    "\n" +
                    "Player P3 played card G:6!\n"

                (g1 + "\n" + m1) should equal (t1) //REMOVE SPACE
                m.initializeGame() // reinitialize the game so I can change the player strategy and execute it
                m.doAdvancedPlayerOrder()
                m.doAdvancedPlayerOrder()
                //m.getPlayerList()(2).addToDeck(new Card(1,3,false,0,1)) // Adds a new card to P2s hand.
                m.setStrategy(m.getPlayerList()(2), "color") //Changes P3 strategy to play the color card first REGARDLESS OF POINT VALUE
                //m.doAdvancedPlayerOrder() //ensure P2 is next when doMove is called

                val t3 =
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, R:6]\n" +
                    "Player P2:[R:7, R:8, R:9, R:11, G:0, G:1, G:2]\n" +
                    "Player P3:[G:3, G:4, G:5, G:7, G:8, G:9]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6, G:6]\n" + 
                    "\n" + 
                    "Player P3 played card G:6!\n"
                val mv3 = m.doMove()._1
                val ga3 = m.doShowGameArea()
                (ga3 + "\n" + mv3) should equal (t3) //REMOVE SPACE
            }
            it("Executes Strategy #4: DrawFirst"){
                val m = new Menu(true)
                m.initializeGame()
                m.doAdvancedPlayerOrder()
                m.doAdvancedPlayerOrder()//Advances it to P3

                val m1 = m.doMove()._1 //uses P2 because the can move first in the non-randomized simulation
                val g1 = m.doShowGameArea() // shows that the card was played according to the default strategy
                val t1 = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, R:6]\n" +
                    "Player P2:[R:7, R:8, R:9, R:11, G:0, G:1, G:2]\n" +
                    "Player P3:[G:3, G:4, G:5, G:7, G:8, G:9]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6, G:6]\n" + 
                    "\n" +
                    "Player P3 played card G:6!\n" +
                    "Player P3 Strategy: Default\n"
                val curS1 = m.getPlayerList()(2).getStrategy()
                (g1 + "\n" + m1 + curS1) should equal (t1) // REMOVE space in "\n "

                m.initializeGame()
                m.doAdvancedPlayerOrder()
                m.doAdvancedPlayerOrder()//Advances it to P3

                m.setStrategy(m.getPlayerList()(2), "draw")
                val m2 = m.doMove()._1
                val g2 = m.doShowGameArea()

                val curS2 = m.getPlayerList()(2).getStrategy()
                (g1 + "\n" + m1 + curS1) should equal (t1) //REMOVE space in "\n "
                m.doAdvancedPlayerOrder() //P1 because doMove advances it
                m.doAdvancedPlayerOrder() //P2
                m.doAdvancedPlayerOrder() //P3 Loaded
                val m3 = m.doMove()._1
                val ga3 = m.doShowGameArea()

                val t3 = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, R:6]\n" +
                    "Player P2:[R:7, R:8, R:9, R:11, G:0, G:1, G:2]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9, B:7, B:8]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5]\n" +
                    "Discard Pile:[B:6]\n" + 
                    "\n" +
                    "Player P3 appears to have to no usable cards! Player P3 has drawn 1 card!\n" +
                    "Player P3 Strategy: DrawFirst\n"
                (ga3 + "\n" + m3 + curS2) should equal (t3)

                m.doAdvancedPlayerOrder() //P1 because doMove advances it
                m.doAdvancedPlayerOrder() //P2
                m.doAdvancedPlayerOrder() //P3 Loaded
                m.doMove()

                m.doAdvancedPlayerOrder() //P1 because doMove advances it
                m.doAdvancedPlayerOrder() //P2
                m.doAdvancedPlayerOrder() //P3 Loaded
                m.doMove()

                //DOES THE ABOVE TWICE TO SATISFY THE 4 CARDS
                m.doAdvancedPlayerOrder() //P1 because doMove advances it
                m.doAdvancedPlayerOrder() //P2
                m.doAdvancedPlayerOrder() //P3 Loaded
                val finM = m.doMove()._1
                val finGa = m.doShowGameArea()
                val t4 = 
                    "---Uno Game Board---\n" +
                    "Player P1:[R:0, R:1, R:2, R:3, R:4, R:5, R:6]\n" +
                    "Player P2:[R:7, R:8, R:9, R:11, G:0, G:1, G:2]\n" +
                    "Player P3:[G:3, G:4, G:5, G:6, G:7, G:8, G:9, B:7, B:8, B:9]\n" +
                    "Player P4:[G:11, B:0, B:1, B:2, B:3, B:4, B:5, Y:0, Y:1]\n" +
                    "Discard Pile:[B:6, B:11]\n" + 
                    "\n" +
                    "Player P3 played card d2! Player P4 had to draw 2 cards!\n" +
                    "Player P3 Strategy: DrawFirst\n"
                (finGa + "\n" + finM + curS2) should equal (t4)
            }
        }
    }
}
