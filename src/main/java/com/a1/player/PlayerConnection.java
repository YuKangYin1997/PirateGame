package com.a1.player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PlayerConnection {
    private Socket socket;
    private ObjectInputStream dIn;
    private ObjectOutputStream dOut;

    public PlayerConnection() {
        try {
            socket = new Socket("localhost", 9999);
            dIn = new ObjectInputStream(socket.getInputStream());
            dOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("player connection failed to open.");
            e.printStackTrace();
        }
    }

    public Player[] receivePlayers() {
        Player[] players = new Player[3];
        try {
            Player p = (Player) dIn.readObject();
            players[0] = p;
            p = (Player) dIn.readObject();
            players[1] = p;
            p = (Player) dIn.readObject();
            players[2] = p;
            return players;
        } catch (IOException e) {
            System.out.println("Score not received");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("class not found");
            e.printStackTrace();
        }
        return players;
    }


    public ObjectInputStream getdIn() {
        return dIn;
    }

    public ObjectOutputStream getdOut() {
        return dOut;
    }

    public int receiveTurnNo() {
        try {
            return dIn.readInt();
        } catch (IOException e) {
            System.out.println("Turn Number receive failed.");
            e.printStackTrace();
        }
        return 0;
    }

    public int[] receiverScores() {
        try {
            int[] scores = new int[3];
            for (int i = 0; i < 3; i++) {
                scores[i] = dIn.readInt();
            }
            return scores;
        } catch (Exception e) {
            System.out.println("Scores received failed");
            e.printStackTrace();
        }
        return null;
    }

    public void sendScore(int score) {
        try {
            dOut.writeInt(score);
            dOut.flush();
        } catch (IOException e) {
            System.out.println("score sent failed.");
            e.printStackTrace();
        }
    }

    public void sendIslandOfSkull(boolean islandOfSkull) {
        try {
            dOut.writeBoolean(islandOfSkull);
            dOut.flush();
        } catch (IOException e) {
            System.out.println("score island of skull failed.");
            e.printStackTrace();
        }
    }
}

