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

public class FullChestTest {
    @Test
    public void fullChestTest101() {
        // 3 monkeys, 3 swords, 1 diamond, 1 parrot FC: coin   => SC 400  (ie no bonus)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_SWORD,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_DIAMOND, Const.DICE_PARROT};
        Card card = new Card(Const.CARD_COIN);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("1".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(400, score);
    }

    @Test
    public void fullChestTest102() {
        // 3 monkeys, 3 swords, 2 coins FC: captain   => SC (100+100+200+500)*2 =  1800
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_SWORD,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_CAPTAIN);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("1".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(1800, score);
    }

    @Test
    public void fullChestTest103() {
        // 3 monkeys, 4 swords, 1 diamond, FC: coin   => SC 1000  (ie 100++200+100+100+bonus)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_SWORD,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_DIAMOND};
        Card card = new Card(Const.CARD_COIN);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("1".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(1000, score);
    }

    @Test
    public void fullChestTest107() {
        // FC: monkey business and RTS: 2 monkeys, 1 parrot, 2 coins, 3 diamonds   SC 1200 (bonus)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_PARROT, Const.DICE_COIN,
                Const.DICE_COIN, Const.DICE_DIAMOND, Const.DICE_DIAMOND, Const.DICE_DIAMOND};
        Card card = new Card(Const.CARD_MONKEY_BUSINESS);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("1".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(1200, score);
    }

}
