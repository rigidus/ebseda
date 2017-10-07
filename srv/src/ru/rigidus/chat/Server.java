package ru.rigidus.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Server extends Thread {

    private static List<ru.rigidus.chat.Connection> connections =
            Collections.synchronizedList(new ArrayList<ru.rigidus.chat.Connection>());
    private static ServerSocket server;

    public void Server() {
        // do nothing
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(Cfg.port);
            while (true) {
                Socket socket = server.accept();
                ru.rigidus.chat.Connection con = new ru.rigidus.chat.Connection(socket);
                con.start();
            }
        } catch (IOException e) {
            if (e.toString().equals("java.net.SocketException: Socket closed")) {
                System.out.println("Server`s socket terminated");
            } else {
                e.printStackTrace();
            }
        } finally {
            closeAll();
        }
    }

    public void closeAll(){
        try {
            server.close();
            synchronized (this.connections) {
                Iterator<ru.rigidus.chat.Connection> iter = this.connections.iterator();
                while (iter.hasNext()) {
                    ((ru.rigidus.chat.Connection) iter.next()).close();
                }
            }
        } catch (Exception e) {
            System.out.println("err: close connections");
        }

    }
}
