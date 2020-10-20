Feature: TreasureChest Test in Level 1b

  @Row_83
  Scenario: roll 3 parrots, 2 swords, 2 diamonds, 1 coin     put 2 diamonds and 1 coin in chest
  then re-roll 2 swords and get 2 parrots put 5 parrots in chest and take out 2 diamonds & coin
  then re-roll the 3 dice and get 1 skull, 1 coin and a parrot
  score 6 parrots + 1 coin for 1100 points
    Given a dieRoll "DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_DIAMOND,DICE_DIAMOND,DICE_COIN"
    And a fortune card "CARD_TREASURE_CHEST"
    When player start a turn with given dieRoll and fortune card
    And player press button 1 to use treasure chest
    And player press button 1 to put dice in treasure chest
    And player put die at index "5,6,7" to treasure chest
    And player press button 3 to exit treasure chest
    And player press button 2 to re-roll
    And player select die at index "3,4" to re-roll
    And player get "DICE_PARROT,DICE_PARROT"
    And player press button 1 to use treasure chest
    And player press button 1 to put dice in treasure chest
    And player put die at index "0,1,2,3,4" to treasure chest
    And player press button 2 to retrieve dice from treasure chest
    And player retrieve die at index "5,6,7" from treasure chest
    And player press button 3 to exit treasure chest
    And player press button 2 to re-roll
    And player select die at index "5,6,7" to re-roll
    And player get "DICE_SKULL,DICE_COIN,DICE_PARROT"
    And player press button 2 to not use treasure chest
    And player press button 1 to score
    Then player get 1100 points in this turn

  @Row_87
  Scenario: roll 2 skulls, 3 parrots, 3 coins   put 3 coins in chest
  then re-rolls 3 parrots and get 2 diamonds 1 coin    put coin in chest (now 4)
  then re-roll 2 diamonds and get 1 skull 1 coin     SC for chest only = 400 + 200 = 600
  also interface reports death & end of turn
    Given a dieRoll "DICE_SKULL,DICE_SKULL,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_COIN,DICE_COIN,DICE_COIN"
    And a fortune card "CARD_TREASURE_CHEST"
    When player press button 1 to use treasure chest
    And player press button 1 to put dice in treasure chest
    And player put die at index "5,6,7" to treasure chest
    And player press button 3 to exit treasure chest
    And player press button 2 to re-roll
    And player select die at index "2,3,4" to re-roll
    And player get "DICE_DIAMOND,DICE_DIAMOND,DICE_COIN"
    And player press button 1 to use treasure chest
    And player press button 1 to put dice in treasure chest
    And player put die at index "4" to treasure chest
    And player press button 3 to exit treasure chest
    And player press button 2 to re-roll
    And player select die at index "2,3" to re-roll
    And player get "DICE_SKULL,DICE_COIN"
    Then player will die
    And player get 600 points in this turn
    And player ends his or her turn

