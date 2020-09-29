package com.a1.test;

import com.a1.game.Game;
import com.a1.game.GameMode;
import com.a1.player.Player;
import com.a1.util.Card;
import com.a1.util.Const;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Part1Test {
    @Test
    public void deathTest48() {
        // die with 3 skulls on first roll  -> interface reports death & end of turn
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_COIN,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
        Assert.assertEquals(0, score);
    }

    @Test
    public void deathTest49() {
        // roll 1 skull, 4 parrots, 3 swords, hold parrots, reroll swords, get 2 skulls 1 sword, die
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_PARROT,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SWORD};  // first roll
        Card card = new Card(Const.CARD_COIN);
        Player player = new Player(" ");

        InputStream in = new ByteArrayInputStream("2 5,6,7 DICE_SKULL,DICE_SKULL,DICE_SWORD\n".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(0, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Test
    public void deathTest50() {
        // roll 2 skulls, 4 parrots, 2 swords, hold parrots, reroll swords, get 1 skull 1 sword  die
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_PARROT,
                Const.DICE_PARROT, Const.DICE_SWORD, Const.DICE_SWORD};  // first roll
        Card card = new Card(Const.CARD_COIN);
        Player player = new Player(" ");

        InputStream in = new ByteArrayInputStream("2 6,7 DICE_SKULL,DICE_SWORD\n".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(0, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Test
    public void deathTest51() {
        // roll 1 skull, 4 parrots, 3 swords, hold parrots, reroll swords, get 1 skull 2 monkeys
        // reroll 2 monkeys, get 1 skull 1 monkey and die
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_PARROT,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SWORD};
        Card card = new Card(Const.CARD_COIN);
        Player player = new Player(" ");

        InputStream in = new ByteArrayInputStream("2 5,6,7 DICE_SKULL,DICE_MONKEY,DICE_MONKEY\n 2 6,7 DICE_SKULL,DICE_MONKEY\n".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(0, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Test
    public void reRollIndexValidTest75() {
        // get 7 swords on first roll, try to roll the 8 die by itself -> interface reports not allowed
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SWORD,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_MONKEY};
        Card card = new Card(Const.CARD_COIN);
        int[] reRollIndexes = {7};
        boolean reRollAllowed = Game.isReRollIndexValid(dieRoll, reRollIndexes, card);
        Assert.assertFalse(reRollAllowed);
    }
}
