Feature: Full Game Test

  @Row_128
  Scenario: game starts, players play turns until a winner is declared (at lease 3 turns)
    Given server has been started
    And player 1 input his or her name as "one"
    And player 1 initialize local players
    And player 1 connect to server
    And player 2 input his or her name as "two"
    And player 2 initialize local players
    And player 2 connect to server
    And player 3 input his or her name as "three"
    And player 3 initialize local players
    And player 3 connect to server
    And player 1 receive three players' info
    And player 2 receive three players' info
    And player 3 receive three players' info
#    turn #1 for player 1
    When player 1 receive turn number 1
    And player 1 receive three players' score
    And player 1 print out three players' score
    And player 1 roll "DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_SKULL,DICE_COIN" with fortune card "CARD_COIN"
    And player 1 start a turn
    And player 1 press button 1 to score
    And player 1 get 200 points in this turn
    And player 1 send server he or she didn't go to island of skull
    And player 1 send server the score he or she earned in this turn
#    turn #1 for player 2
    And player 2 receive turn number 1
    And player 2 receive three players' score
    And player 2 print out three players' score
    And player 2 roll "DICE_DIAMOND,DICE_DIAMOND,DICE_COIN,DICE_COIN,DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_PARROT" with fortune card "CARD_CAPTAIN"
    And player 2 start a turn
    And player 2 press button 1 to score
    And player 2 get 800 points in this turn
    And player 2 send server he or she didn't go to island of skull
    And player 2 send server the score he or she earned in this turn
#    turn #1 for player 3
    And player 3 receive turn number 1
    And player 3 receive three players' score
    And player 3 print out three players' score
    And player 3 roll "DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN" with fortune card "CARD_COIN"
    And player 3 start a turn
    And player 3 will die
    And player 3 send server he or she didn't go to island of skull
    And player 3 send server the score he or she earned in this turn
#    turn #2 for player 1
    And player 1 receive turn number 2
    And player 1 receive three players' score
    And player 1 print out three players' score
    And player 1 roll "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_COIN,DICE_COIN,DICE_PARROT,DICE_SWORD" with fortune card "CARD_COIN"
    And player 1 start a turn
    And player 1 press button 1 to score
    And player 1 get 600 points in this turn
    And player 1 send server he or she didn't go to island of skull
    And player 1 send server the score he or she earned in this turn
#    turn #2 for player 2
    And player 2 receive turn number 2
    And player 2 receive three players' score
    And player 2 print out three players' score
    And player 2 start a turn
    And player 2 roll "DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_COIN,DICE_COIN" with fortune card "CARD_SORCERESS"
    And player 2 has not used Sorceress Card
    And player 2 press button 2 to re-roll
    And player 2 select die at index "0,1" to re-roll
    And player 2 get "DICE_SKULL,DICE_DIAMOND"
    And player 2 press button 1 to use Sorceress Card
    And player 2 select die at index "0,1" to re-roll
    And player 2 get "DICE_COIN,DICE_COIN"
    And player 2 press button 1 to score
    And player 2 get 600 points in this turn
    And player 2 send server he or she didn't go to island of skull
    And player 2 send server the score he or she earned in this turn
#    turn #2 for player 3
    And player 3 receive turn number 2
    And player 3 receive three players' score
    And player 3 print out three players' score
    And player 3 roll "DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_SKULL" with fortune card "CARD_COIN"
    And player 3 start a turn
    And player 3 press button 1 to score
    And player 3 get 400 points in this turn
    And player 3 send server he or she didn't go to island of skull
    And player 3 send server the score he or she earned in this turn
#    turn #3 for player 1
    And player 1 receive turn number 3
    And player 1 receive three players' score
    And player 1 print out three players' score
    And player 1 start a turn
    And player 1 roll "DICE_COIN,DICE_DIAMOND,DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD" with fortune card "CARD_CAPTAIN"
    And player 1 press button 2 to re-roll
    And player 1 select die at index "0,1,2" to re-roll
    And player 1 get "DICE_SWORD,DICE_SWORD,DICE_SWORD"
    And player 1 press button 1 to score
    And player 1 get 9000 points in this turn
    And player 1 send server he or she didn't go to island of skull
    And player 1 send server the score he or she earned in this turn
    And player 2 and player 3 can still play one more turn
#    turn #3 for player 2
    And player 2 receive turn number 3
    And player 2 receive three players' score
    And player 2 print out three players' score
    And player 2 roll "DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_SWORD,DICE_SWORD,DICE_PARROT,DICE_PARROT" with fortune card "CARD_DIAMOND"
    And player 2 start a turn
    And player 2 press button 1 to score
    And player 2 get 700 points in this turn
    And player 2 send server he or she didn't go to island of skull
    And player 2 send server the score he or she earned in this turn
#    turn #3 for player 3
    And player 3 receive turn number 3
    And player 3 receive three players' score
    And player 3 print out three players' score
    And player 3 roll "DICE_SWORD,DICE_SWORD,DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_PARROT,DICE_SKULL,DICE_SKULL" with fortune card "CARD_DIAMOND"
    And player 3 start a turn
    And player 3 press button 1 to score
    And player 3 get 100 points in this turn
    And player 3 send server he or she didn't go to island of skull
    And player 3 send server the score he or she earned in this turn
#    game is over
    Then player 1 receive turn number -1
    And player 2 receive turn number -1
    And player 3 receive turn number -1
    And player 1 receive three players' score
    And player 2 receive three players' score
    And player 3 receive three players' score
    And player 1 receive winner
    And player 2 receive winner
    And player 3 receive winner
    And server print out three players' score after one turn
    And stop server

