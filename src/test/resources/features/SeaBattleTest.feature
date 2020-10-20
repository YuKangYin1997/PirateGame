Feature: SeaBattle Test in level 1b

  @DieOnFirstRoll
  Scenario Outline: die on first roll
  FC 2 swords, die on first roll   => lose 300 points (row 109)
  FC 3 swords, die on first roll   => lose 500 points (row 110)
  FC 4 swords, die on first roll   => lose 1000 points (row 111)
    Given a dieRoll <dieRoll>
    And a fortune card <fortuneCard>
    When player start a turn with given dieRoll and fortune card
    Then player will die
    And player get <expectedScore> points in this turn
    Examples:
      | dieRoll                                                                               | fortuneCard               | expectedScore |
      | "DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN" | "CARD_2_SABRE_SEA_BATTLE" | -300          |
      | "DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN" | "CARD_3_SABRE_SEA_BATTLE" | -500          |
      | "DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN" | "CARD_4_SABRE_SEA_BATTLE" | -1000         |

  @Row_112
  Scenario: show deduction received cannot make your score negative
    Given a dieRoll "DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_SKULL,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN"
    And a fortune card "CARD_4_SABRE_SEA_BATTLE"
    When player start a turn with given dieRoll and fortune card
    Then player will die
    And player's score is not negative

  @ScoreOnFirstRoll
  Scenario Outline: score on first roll
  FC 2 swords, roll 3 monkeys 2 swords, 1 coin, 2 parrots  SC = 100 + 100 + 300 = 500   (row 113)
  FC 3 swords, roll 3 monkeys 4 swords  SC = 100 + 200 + 500 = 800                      (row 116)
  FC 4 swords, roll 3 monkeys 4 swords 1 skull  SC = 100 +200 + 1000 = 1300             (row 119)
    Given a dieRoll <dieRoll>
    And a fortune card <fortuneCard>
    When player start a turn with given dieRoll and fortune card
    And player press button 1 to score
    Then player get <expectedScore> points in this turn
    Examples:
      | dieRoll                                                                                       | fortuneCard               | expectedScore |
      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD,DICE_COIN,DICE_PARROT,DICE_PARROT" | "CARD_2_SABRE_SEA_BATTLE" | 500           |
      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_PARROT" | "CARD_3_SABRE_SEA_BATTLE" | 800           |
      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SKULL"  | "CARD_4_SABRE_SEA_BATTLE" | 1300          |

  @ScoreOnSecondRoll
  Scenario Outline: score on second roll
  FC 2 swords, roll 4 monkeys 1 sword, 1 skull  2 parrots, then reroll 2 parrots and get 1 sword and 1 skull   SC = 200 +  300 = 500 (row 115)
    Given a dieRoll <dieRoll>
    And a fortune card <fortuneCard>
    When player start a turn with given dieRoll and fortune card
    And player press button 2 to re-roll
    And player select die at index <reRollIndexes> to re-roll
    And player get <riggedDieRoll>
    And player press button 1 to score
    Then player get <expectedScore> points in this turn
    Examples:
      | dieRoll                                                                                         | fortuneCard               | reRollIndexes | riggedDieRoll           | expectedScore |
      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SKULL,DICE_PARROT,DICE_PARROT" | "CARD_2_SABRE_SEA_BATTLE" | "6,7"         | "DICE_SWORD,DICE_SKULL" | 500           |

  @Row_118
  Scenario: FC 3 swords, roll 4 monkeys 2 swords 2 skulls
  then reroll 4 monkeys and get  2 skulls and 2 swords   -> DIE
    Given a dieRoll "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD,DICE_SKULL,DICE_SKULL"
    And a fortune card "CARD_3_SABRE_SEA_BATTLE"
    When player start a turn with given dieRoll and fortune card
    And player press button 2 to re-roll
    And player select die at index "0,1,2,3" to re-roll
    And player get "DICE_SKULL,DICE_SKULL,DICE_SWORD,DICE_SWORD"
    Then player will die

  @Row_120
  Scenario: FC 4 swords, roll 3 monkeys, 1 sword, 1 skull, 1 diamond, 2 parrots
  then re-roll 2 parrots and get 2 swords thus you have 3 monkeys, 3 swords, 1 diamond, 1 skull
  then re-roll 3 monkeys and get  1 sword and 2 parrots  SC = 200 + 100 + 1000 = 1300
    Given a dieRoll "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SKULL,DICE_DIAMOND,DICE_PARROT,DICE_PARROT"
    And a fortune card "CARD_4_SABRE_SEA_BATTLE"
    When player start a turn with given dieRoll and fortune card
    And player press button 2 to re-roll
    And player select die at index "6,7" to re-roll
    And player get "DICE_SWORD,DICE_SWORD"
    And player press button 2 to re-roll
    And player select die at index "0,1,2" to re-roll
    And player get "DICE_SWORD,DICE_PARROT,DICE_PARROT"
    And player press button 1 to score
    Then player get 1300 points in this turn


