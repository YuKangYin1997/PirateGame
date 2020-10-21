Feature: One Turn Game Test

  @Row_126
  Scenario: game starts, each player plays a turn with scores being updated correctly for each player
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
    When player 1 receive turn number 1
    And player 1 receive three players' score
    And player 1 print out three players' score
    And player 1 roll "DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_SKULL,DICE_COIN" with fortune card "CARD_COIN"
    And player 1 start a turn
    And player 1 press button 1 to score
    And player 1 get 200 points in this turn
    And player 1 send server he or she didn't go to island of skull
    And player 1 send server the score he or she earned in this turn
    And player 2 receive turn number 1
    And player 2 receive three players' score
    And player 2 print out three players' score
    And player 2 roll "DICE_DIAMOND,DICE_DIAMOND,DICE_COIN,DICE_COIN,DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_PARROT" with fortune card "CARD_CAPTAIN"
    And player 2 start a turn
    And player 2 press button 1 to score
    And player 2 get 800 points in this turn
    And player 2 send server he or she didn't go to island of skull
    And player 2 send server the score he or she earned in this turn
    And player 3 receive turn number 1
    And player 3 receive three players' score
    And player 3 print out three players' score
    And player 2 roll "DICE_DIAMOND,DICE_DIAMOND,DICE_DIAMOND,DICE_SKULL,DICE_SWORD,DICE_SWORD,DICE_PARROT,DICE_PARROT" with fortune card "CARD_COIN"
    And player 3 start a turn
    And player 3 press button 1 to score
    And player 3 get 500 points in this turn
    And player 3 send server he or she didn't go to island of skull
    And player 3 send server the score he or she earned in this turn
    Then server print out three players' score after one turn
    And stop server
