package com.a1.server;

import com.a1.game.Game;
import com.a1.player.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private int playerNumber;
    private int turnNumber;
    private ServerThread[] serverThreads;
    private Player[] players;

    public Server() {
        System.out.println("Starting Game Server");
        playerNumber = 0;
        turnNumber = 0;
        serverThreads = new ServerThread[3];
        players = new Player[3];
        for (int i = 0; i < players.length; ++i) {
            players[i] = new Player("");
        }
        try {
            serverSocket = new ServerSocket(9999);
        } catch (IOException e) {
            System.out.println("Server Failed to start.");
        }
    }

    public void acceptConnections() throws ClassNotFoundException {
        try {
            System.out.println("Waiting for players...");
            while (playerNumber < 3) {
                // accept connection from player(client)
                Socket socket = serverSocket.accept();
                playerNumber++;

                // create a thead, using this thread to communicating with player(client)
                ServerThread serverThread = new ServerThread(socket, playerNumber);

                // store this thread to gameServerThread array
                serverThreads[playerNumber - 1] = serverThread;

                // send playerId to player(client)
                serverThread.getdOut().writeInt(playerNumber);
                serverThread.getdOut().flush();

                // get player instance(name and scoreSheet) from player(client)
                Player player = serverThread.receivePlayer();
                System.out.println("Game Player " + player.getPlayerId() + " ~ " + player.getName() + " ~ has joined");

                // store gamePlayer to gamePlayer array
                players[playerNumber - 1] = player;
            }
            System.out.println("Three players have joined the game");

            // start serverThreads
            for (int i = 0; i < 3; ++i) {
                new Thread(serverThreads[i]).start();
            }
        } catch (IOException e) {
            System.out.println("Could not connect 3 game players");
            e.printStackTrace();
        }
    }

    public void gameLoop() {
        try {
            // send all three players' name and scoreSheet to all three players
            serverThreads[0].sendPlayers(players);
            serverThreads[1].sendPlayers(players);
            serverThreads[2].sendPlayers(players);

            boolean ended = false;
            while (!ended) {
                turnNumber++;
                // send the round number
                System.out.println("*****************************************");
                System.out.println("Turn number " + turnNumber);

                // player0
                serverThreads[0].sendTurnNo(turnNumber);
                serverThreads[0].sendScores(players);
                boolean islandOfSkull0 = serverThreads[0].receiveIslandOfSkull();
                int score0 = serverThreads[0].receiveScore(); // receive the player0's score
                if (score0 < 0 && islandOfSkull0) { // negative score due to island of skull
                    players[1].setScore(Game.calScore(players[1].getScore(), score0));  // deduct
                    players[2].setScore(Game.calScore(players[2].getScore(), score0));  // deduct
                } else {
                    players[0].setScore(Game.calScore(players[0].getScore(), score0));  // add
                }
                System.out.println("Player 1 finishes turn and now his/her score is " + players[0].getScore());

                serverThreads[1].sendTurnNo(turnNumber);
                serverThreads[1].sendScores(players);
                boolean islandOfSkull1 = serverThreads[1].receiveIslandOfSkull();
                int score1 = serverThreads[1].receiveScore();
                if (score1 < 0 && islandOfSkull1) { // negative score due to island of skull
                    players[0].setScore(Game.calScore(players[0].getScore(), score1));  // deduct
                    players[2].setScore(Game.calScore(players[2].getScore(), score1));  // deduct
                } else {
                    players[1].setScore(Game.calScore(players[1].getScore(), score1));  // add
                }
                System.out.println("Player 2 finishes turn and now his/her score is " + players[1].getScore());

                serverThreads[2].sendTurnNo(turnNumber);
                serverThreads[2].sendScores(players);
                boolean islandOfSkull2 = serverThreads[2].receiveIslandOfSkull();
                int score2 = serverThreads[2].receiveScore();
                if (score2 < 0 && islandOfSkull2) { // negative score due to island of skull
                    players[0].setScore(Game.calScore(players[0].getScore(), score2));  // deduct
                    players[1].setScore(Game.calScore(players[1].getScore(), score2));  // deduct
                } else {
                    players[2].setScore(Game.calScore(players[2].getScore(), score2));  // add
                }
                System.out.println("Player 3 finishes turn and now his/her score is " + players[2].getScore());

                ended = Game.isEnded(players);
            }

            serverThreads[0].sendTurnNo(-1);
            serverThreads[1].sendTurnNo(-1);
            serverThreads[2].sendTurnNo(-1);

            // send final scores
            serverThreads[0].sendScores(players);
            serverThreads[1].sendScores(players);
            serverThreads[2].sendScores(players);

            // get winner
            Player winner = Game.getWinner(players);
            System.out.println("The winner is " + winner.getName());
            for (ServerThread serverThread : serverThreads) {
                serverThread.sendPlayer(winner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        for (ServerThread serverThread : serverThreads) {
            serverThread.stop();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("server socket close fail");
            e.printStackTrace();
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.acceptConnections();
        server.gameLoop();
        server.stop();
    }
}

