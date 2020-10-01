package com.a1.util;

import com.a1.game.Game;
import com.a1.game.GameMode;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TreasureChestUtil {
    /**
     * 1. skull can't be put in treasure chest
     * 2. the dice in treasure chest can't be put again
     * 3. indexes boundary check
     */
    public static boolean isPutIndexValid(String[] dieRoll, Card card, int[] dieIndexes) {
        List<Integer> chest = card.getTreasureChest();
        for (int dieIndex : dieIndexes) {
            if (dieIndex > 7 || dieRoll[dieIndex].equals(Const.DICE_SKULL) || chest.contains(dieIndex))
                return false;
        }
        return true;
    }

    /**
     * 1. the dice outside treasure chest can't be retrieved
     * 2. boundary check
     */
    public static boolean isRetrieveIndexValid(String[] dieRoll, Card card, int[] dieIndexes) {
        List<Integer> chest = card.getTreasureChest();
        for (int dieIndex : dieIndexes) {
            if (dieIndex > 7 || !chest.contains(dieIndex))
                return false;
        }
        return true;
    }

    /**
     * put dice in treasure chest
     */
    public static void putInTreasureChest(Card card, int[] dieIndexes) {
        List<Integer> chest = card.getTreasureChest();
        for (int dieIndex : dieIndexes) {
            chest.add(dieIndex);
        }
    }

    /**
     * remove dice from treasure chest
     */
    public static void retrieveFromTreasureChest(Card card, int[] dieIndexes) {
        List<Integer> chest = card.getTreasureChest();
        for (int dieIndex : dieIndexes) {
            chest.remove(chest.indexOf(dieIndex));
        }
    }

    /**
     * put is not allowed if all dice outside the chest are skull
     */
    public static boolean isPutAllowed(String[] dieRoll, Card card) {
        List<Integer> chest = card.getTreasureChest();
        for (int i = 0; i < dieRoll.length; ++i) {
            if (!chest.contains(i) && !dieRoll[i].equals(Const.DICE_SKULL))
                return true;
        }
        return false;
    }

    /**
     * retrieve is not allow if chest is empty
     */
    public static boolean isRetrieveAllowed(Card card) {
        return card.getTreasureChest().size() > 0;
    }

    public static void printTreasureChest(String[] dieRoll, Card card) {
        List<Integer> chest = card.getTreasureChest();
        if (chest.size() == 0) {
            System.out.println("*******************     Empty Treasure Chest     *******************");
        } else {
            System.out.println("*******************     Treasure Chest     *******************");
            for (int i = 0;i < chest.size(); ++i) {
                int dieIndex = chest.get(i);
                String dieName = dieRoll[dieIndex];
                System.out.print(dieName + "(" + dieIndex + ")");
                if (i < chest.size() - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            System.out.println("**************************************************************\n");
        }
    }

    public static void usingTreasureChest(String[] dieRoll, Card card, Scanner sc) {
        int action = 0;
        while (true) {
            System.out.println("\nSelect an action:");
            System.out.println("(1) put");
            System.out.println("(2) retrieve");
            System.out.println("(3) exit chest");
            action = sc.nextInt();
            // Print Action for JUnit Test
            if (GameMode.mode.equals(GameMode.JUNIT_TEST)) {
                System.out.println(action);
            }

            if (isPutAllowed(dieRoll, card) && action == 1) {  // put
                boolean putIndexValid = false;
                int[] dieIndexes = {};
                while (!putIndexValid) {
                    System.out.println("Select the die to put(0,1,2...) ");
                    String[] str = (sc.next()).replaceAll("\\s", "").split(",");

                    // Print input str for JUnit Test
                    if (GameMode.mode.equals(GameMode.JUNIT_TEST)) {
                        for (int i = 0; i < str.length; i++) {
                            System.out.print(str[i]);
                            if (i < str.length - 1) {
                                System.out.print(" ");
                            }
                        }
                        System.out.println();
                    }

                    dieIndexes = Game.convertStringArrayToInt(str);
                    putIndexValid = isPutIndexValid(dieRoll, card, dieIndexes);
                    if (!putIndexValid) {
                        System.out.println("Sorry, the selected die are not valid...");
                    }
                }
                putInTreasureChest(card, dieIndexes);
            } else if (isRetrieveAllowed(card) && action == 2) {  // retrieve
                boolean retrieveIndexValid = false;
                int[] dieIndexes = {};
                while (!retrieveIndexValid) {
                    System.out.println("Select the die to retrieve(0,1,2...) ");
                    String[] str = (sc.next()).replaceAll("\\s", "").split(",");

                    // Print input str for JUnit Test
                    if (GameMode.mode.equals(GameMode.JUNIT_TEST)) {
                        for (int i = 0; i < str.length; i++) {
                            System.out.print(str[i]);
                            if (i < str.length - 1) {
                                System.out.print(" ");
                            }
                        }
                        System.out.println();
                    }

                    dieIndexes = Game.convertStringArrayToInt(str);
                    retrieveIndexValid = isRetrieveIndexValid(dieRoll, card, dieIndexes);
                    if (!retrieveIndexValid) {
                        System.out.println("Sorry, the selected die are not valid...");
                    }
                }
                retrieveFromTreasureChest(card, dieIndexes);
            } else if (action == 3) {  // exit treasure chest
                break;
            } else {
                System.out.println("Sorry, action is not valid...");
            }
        }
    }

    /**
     * when a player is dead, s/he can still get score using treasure chest card
     * @param set store dice which has contribution to a player's score, useless in this function,
     *            just want to re-use the methods written in Game.java
     */
    public static int rollToScore(String[] dieRoll, Card card, Set<Integer> set) {
        List<Integer> chest = card.getTreasureChest();
        if (chest.size() == 0) return 0;

        // construct a new dieRoll, consisting all the dice in treasure chest
        String[] newDieRoll = new String[chest.size()];
        for (int i = 0; i < newDieRoll.length; i++) {
            newDieRoll[i] = dieRoll[chest.get(i)];
        }
        int faceValueOfDieRoll = Game.getFaceValueOfDieRoll(newDieRoll, set);
        System.out.println("faceValueOfDieRoll: " + faceValueOfDieRoll);

        int scoreOfSequence = Game.getScoreOfSequence(newDieRoll, card, set);
        System.out.println("scoreOfSequence: "+ scoreOfSequence);

        // no faceValueOfCard
        // no sea battle bonus
        // no full chest
        // no double score
        return faceValueOfDieRoll + scoreOfSequence;
    }
}

