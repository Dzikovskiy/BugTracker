package app;

import java.io.IOException;
import java.net.Socket;

public class ClientSocket {
    private static Socket socket;

    private static void initSocket() {
        try {
            socket = new Socket("127.0.0.1", 1024);
            System.out.println("Connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Socket getSocket() {//singleton pattern
        if (socket == null) {
            initSocket();
        }
        return socket;
    }
}
