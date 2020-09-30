package com.a1.test;

import com.a1.game.GameMode;
import com.a1.player.Player;
import com.a1.util.Card;
import com.a1.util.Const;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class TreasureChestTest {
    @Test
    public void treasureChestTest93() {
        // roll 3 parrots, 2 swords, 2 diamonds, 1 coin, put 2 diamonds and 1 coin in chest
        // then reroll 2 swords and get 2 parrots put 5 parrots in chest and take out 2 diamonds & coin
        // then reroll the 3 dice and get 1 skull, 1 coin and a parrot
        // score 6 parrots + 1 coin for 1100 points
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_SWORD,
                Const.DICE_SWORD, Const.DICE_DIAMOND, Const.DICE_DIAMOND, Const.DICE_COIN};
        Card card = new Card(Const.CARD_TREASURE_CHEST);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("1 1 5,6,7 3 2 3,4 DICE_PARROT,DICE_PARROT\n 1 1 0,1,2,3,4 2 5,6,7 3 2 5,6,7 DICE_SKULL,DICE_COIN,DICE_PARROT\n 2 1".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(1100, score);
    }

    @Test
    public void treasureChestTest9798() {
        // roll 2 skulls, 3 parrots, 3 coins   put 3 coins in chest
        // then rerolls 3 parrots and get 2 diamonds 1 coin, put coin in chest (now 4)
        // then reroll 2 diamonds and get 1 skull 1 coin, SC for chest only = 400 + 200 = 600
        // also interface reports death & end of turn
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_PARROT, Const.DICE_PARROT,
                Const.DICE_PARROT, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_TREASURE_CHEST);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("1 1 5,6,7 3 2 2,3,4 DICE_DIAMOND,DICE_DIAMOND,DICE_COIN\n 1 1 4 3 2 2,3 DICE_SKULL,DICE_COIN\n".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(600, score);
    }
}
