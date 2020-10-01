package com.a1.util;

import java.util.HashMap;
import java.util.Map;

public class CardUtil {
    public static final int cardTypeNumber;
    public static final Map<Integer, String> cardMap;

    static {
        cardTypeNumber = 11;
        cardMap = new HashMap<Integer, String>();
        cardMap.put(1, Const.CARD_COIN);
        cardMap.put(2, Const.CARD_DIAMOND);
        cardMap.put(3, Const.CARD_SORCERESS);
        cardMap.put(4, Const.CARD_CAPTAIN);
        cardMap.put(5, Const.CARD_TREASURE_CHEST);
        cardMap.put(6, Const.CARD_MONKEY_BUSINESS);
        cardMap.put(7, Const.CARD_1_SKULL);
        cardMap.put(8, Const.CARD_2_SKULL);
        cardMap.put(9, Const.CARD_2_SABRE_SEA_BATTLE);
        cardMap.put(10, Const.CARD_3_SABRE_SEA_BATTLE);
        cardMap.put(11, Const.CARD_4_SABRE_SEA_BATTLE);
    }

    /**
     * get index-th card from card deck
     * @param index
     * @return card's name
     */
    public static Card drawCard(int index) {
        String name = cardMap.get(index);
        return new Card(name);
    }
}

