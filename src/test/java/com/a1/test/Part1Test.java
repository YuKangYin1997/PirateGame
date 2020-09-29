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
    public void scoreTest53() {
        // score first roll with nothing but 2 diamonds and 2 coins and FC is captain (SC 800)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_DIAMOND, Const.DICE_DIAMOND, Const.DICE_COIN, Const.DICE_COIN,
                Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_PARROT, Const.DICE_PARROT};
        Card card = new Card(Const.CARD_CAPTAIN);
        InputStream in = new ByteArrayInputStream("1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(800, score);
    }

    @Test
    public void scoreTest54() {
        // get set of 2 monkeys on first roll, get 3rd monkey on 2nd roll (SC 200 since FC is coin)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_PARROT, Const.DICE_PARROT,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SKULL, Const.DICE_SKULL};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("2 4,5 DICE_MONKEY,DICE_SWORD\n 1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(200, score);
    }

    @Test
    public void scoreTest55() {
        // score 2 sets of 3 (monkey, swords) in RTS on first roll   (SC 300)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_SKULL,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SKULL};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(300, score);
    }

    @Test
    public void scoreTest56() {
        // score 2 sets of 3 (monkey, parrots) in RTS using 2 rolls   (SC 300)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_SKULL,
                Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_SKULL};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("2 1,2 DICE_MONKEY,DICE_MONKEY\n 1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(300, score);
    }

    @Test
    public void scoreTest57() {
        // score a set of 3 diamonds correctly (i.e., 400 points)   (SC 500)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_DIAMOND, Const.DICE_DIAMOND, Const.DICE_DIAMOND, Const.DICE_SKULL,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_PARROT, Const.DICE_PARROT};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(500, score);
    }

    @Test
    public void scoreTest58() {
        // score a set of 4 coins correctly (i.e., 200 + 400 points) with FC is a diamond (SC 700)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN, Const.DICE_COIN,
                Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_PARROT, Const.DICE_PARROT};
        Card card = new Card(Const.CARD_DIAMOND);
        InputStream in = new ByteArrayInputStream("1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(700, score);
    }

    @Test
    public void scoreTest59() {
        // score set of 3 swords and set of 4 parrots correctly on first roll (SC 400 because of FC)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_SWORD, Const.DICE_PARROT,
                Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_SKULL};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(400, score);
    }

    @Test
    public void scoreTest60() {
        // score set of 3 coins+ FC and set of 4 swords correctly over several rolls (SC = 200+400+200 = 800)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_DIAMOND, Const.DICE_DIAMOND, Const.DICE_SWORD, Const.DICE_SWORD,
                Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_MONKEY, Const.DICE_MONKEY};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("2 0,1 DICE_COIN,DICE_COIN\n 2 4,5,6,7 DICE_COIN,DICE_SWORD,DICE_SWORD,DICE_PARROT\n 1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(800, score);
    }

    @Test
    public void scoreTest61() {
        // same as previous row but with captain fortune card  (SC = (100 + + 300 + 200)*2 = 1200)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_DIAMOND, Const.DICE_DIAMOND, Const.DICE_SWORD, Const.DICE_SWORD,
                Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_MONKEY, Const.DICE_MONKEY};
        Card card = new Card(Const.CARD_CAPTAIN);
        InputStream in = new ByteArrayInputStream("2 0,1 DICE_COIN,DICE_COIN\n 2 4,5,6,7 DICE_COIN,DICE_SWORD,DICE_SWORD,DICE_PARROT\n 1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(1200, score);
    }

    @Test
    public void scoreTest62() {
        // score set of 5 swords over 3 rolls (SC 600)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_DIAMOND, Const.DICE_DIAMOND, Const.DICE_SWORD, Const.DICE_SWORD,
                Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_MONKEY, Const.DICE_SKULL};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("2 0,1 DICE_SWORD,DICE_MONKEY\n 2 4,5,6 DICE_SWORD,DICE_MONKEY,DICE_SWORD\n 1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(600, score);
    }

    @Test
    public void scoreTest63() {
        // score set of 6 monkeys on first roll (SC 1100)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY,
                Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_PARROT, Const.DICE_SKULL};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(1100, score);
    }

    @Test
    public void scoreTest64() {
        // score set of 7 parrots on first roll (SC 2100)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_PARROT,
                Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_MONKEY};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(2100, score);
    }

    @Test
    public void scoreTest69() {
        // score a set of 2 diamonds over 2 rolls with FC is diamond (SC 400)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_DIAMOND, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_SKULL,
                Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_SWORD, Const.DICE_SWORD};
        Card card = new Card(Const.CARD_DIAMOND);
        InputStream in = new ByteArrayInputStream("2 4,5,6 DICE_SKULL,DICE_MONKEY,DICE_DIAMOND\n 1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(400, score);
    }

    @Test
    public void scoreTest70() {
        // score a set of 3 diamonds over 2 rolls (SC 500)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_DIAMOND, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_SKULL,
                Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_SWORD, Const.DICE_SWORD};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("2 4,5,6,7 DICE_SKULL,DICE_DIAMOND,DICE_MONKEY,DICE_DIAMOND\n 1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(500, score);
    }

    @Test
    public void scoreTest71() {
        // score a set of 3 coins over 2 rolls  (SC 600)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_COIN, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_SKULL,
                Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_SWORD, Const.DICE_SWORD};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("2 4,5,6,7 DICE_SKULL,DICE_COIN,DICE_MONKEY,DICE_COIN\n 1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(600, score);
    }

    @Test
    public void scoreTest72() {
        // score a set of 3 coins over 2 rolls  with FC is diamond (SC 500)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_COIN, Const.DICE_PARROT, Const.DICE_PARROT, Const.DICE_SKULL,
                Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_SWORD, Const.DICE_SWORD};
        Card card = new Card(Const.CARD_DIAMOND);
        InputStream in = new ByteArrayInputStream("2 4,5,6,7 DICE_SKULL,DICE_COIN,DICE_MONKEY,DICE_COIN\n 1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(500, score);
    }

    @Test
    public void scoreTest73() {
        // score a set of 4 monkeys and a set of 3 coins (including the COIN fortune card) (SC 600)
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll = {Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY, Const.DICE_MONKEY,
                Const.DICE_COIN, Const.DICE_COIN, Const.DICE_PARROT, Const.DICE_SWORD};
        Card card = new Card(Const.CARD_COIN);
        InputStream in = new ByteArrayInputStream("1".getBytes());
        Player player = new Player(" ");
        int score = player.playTurn(dieRoll, card, new Scanner(in));
        Assert.assertEquals(600, score);
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
