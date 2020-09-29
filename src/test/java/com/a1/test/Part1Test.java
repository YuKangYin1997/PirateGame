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
}
