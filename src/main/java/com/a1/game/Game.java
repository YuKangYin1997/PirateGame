package com.a1.game;

import com.a1.player.Player;
import com.a1.util.*;

import java.util.*;

public class Game {

    /**
     * calculate number of skull from a player's dieRoll and fortune card
     */
    public static int getSkullNum(String[] dieRoll, Card card) {
        return getSkullNumInDieRoll(dieRoll) + getSkullNumInCard(card);
    }

    /**
     * calculate number of skull from a player's dieRoll
     */
    public static int getSkullNumInDieRoll(String[] dieRoll) {
        int num = 0;
        for (String die : dieRoll) {
            if (die.equals(Const.DICE_SKULL))
                num++;
        }
        return num;
    }

    /**
     * calculate number of skull from a player's card
     */
    public static int getSkullNumInCard(Card card) {
        int num = 0;
        if (card.getName().equals(Const.CARD_1_SKULL)) {
            num += 1;
        }
        if (card.getName().equals(Const.CARD_2_SKULL)) {
            num += 2;
        }
        return num;
    }

    public static void printDieRoll(String[] dieRoll) {
        System.out.print("Current dieRoll : ");
        System.out.print("[");
        for (int i = 0; i < dieRoll.length; ++i) {
            System.out.print(dieRoll[i].substring(dieRoll[i].indexOf("_") + 1) + "(" + i + ")");
            if (i < dieRoll.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println("]");
    }

    public static void printCard(Card card) {
        System.out.print("Current Card : ");
        System.out.print("[");
        System.out.println(card.getName().substring(card.getName().indexOf("_") + 1) + "]");
    }

    public static void printScore(Player player) {
        System.out.println(
                "|---------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("| Scores for player : " + player.getName());
        System.out.println("| Total Score : " + player.getScore());
        System.out.println(
                "|---------------------------------------------------------------------------------------------------------------------------------------|");
    }

    /**
     * if a player accumulate less than 3 skulls and choose to re-roll, considering
     * 1. at least two dice are re-rolled
     * 2. if player has a treasure chest card, the dice in the chest shouldn't be re-rolled
     * 3. any skull can't be re-rolled
     * 4. boundary check
     * checks if re-roll indexes are valid
     */
    public static boolean isReRollIndexValid(String[] dieRoll, int[] reRollIndexes, Card card) {
        if (reRollIndexes.length < 2) {
            System.out.println("Sorry, re-roll in not allowed...");
            return false;
        }

        if (card.getName().equals(Const.CARD_TREASURE_CHEST)) {
            List<Integer> treasureChest = card.getTreasureChest();
            if (treasureChest != null) {
                for (int index : reRollIndexes) {
                    if (treasureChest.contains(index)) {
                        System.out.println("Sorry, re-roll in not allowed...");
                        return false;
                    }
                }
            }
        }

        // make sure skull shouldn't be re-rolled
        for (int index : reRollIndexes) {
            if (index > 7 || dieRoll[index].equals(Const.DICE_SKULL)) {
                System.out.println("Sorry, re-roll in not allowed...");
                return false;
            }
        }
        return true;
    }

    /**
     * if a player accumulate less than 3 skulls, check if re-rolled is allowed before s/he choose to
     * 1. if s/he has a treasure chest card, check if at least two none-skull dice are outside the chest
     */
    public static boolean isReRollAllowed(String[] dieRoll, Card card) {
        boolean allowed;
        if (card.getName().equals(Const.CARD_TREASURE_CHEST) && card.getTreasureChest().size() > 0) {
            List<Integer> chest = card.getTreasureChest();
            int notSkullNumber = 0;
            for (int i = 0; i < dieRoll.length; i++) {
                if (!dieRoll[i].equals(Const.DICE_SKULL) && !chest.contains(i))
                    notSkullNumber++;
            }
            if (notSkullNumber >= 2) allowed = true;
            else allowed = false;
        } else { // don't have treasure card
            allowed = true;
        }
        if (!allowed) System.out.println("Sorry, you are not allowed to re-roll...");
        return allowed;
    }

    /**
     * convert "0","1","2","3"... to 0,1,2,3...
     */
    public static int[] convertStringArrayToInt(String[] strings) {
        int[] ints = new int[strings.length];
        for (int i = 0; i < strings.length; ++i) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        return ints;
    }

    /**
     * re-roll selected dice and return a new dieRoll
     */
    public static void reRollDice(String[] dieRoll, int[] reRollIndexes) {
        for (int index : reRollIndexes) {
            reRollAt(dieRoll, index);
        }
    }

    /**
     *  [Only For JUnit Test] re-roll selected dice and return a new dieRoll
     */
    public static void riggedReRollDice(String[] dieRoll, int[] reRollIndexes, String[] targets) {
        for (int i = 0; i < reRollIndexes.length; i++) {
            riggedReRollAt(dieRoll, reRollIndexes[i], targets[i]);
        }
    }

    /**
     * re-roll index-th dice in the dieRoll
     */
    public static void reRollAt(String[] dieRoll, int index) {
        int rand = (int) (Math.random() * DiceUtil.diceTypeNumber + 1); // [1, 6]
        dieRoll[index] = DiceUtil.diceMap.get(rand);
    }

    /**
     * [Only For JUnit Test] re-roll index-th dice in the dieRoll
     */
    public static void riggedReRollAt(String[] dieRoll, int index, String target) {
        String actual = "";
        int rand = 0;
        while (!actual.equals(target)) {
            rand = (int) (Math.random() * DiceUtil.diceTypeNumber + 1); // [1, 6]
            actual = DiceUtil.diceMap.get(rand);
        }
        dieRoll[index] = DiceUtil.diceMap.get(rand);
    }

    /**
     * calculate the score a player earned in round
     * 1. his player is still alive at this time
     * 2. if player is dead, s/he can still get points with treasure chest, which is coded in TreasureChestUtil.java
     * @return the score a player earned in a round
     */
    public static int rollToScore(String[] dieRoll, Card card) {
        Set<Integer> set = new HashSet<Integer>();
        int faceValueOfDieRoll = getFaceValueOfDieRoll(dieRoll, set);
        System.out.println("faceValueOfDieRoll: " + faceValueOfDieRoll);

        int faceValueOfCard = getFaceValueOfCard(card);
        System.out.println("faceValueOfCard: " + faceValueOfCard);

        int scoreOfSequence = getScoreOfSequence(dieRoll, card, set);
        System.out.println("scoreOfSequence: " + scoreOfSequence);

        int bonusFromSeaBattle = SeaBattleUtil.calSeaBattleBonus(dieRoll, card, false, set);
        System.out.println("bonusFromSeaBattle: " + bonusFromSeaBattle);

        int fullChestBonus = getFullChestBonus(set);
        System.out.println("fullChestBonus:" + fullChestBonus);

        int sum = faceValueOfDieRoll + faceValueOfCard + scoreOfSequence + bonusFromSeaBattle + fullChestBonus;
        if (card.getName().equals(Const.CARD_CAPTAIN)) {
            sum *= 2;
        }
        return sum;
    }

    /**
     * get face value of a player's dieRoll
     * @param set store dice which has contribution to a player's score
     */
    public static int getFaceValueOfDieRoll(String[] dieRoll, Set<Integer> set) {
        int faceValue = 0;
        for (int i = 0; i < dieRoll.length; i++) {
            if (dieRoll[i].equals(Const.DICE_COIN) || dieRoll[i].equals(Const.DICE_DIAMOND)) {
                faceValue += 100;
                set.add(i);  // store this dice
            }
        }
        return faceValue;
    }

    /**
     * get face value of a player's card
     */
    public static int getFaceValueOfCard(Card card) {
        if (card.getName().equals(Const.CARD_COIN) || card.getName().equals(Const.CARD_DIAMOND))
            return 100;
        else
            return 0;
    }

    /**
     * get sequence value generated from a player's dieRoll and card
     * @param set store dice which has contribution to a player's score
     */
    public static int getScoreOfSequence(String[] dieRoll, Card card, Set<Integer> set) {
        int score = 0;
        // if a player has Monkey Business Card, convert monkey dice to parrot dice at first
        if (card.getName().equals(Const.CARD_MONKEY_BUSINESS)) {
            for (int i = 0; i < dieRoll.length; i++) {
                if (dieRoll[i].equals(Const.DICE_MONKEY))
                    dieRoll[i] = Const.DICE_PARROT;
            }
        }

        // calculate how many time a dice appeared
        // e.g. {"coin":2, "sword":2, "parrot":4, "skull":1}
        Map<String, Integer> dict = new HashMap<String, Integer>();
        for (String die : dieRoll) {
            if (dict.containsKey(die)) {
                dict.put(die, dict.get(die) + 1);
            } else {
                dict.put(die, 1);
            }
        }
        // if a player has coin or diamond card, it can also be used to form a sequence
        if (card.getName().equals(Const.CARD_COIN)) {
            if (dict.containsKey(Const.DICE_COIN)) {
                dict.put(Const.DICE_COIN, dict.get(Const.DICE_COIN) + 1);
            } else {
                dict.put(Const.DICE_COIN, 1);
            }
        }
        if (card.getName().equals(Const.CARD_DIAMOND)) {
            if (dict.containsKey(Const.DICE_DIAMOND)) {
                dict.put(Const.DICE_DIAMOND, dict.get(Const.DICE_DIAMOND) + 1);
            } else {
                dict.put(Const.DICE_DIAMOND, 1);
            }
        }

        for (Map.Entry<String, Integer> entry : dict.entrySet()) {
            Integer appearedNum = entry.getValue();
            String dieName = entry.getKey();

            // backtrack which die make this sequence
            if (appearedNum >= 3) {
                for (int i = 0; i < dieRoll.length; i++) {
                    if (dieRoll[i].equals(dieName)) {
                        set.add(i);
                    }
                }
            }

            // get score generated from sequence
            if (appearedNum == 3) {
                score += 100;
            } else if (appearedNum == 4) {
                score += 200;
            } else if (appearedNum == 5) {
                score += 500;
            } else if (appearedNum == 6) {
                score += 1000;
            } else if (appearedNum == 7) {
                score += 2000;
            } else if (appearedNum == 8 || appearedNum == 9) {
                score += 4000;
            } else { // appear 1,2 times
                score += 0;
            }
        }
        return score;
    }

    public static int getFullChestBonus(Set<Integer> set) {
        if (isFullChest(set))
            return 500;
        else
            return 0;
    }

    /**
     * if all dice generate score, the index of them will be in the set
     * so if the size=8, the full chest satisfied
     */
    public static boolean isFullChest(Set<Integer> set) {
        return set.size() == 8;
    }

    public static int calScore(int oldScore, int earnedScore) {
        oldScore += earnedScore;
        if (oldScore < 0)
            return 0;
        else
            return oldScore;
    }

    public static Player getWinner(Player[] players) {
        if (GameMode.mode.equals(GameMode.JUNIT_TEST)) {
            printScore(players[0]);
            printScore(players[1]);
            printScore(players[2]);
        }
        Player temp = players[1];
        if (players[0].getScore() >= players[1].getScore())
            temp = players[0];
        if (players[2].getScore() >= temp.getScore())
            return players[2];
        return temp;
    }

    public static boolean isEnded(Player[] players) {
        for (Player player : players) {
            if (player.getScore() >= 6000) {
                return true;
            }
        }
        return false;
    }

    /**
     * [only for short video]
     */
    public static String[] generate2Sword2Monkey1Parrot3Skull() {
        return new String[]{Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_MONKEY,
                Const.DICE_MONKEY, Const.DICE_PARROT, Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_SKULL};
    }

    /**
     * [only for short video]
     */
    public static String[] generate3Monkey2Sword2Parrot1Skull() {
        return new String[]{Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_SKULL};
    }

    /**
     * [only for short video]
     */
    public static String[] generate4Monkey2Sword2Parrot() {
        return new String[]{Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_PARROT, Const.DICE_PARROT};
    }

    /**
     * [only for short video]
     */
    public static String[] generate8Sword() {
        String[] dice = new String[8];
        Arrays.fill(dice, Const.DICE_SWORD);
        return dice;
    }

    /**
     * [only for short video]
     */
    public static String[] generate7Coin1Skull() {
        return new String[]{Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_SKULL};
    }

    public static String[] generate8Dice() {
        String[] dice = new String[8];
        for (int i = 0; i < 8; i++) {
            int rand = (int) (Math.random() * DiceUtil.diceTypeNumber + 1);
            String diceName = DiceUtil.diceMap.get(rand);
            dice[i] = diceName;
        }
        return dice;
    }

    public static Card drawCard() {
        int rand = (int) (Math.random() * CardUtil.cardTypeNumber + 1);
        return CardUtil.drawCard(rand);
    }


}
