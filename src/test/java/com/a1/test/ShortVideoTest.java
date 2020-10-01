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

public class ShortVideoTest {
    @Test
    public void shortVideo1Test() {
        GameMode.mode = GameMode.JUNIT_TEST;
        String[] dieRoll1 = Game.generate2Sword2Monkey1Parrot3Skull();
        Card card1 = new Card(Const.CARD_COIN);
        Player player1 = new Player("One");
        player1.setPlayerId(1);
        InputStream in1 = new ByteArrayInputStream("1".getBytes());
        int score1 = player1.playTurn(dieRoll1, card1, new Scanner(in1));
        player1.setScore(score1);

        String[] dieRoll2 = Game.generate3Monkey2Sword2Parrot1Skull();
        Card card2 = new Card(Const.CARD_COIN);
        Player player2 = new Player("Two");
        player2.setPlayerId(2);
        InputStream in2 = new ByteArrayInputStream("1".getBytes());
        int score2 = player2.playTurn(dieRoll2, card2, new Scanner(in2));
        player2.setScore(score2);

        String[] dieRoll3 = Game.generate8Sword();
        Card card3 = new Card(Const.CARD_CAPTAIN);
        Player player3 = new Player("Three");
        player3.setPlayerId(3);
        InputStream in3 = new ByteArrayInputStream("1".getBytes());
        int score3 = player3.playTurn(dieRoll3, card3, new Scanner(in3));
        player3.setScore(score3);

        Player[] players = new Player[]{player1, player2, player3};
        Player winner = Game.getWinner(players);
        Assert.assertEquals(winner.getPlayerId(), player3.getPlayerId());
        Assert.assertTrue(Game.isEnded(players));
    }

    @Test
    public void shortVideo2Test() {
        GameMode.mode = GameMode.JUNIT_TEST;

        Player player1 = new Player("Tim");
        player1.setPlayerId(1);
        String[] dieRoll1_1 = Game.generate3Monkey2Sword2Parrot1Skull();
        Card card1_1 = new Card(Const.CARD_COIN);
        String[] dieRoll1_2 = Game.generate3Monkey2Sword2Parrot1Skull();
        Card card1_2 = new Card(Const.CARD_COIN);
        InputStream in1 = new ByteArrayInputStream("1 1".getBytes());
        Scanner sc1 = new Scanner(in1);
        int score1_1 = player1.playTurn(dieRoll1_1, card1_1, sc1);
        int score1_2 = player1.playTurn(dieRoll1_2, card1_2, sc1);
        player1.setScore(score1_1 + score1_2);

        Player player2 = new Player("Amy");
        player2.setPlayerId(2);
        String[] dieRoll2_1 = Game.generate7Coin1Skull();
        Card card2_1 = new Card(Const.CARD_COIN);
        String[] dieRoll2_2 = Game.generate7Coin1Skull();
        Card card2_2 = new Card(Const.CARD_COIN);
        InputStream in2 = new ByteArrayInputStream("1 1".getBytes());
        Scanner sc2 = new Scanner(in2);
        int score2_1 = player1.playTurn(dieRoll2_1, card2_1, sc2);
        int score2_2 = player1.playTurn(dieRoll2_2, card2_2, sc2);
        player2.setScore(score2_1 + score2_2);

        Player player3 = new Player("Jack");
        player3.setPlayerId(3);
        String[] dieRoll3_1 = Game.generate4Monkey2Sword2Parrot();
        Card card3_1 = new Card(Const.CARD_COIN);
        String[] dieRoll3_2 = Game.generate4Monkey2Sword2Parrot();
        Card card3_2 = new Card(Const.CARD_COIN);
        InputStream in3 = new ByteArrayInputStream("1 1".getBytes());
        Scanner sc3 = new Scanner(in3);
        int score3_1 = player1.playTurn(dieRoll3_1, card3_1, sc3);
        int score3_2 = player1.playTurn(dieRoll3_2, card3_2, sc3);
        player3.setScore(score3_1 + score3_2);

        Player[] players = new Player[]{player1, player2, player3};
        Player winner = Game.getWinner(players);
        Assert.assertEquals(winner.getPlayerId(), player2.getPlayerId());
        Assert.assertTrue(Game.isEnded(players));
    }
}
