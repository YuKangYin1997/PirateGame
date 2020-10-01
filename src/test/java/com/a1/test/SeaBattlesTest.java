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

public class SeaBattlesTest {
    @Test
    public void seaBattleTest118() {
        // FC 2 swords, die on first roll   => lose 300 points
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_SKULL,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_2_SABRE_SEA_BATTLE);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(-300, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Test
    public void seaBattleTest119() {
        // FC 3 swords, die on first roll   => lose 500 points
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_SKULL,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_3_SABRE_SEA_BATTLE);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(-500, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Test
    public void seaBattleTest120() {
        // FC 4 swords, die on first roll   => lose 1000 points
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_SKULL,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_4_SABRE_SEA_BATTLE);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(-1000, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Test
    public void seaBattleTest121() {
        // show deduction received cannot make your score negative
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_SKULL, Const.DICE_SKULL,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN};
        Card card = new Card(Const.CARD_4_SABRE_SEA_BATTLE);
        Player player = new Player(" ");
        Assert.assertEquals(0, player.getScore());
        InputStream in = new ByteArrayInputStream("".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));  // this player should lose 1000 points
        Assert.assertEquals(-1000, score);
        Assert.assertFalse(player.getScore() < 0);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Test
    public void seaBattleTest122() {
        // FC 2 swords, roll 3 monkeys 2 swords, 1 coin, 2 parrots  SC = 100 + 100 + 300 = 500
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_SWORD,
                Const.DICE_SWORD, Const.DICE_COIN, Const.DICE_PARROT, Const.DICE_PARROT};
        Card card = new Card(Const.CARD_2_SABRE_SEA_BATTLE);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("1".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(500, score);
    }

    @Test
    public void seaBattleTest124() {
        // FC 2 swords, roll 4 monkeys 1 sword, 1 skull  2 parrots
        // then reroll 2 parrots and get 1 sword and 1 skull   SC = 200 +  300 = 500
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY,
                Const.DICE_SWORD, Const.DICE_SKULL, Const.DICE_PARROT, Const.DICE_PARROT};
        Card card = new Card(Const.CARD_2_SABRE_SEA_BATTLE);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("2 6,7 DICE_SWORD,DICE_SKULL\n 1".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(500, score);
    }

    @Test
    public void seaBattleTest125() {
        // FC 3 swords, roll 3 monkeys 4 swords  SC = 100 + 200 + 500 = 800
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_SWORD,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_PARROT};
        Card card = new Card(Const.CARD_3_SABRE_SEA_BATTLE);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("1".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(800, score);
    }

    @Test
    public void seaBattleTest127() {
        // FC 3 swords, roll 4 monkeys 2 swords 2 skulls
        // then reroll 4 monkeys and get  2 skulls and 2 swords   -> DIE
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SKULL, Const.DICE_SKULL};
        Card card = new Card(Const.CARD_3_SABRE_SEA_BATTLE);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("2 0,1,2,3 DICE_SKULL,DICE_SKULL,DICE_SWORD,DICE_SWORD\n".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(-500, score);
        Assert.assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Test
    public void seaBattleTest128() {
        // FC 4 swords, roll 3 monkeys 4 swords 1 skull  SC = 100 +200 + 1000 = 1300
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_SWORD,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SKULL};
        Card card = new Card(Const.CARD_4_SABRE_SEA_BATTLE);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("1".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(1300, score);
    }

    @Test
    public void seaBattleTest131() {
        // FC 4 swords, roll 3 monkeys, 1 sword, 1 skull, 1 diamond, 2 parrots
        // then reroll 2 parrots and get 2 swords thus you have 3 monkeys, 3 swords, 1 diamond, 1 skull
        // then reroll 3 monkeys and get  1 sword and 2 parrots  SC = 200 + 100 + 1000 = 1300
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_SWORD,
                Const.DICE_SKULL, Const.DICE_DIAMOND, Const.DICE_PARROT, Const.DICE_PARROT};
        Card card = new Card(Const.CARD_4_SABRE_SEA_BATTLE);
        Player player = new Player(" ");
        InputStream in = new ByteArrayInputStream("2 6,7 DICE_SWORD,DICE_SWORD\n 2 0,1,2 DICE_SWORD,DICE_PARROT,DICE_PARROT\n 1".getBytes());
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(1300, score);
    }
}
