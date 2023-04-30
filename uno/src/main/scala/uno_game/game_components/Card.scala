package game_components
import game_components._


/**
 * @constructor Creates a card object that contains the
 * core functions of a card
 * @param i the number on the card
 * @param c the color of the card
 * @param s if the card is special or not
 * @param a the ability of the card
 * @param v associated point value of the card
 */ 
class Card(i: Int, c: Int, s: Boolean, a: Int, v: Int) {
    private val id = i //Basically point value
    private val color = c //Color of the card as an int
    private val special = s //If the card is a special card or not
    private val ability = a //
    private val pointValue = v

    //abilities are defined as follows:
        //cc == Color Change == 1
        //d2 == Draw 2 == 2
        //d4 == Draw 4 == 3
        //st == Skip Turn == 4
        //sw == Swap Hands == 5
    //colors are defined in code as follows
        //1 == red
        //2 == green
        //3 == blue
        //4 == yellow
        //5 == any (change color and any of the draw cards)

    /**
     * Returns the value of the card
     * @return the value of the card
     */ 
    def getPointValue(): Int = {
        return pointValue
    }

    /**
     * Converts the ability code to a string version
     * @return the string with ability code
     */ 
    def getAbility(): String = {
        if(ability == 1) return "cc"
        else if(ability == 2) return "d2"
        else if (ability == 3) return "d4"
        else if (ability == 4) return "st"
        else if(ability == 5) return "sw"
        else {
            var str = "ERR: %d Invalid Ability Code!\n".format(this.getAbilityCode())
            str += "Card Number: %s\n".format(this.getNum())
            str += "Card Color: %s\n".format(this.getColor())
            str += "Card Specialty: %s".format(this.isSpecial().toString)
            return str
        }
    }

    /**
     * Gets the numerical code for the ability (used internally)
     * @return the code for the ability
     */
    
    def getAbilityCode(): Int = {
        return ability
    }

    /**
     * Converts the color code to a string version
     * @return the string with the color code
     */ 

    def getColor(): String = {
        if(color == 1) return "R"
        else if(color == 2) return "G"
        else if (color == 3) return "B"
        else if (color == 4) return "Y"
        else if(color == 5) return "A"
        else return "ERR: Invalid Color Code!"
    }

    /**
     * Gets the numerical code for the color (used internally)
     * @return the code for the color
     */
    def getColorCode(): Int = {
        return color
    }

    /**
     * Returns the number on the card
     * @return the number of the card
     */ 
    def getNum(): Int = {
        return id
    }

    /**
     * Returns whether or not the card is special
     * @return whether the card is special or not
     */ 
    def isSpecial(): Boolean = {
        return special
    }

    /**
     * Executes the card's ability
     * 
     */ 
    def doSpecial(m: Menu): String = {
        if(a == 1){ //cc
            return "no"
        }
        else if(a == 2){ //d2
            val recPlayer = m.playerOrder.get()(0) //move function automatically moves to the next player
            for(i <- 0 to 1){
                recPlayer.addToDeck(m.drawCard())
            }
            m.doAdvancedPlayerOrder()
            return ("Player %s had to draw 2 cards!".format(recPlayer.getName()))
        }

        else if(a == 3){ //d4
            val recPlayer = m.playerOrder.get()(0) //move function automatically moves to the next player
            for(i <- 0 to 3){
                recPlayer.addToDeck(m.drawCard())
            }
            m.doAdvancedPlayerOrder()
            return ("Player %s had to draw 4 cards!".format(recPlayer.getName()))
        }

        else if(a == 4){ //skip
            return "no"
        }
        else{ //swap hands
            return "no"
        }
    }
}
