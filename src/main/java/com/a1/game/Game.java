package com.a1.game;

import com.a1.util.Card;
import com.a1.util.Const;

import java.util.List;

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
}
