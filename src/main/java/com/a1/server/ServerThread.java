package com.a1.server;

import com.a1.player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable{

    private Socket socket;
    private int playerId;
    private ObjectInputStream dIn;
    private ObjectOutputStream dOut;

    public ServerThread(Socket socket, int playerId) {
        this.socket = socket;
        this.playerId = playerId;
        try {
            this.dOut = new ObjectOutputStream(socket.getOutputStream());
            this.dIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("com.a1.util.Game Server Thread IO Created failed");
        }
    }

    public void sendPlayers(Player[] players) {
        try {
            for (Player player : players) {
                dOut.writeObject(player);
                dOut.flush();
            }
        } catch (IOException ex) {
            System.out.println("Score not sent");
            ex.printStackTrace();
        }
    }

    public ObjectInputStream getdIn() {
        return dIn;
    }

    public ObjectOutputStream getdOut() {
        return dOut;
    }

    /**
     * run function for threads --> main body of the thread will start here
     */
    public void run() {
        try {
            while (true) {
            }
        } catch (Exception ex) {
            {
                System.out.println("com.a1.util.Game Server Thread Run failed");
                ex.printStackTrace();
            }
        }
    }

    public void sendTurnNo(int turnNumber) {
        try {
            dOut.writeInt(turnNumber);
            dOut.flush();
        } catch (Exception e) {
            System.out.println("Turn number send failed");
            e.printStackTrace();
        }
    }

    public void sendScores(Player[] players) {
        try {
            for (Player player : players) {
                dOut.writeInt(player.getScore());
            }
            dOut.flush();
        } catch (Exception e) {
            System.out.println("Send players' scores failed");
            e.printStackTrace();
        }
    }

    public int receiveScore() {
        try {
            return dIn.readInt();
        } catch (Exception e) {
            System.out.println("Score receive failed.");
            e.printStackTrace();
        }
        return 0;
    }

    public boolean receiveIslandOfSkull() {
        try {
            return dIn.readBoolean();
        } catch (Exception e) {
            System.out.println("Island of Skull receive failed.");
            e.printStackTrace();
        }
        return false;
    }
}

