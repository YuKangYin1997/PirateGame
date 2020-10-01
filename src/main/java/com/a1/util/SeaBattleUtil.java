package com.a1.util;

import java.util.Set;

public class SeaBattleUtil {
    public static boolean hasSeaBattleCard(Card card) {
        return card.getName().equals(Const.CARD_2_SABRE_SEA_BATTLE) ||
                card.getName().equals(Const.CARD_3_SABRE_SEA_BATTLE) ||
                card.getName().equals(Const.CARD_4_SABRE_SEA_BATTLE);
    }

    /**
     * calculate sea battle bonus
     * 1. if player doesn't have sea battle card, return 0
     * 2. if player has sea battle card, and s/he is dead, s/he lose indicated bonus points
     * 3. if player has sea battle card, and s/he is alive, but doesn't have indicated sword, s/he lose indicated bonus points
     * 4. if player has sea battle card, and s/he is alive and has indicated sword, s/he get indicated bonus points
     */
    public static int calSeaBattleBonus(String[] dieRoll, Card card, boolean dead, Set<Integer> set) {
        if (!hasSeaBattleCard(card)) return 0;

        if (dead) {  // play is dead, so s/he loses the sea battle bonus
            if (card.getName().equals(Const.CARD_2_SABRE_SEA_BATTLE)) {
                return -300;
            } else if (card.getName().equals(Const.CARD_3_SABRE_SEA_BATTLE)) {
                return -500;
            } else if (card.getName().equals(Const.CARD_4_SABRE_SEA_BATTLE)) {
                return -1000;
            }
        } else {    // player is alive
            int swordNum = getSwordNum(dieRoll);
            if (card.getName().equals(Const.CARD_2_SABRE_SEA_BATTLE) && swordNum >= 2) { // win 2
                for (int i = 0; i < dieRoll.length; i++) {
                    if (dieRoll[i].equals(Const.DICE_SWORD))
                        set.add(i);
                }
                return 300;
            }
            else if (card.getName().equals(Const.CARD_2_SABRE_SEA_BATTLE) && swordNum < 2) { // lose 2
                return -300;
            }
            else if (card.getName().equals(Const.CARD_3_SABRE_SEA_BATTLE) && swordNum >= 3) { // win 3
                for (int i = 0; i < dieRoll.length; i++) {
                    if (dieRoll[i].equals(Const.DICE_SWORD))
                        set.add(i);
                }
                return 500;
            }
            else if (card.getName().equals(Const.CARD_3_SABRE_SEA_BATTLE) && swordNum < 3) {  // lose 3
                return -500;
            }
            else if (card.getName().equals(Const.CARD_4_SABRE_SEA_BATTLE) && swordNum >= 4) {  // win 4
                for (int i = 0; i < dieRoll.length; i++) {
                    if (dieRoll[i].equals(Const.DICE_SWORD))
                        set.add(i);
                }
                return 1000;
            }
            else if (card.getName().equals(Const.CARD_4_SABRE_SEA_BATTLE) && swordNum < 4) {  // lose 4
                return -1000;
            }
        }
        return 0;
    }

    public static int getSwordNum(String[] dieRoll) {
        int swordNum = 0;
        for (String die : dieRoll) {
            if (die.equals(Const.DICE_SWORD)) {
                swordNum++;
            }
        }
        return swordNum;
    }
}
