package ru.rigidus.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Thread {

    private static List<ru.rigidus.chat.Connection> connections = Collections.synchronizedList(new ArrayList<ru.rigidus.chat.Connection>());
    private static volatile ServerSocket server;

    public void send(Message msg) {
        synchronized(connections) {
            Iterator<Connection> iter = connections.iterator();
            while(iter.hasNext()) {
                iter.next().out.println(msg.from + ": " + msg.text);
            }
        }
    }

    public void Server() {
        // do nothing
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(Cfg.port);
            while (true) {
                Socket socket = server.accept();
                ru.rigidus.chat.Connection con = new ru.rigidus.chat.Connection(socket, this);
                connections.add(con);
                con.start();
            }
        } catch (IOException e) {
            if (e.toString().equals("java.net.SocketException: Socket closed")) {
                System.out.println("Server`s socket terminated");
                //closeAll();
            } else {
                e.printStackTrace();
            }
        } finally {
            closeAll();
        }
    }

    public void killServerSocket() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeAll() {
        System.out.println("Server()->CloseAll()");
        try {
            synchronized (this.connections) {
                Iterator<ru.rigidus.chat.Connection> iter = this.connections.iterator();
                while (iter.hasNext()) {
                    iter.next().close();
                }
            }
        } catch (Exception e) {
            System.out.println("err: close connections");
        }

    }
}
