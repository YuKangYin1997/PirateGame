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

public class SkullFortuneCardTest {
    @Test
    public void skullFortuneCardTest110() {
        // die by rolling one skull and having a FC with two skulls
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_2_SKULL);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(0, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Test
    public void skullFortuneCardTest111() {
        // die by rolling 2 skulls and having a FC with 1 skull
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_COIN, Const.DICE_COIN,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_1_SKULL);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(0, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }
}
