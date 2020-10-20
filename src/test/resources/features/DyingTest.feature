Feature: Dying Test in Level 1a

  @DieInFirstRoll
  Scenario: die in first roll
            die with 3 skulls on first roll  -> interface reports death & end of turn (row 38)
    Given a dieRoll "DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN"
    And a fortune card "CARD_COIN"
    When player start a turn with given dieRoll and fortune card
    Then player will die
    And player ends his or her turn

  @DieInThirdRoll
  Scenario Outline: die in second roll
                    roll 1 skull, 4 parrots, 3 swords, hold parrots, re-roll swords, get 2 skulls 1 sword  die (row 39)
                    roll 2 skulls, 4 parrots, 2 swords, hold parrots, re-roll swords, get 1 skull 1 sword  die (row 40)
    Given a dieRoll <dieRoll>
    And a fortune card <fortuneCard>
    When player start a turn with given dieRoll and fortune card
    When player press button 2 to re-roll
    And player select die at index <reRollIndexes> to re-roll
    And player get <riggedDieRoll>
    Then player will die
    Examples:
      | dieRoll                                                                                       | fortuneCard | reRollIndexes | riggedDieRoll                      |
      | "DICE_SKULL,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_SWORD" | "CARD_COIN" | "5,6,7"       | "DICE_SKULL,DICE_SKULL,DICE_SWORD" |
      | "DICE_SKULL,DICE_SKULL,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_SWORD,DICE_SWORD" | "CARD_COIN" | "6,7"         | "DICE_SKULL,DICE_SWORD"            |

  @DieInThirdRoll
  Scenario: die in third roll
            roll 1 skull, 4 parrots, 3 swords, hold parrots, re-roll swords, get 1 skull 2 monkeys
            re-roll 2 monkeys, get 1 skull 1 monkey and die (row 42)
    Given a dieRoll "DICE_SKULL,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_SWORD"
    And a fortune card "CARD_COIN"
    When player start a turn with given dieRoll and fortune card
    And player press button 2 to re-roll
    And player select die at index "5,6,7" to re-roll
    And player get "DICE_SKULL,DICE_MONKEY,DICE_MONKEY"
    And player press button 2 to re-roll
    And player select die at index "6,7" to re-roll
    And player get "DICE_SKULL,DICE_MONKEY"
    Then player will die


