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

public class SkullsIslandTest {
    @Test
    public void skullsIslandTest112() {
        // roll 2 skulls AND have a FC with two skulls: roll 2 skulls next roll, then 1 skull => -700
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_COIN, Const.DICE_COIN,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_2_SKULL);
        Player player = new Player(" ");
        player.initializeGamePlayers();
        InputStream in = new ByteArrayInputStream("1 2,3,4 DICE_SKULL,DICE_SKULL,DICE_DIAMOND\n 1 4,5,6,7 DICE_MONKEY,DICE_DIAMOND,DICE_SKULL,DICE_COIN\n 2".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(-700, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Test
    public void skullsIslandTest113() {
        // roll 3 skulls AND have a FC with two skulls: roll no skulls next roll  => -500
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_COIN,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_2_SKULL);
        Player player = new Player(" ");
        player.initializeGamePlayers();
        InputStream in = new ByteArrayInputStream("1 3,4,5,6,7 DICE_MONKEY,DICE_PARROT,DICE_SWORD,DICE_DIAMOND,DICE_SWORD\n".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(-500, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Test
    public void skullsIslandTest114() {
        // roll 3 skulls AND have a FC with 1 skull: roll 1 skull next roll then none => -500
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_COIN,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_1_SKULL);
        Player player = new Player(" ");
        player.initializeGamePlayers();
        InputStream in = new ByteArrayInputStream("1 3,4,5,6,7 DICE_MONKEY,DICE_PARROT,DICE_SWORD,DICE_SKULL,DICE_DIAMOND\n 1 3,4,5,7 DICE_SWORD,DICE_MONKEY,DICE_COIN,DICE_PARROT\n".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(-500, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Test
    public void skullsIslandTest115() {
        // show deduction received cannot make your score negative
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_COIN, Const.DICE_COIN,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_2_SKULL);
        Player player = new Player(" ");
        player.initializeGamePlayers();
        player.setPlayerId(0);
        Player[] players = player.getPlayers();
        Assert.assertEquals(0, players[1].getScore());
        Assert.assertEquals(0, players[2].getScore());

        InputStream in = new ByteArrayInputStream("1 2,3,4 DICE_SKULL,DICE_SKULL,DICE_DIAMOND\n 1 4,5,6,7 DICE_MONKEY,DICE_DIAMOND,DICE_SKULL,DICE_COIN\n 2".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));  // other opponents should lose 700 points
        Assert.assertEquals(-700, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
        Assert.assertFalse(players[1].getScore() < 0);
        Assert.assertFalse(players[2].getScore() < 0);
    }
}
