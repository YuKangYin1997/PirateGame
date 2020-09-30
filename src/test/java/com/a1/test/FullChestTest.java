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
}
