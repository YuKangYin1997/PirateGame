package com.a1.mock;

import com.a1.server.Server;

public class MockServer implements Runnable {

    private Server server;

    public MockServer() {
        server = new Server();
    }

    public Server getServer() {
        return server;
    }

    public void run() {
        try {
            server.acceptConnections();
        } catch (ClassNotFoundException e) {
            System.out.println("Mockserver accept connection fail!");
            e.printStackTrace();
        }
        server.gameLoop();
    }

    public void stop() {
        server.stop();
    }
}
