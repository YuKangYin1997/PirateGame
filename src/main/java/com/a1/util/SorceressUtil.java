package com.a1.util;

import com.a1.game.Game;
import com.a1.game.GameMode;

import java.util.Scanner;

public class SorceressUtil {

    /**
     * 1. at least re-roll two dice
     * 2. at most re-roll one skull
     * 3. boundary check
     */
    public static boolean isReRollIndexValid(String[] dieRoll, int[] reRollIndexInt) {
        if (reRollIndexInt.length < 2) {
            return false;
        }

        int skullNum = 0;
        for (int index : reRollIndexInt) {
            if (index > 7) return false;
            if (skullNum >= 1 && dieRoll[index].equals(Const.DICE_SKULL)) return false;
            if (skullNum < 1 && dieRoll[index].equals(Const.DICE_SKULL)) {
                skullNum++;
            }
        }
        return true;
    }


    public static void usingSorceressCard(String[] dieRoll, Card card, Scanner sc) {
        boolean valid = false;
        int[] reRollIndexes = {};
        while (!valid) {
            System.out.println("Select the die to re-roll(0,1,2...) ");
            String[] strs = (sc.next()).replaceAll("\\s", "").split(",");

            // Print strs for JUnit Test
            if (GameMode.mode.equals(GameMode.JUNIT_TEST) || GameMode.mode.equals(GameMode.CUCUMBER_TEST)) {
                for (int i = 0; i < strs.length; i++) {
                    System.out.print(strs[i]);
                    if (i < strs.length - 1)
                        System.out.print(" ");
                }
                System.out.println();
            }

            reRollIndexes = Game.convertStringArrayToInt(strs);
            valid = SorceressUtil.isReRollIndexValid(dieRoll, reRollIndexes);
            if (!valid) {
                System.out.println("Sorry, the selected die are not valid...");
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
        card.setUsed(true);
    }
}
