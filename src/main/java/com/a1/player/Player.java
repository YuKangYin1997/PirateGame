package com.a1.player;

import com.a1.game.Game;
import com.a1.game.GameMode;
import com.a1.util.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Scanner;

public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    private int playerId;
    private String name;
    private int score;
    private Player[] players;

    public Player(String name) {
        this.name = name;
        score = 0;
        players = new Player[3];
    }

    /**
     * check if a player is dead with his/her dieRoll and card
     * @return if a player is dead, return true; otherwise, return false
     */
    public boolean playerIsDead(String[] dieRoll, Card card) {
        int skullNumber = Game.getSkullNum(dieRoll, card);
        if (skullNumber >= 4) {
            return true;
        } else if (skullNumber == 3) {
            if (card.getName().equals(Const.CARD_SORCERESS) && card.isUsed()) {
                return true;
            } else if (card.getName().equals(Const.CARD_SORCERESS) && !card.isUsed()) {
                return false;
            } else {  // without SORCERESS
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * play for a turn, a turn may consist of many rounds
     * @return the score this player get in this turn
     */
    public int playTurn(String[] dieRoll, Card card, Scanner sc) {
        int roundNo = 0;
        while (true) {
            roundNo++;
            System.out.println("\n\n*** Round " + roundNo + " ***");
            Game.printDieRoll(dieRoll);
            Game.printCard(card);

            boolean dead = playerIsDead(dieRoll, card);
            int skullNum = Game.getSkullNum(dieRoll, card);
            if (dead) {  // player is dead
                if (skullNum >= 4) {
                    if (roundNo == 1) {

                    } else {
                        if (card.getName().equals(Const.CARD_TREASURE_CHEST)) {
                            if (card.getTreasureChest().size() == 0) {
                                System.out.println("Sorry, you die, and your treasure chest is empty. You get 0 point in your turn.");
                                return 0;
                            } else {
                                int score = TreasureChestUtil.rollToScore(dieRoll, card, new HashSet<Integer>());  // none-negative score
                                System.out.println("Sorry, you die. You get " + score + " points from your treasure chest in this turn.");
                                return score;
                            }
                        }
                    }
                } else if (skullNum == 3) {
                    if (!card.getName().equals(Const.CARD_SORCERESS)) {
                        if (card.getName().equals(Const.CARD_TREASURE_CHEST)) {
                            if (card.getTreasureChest().size() == 0) {
                                System.out.println("Sorry, you die, and your treasure chest is empty. You get 0 point in your turn.");
                                return 0;
                            } else {
                                int score = TreasureChestUtil.rollToScore(dieRoll, card, new HashSet<Integer>());  // none-negative score
                                System.out.println("Sorry, you die. You get " + score + " points from your treasure chest in this turn.");
                                return score;
                            }
                        } else if (SeaBattleUtil.hasSeaBattleCard(card)) {

                        } else {
                            System.out.println("Sorry, you die. You earned 0 points in your turn.");
                            return 0;
                        }
                    }
                    if (card.getName().equals(Const.CARD_SORCERESS) && card.isUsed()) {
                        System.out.println("Sorry, you die. You earned 0 points in your turn.");
                        return 0;
                    }
                }
            } else {  // player is alive
                if (card.getName().equals(Const.CARD_SORCERESS) && !card.isUsed()) {
                    if (skullNum == 3) {
                        System.out.println("\nSorry, you accumulate 3 skulls, but you can use your Sorceress Card to re-roll one of them.");
                        SorceressUtil.usingSorceressCard(dieRoll, card, sc);  // containing re-roll
                        continue; // jump to next round
                    } else if (skullNum == 2 || skullNum == 1) {
                        System.out.println("Do you want to use your Sorceress Card in this turn?");
                        System.out.println("\nSelect an action: ");
                        System.out.println("(1) yes");
                        System.out.println("(2) no");
                        int act = sc.nextInt();
                        if (act == 1) {
                            System.out.println("***");
                            SorceressUtil.usingSorceressCard(dieRoll, card, sc);
                            continue; // jump to next round
                        }
                    }
                } else if (card.getName().equals(Const.CARD_TREASURE_CHEST)) {
                    System.out.println("Do you want to use your Treasure Chest?");
                    System.out.println("\nSelect an action: ");
                    System.out.println("(1) yes");
                    System.out.println("(2) no");
                    int act = sc.nextInt();
                    if (act == 1) {
                        TreasureChestUtil.usingTreasureChest(dieRoll, card, sc);
                    }
                }
                // before a player choose to re-roll, check if re-rolled is allow
                boolean reRollAllowed = Game.isReRollAllowed(dieRoll, card);
                System.out.println("\nSelect an action: ");
                System.out.println("(1) Roll to Score");
                if (reRollAllowed) {
                    System.out.println("(2) Choose dice index to re-roll");
                }

                int act = sc.nextInt();
                if (act == 1) {  // roll to score
                    int score = Game.rollToScore(dieRoll, card);
                    System.out.println("Finish your turn, you earned " + score + " points.");
                    return score;
                } else if (act == 2 && reRollAllowed) {  // re-roll
                    boolean valid = false;
                    int[] reRollIndexes = {};
                    while (!valid) {
                        System.out.println("Select the die to re-roll(0,1,2...) ");
                        String[] strs = (sc.next()).replaceAll("\\s", "").split(",");
                        reRollIndexes = Game.convertStringArrayToInt(strs);
                        valid = Game.isReRollIndexValid(dieRoll, reRollIndexes, card);
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
                }
            }
        }
    }
}
