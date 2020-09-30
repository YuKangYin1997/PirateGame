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

public class MonkeyBusinessTest {
    @Test
    public void monkeyBusinessTest85() {
        // first roll gets 3 monkeys 3 parrots  1 skull 1 coin  SC = 1100  (i.e., sequence of of 6 + coin)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_PARROT,
                Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_SKULL, Const.DICE_COIN};
        Card card = new Card(Const.CARD_MONKEY_BUSINESS);
        InputStream in = new ByteArrayInputStream("1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(1100, score);
    }

    @Test
    public void monkeyBusinessTest86() {
        // over several rolls: 2 monkeys, 1 parrot, 2 coins, 1 diamond, 2 swords SC 400
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_COIN};
        Card card = new Card(Const.CARD_MONKEY_BUSINESS);
        InputStream in = new ByteArrayInputStream("2 2,3,4,5 DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_DIAMOND\n 2 3,4 DICE_SWORD,DICE_COIN\n 1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(400, score);
    }

    @Test
    public void monkeyBusinessTest87() {
        // over several rolls get 3 monkeys, 4 parrots, 1 sword  SC 2000 (ie seq of 7)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_COIN};
        Card card = new Card(Const.CARD_MONKEY_BUSINESS);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("2 3,5,6,7 DICE_PARROT,DICE_SWORD,DICE_SWORD,DICE_DIAMOND\n 2 4,5,7 DICE_PARROT,DICE_PARROT,DICE_PARROT\n 1".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(2000, score);
    }
}
