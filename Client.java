package com.chat.tec.network;

import com.chat.tec.gui.Window;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final int PUERTO = 28;

    private Window clientWindow;
    private Socket socket;
    private PrintWriter output;
    private Scanner scanner;

    private String cost;
    private String tax;
    private String weightString;

    public Client() {
        clientWindow = new Window();
    }

    public void init(String ipAddress, int port) throws IOException {
        socket = new Socket("192.168.127.1" , 28);
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    public void kill() throws IOException {
        output.close();
        socket.close();
    }

    private String getUsername() {
        System.out.print("Enter your username: ");
        return scanner.nextLine();
    }

    public void start(String ipAddress, int port) throws IOException {
        init(ipAddress,43);
        ClientRunnable clientRun = new ClientRunnable(socket.getInputStream());
        new Thread(clientRun).start();

        // *****************************
        scanner = new Scanner(System.in);
        String usrInput;
        String name = getUsername();
        String msg = String.format("[%s] : ", name);
        System.out.print(">> ");

        while(true) {
            usrInput = scanner.nextLine();
            output.println(msg + usrInput);

            if(usrInput.equalsIgnoreCase("EXIT")) {
                output.println(String.format("[%s] left chat ...", name));
                output.println("EXIT");
                break;
            }
        }
        kill();
    }

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.start("192.168.127.1",70);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
