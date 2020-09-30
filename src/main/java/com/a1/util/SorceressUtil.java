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

            reRollIndexes = Game.convertStringArrayToInt(strs);
            valid = SorceressUtil.isReRollIndexValid(dieRoll, reRollIndexes);
            if (!valid) {
                System.out.println("Sorry, the selected die are not valid...");
            }
        }

        if (GameMode.mode.equals(GameMode.JUNIT_TEST)) {  // only for JUnit test
            String targetStr = sc.nextLine();
            String[] targets = targetStr.trim().split(",");
            Game.riggedReRollDice(dieRoll, reRollIndexes, targets);
            System.out.println("****** After Re-Roll ******");
            Game.printDieRoll(dieRoll);
            System.out.println("***************************");
        } else if (GameMode.mode.equals(GameMode.REAL_GAME)) {
            Game.reRollDice(dieRoll, reRollIndexes);
        }
        card.setUsed(true);
    }
}
