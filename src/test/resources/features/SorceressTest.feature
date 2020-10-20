Feature: SorceressCard Test in Level 1b

  @Row_70
  Scenario: roll 2 skulls, re-roll one of them due to sorceress, then go to next round of turn
    Given a dieRoll "DICE_SKULL,DICE_SKULL,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN"
    And a fortune card "CARD_SORCERESS"
    When player start a turn with given dieRoll and fortune card
    And Sorceress Card has not been used
    And number of skulls in dieRoll is 2
    And player select die at index "1,2" to re-roll
    And player get "DICE_MONKEY,DICE_PARROT"
    Then Sorceress Card has been used
    And number of skulls in dieRoll is 1

  @Row_71
  Scenario: roll no skulls, then next round roll 1 skull and re-roll for it, then score
    Given a dieRoll "DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_COIN,DICE_COIN"
    And a fortune card "CARD_SORCERESS"
    When player start a turn with given dieRoll and fortune card
    And Sorceress Card has not been used
    And player press button 2 to re-roll
    And player select die at index "0,1" to re-roll
    And player get "DICE_SKULL,DICE_DIAMOND"
    And player press button 1 to use Sorceress Card
    And player select die at index "0,1" to re-roll
    And player get "DICE_COIN,DICE_COIN"
    And player press button 1 to score
    Then player get 600 points in this turn

  @Row_72
  Scenario: roll no skulls, then next round roll 1 skull and re-roll for it, then go to next round
    Given a dieRoll "DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_COIN,DICE_COIN"
    And a fortune card "CARD_SORCERESS"
    When player start a turn with given dieRoll and fortune card
    And Sorceress Card has not been used
    And next round roll one skull
    And player select die at index "0,1" to re-roll
    And player get "DICE_MONKEY,DICE_PARROT"
    Then Sorceress Card has been used
    And number of skulls in dieRoll is 0
