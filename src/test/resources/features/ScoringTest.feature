Feature: Scoring Test in Level 1a

  @ScoreOnFirstRoll
  Scenario Outline: score on first roll
  score first roll with nothing but 2 diamonds and 2 coins and FC is captain (SC 800)             (row 43)
  score 2 sets of 3 (monkey, swords) in RTS on first roll   (SC 300)                              (row 45)
  score a set of 3 diamonds correctly (i.e., 400 points)   (SC 500)                               (row 47)
  score a set of 4 coins correctly (i.e., 200 + 400 points) with FC is a diamond (SC 700)         (row 48)
  score set of 3 swords and set of 4 parrots correctly on first roll (SC 400 because of FC)       (row 49)
  score set of 6 monkeys on first roll (SC 1100)                                                  (row 53)
  score set of 7 parrots on first roll (SC 2100)                                                  (row 54)
  score set of 8 coins on first roll (SC 5400)  seq of 8 + 9 coins +  full chest (if you have it) (row 55)
  score set of 8 coins on first roll and FC is diamond (SC 5400)                                  (row 56)
  score set of 8 swords on first roll and FC is captain (SC 4500x2 = 9000) if you have full chest (row 57)
  score a set of 4 monkeys and a set of 3 coins (including the COIN fortune card) (SC 600)        (row 63)
    Given a dieRoll <dieRoll>
    And a fortune card <fortuneCard>
    When player start a turn with given dieRoll and fortune card
    And player press button 1 to score
    Then player get <expectedScore> points in this turn
    Examples:
      | dieRoll                                                                                           | fortuneCard    | expectedScore |
      | "DICE_DIAMOND,DICE_DIAMOND,DICE_COIN,DICE_COIN,DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_PARROT"   | "CARD_CAPTAIN" | 800           |
      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SKULL,DICE_SKULL"      | "CARD_COIN"    | 300           |
      | "DICE_DIAMOND,DICE_DIAMOND,DICE_DIAMOND,DICE_SKULL,DICE_SWORD,DICE_SWORD,DICE_PARROT,DICE_PARROT" | "CARD_COIN"    | 500           |
      | "DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_SWORD,DICE_SWORD,DICE_PARROT,DICE_PARROT"           | "CARD_DIAMOND" | 700           |
      | "DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_SKULL"     | "CARD_COIN"    | 400           |
      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_SKULL"  | "CARD_COIN"    | 1100          |
      | "DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_MONKEY" | "CARD_COIN"    | 2100          |
      | "DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN"                 | "CARD_COIN"    | 5400          |
      | "DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN,DICE_COIN"                 | "CARD_DIAMOND" | 5400          |
      | "DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD"         | "CARD_CAPTAIN" | 9000          |
      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_COIN,DICE_COIN,DICE_PARROT,DICE_SWORD"      | "CARD_COIN"    | 600           |

  @ScoreOnSecondRoll
  Scenario Outline: score on second roll
  get set of 2 monkeys on first roll, get 3rd monkey on 2nd roll (SC 200 since FC is coin) (row 44)
  score 2 sets of 3 (monkey, parrots) in RTS using 2 rolls   (SC 300)                      (row 46)
  score a set of 2 diamonds over 2 rolls with FC is diamond (SC 400)                       (row 59)
  score a set of 3 diamonds over 2 rolls (SC 500)                                          (row 60)
  score a set of 3 coins over 2 rolls  (SC 600)                                            (row 61)
  score a set of 3 coins over 2 rolls  with FC is diamond (SC 500)                         (row 62)
    Given a dieRoll <dieRoll>
    And a fortune card <fortuneCard>
    When player start a turn with given dieRoll and fortune card
    And player press button 2 to re-roll
    And player select die at index <reRollIndexes> to re-roll
    And player get <riggedDieRoll>
    And player press button 1 to score
    Then player get <expectedScore> points in this turn
    Examples:
      | dieRoll                                                                                         | fortuneCard    | reRollIndexes | riggedDieRoll                                      | expectedScore |
      | "DICE_MONKEY,DICE_MONKEY,DICE_PARROT,DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_SKULL,DICE_SKULL"   | "CARD_COIN"    | "4,5"         | "DICE_MONKEY,DICE_SWORD"                           | 200           |
      | "DICE_MONKEY,DICE_PARROT,DICE_PARROT,DICE_SKULL,DICE_PARROT,DICE_PARROT,DICE_PARROT,DICE_SKULL" | "CARD_COIN"    | "1,2"         | "DICE_MONKEY,DICE_MONKEY"                          | 300           |
      | "DICE_DIAMOND,DICE_PARROT,DICE_PARROT,DICE_SKULL,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD" | "CARD_DIAMOND" | "4,5,6"       | "DICE_SKULL,DICE_MONKEY,DICE_DIAMOND"              | 400           |
      | "DICE_DIAMOND,DICE_PARROT,DICE_PARROT,DICE_SKULL,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD" | "CARD_COIN"    | "4,5,6,7"     | "DICE_SKULL,DICE_DIAMOND,DICE_MONKEY,DICE_DIAMOND" | 500           |
      | "DICE_COIN,DICE_PARROT,DICE_PARROT,DICE_SKULL,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD"    | "CARD_COIN"    | "4,5,6,7"     | "DICE_SKULL,DICE_COIN,DICE_MONKEY,DICE_COIN"       | 600           |
      | "DICE_COIN,DICE_PARROT,DICE_PARROT,DICE_SKULL,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD"    | "CARD_DIAMOND" | "4,5,6,7"     | "DICE_SKULL,DICE_COIN,DICE_MONKEY,DICE_COIN"       | 500           |

  @ScoreOnThirdRoll
  Scenario Outline: score on third roll
  score set of 3 coins+ FC and set of 4 swords correctly over several rolls (SC = 200+400+200 = 800) (row 50)
  same as previous row but with captain fortune card  (SC = (100 + + 300 + 200)*2 = 1200)            (row 51)
  score set of 5 swords over 3 rolls (SC 600)                                                        (row 52)
  score set of 8 monkeys over several rolls (SC 4600 because of FC is coin and full chest)           (row 58)
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
      | dieRoll                                                                                           | fortuneCard    | reRollIndexes1 | riggedDieRoll1                                    | reRollIndexes2 | riggedDieRoll2                                | expectedScore |
      | "DICE_DIAMOND,DICE_DIAMOND,DICE_SWORD,DICE_SWORD,DICE_PARROT,DICE_PARROT,DICE_MONKEY,DICE_MONKEY" | "CARD_COIN"    | "0,1"          | "DICE_COIN,DICE_COIN"                             | "4,5,6,7"      | "DICE_COIN,DICE_SWORD,DICE_SWORD,DICE_PARROT" | 800           |
      | "DICE_DIAMOND,DICE_DIAMOND,DICE_SWORD,DICE_SWORD,DICE_PARROT,DICE_PARROT,DICE_MONKEY,DICE_MONKEY" | "CARD_CAPTAIN" | "0,1"          | "DICE_COIN,DICE_COIN"                             | "4,5,6,7"      | "DICE_COIN,DICE_SWORD,DICE_SWORD,DICE_PARROT" | 1200          |
      | "DICE_DIAMOND,DICE_DIAMOND,DICE_SWORD,DICE_SWORD,DICE_PARROT,DICE_PARROT,DICE_MONKEY,DICE_SKULL"  | "CARD_COIN"    | "0,1"          | "DICE_SWORD,DICE_MONKEY"                          | "4,5,6"        | "DICE_SWORD,DICE_MONKEY,DICE_SWORD"           | 600           |
      | "DICE_COIN,DICE_COIN,DICE_DIAMOND,DICE_DIAMOND,DICE_MONKEY,DICE_MONKEY,DICE_SWORD,DICE_SWORD"     | "CARD_COIN"    | "0,1,2,3"      | "DICE_MONKEY,DICE_MONKEY,DICE_MONKEY,DICE_MONKEY" | "6,7"          | "DICE_MONKEY,DICE_MONKEY"                     | 4600          |

  @ReRollNotAllowed
  Scenario: get 7 swords on first roll, try to roll the 8 die by itself -> interface reports not allowed  (row 65)
    Given a dieRoll "DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_SWORD,DICE_MONKEY"
    And a fortune card "CARD_COIN"
    When player wants to re-roll the number 7 die
    Then interface report re-roll is not allowed