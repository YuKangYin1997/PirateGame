package com.a1.player;

import com.a1.game.Game;
import com.a1.game.GameMode;
import com.a1.util.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Player {

    private int playerId;
    private String name;
    private int score;
    private PlayerConnection playerConnection;
    private Player[] players;

    public Player(String name) {
        this.name = name;
        score = 0;
        players = new Player[3];
    }

    public Player(int playerId, String name, int score) {
        this.playerId = playerId;
        this.name = name;
        this.score = score;
    }

    public void initializeGamePlayers() {
        for (int i = 0; i < 3; i++) {
            players[i] = new Player(" ");
        }
    }

    public void connectToClient() {
        playerConnection = new PlayerConnection();
        try {
            playerId = playerConnection.getdIn().readInt();
            System.out.println("Connected as " + playerId);

            // send this player to server
            playerConnection.sendPlayer(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void startGame() {
        players = playerConnection.receivePlayers();

        while (true) {
            int turnNo = playerConnection.receiveTurnNo();
            if (turnNo == -1)
                break;

            System.out.println("\n \n \n ********Turn Number " + turnNo + "********");
            int[] scores = playerConnection.receiverScores();

            for (int i = 0; i < 3; i++) {
                players[i].setScore(scores[i]);
            }

            printPlayerScores(players);

            String[] dieRoll = Game.generate8Dice();
            Card card = Game.drawCard();
            if (GameMode.mode.equals(GameMode.SHORT_GAME_1)) { // rigging game for short video 1
                if (playerId == 1) {
                    dieRoll = Game.generate2Sword2Monkey1Parrot3Skull();
                    card = new Card(Const.CARD_COIN);
                } else if (playerId == 2) {
                    dieRoll = Game.generate3Monkey2Sword2Parrot1Skull();
                    card = new Card(Const.CARD_COIN);
                } else if (playerId == 3) {
                    dieRoll = Game.generate8Sword();
                    card = new Card(Const.CARD_CAPTAIN);
                }
            } else if (GameMode.mode.equals(GameMode.SHORT_GAME_2)) { // rigging game for short video 2
                if (playerId == 1) {
                    dieRoll = Game.generate3Monkey2Sword2Parrot1Skull();
                    card = new Card(Const.CARD_COIN);
                } else if (playerId == 2) {
                    dieRoll = Game.generate7Coin1Skull();
                    card = new Card(Const.CARD_COIN);
                } else if (playerId == 3) {
                    dieRoll = Game.generate4Monkey2Sword2Parrot();
                    card = new Card(Const.CARD_COIN);
                }
            }
            Scanner sc = new Scanner(System.in);
            int earnedScore = playTurn(dieRoll, card, sc);

            System.out.println("\n");
            boolean islandOfSkull = false;
            if (Game.getSkullNum(dieRoll, card) >= 4 && !SeaBattleUtil.hasSeaBattleCard(card) && earnedScore < 0) { // island of skull
                islandOfSkull = true;
                System.out.println("Turn is End. You deduct " + (-earnedScore) + " points of other opponents in Island of Skull.");
            } else {
                System.out.println("Turn is End. You earn " + earnedScore + " points.");
            }
            System.out.println("\n");
            playerConnection.sendIslandOfSkull(islandOfSkull);
            playerConnection.sendScore(earnedScore);
        }
    }

    /**
     * print all 3 players' scores
     */
    public void printPlayerScores(Player[] players) {
        if (playerId == 1) {
            Game.printScore(players[0]);
            Game.printScore(players[1]);
            Game.printScore(players[2]);
        } else if (playerId == 2) {
            Game.printScore(players[1]);
            Game.printScore(players[0]);
            Game.printScore(players[2]);
        } else {
            Game.printScore(players[2]);
            Game.printScore(players[0]);
            Game.printScore(players[1]);
        }
    }

    public void returnWinner() {
        int[] scores = playerConnection.receiverScores();
        for (int i = 0; i < 3; ++i) {
            players[i].setScore(scores[i]);
        }
        printPlayerScores(players);
        Player winner = playerConnection.receivePlayer();
        if (playerId == winner.getPlayerId()) {
            System.out.println("You win!");
        } else {
            System.out.println("The winner is " + winner.getName());
        }
        System.out.println("Game over!");
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
                    if (roundNo == 1) {
                        if (SeaBattleUtil.hasSeaBattleCard(card)) {
                            int score = SeaBattleUtil.calSeaBattleBonus(dieRoll, card, true, new HashSet<Integer>());  // negative score
                            System.out.println("Sorry, you die. You lose sea battle, too. You lose " + (-score) + " points in your turn.");
                            return score;
                        } else {
                            int score = IslandOfSkullUtil.deductScore(dieRoll, card, sc);  // negative score
                            for (int i = 0; i < players.length; i++) {  // deduct other players' score
                                if (playerId != i) {
                                    players[i].setScore(Game.calScore(players[i].getScore(), score));
                                }
                            }
                            System.out.println("In the island of skull, you deduct " + (-score) + " points of your opponents.");
                            return score;
                        }
                    } else {
                        if (card.getName().equals(Const.CARD_TREASURE_CHEST)) {
                            if (card.getTreasureChest().size() == 0) {
                                System.out.println("Sorry, you die, and your treasure chest is empty. You get 0 point in your turn.");
                                return 0;
                            } else {
                                int score = TreasureChestUtil.rollToScore(dieRoll, card, new HashSet<Integer>());  // none-negative score
                                System.out.println("Sorry, you die. You get " + score + " points from your treasure chest in this turn.");
                                return score;
                            }
                        } else if (SeaBattleUtil.hasSeaBattleCard(card)) {
                            int score = SeaBattleUtil.calSeaBattleBonus(dieRoll, card, true, new HashSet<Integer>());  // negative score
                            System.out.println("Sorry, you die. You lose sea battle, too. You lose " + (-score) + " points in your turn.");
                            return score;
                        } else {
                            System.out.println("Sorry, you die. You earned 0 points in your turn.");
                            return 0;
                        }
                    }
                } else if (skullNum == 3) {
                    if (!card.getName().equals(Const.CARD_SORCERESS)) {
                        if (card.getName().equals(Const.CARD_TREASURE_CHEST)) {
                            if (card.getTreasureChest().size() == 0) {
                                System.out.println("Sorry, you die, and your treasure chest is empty. You get 0 point in your turn.");
                                return 0;
                            } else {
                                int score = TreasureChestUtil.rollToScore(dieRoll, card, new HashSet<Integer>());  // none-negative score
                                System.out.println("Sorry, you die. You get " + score + " points from your treasure chest in this turn.");
                                return score;
                            }
                        } else if (SeaBattleUtil.hasSeaBattleCard(card)) {
                            int score = SeaBattleUtil.calSeaBattleBonus(dieRoll, card, true, new HashSet<Integer>());  // negative score
                            System.out.println("Sorry, you die. You lose sea battle, too. You lose " + (-score) + " points in your turn.");
                            return score;
                        } else {
                            System.out.println("Sorry, you die. You earned 0 points in your turn.");
                            return 0;
                        }
                    }
                    if (card.getName().equals(Const.CARD_SORCERESS) && card.isUsed()) {
                        System.out.println("Sorry, you die. You earned 0 points in your turn.");
                        return 0;
                    }
                }
            } else {  // player is alive
                if (card.getName().equals(Const.CARD_SORCERESS) && !card.isUsed()) {
                    if (skullNum == 3) {
                        System.out.println("\nSorry, you accumulate 3 skulls, but you can use your Sorceress Card to re-roll one of them.");
                        SorceressUtil.usingSorceressCard(dieRoll, card, sc);  // containing re-roll
                        continue; // jump to next round
                    } else if (skullNum == 2 || skullNum == 1) {
                        System.out.println("Do you want to use your Sorceress Card in this turn?");
                        System.out.println("\nSelect an action: ");
                        System.out.println("(1) yes");
                        System.out.println("(2) no");
                        int act = sc.nextInt();
                        // Print act number in JUnit Test and Cucumber Test
                        if (GameMode.mode.equals(GameMode.JUNIT_TEST) || GameMode.mode.equals(GameMode.CUCUMBER_TEST)) {
                            System.out.println(act);
                        }
                        if (act == 1) {
                            SorceressUtil.usingSorceressCard(dieRoll, card, sc);
                            continue; // jump to next round
                        }
                    }
                } else if (card.getName().equals(Const.CARD_TREASURE_CHEST)) {
                    System.out.println("Do you want to use your Treasure Chest?");
                    System.out.println("\nSelect an action: ");
                    System.out.println("(1) yes");
                    System.out.println("(2) no");
                    int act = sc.nextInt();
                    // Print act Number in Junit Test and Cucumber Test
                    if (GameMode.mode.equals(GameMode.JUNIT_TEST) || GameMode.mode.equals(GameMode.CUCUMBER_TEST)) {
                        System.out.println(act);
                    }
                    if (act == 1) {
                        TreasureChestUtil.usingTreasureChest(dieRoll, card, sc);
                    }
                }
                // before a player choose to re-roll, check if re-rolled is allow
                boolean reRollAllowed = Game.isReRollAllowed(dieRoll, card);
                System.out.println("\nSelect an action: ");
                System.out.println("(1) Roll to Score");
                if (reRollAllowed) {
                    System.out.println("(2) Choose dice index to re-roll");
                }

                int act = sc.nextInt();
                // Print act Number in JUnit Test and Cucumber Test
                if (GameMode.mode.equals(GameMode.JUNIT_TEST) || GameMode.mode.equals(GameMode.CUCUMBER_TEST)) {
                    System.out.println(act);
                }

                if (act == 1) {  // roll to score
                    int score = Game.rollToScore(dieRoll, card);
                    System.out.println("Finish your turn, you earned " + score + " points.");
                    return score;
                } else if (act == 2 && reRollAllowed) {  // re-roll
                    boolean valid = false;
                    int[] reRollIndexes = {};
                    while (!valid) {
                        System.out.println("Select the die to re-roll(0,1,2...) ");
                        String[] strs = (sc.next()).replaceAll("\\s", "").split(",");

                        // Print input strs for JUnit Test and Cucumber Test
                        if (GameMode.mode.equals(GameMode.JUNIT_TEST) || GameMode.mode.equals(GameMode.CUCUMBER_TEST)) {
                            for (int i = 0; i < strs.length; i++) {
                                System.out.print(strs[i]);
                                if (i < strs.length - 1) {
                                    System.out.print(" ");
                                }
                            }
                            System.out.println();
                        }

                        reRollIndexes = Game.convertStringArrayToInt(strs);
                        valid = Game.isReRollIndexValid(dieRoll, reRollIndexes, card);
                        if (!valid) {
                            System.out.println("Sorry, the selected die are not valid...");
                        }
                    }

                    Game.reRollDice(dieRoll, reRollIndexes);
                    System.out.println("After Real Re-Roll:");
                    Game.printDieRoll(dieRoll);
                    // Rigging the game
                    if (GameMode.mode.equals(GameMode.JUNIT_TEST) || GameMode.mode.equals(GameMode.CUCUMBER_TEST)) {
                        String targetStr = sc.nextLine();
                        String[] targets = targetStr.trim().split(",");
                        Game.riggedReRollDice(dieRoll, reRollIndexes, targets);
                        System.out.println("After Rigging Re-Roll:");
                        Game.printDieRoll(dieRoll);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
//        GameMode.mode = GameMode.SHORT_GAME_1;
//        GameMode.mode = GameMode.SHORT_GAME_2;
        Scanner sc = new Scanner(System.in);
        System.out.print("What is your name ? ");
        String name = sc.next();
        Player player = new Player(name);
        player.initializeGamePlayers();
        player.connectToClient();
        player.startGame();
        player.returnWinner();
        sc.close();
    }
}
