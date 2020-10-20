Feature: SkullsIsland Test in level 1b

  @Row_102
  Scenario: roll 5 skulls with FC: Captain => -1000 for all other players  (Captain doubles the penalty)
    Given a dieRoll "DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_COIN,DICE_COIN,DICE_COIN"
    And a fortune card "CARD_CAPTAIN"
    When player start a turn with given dieRoll and fortune card
    And player press button 2 to deduct other players' score immediately
    Then other players get -1000 points

  @Row_103
  Scenario: roll 2 skulls AND have a FC with two skulls: roll 2 skulls next roll, then 1 skull => -700
    Given a dieRoll "DICE_SKULL,DICE_SKULL,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN"
    And a fortune card "CARD_2_SKULL"
    When player start a turn with given dieRoll and fortune card
    And player press button 1 to re-roll in Skulls Island
    And player select die at index "2,3,4" to re-roll
    And player get "DICE_SKULL,DICE_SKULL,DICE_DIAMOND"
    And player press button 1 to re-roll in Skulls Island
    And player select die at index "4,5,6,7" to re-roll
    And player get "DICE_MONKEY,DICE_DIAMOND,DICE_SKULL,DICE_COIN"
    And player press button 2 to deduct other players' score immediately
    Then other players get -700 points

  @Row_104
  Scenario: roll 3 skulls AND have a FC with two skulls: roll no skulls next roll  => -500
    Given a dieRoll "DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN"
    And a fortune card "CARD_2_SKULL"
    When player start a turn with given dieRoll and fortune card
    And player press button 1 to re-roll in Skulls Island
    And player select die at index "3,4,5,6,7" to re-roll
    And player get "DICE_MONKEY,DICE_PARROT,DICE_SWORD,DICE_DIAMOND,DICE_SWORD"
    Then other players get -500 points

  @Row_105
  Scenario: roll 3 skulls AND have a FC with 1 skull: roll 1 skull next roll then none => -500
    Given a dieRoll "DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN"
    And a fortune card "CARD_1_SKULL"
    When player press button 1 to re-roll in Skulls Island
    And player select die at index "3,4,5,6,7" to re-roll
    And player get "DICE_MONKEY,DICE_PARROT,DICE_SWORD,DICE_SKULL,DICE_DIAMOND"
    And player press button 1 to re-roll in Skulls Island
    And player select die at index "3,4,5,7" to re-roll
    And player get "DICE_SWORD,DICE_MONKEY,DICE_COIN,DICE_PARROT"
    Then other players get -500 points

  @Row_106
  Scenario: show deduction received cannot make your score negative
    Given a dieRoll "DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_COIN,DICE_COIN,DICE_COIN"
    And a fortune card "CARD_CAPTAIN"
    When player start a turn with given dieRoll and fortune card
    And player press button 2 to deduct other players' score immediately
    Then other players get -1000 points
    And other players' scores are not negative

