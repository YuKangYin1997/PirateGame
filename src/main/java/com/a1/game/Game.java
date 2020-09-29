package com.a1.game;

import com.a1.util.Card;
import com.a1.util.Const;

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
}
