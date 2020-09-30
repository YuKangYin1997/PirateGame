package com.a1.test;

import com.a1.game.Game;
import com.a1.game.GameMode;
import com.a1.player.Player;
import com.a1.util.Card;
import com.a1.util.Const;
import com.a1.util.SorceressUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class SorceressTest {
    @Test
    public void sorceressTest80() {
        // roll 2 skulls, reroll one of them due to sorceress, then go to next round of turn
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_COIN, Const.DICE_COIN,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_SORCERESS);
        Assert.assertFalse(card.isUsed());
        int before = Game.getSkullNumInDieRoll(dieRoll);
        Assert.assertEquals(2, before);

        InputStream in = new ByteArrayInputStream("1,2 DICE_MONKEY,DICE_PARROT\n".getBytes());
        SorceressUtil.usingSorceressCard(dieRoll, card, new Scanner(in));
        int after = Game.getSkullNumInDieRoll(dieRoll);
        Assert.assertEquals(1, after);
        Assert.assertTrue(card.isUsed());
    }

    @Test
    public void sorceressTest81() {
        // roll no skulls, then next round roll 1 skull and reroll for it, then score
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_PARROT, Const.DICE_PARROT,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_SORCERESS);
        Assert.assertFalse(card.isUsed());
        InputStream in = new ByteArrayInputStream("2 0,1 DICE_SKULL,DICE_DIAMOND\n 1 0,1 DICE_COIN,DICE_COIN\n 1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(600, score);
        Assert.assertTrue(card.isUsed());
    }

    @Test
    public void sorceressTest82() {
        // roll no skulls, then next round roll 1 skull and reroll for it, then go to next round
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_PARROT, Const.DICE_PARROT,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_COIN, Const.DICE_COIN};
        int[] reRollIndexes = {0, 1};
        String[] targets = {Const.DICE_SKULL, Const.DICE_DIAMOND};
        Game.riggedReRollDice(dieRoll, reRollIndexes, targets);

        Card card = new Card(Const.CARD_SORCERESS);
        Assert.assertFalse(card.isUsed());
        int before = Game.getSkullNumInDieRoll(dieRoll);
        Assert.assertEquals(1, before);
        InputStream in = new ByteArrayInputStream("0,1 DICE_MONKEY,DICE_PARROT\n".getBytes());
        SorceressUtil.usingSorceressCard(dieRoll, card, new Scanner(in));
        int after = Game.getSkullNumInDieRoll(dieRoll);
        Assert.assertEquals(0, after);
        Assert.assertTrue(card.isUsed());
    }
}
