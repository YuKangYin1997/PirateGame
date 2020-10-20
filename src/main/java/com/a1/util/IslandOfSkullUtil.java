package com.a1.util;

import com.a1.game.Game;
import com.a1.game.GameMode;

import java.util.Scanner;

public class IslandOfSkullUtil {

    /**
     * checks if re-roll indexes are valid
     * 1. a player should at least re-roll two dice
     * 2. none of the re-rolled dice can be skull
     * 3. boundary
     */
    public static boolean isReRollIndexValid(String[] dieRoll, int[] reRollIndexInt) {
        if (reRollIndexInt.length < 2) {
            return false;
        }

        for (int index : reRollIndexInt) {
            if (index > 7 || dieRoll[index].equals(Const.DICE_SKULL)) {
                return false;
            }
        }
        return true;
    }

    /**
     * before a player choose to re-roll in island of skull, checks if re-rolled is allowed
     * 1. player should have a least two dice which are not skull
     * 2. if s/he has already re-rolled in last roll, s/he should at least get a new skull
     * @param reRollIndexes reRollIndex of last roll
     * @param curDieRoll die roll after last roll
     */
    public static boolean isReRollAllowed(int[] reRollIndexes, String[] curDieRoll) {
        int skullNum = Game.getSkullNumInDieRoll(curDieRoll);
        int notSkullNum = 8 - skullNum;
        if (notSkullNum < 2)
            return false;

        // reRollIndexes == null occurs in this situation:
        // a player accumulate 4 or more than 4 skull in his/her first roll,
        // so s/he goes to island of skull, and at this time reRollIndex == null

        // reRollIndexes != null occurs in this situation:
        // a player goes to island of skull and has at least re-rolled once,
        // so reRollIndexes is the dice s/he chose to re-roll last time
        if (reRollIndexes != null) {
            int newSkullNumber = getNewSkullNumber(curDieRoll, reRollIndexes);
            if (newSkullNumber < 1)
                return false;
        }
        return true;
    }

    /**
     * get the number of newly generated skull from last re-roll
     */
    public static int getNewSkullNumber(String[] curDieRoll, int[] reRollIndexes) {
        int newSkullNumber = 0;
        for (int index : reRollIndexes) {
            if (curDieRoll[index].equals(Const.DICE_SKULL)) {
                newSkullNumber++;
            }
        }
        return newSkullNumber;
    }

    /**
     * a player go to island of skull and deduct other players' score
     * @return the score other players lose (using negative number to identify from earned score)
     */
    public static int deductScore(String[] dieRoll, Card card, Scanner sc) {
        System.out.println("\n\n*********Welcome to Island of Skull***********");

        // check whether re-roll is allowed when the player enters Skull Island
        boolean reRollAllowed = IslandOfSkullUtil.isReRollAllowed(null, dieRoll);
        boolean exitIsland = false;  // true only if player wants to deducts other's score

        if (!reRollAllowed) {
            System.out.println("Sorry, re-roll is not allowed any more...");
            return calDeductScore(dieRoll, card);
        }

        while (reRollAllowed && !exitIsland) {
            boolean actionNumValid = false;
            int act = 0;
            while (!actionNumValid) {
                System.out.println("\nSelect an action: ");
                System.out.println("(1) Choose dice number to roll again");
                System.out.println("(2) Deduct others' score");
                act = sc.nextInt();

                // Print act Number in JUnit Test
                if (GameMode.mode.equals(GameMode.JUNIT_TEST) || GameMode.mode.equals(GameMode.CUCUMBER_TEST)) {
                    System.out.println(act);
                }

                if (act == 1 || act == 2) {
                    actionNumValid = true;
                } else {
                    System.out.println("Sorry, action number is invalid...");
                }
            }

            if (act == 1) { // re-roll
                boolean reRollIndexValid = false;
                int[] reRollIndexes = {};
                while (!reRollIndexValid) {
                    System.out.println("Select the die to re-roll(0,1,2...) ");
                    String[] str = (sc.next()).replaceAll("\\s", "").split(",");

                    // REFACTOR
                    if (GameMode.mode.equals(GameMode.JUNIT_TEST) || GameMode.mode.equals(GameMode.CUCUMBER_TEST)) {
                        for (int i = 0; i < str.length; i++) {
                            System.out.print(str[i]);
                            if (i < str.length - 1)
                                System.out.print(" ");
                        }
                        System.out.println();
                    }

                    reRollIndexes = Game.convertStringArrayToInt(str);
                    reRollIndexValid = IslandOfSkullUtil.isReRollIndexValid(dieRoll, reRollIndexes);
                    if (!reRollIndexValid) {
                        System.out.println("Sorry, selected die are not valid...");
                    }
                }

                Game.reRollDice(dieRoll, reRollIndexes);
                System.out.println("After Real Re-Roll:");
                Game.printDieRoll(dieRoll);
                // Rigging the game
                if (GameMode.mode.equals(GameMode.JUNIT_TEST) || GameMode.mode.equals(GameMode.CUCUMBER_TEST)) {
                    String targetStr = sc.nextLine();
                    String[] targets = targetStr.trim().split(",");
                    Game.riggedReRollDice(dieRoll, reRollIndexes, targets);
                    System.out.println("After Rigging Re-Roll:");
                    Game.printDieRoll(dieRoll);
                }

                // after player's re-roll checks if s/he is able to re-roll
                reRollAllowed = IslandOfSkullUtil.isReRollAllowed(reRollIndexes, dieRoll);
                if (!reRollAllowed) {
                    System.out.println("Sorry, re-roll is not allowed any more...");
                }
            } else {  // act = 2 exit island of skull and deduct others' score
                exitIsland = true;
            }
        }

        // deduct other players' score
        return calDeductScore(dieRoll, card);
    }

    /**
     * deduct other opponents' score
     * @return negative number
     */
    public static int calDeductScore(String[] dieRoll, Card card) {
        int skullNumber = Game.getSkullNum(dieRoll, card);

        int deductScore = skullNumber * (-100);
        if (card.getName().equals(Const.CARD_CAPTAIN)) {
            deductScore *= 2;
        }
        return deductScore;
    }

}
