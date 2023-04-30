# My CS403 Project - CDT Jalen Morgan

## Base Game: UNO

## Description

My game will be modeled after the popular 4-player card game, UNO. There are a total of 100 cards in the deck including the special cards.

## Rules

My game will closely follow the original rules of the game, with slight modification. The original rules are as follows:

>The game is modified for exactly 4 players. Every player *starts* with seven cards, and they are _dealt_ face down. The rest of the cards are placed in a Draw Pile face down. Next to the pile a space should be designated for a Discard Pile. The top card should be placed in the Discard Pile, and the game *begins*!

The first person to run out of cards wins that round and at that point the *point calculation* will take place. At the end of the specified **n** rounds, the person with the highest score is the winner. The point values for each card is evaluated at the face value of the card plus 1 *(Card Value + 1)* with the exception of the Special Cards, which have their own associated values which is discussed in the [**appropriate section**](#special-cards). The starting deck for which the cards are drawn from is seeded and random (meaning that unless the seed value is the same, the deck won't be in the same order for any 2 games).

## Gameplay

My version of the game will follow roughly the same gameplay flow as the original game. That original flow is as follows:

> The **Player** to the left of the dealer plays first. Play passes to the left to start. Match the top card on the *DISCARD* pile either by number, color or word.
>
> For example, if the card is a Green 7, you must play a Green card or any color 7. Or, you may play any Wild card or a Wild Draw 4 card. If you don't have anything that matches, you must pick a card from the DRAW pile. If you draw a card you can play, play it.
Otherwise, play moves to the next person. Before playing your next to last card, you must say "UNO". If you don't say UNO and another player catches you with just one card before the next player begins their turn you must pick FOUR more cards from the DRAW pile.
>
>If you are not caught before the next player either draws a card from the DRAW pile or draws a card from their hand to play, you do not have to draw the extra cards. Once a player plays their last card, the hand is over. Points are tallied (see Scoring section) and you start over again.

But there are slight modifications to these rules, specifically in the available cards. These differences are highlighted in the [**Special Cards**](#special-cards) section of this document.

The Player order will be determined randomly and will proceed accordingly throughout the duration of the game. The order does not change with the exception of the skip card, which simply advances the player index by 2, skipping the next player. The Draw cards do this same thing as well, after requiring the next player to draw 2 cards.

## Standard Cards

The standard cards take the form of the numbers 0 through 9, in colors Red, Green, Blue, and Yellow. There are multiple of each to facilitate larger groups. These cards have point values associated with their number. There are 2 of each of the number cards per color, making a total of 20 cards per color, meaning 80 cards in total.

## Special Cards

Unlike the official UNO game, my clone will only have 4 special cards and one of them is a differnt one from the official game. Those cards are:

- *Color Change* - 20pts

  - Grants the current user the ability to change the color

- *Draw 2* - 30pts

  - Makes a player draw 2 cards. The player is defined as the next player in the turn order

- *Draw 4* - 40pts

  - Makes a player draw 4 cards. The player is defined as the next player in the turn order

- *Skip Turn* - 45 pts

  - Grants the ability to skip the next player's turn

- *Swap Hands* - 50pts

  - Grants the current user the ability to swap hands with any player

There are 4 of each of the special cards, with the exception of the *Skip Turn* and *Swap Hands* card, which have 1 per color resulting in the total number of special cards being 20 total.


## How to Win

The player who runs out of cards first wins!

## To-Do

-	Implement the 3 other Special Cards (Color Change, Skip Turn, and Swap Hands)
-	Remove dead code

# Sources Used
https://www.unorules.com/ - Used the original rules from the game from this site then modified to make them simpler to code.
