package com.a1.util;

public class SeaBattleUtil {
    public static boolean hasSeaBattleCard(Card card) {
        return card.getName().equals(Const.CARD_2_SABRE_SEA_BATTLE) ||
                card.getName().equals(Const.CARD_3_SABRE_SEA_BATTLE) ||
                card.getName().equals(Const.CARD_4_SABRE_SEA_BATTLE);
    }
}
