package game_components

import game_components._
import scala.collection.mutable.Queue

/**
 * @constructor Creates the queue object that contains the
 * core functions to manage player order
 * 
 */ 
class PlayerQueue{

    private var queue = Queue[Player]()

    /**
     * Grabs the player queue from the object
     * @return the player queue
     */ 
    def get(): Queue[Player] = {
        return queue
    }

    /**
     * Adds a player to the queue
     * @param p the player to add
     */ 
    def addPlayer(p: Player): Unit = {
        queue.enqueue(p)
    }

    /**
     * Remove a player from the queue
     *
     */ 
    def removePlayer(): Unit = {
        queue.clear()
    }

    /**
     * Advances the player order
     * 
     */ 
    def advanceOrder(): Unit = {
        val tmp = queue.dequeue
        queue.enqueue(tmp)
    }

}
