package mvc_components

import game_components.Menu
/**
 * @constructor The model, which serves as the object that contains the
 * current game. This is basically the game engine itself.
 * @param debug specify whether or not to run the game in simualation mode
 */ 
class Model(debug: Boolean) {

  private val game = new Menu(debug)

  /**
   * Returns the game that is currently running
   * @return the game
   */ 
  def getGame = game
}