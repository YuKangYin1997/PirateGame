Feature: MonkeyBusiness Test in Level 1b

  @Row_75
  Scenario: first roll gets 3 monkeys 3 parrots  1 skull 1 coin  SC = 1100  (i.e., sequence of of 6 + coin)
    Given a dieRoll "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_SKULL,DICE_COIN"
    And a fortune card "CARD_MONKEY_BUSINESS"
    When player press button 1 to score
    Then player get 1100 points in this turn

  @Row_76_77
  Scenario Outline: score over several rolls
  over several rolls: 2 monkeys, 1 parrot, 2 coins, 1 diamond, 2 swords         SC 400   (row 76)
  over several rolls get 3 monkeys, 4 parrots, 1 sword    SC 2000 (ie seq of 7)          (row 77)
    Given a dieRoll <dieRoll>
    And a fortune card <fortuneCard>
    When player start a turn with given dieRoll and fortune card
    And player press button 2 to re-roll
    And player select die at index <reRollIndexes1> to re-roll
    And player get <riggedDieRoll1>
    And player press button 2 to re-roll
    And player select die at index <reRollIndexes2> to re-roll
    And player get <riggedDieRoll2>
    And player press button 1 to score
    Then player get <expectedScore> points in this turn
    Examples:
      | dieRoll                                                                                      | fortuneCard            | reRollIndexes1 | riggedDieRoll1                                   | reRollIndexes2 | riggedDieRoll2                        | expectedScore |
      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_COIN" | "CARD_MONKEY_BUSINESS" | "2,3,4,5"      | "DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_DIAMOND" | "3,4"          | "DICE_SWORD,DICE_COIN"                | 400           |
      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_COIN" | "CARD_MONKEY_BUSINESS" | "3,5,6,7"      | "DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_DIAMOND" | "4,5,7"        | "DICE_PARROT,DICE_PARROT,DICE_PARROT" | 2000          |