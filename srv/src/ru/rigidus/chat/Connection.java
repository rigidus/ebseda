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
    private String name;

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
        try {
            System.out.println("Connection->run()");
            out.println("# enter your name here:");
            name = in.readLine();
            // TODO : send presence message to others
            System.out.println("# to all: " + name + " comes here");
            String msg;
            while (true) {
                msg = in.readLine();
                if(null == msg) {
                    System.out.println("# srv: " + name + " - connect lost");
                    break;
                } else {
                    if (msg.equals("exit")) break;
                    // TODO : send msg to others
                    System.out.println("<" + name + ">: " + msg);
                }
            }
            // TODO: send unpresense message to others
            System.out.println("# to all: " + name + " has left");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
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
