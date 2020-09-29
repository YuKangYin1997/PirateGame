package com.a1.player;

import com.a1.game.Game;
import com.a1.util.Card;
import com.a1.util.Const;
import com.a1.util.SeaBattleUtil;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Scanner;

public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    private int playerId;
    private String name;
    private int score;
    private Player[] players;

    public Player(String name) {
        this.name = name;
        score = 0;
        players = new Player[3];
    }

    /**
     * check if a player is dead with his/her dieRoll and card
     * @return if a player is dead, return true; otherwise, return false
     */
    public boolean playerIsDead(String[] dieRoll, Card card) {
        int skullNumber = Game.getSkullNum(dieRoll, card);
        if (skullNumber >= 4) {
            return true;
        } else if (skullNumber == 3) {
            if (card.getName().equals(Const.CARD_SORCERESS) && card.isUsed()) {
                return true;
            } else if (card.getName().equals(Const.CARD_SORCERESS) && !card.isUsed()) {
                return false;
            } else {  // without SORCERESS
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * play for a turn, a turn may consist of many rounds
     * @return the score this player get in this turn
     */
    public int playTurn(String[] dieRoll, Card card, Scanner sc) {
        int roundNo = 0;
        while (true) {
            roundNo++;
            System.out.println("\n\n*** Round " + roundNo + " ***");
            Game.printDieRoll(dieRoll);
            Game.printCard(card);

            boolean dead = playerIsDead(dieRoll, card);
            int skullNum = Game.getSkullNum(dieRoll, card);
            if (dead) {  // player is dead
                if (skullNum >= 4) {

                } else if (skullNum == 3) {
                    if (!card.getName().equals(Const.CARD_SORCERESS)) {
                        if (card.getName().equals(Const.CARD_TREASURE_CHEST)) {

                        } else if (SeaBattleUtil.hasSeaBattleCard(card)) {

                        } else {
                            System.out.println("Sorry, you die. You earned 0 points in your turn.");
                            return 0;
                        }
                    }
                }
            }
        }
    }
}