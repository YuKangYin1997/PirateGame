Feature: FullChest Test in Level 1b

  @ScoreOnFirstRoll
  Scenario Outline: score on first roll
  3 monkeys, 3 swords, 1 diamond, 1 parrot FC: coin   => SC 400  (ie no bonus)              (row 91)
  3 monkeys, 3 swords, 2 coins FC: captain   => SC (100+100+200+500)*2 =  1800              (row 92)
  3 monkeys, 4 swords, 1 diamond, FC: coin   => SC 1000  (ie 100++200+100+100+bonus)        (row 93)
  FC: monkey business and RTS: 2 monkeys, 1 parrot, 2 coins, 3 diamonds   SC 1200 (bonus)   (row 97)
    Given a dieRoll <dieRoll>
    And a fortune card <fortuneCard>
    When player start a turn with given dieRoll and fortune card
    And player press button 1 to score
    Then player get <expectedScore> points in this turn
    Examples:
      | dieRoll                                                                                          | fortuneCard            | expectedScore |
      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_DIAMOND,DICE_PARROT"  | "CARD_COIN"            | 400           |
      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_COIN,DICE_COIN"       | "CARD_CAPTAIN"         | 1800          |
      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_DIAMOND"   | "CARD_COIN"            | 1000          |
      | "DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_COIN,DICE_COIN,DICE_DIAMOND,DICE_DIAMOND,DICE_DIAMOND" | "CARD_MONKEY_BUSINESS" | 1200          |

  @Row_96
  Scenario: FC: 2 sword sea battle, first  roll:  4 monkeys, 1 sword, 2 parrots and a coin
  then re-roll 2 parrots and get coin and 2nd sword
  score is: 200 (coins) + 200 (monkeys) + 300 (swords of battle) + 500 (full chest) = 1200
    Given a dieRoll "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_PARROT,DICE_PARROT,DICE_COIN"
    And a fortune card "CARD_2_SABRE_SEA_BATTLE"
    When player start a turn with given dieRoll and fortune card
    And player press button 2 to re-roll
    And player select die at index "5,6" to re-roll
    And player get "DICE_COIN,DICE_SWORD"
    And player press button 1 to score
    Then player get 1200 points in this turn