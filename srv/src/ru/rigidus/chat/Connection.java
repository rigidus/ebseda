package ru.rigidus.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection extends Thread {

    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Connection (Socket socket) {
        this.socket = socket;
        try {
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }

    }

    @Override
    public void run() {
        // TODO
        System.out.println("Connection->run()");
    }

    public void close() {
        System.out.println("Connection->close();");
        try {
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("connection was not closed");
        }
    }
}
