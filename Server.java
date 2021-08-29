package com.chat.tec.network;

import com.chat.tec.gui.Window;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private final int PUERTO = 23;

    private ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clientsList;

    public Server() {
        clientsList = new ArrayList<>();
    }


    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(23);
        System.out.println("Server listening \uD83D\uDE80 on port: " + 23 + " ...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket, clientsList);
            clientsList.add(clientHandler);
            clientHandler.start(); // ⚠️start is the runnable method for threads!
            System.out.println("New client connected! ...");
        }
    }

    public double calculateCost(int cost, int tax, int weight) {
        double total = (cost * tax / 100) + (weight * 0.15d);
        return total;
    }


    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) {
        Server mainServer = new Server();
        try {
            mainServer.start(48);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
