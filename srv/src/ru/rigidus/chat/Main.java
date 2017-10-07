package ru.rigidus.chat;

import java.io.IOException;

public class Main {

    private static Server srv;

    public static void main(String[] args) {
        try {
            srv = new Server();
            srv.start();
            System.out.println("Server started. Press ENTER for finish");
            System.in.read();
            srv.closeAll();
            srv.join();
            System.out.println("Server stopped. Quit");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
