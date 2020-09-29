package com.a1.util;

import java.util.HashMap;
import java.util.Map;

public class DiceUtil {
    public static final int diceTypeNumber;
    public static final Map<Integer, String> diceMap;

    static {
        diceTypeNumber = 6;
        diceMap = new HashMap<Integer, String>();
        diceMap.put(1, Const.DICE_SKULL);
        diceMap.put(2, Const.DICE_MONKEY);
        diceMap.put(3, Const.DICE_PARROT);
        diceMap.put(4, Const.DICE_SWORD);
        diceMap.put(5, Const.DICE_COIN);
        diceMap.put(6, Const.DICE_DIAMOND);
    }
}
